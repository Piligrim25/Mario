import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Application {
    public static ArrayList<Block> platforms = new ArrayList<>();
    public static ArrayList<Block> bricks = new ArrayList<>();
    public static ArrayList<Block> bonuses = new ArrayList<>();
    public static ArrayList<Block> bonusesMushroom = new ArrayList<>();

    public static ArrayList<Enemy> enemyList = new ArrayList<>();

    private HashMap<KeyCode,Boolean> keys = new HashMap<>();

    Image backgroundImg = new Image(getClass().getResourceAsStream("background.png"));
    ImageView background;
    public static final int BLOCK_SIZE = 45;
    public static final int MARIO_SIZE = 40;
    Sound mainTheme;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();
    public Character player;

    /*public Enemy enemy;
    public Enemy enemy2;
    public Enemy enemy3;*/
    public Mushroom mushroom;
    int levelNumber = 0;
    private int levelWidth;
    Text marioLives;
    /* @FXML
     Pane appRoot;
     @FXML
     Pane gameRoot;*/

    private void initContent(){
        background = new ImageView(backgroundImg);
        background.setFitHeight(14*BLOCK_SIZE);
        background.setFitWidth(212*BLOCK_SIZE);
        arrangeBlocks();
        addCharacters();
        //mainTheme = new Sound("resources\\mainTheme.mp3");
    }

    private void arrangeBlocks() {
        levelWidth = LevelData.levels[levelNumber][0].length()*BLOCK_SIZE;
        for(int i = 0; i < LevelData.levels[levelNumber].length; i++){
            String line = LevelData.levels[levelNumber][i];
            for(int j = 0; j < line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Block platformFloor = new Block(Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '2':
                        bricks.add(new Block(Block.BlockType.BRICK,j*BLOCK_SIZE,i*BLOCK_SIZE));
                        break;
                    case '3':
                        bonuses.add(new Block(Block.BlockType.BONUS,j*BLOCK_SIZE,i*BLOCK_SIZE));
                        break;
                    case '4':
                        Block stone = new Block(Block.BlockType.STONE,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '5':
                        Block PipeTopBlock = new Block(Block.BlockType.PIPE_TOP,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '6':
                        Block PipeBottomBlock = new Block(Block.BlockType.PIPE_BOTTOM,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                    case '7':
                        bonusesMushroom.add(new Block(Block.BlockType.BONUS,j*BLOCK_SIZE,i*BLOCK_SIZE));
                        break;
                    case '*':
                        Block InvisibleBlock = new Block(Block.BlockType.INVISIBLE_BLOCK,j * BLOCK_SIZE, i * BLOCK_SIZE);
                        break;
                }
            }
        }
    }

    private void addEnemys(){
        enemyList.add(new Enemy(0, 800, 500));
        enemyList.add(new Enemy(1, 700, 500));
        enemyList.add(new Enemy(0, 1600, 500));
        gameRoot.getChildren().addAll(enemyList);
    }

    public void addCharacters() {
        //enemy = new Enemy(0, 800, 500);
        //enemy2 = new Enemy(1, 700, 500);
        //enemy3 = new Enemy(0, 1600, 500);
        //mushroom = new Mushroom();
        addEnemys();
        player = new Character(0);
        player.setTranslateX(0);
        player.setTranslateY(400);
        player.setLives(3);
        movingListener();
        Button button = new Button();
        button.setText("Reload");
        button.setOnAction(event -> {
            restart();
        });
        marioLives = new Text();
        marioLives.setText("Lives: "+player.getLives());
        marioLives.setX(500);
        marioLives.setY(10);

        gameRoot.getChildren().add(player);
        /*gameRoot.getChildren().add(enemy);
        gameRoot.getChildren().add(enemy2);
        gameRoot.getChildren().add(enemy3);*/
        appRoot.getChildren().addAll(background, gameRoot);
        appRoot.getChildren().add(button);
        appRoot.getChildren().add(marioLives);
    }

    private void update() {
        marioLives.setText("");
        marioLives.setText("Lives: "+player.getLives());
        if (player != null) {
            playerControll();
            enemyControll();
            checkForMushroomBlock();
            checkForKillByTurtle();
            //System.out.println("lives: "+player.lives);
            if (player.ifFalls()) {
                //restart();
                //player.death();
                player.death();
                //player.imageView.setImage(null);
                player = null;
                player = new Character(0);
                gameRoot.getChildren().add(player);
            }
        }
    }

    private void checkForMushroomBlock() {
        if (!player.isCanJump()) {
            for (Node block : bonusesMushroom) {
                if (block.getTranslateY() + BLOCK_SIZE + 2 >= player.getTranslateY()
                        && block.getTranslateY() + BLOCK_SIZE <= player.getTranslateY()
                        && player.getTranslateX() < block.getTranslateX() + 20
                        && player.getTranslateX() >= block.getTranslateX() - 20) {
                    //block.setTranslateY(block.getTranslateY() - 10);
                    if (mushroom == null) {
                        mushroom = new Mushroom();
                        if (!mushroom.getIsOne()) {
                            mushroom.setIsOne(true);
                            mushroom.setTranslateX(block.getTranslateX());
                            mushroom.setTranslateY(block.getTranslateY() - MARIO_SIZE);
                            gameRoot.getChildren().add(mushroom);
                        }
                    }
                }
            }
        }
        collisionWithMushroom();
    }

    private void collisionWithMushroom() {
        if (mushroom != null) {
            if (mushroom.playerVelocity.getY() < 10)
                mushroom.playerVelocity = mushroom.playerVelocity.add(0, 1);
            mushroom.moveY((int) mushroom.playerVelocity.getY());
            if (mushroom.getIsOne())
                mushroom.moveX(mushroom.isRightSide() ? 2 : -2);
            if (mushroom.getTranslateX() <= 1)
                mushroom.delete();
            if (player.getTranslateY() < mushroom.getTranslateY() + player.getHeight()
                    && player.getTranslateY() > mushroom.getTranslateY() - player.getHeight()
                    && player.getTranslateX() < mushroom.getTranslateX() + MARIO_SIZE
                    && player.getTranslateX() > mushroom.getTranslateX() - MARIO_SIZE) {
                if (player.getHeight() != MARIO_SIZE*2)
                    player.setTranslateY(player.getTranslateY() - MARIO_SIZE);
                mushroom.delete();
                mushroom = null;
                player.grou();
            }
        }
    }

    private void enemyControll() {
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            if (enemy != null) {
                if (enemy.getIsAlive()) {
                    if (enemy.playerVelocity.getY() < 10)
                        enemy.playerVelocity = enemy.playerVelocity.add(0, 1);
                    enemy.moveY((int) enemy.playerVelocity.getY());

                    enemy.animation.play();
                    if (enemy.getTranslateX() <= 1) {
                        enemy.deleteEnemy();
                    } else if (!enemy.isKeepGoing()) {
                        if (!enemy.isRightSide()) {
                            enemy.setScaleX(1);
                            enemy.move(-1);
                        } else {
                            enemy.setScaleX(-1);
                            enemy.move(1);
                        }
                    }
                    if (enemy.isKeepGoing() && enemy.getDeadCount() >= 1) {
                        if (enemy.isRightSide()) {
                            enemy.move(3);
                        } else {
                            enemy.move(-3);
                        }
                    }
                    boolean checkForKill = checkForKill(enemy);
                    if (checkForKill && enemy.getDeadCount() < 1) {
                        enemy.death();
                        enemy.setAlive(false);
                        enemy.setDeadCount(enemy.getDeadCount() + 1);
                        //enemy.setKeepGoing(false);
                    }
                    if (checkForKill && enemy.getEnemyType() == 1) {
                        enemy.death();
                        enemy.setAlive(true);
                        enemy.setKeepGoing(true);
                    }
                }
            }
        }
    }

    private boolean checkForKill(Enemy enemy) {
        boolean isDead = false;
        if (player.getTranslateX() < enemy.getTranslateX() + enemy.getWidth()
                && player.getTranslateX() > enemy.getTranslateX() - enemy.getWidth()) {
            if (player.getTranslateY() >= enemy.getTranslateY() - player.getHeight() &&
                    player.getTranslateY() <= enemy.getTranslateY() - player.getHeight() + 5) {
                if (player.getTranslateX() < enemy.getTranslateX()) {
                    enemy.setRightSide(true);
                } else enemy.setRightSide(false);
                isDead = true;
                player.playerVelocity = player.playerVelocity.add(0, -20);
            }

            if (player.getTranslateY() > enemy.getTranslateY() - player.getHeight() + 5 &&
                    player.getTranslateY() < enemy.getTranslateY() + player.getHeight()) {
                if (player.getCharacterType() == 1) {
                    //player.death();
                    double translateX = player.getTranslateX();
                    double translateY = player.getTranslateY();
                    //player.death();
                    player.imageView.setImage(null);
                    player = null;
                    player = new Character(0);
                    player.setTranslateX(translateX);
                    player.setTranslateY(translateY + MARIO_SIZE);
                    movingListener();
                    gameRoot.getChildren().add(player);
                    //player.setDieOnes(true);
                    //player.setAlive(true);
                    player.setCharacterType(1);
                    player.setDeadCount(player.getDeadCount()+1);
                } else if (/*!player.getIsAlive() && */player.getDeadCount() == 0 /*&& player.isDieOnes()*/) player.death();
                player.setCanJump(false);
                player.setCharacterType(0);
            } else {
                player.setDeadCount(0);
                player.setAlive(true);
                player.setDieOnes(false);
            }
        }
        return isDead;
    }

    private void movingListener() {
        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
                background.setLayoutX(-(offset - 640));
            }
        });
    }

    private void checkForKillByTurtle() {
        Enemy turtle = null;
        for (int i = 0; i < enemyList.size(); i++) {
            Enemy enemy = enemyList.get(i);
            if (enemy.getEnemyType() == 1 && enemy.getDeadCount() >= 1) {
                turtle = enemy;
            }
            Enemy enemy1;
            for (int j = 0; j < enemyList.size(); j++) {
                enemy1 = enemyList.get(j);
                if (turtle != null && turtle != enemy1 && turtle != enemy
                        && turtle.getTranslateY() >= enemy1.getTranslateY() - turtle.getHeight()
                        && turtle.getTranslateY() <= enemy1.getTranslateY() + turtle.getHeight()
                        && turtle.getTranslateX() < enemy1.getTranslateX() + enemy1.getWidth()
                        && turtle.getTranslateX() > enemy1.getTranslateX() - enemy1.getWidth()
                        && turtle.getEnemyType() == 1) {
                    enemy1.death();
                    enemy1.setAlive(false);
                }
            }
        }
    }

    private void playerControll() {
        if(isPressed(KeyCode.UP) && player.getTranslateY()>=5){
            player.jumpPlayer();
            player.setCanJump(false);
        }
        if(isPressed(KeyCode.LEFT) && player.getTranslateX()>=5){
            player.setScaleX(-1);
            player.animation.play();
            player.moveX(-5);
        }
        if(isPressed(KeyCode.RIGHT) && player.getTranslateX()+40 <=levelWidth-5){
            player.setScaleX(1);
            player.animation.play();
            player.moveX(5);
        }
        if(player.playerVelocity.getY()<10){
            player.playerVelocity = player.playerVelocity.add(0,1);
        } else player.setCanJump(false);
        player.moveY((int)player.playerVelocity.getY());
    }

    private void restart(){
        platforms.clear();
        keys.clear();
        gameRoot.getChildren().clear();
        appRoot.getChildren().removeAll(gameRoot);
        gameRoot.getChildren().clear();
        //gameRoot.getChildren().removeAll();
        //mainTheme.getMediaPlayer().stop();
        initContent();
    }

    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(appRoot,1000,620);
        scene.setOnKeyPressed(event-> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
             player.animation.stop();
        });
        primaryStage.setTitle("Mario");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
