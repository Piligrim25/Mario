import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.blocks.Block;
import model.blocks.BlockFactory;
import model.blocks.BlockType;
import model.blocks.LevelData;
import model.creatures.Enemy;
import model.creatures.EnemyFactory;
import model.creatures.EnemyType;
import model.creatures.Goomba;
import model.creatures.KoopaTroopa;
import model.creatures.Character;
import model.creatures.Mushroom;
import view.View;
import controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Application {

	private BlockFactory blockFactory = new BlockFactory();
	private EnemyFactory enemyFactory = new EnemyFactory();
	
	private Controller controller;
	private View view;
	
	Image marioImg = new Image(getClass().getResourceAsStream("mario.png"));
	//Image marioImg = new Image(getClass().getResourceAsStream("scottpilgrim_multiple.png"));
	
	Image mushroomImg = new Image(getClass().getResourceAsStream("mushroom.png"));

	private static ArrayList<Block> platforms = new ArrayList<>();
	private static ArrayList<Block> bricks = new ArrayList<>();
	private static ArrayList<Block> bonuses = new ArrayList<>();
	private static ArrayList<Block> bonusesMushroom = new ArrayList<>();

	private static ArrayList<Enemy> enemyList = new ArrayList<>();

	private HashMap<KeyCode, Boolean> keys = new HashMap<>();

	private Image backgroundImg = new Image(getClass().getResourceAsStream("background.png"));
	private ImageView background;
	
	private static final int BLOCK_SIZE_WIDTH = 45;
	private static final int BLOCK_SIZE_HEIGHT = 45;
	private static final int MARIO_SIZE = 40;
	
	//private Sound mainTheme;
	private static Pane appRoot = new Pane();
	private static Pane gameRoot = new Pane();
	
	private Character player;
	private Mushroom mushroom;
	
	private int levelNumber = 0;
	private Text marioLives;
	private Text marioScore;
	private int score = 0;
	private Button restartButton;
	
	private int count = 3;
	private int columns = 3;
	private int width = 16;
	private int height = 16;
	private int offsetX = 96;
	private int offsetY = 32;
	private Point2D gravity = new Point2D(0, 0);
	private int lives = 3;
	private double animationDuration = 200.0;
	
	/*private int count = 8;
	private int columns = 8;
	private int width = 108;
	private int height = 140;
	private int offsetX = 0;
	private int offsetY = 0;
	private Point2D gravity = new Point2D(0, 0);
	private int lives = 3;
	private double animationDuration = 700.0;*/
	
	private double start = 0;
	private double finish = 0;
	private double timeOfImmortality = 1_000_000;
	
	/* @FXML Pane appRoot;
	 * @FXML Pane gameRoot;
	 */

	private void initContent() {
		background = new ImageView(backgroundImg);
		background.setFitHeight(14 * BLOCK_SIZE_WIDTH);
		background.setFitWidth(212 * BLOCK_SIZE_WIDTH);
		view = new View(blockFactory, platforms, bricks, bonuses, bonusesMushroom,
				BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, gameRoot, levelNumber);
		view.arrangeBlocks();
		addCharacters();
		controller = new Controller(BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, player,
				enemyFactory, enemyList, keys, platforms, gameRoot);
		
		// mainTheme = new Sound("resources\\mainTheme.mp3");
	}
	
	private void addInterface() {
		restartButton = new Button();
		restartButton.setText("Reload");
		restartButton.setOnAction(event -> {
			restart();
		});
		
		marioLives = new Text();
		marioLives.setFill(Color.grayRgb(80));
		marioLives.setFont(new Font("Roboto", 20));
		marioLives.setEffect(new GaussianBlur(1.5));
		marioLives.setX(500);
		marioLives.setY(30);
		
		marioScore = new Text();
		marioScore.setFont(new Font("Roboto", 20));
		marioScore.setFill(Color.grayRgb(80));
		marioScore.setEffect(new GaussianBlur(1.5));
		marioScore.setX(800);
		marioScore.setY(30);
		
		appRoot.getChildren().add(restartButton);
		appRoot.getChildren().add(marioLives);
		appRoot.getChildren().add(marioScore);
	}

	private void addEnemys() {
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 800, 300, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.KOOPA_TROOPA, 700, 300, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 1600, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 2230, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.KOOPA_TROOPA, 2450, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.KOOPA_TROOPA, 3900, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 5430, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.KOOPA_TROOPA, 5500, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 8000, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		enemyList.add(
				enemyFactory.createEnemy(EnemyType.GOOMBA, 7890, 500, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms));
		gameRoot.getChildren().addAll(enemyList);
	}

	public void addCharacters() {
		player = new Character(marioImg, width, height, lives, count, columns, offsetX, offsetY, gravity,
				animationDuration, platforms, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
		gameRoot.getChildren().add(player);
		player.setTranslateX(0);
		player.setTranslateY(400);
		player.setLives(3);
		
		movingListener();
		
		addEnemys();
	
		appRoot.getChildren().addAll(background, gameRoot);
		addInterface();
	}

	private void update() {
		marioLives.setText("");
		marioLives.setText("Lives: " + player.getLives());

		marioScore.setText("");
		marioScore.setText("Score: " + score);
		if (player != null) {
			
			//System.out.println(player.getTranslateX());
			controller.playerControll();
			enemyControll();
			
			collisionWithMushroomBlock();
			collisionWithBonusesBlock();
			collisionWithBrickBlock();
			checkForKillByTurtle();
			collisionWithMushroom();
			checkForWin();
			
			if (player.ifFalls()) {
				player.death();
				restart();
			}
		}
	}
	
	private void checkForWin() {
		if(player.getTranslateX() > 8889) {
			marioLives.setText("You win!");
			marioLives.setY(300);
			marioLives.setX(500);
			System.out.println("You win!");
			if(player.getTranslateX() > 8900) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				restart();
			}
		}
	}

	private void collisionWithBrickBlock() {
		if (!player.isCanJump()) {
			for (Node block : bricks) {
				if (block.getTranslateY() + BLOCK_SIZE_HEIGHT + 1 == player.getTranslateY()
						&& player.getTranslateX() < block.getTranslateX() + 20
						&& player.getTranslateX() >= block.getTranslateX() - 20
						&& player.isGrewUp()) {
					gameRoot.getChildren().remove(block);
					platforms.remove(block);
					block = null;
				}
			}
		}
	}
	
	private void collisionWithBonusesBlock() {
		if (!player.isCanJump()) {
			for (Node block : bonuses) {
				if (block.getTranslateY() + BLOCK_SIZE_HEIGHT + 1 == player.getTranslateY()
						&& player.getTranslateX() < block.getTranslateX() + 20
						&& player.getTranslateX() >= block.getTranslateX() - 20) {
					score += 100;
					//System.out.println(score);
				}
			}
		}
	}

	private void collisionWithMushroomBlock() {
		if (!player.isCanJump() && !player.isGrewUp()) {
			for (Block block : bonusesMushroom) {
				if (block != null
						&& block.getTranslateY() + BLOCK_SIZE_HEIGHT + 1 == player.getTranslateY()
						&& player.getTranslateX() < block.getTranslateX() + 20
						&& player.getTranslateX() >= block.getTranslateX() - 20) {
					if (mushroom == null) {
						mushroom = new Mushroom(mushroomImg, 256, 256, lives, 0, 0, 0, 0, gravity,
								0, platforms, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
						if (!mushroom.getIsOne()) {
							mushroom.setIsOne(true);
							mushroom.setTranslateX(block.getTranslateX());
							mushroom.setTranslateY(block.getTranslateY() - BLOCK_SIZE_HEIGHT);
							gameRoot.getChildren().add(mushroom);
							bonusesMushroom.remove(block);
							break;
						}
					}
				}
			}
		}
	}

	private void collisionWithMushroom() {
		if (mushroom != null) {
			if (mushroom.getGravity().getY() < 10)
				mushroom.setGravity(mushroom.getGravity().add(0, 1));
			mushroom.moveY((int) mushroom.getGravity().getY());
			if (mushroom.getIsOne())
				mushroom.move(mushroom.isRightSide() ? 2 : -2);
			if (mushroom.getTranslateX() <= -20 || mushroom.getTranslateY() > appRoot.getHeight()) { 
				gameRoot.getChildren().remove(mushroom);
			}
			if (player.getBoundsInParent().intersects(mushroom.getBoundsInParent())) {
				if (!player.isGrewUp())
					player.setTranslateY(player.getTranslateY() - player.getHeight());
				gameRoot.getChildren().remove(mushroom);
				mushroom = null;
				player.growUp();
			}
		}
	}

	private void enemyControll() {
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			if (enemy != null) {
				if (enemy.isAlive()) {
					
					// Gravitation
					if (enemy.getGravity().getY() < 10) // maybe add set
						enemy.setGravity(enemy.getGravity().add(0, 1)); 
					enemy.moveY((int) enemy.getGravity().getY());
					
					// animation
					enemy.getAnimation().play(); 
					
					// choose moving side
					if (enemy.isAlive()) { 
						if (!enemy.isRightSide()) {
							enemy.setScaleX(1);
							enemy.move(-1);
						} else {
							enemy.setScaleX(-1);
							enemy.move(1);
						}
					}
					
					// turtle choose moving side
					if (enemy instanceof KoopaTroopa && enemy.getLives() < 2) {
						if (enemy.isRightSide()) {
							enemy.move(3);
						} else {
							enemy.move(-3);
						}
					}
					
					// check for player killing enemy
					boolean checkForKill = checkForKill(enemy);
					if (checkForKill) {
						if (enemy instanceof Goomba) {
							enemy.setAlive(false);
						}
						enemyFactory.enemyDeath(enemy);
						enemy.setLives(enemy.getLives() - 1);
					}
				}
				
				// delete if enemy live map
				if (enemy.getTranslateX() <= -20 || enemy.getTranslateY() > appRoot.getHeight()) { 
					gameRoot.getChildren().remove(enemy);
					enemy = null;
				}
			}
		}
	}
	
	private boolean checkForKill(Enemy enemy) {
		boolean isDead = false;
		// collision player with enemy
		if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
			// player killing enemy
			if (player.getTranslateY() >= enemy.getTranslateY() - player.getHeight()
					&& player.getTranslateY() <= enemy.getTranslateY() - player.getHeight() + 5) { 

				if (player.getTranslateX() < enemy.getTranslateX()) {
					enemy.setRightSide(true);
				} else
					enemy.setRightSide(false);
				isDead = true;
				if (!player.isJumpOnes()) {
					player.setGravity(player.getGravity().add(0, -20));
					player.setJumpOnes(true);
				}
				
			} else if(finish - start > timeOfImmortality) {
				
				//System.out.println(finish - start);
				// enemy killing player
				start = System.currentTimeMillis() * 1000;
				if (player.isGrewUp()) {
					player.diminish();
					player.setTranslateY(player.getTranslateY() + MARIO_SIZE);
					player.setGrewUp(false);
					System.out.println("diminish");
				} else {
					if (player.getLives() == 1) {
						// TODO add game over
						System.out.println("Game over!");
						restart();
					} else {
						player.death();
					}
				}
				//System.out.println(player.getLives());
			}
			player.setCanJump(false);
		}
		finish = System.currentTimeMillis() * 1000;
		// check for player jump just ones
		if(player.getGravity().getY() == 0) 
			player.setJumpOnes(false);
		return isDead;
	}
	
	// camera
	private void movingListener() { 
		player.translateXProperty().addListener((obs, old, newValue) -> {
			int offset = newValue.intValue();
			int bound = (int) (appRoot.getWidth() * 0.6);
			if (offset > bound && offset < 
					(LevelData.levels[levelNumber][0].length() * BLOCK_SIZE_WIDTH) - bound) {
				gameRoot.setLayoutX(-(offset - bound));
				background.setLayoutX(-(offset - bound));
			}
		});
	}

	private void checkForKillByTurtle() {
		Enemy turtle = null;
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy enemy = enemyList.get(i);
			if (enemy instanceof KoopaTroopa && enemy.getLives() < 2) {
				turtle = enemy;
			}
			Enemy enemy1;
			for (int j = 0; j < enemyList.size(); j++) {
				enemy1 = enemyList.get(j);
				if (turtle != null
						&& enemy1 != null && enemy1.isAlive() && turtle != enemy1
						&& turtle.getTranslateY() >= enemy1.getTranslateY() - turtle.getHeight()
						&& turtle.getTranslateY() <= enemy1.getTranslateY() + turtle.getHeight()
						&& turtle.getTranslateX() < enemy1.getTranslateX() + enemy1.getWidth()
						&& turtle.getTranslateX() > enemy1.getTranslateX() - enemy1.getWidth()
						&& (!(enemy1 instanceof KoopaTroopa && enemy1.getLives() < 2))) {
					System.out.println("must kill");
					enemyFactory.enemyDeath(enemy1);
					if(enemy1 instanceof Goomba) {
						enemy1.setAlive(false);
					} else if(enemy1 instanceof KoopaTroopa) {
						enemy1.setAlive(true);
					}
				}
			}
		}
	}

	private void restart() {
		// TODO fix restart for less ram
		score = 0;
		platforms.clear();
		keys.clear(); 
		enemyList.clear();
		background = null;
		//mainTheme.mediaPlayer.stop();
		
		player.getAnimation().stop();
		player = null;
		
		gameRoot.getChildren().clear();
		appRoot.getChildren().clear();
		initContent();
		gameRoot.setLayoutX(0);
		background.setLayoutX(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initContent();
		Scene scene = new Scene(appRoot, 1000, 620);
		scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
			player.getAnimation().stop();
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
