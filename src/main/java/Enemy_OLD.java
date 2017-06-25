import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.blocks.Block;

public class Enemy_OLD extends Pane {
    Image enemyImage = new Image(getClass().getResourceAsStream("enemy.png"));
    ImageView imageView = new ImageView(enemyImage);
    
    int count = 2;
    int columns = 2;
    int offsetX = 0;
    int offsetY = 0;
    
    public SpriteAnimation animation;    
    int width = 16;
    int height = 16;
    
    
    public Point2D playerVelocity = new Point2D(0,0);
    
    boolean isAlive;
    int deadCount;
    boolean keepGoing;
    int enemyType;
    boolean rightSide;

    public Enemy_OLD(int enemyType, int x, int y){
        imageView.setFitHeight(Game.MARIO_SIZE);
        imageView.setFitWidth(Game.MARIO_SIZE);
        isAlive = true;
        this.enemyType = enemyType;
        this.setTranslateX(x);
        this.setTranslateY(y);
        if (enemyType == 0){
            imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView, Duration.millis(500),count,columns,offsetX,offsetY,width,height);
        }
        if (enemyType == 1){
            imageView.setFitHeight(Game.MARIO_SIZE+10);
            int offsetX = 48;
            this.height = 22;
            imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
            animation = new SpriteAnimation(this.imageView,Duration.millis(200),count,columns,offsetX,offsetY,width,height);
        }
        getChildren().addAll(this.imageView);
    }

    public void move(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : Game.platforms) {
                if (this.getTranslateX() < platform.getTranslateX() + Game.BLOCK_SIZE_WIDTH
                        && this.getTranslateX() > platform.getTranslateX() - Game.BLOCK_SIZE_WIDTH
                        && this.getTranslateY() < platform.getTranslateY() + this.getHeight()
                        && this.getTranslateY() > platform.getTranslateY()) {
                    if (movingRight) {
                        rightSide = false;
                    } else {
                        rightSide = true;
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (rightSide ? 1 : -1));
        }
    }

    public void death() {
        animation.stop();
        switch (getEnemyType()){
            case 0:
                imageView.setImage(null);
                enemyImage = new Image(getClass().getResourceAsStream("enemyDead.png"));
                imageView = new ImageView(enemyImage);
                imageView.setFitHeight(10);
                imageView.setFitWidth(40);
                imageView.setTranslateY(imageView.getTranslateX()+30);
                break;
            case 1:
                imageView.setImage(null);
                imageView = new ImageView(enemyImage);
                int offsetX = 80;
                int offsetY = 5;
                int height = 12;
                imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
                imageView.setFitHeight(30);
                imageView.setFitWidth(40);
                break;
        }
        //deadMove(imageView);
        getChildren().addAll(this.imageView);
        //new Sound("resources\\kick.wav");
        /*try {
            wait(100, (int) Math.pow(10, 5));
            imageView.setImage(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

   /* public void deadMove(ImageView imageView) {
        if (getEnemyType() == 1) {
            imageView.setTranslateY(imageView.getTranslateY() - 5);
        }
    }*/

    public void moveY(int value){
        boolean movingDown = value > 0;
        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :Game.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if(this.getTranslateY()+ this.imageView.getFitHeight()/*Game.MARIO_SIZE*/ == platform.getTranslateY()){
                            this.setTranslateY(this.getTranslateY()-1);
                            return;
                        }
                    } else {
                        if(this.getTranslateY() == platform.getTranslateY()+ Game.BLOCK_SIZE_HEIGHT){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            if(this.getTranslateY()>640){
            /*this.setTranslateX(0);
            this.setTranslateY(400);
            Game.gameRoot.setLayoutX(0);*/
                this.imageView.setImage(null);
            }
        }
    }

    public void deleteEnemy(){
        this.imageView = null;
        Game.gameRoot.getChildren().remove(this);
        this.setAlive(false);
    }

    public boolean isRightSide() {
        return rightSide;
    }
    public void setRightSide(boolean rightSide) {
        this.rightSide = rightSide;
    }
    public boolean isKeepGoing() {
        return keepGoing;
    }
    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }
    public int getDeadCount() {
        return deadCount;
    }
    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean getIsAlive() {
        return isAlive;
    }
    public int getEnemyType() {
        return enemyType;
    }
}
