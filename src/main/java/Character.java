import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.blocks.Block;

public class Character extends Pane {
	Image marioImg = new Image(getClass().getResourceAsStream("mario.png"));
	ImageView imageView = new ImageView(marioImg);
	int count = 3;
	int columns = 3;
	int width = 16;
	int height = 16;
	public SpriteAnimation animation;
	public Point2D playerVelocity;
	private boolean canJump = true;
	int characterType;
	private int lives;
	boolean isAlive;
	public boolean dieOnes;
	public int deadCount;

	public Character(int characterType) {
		playerVelocity = new Point2D(0, 0);
		isAlive = true;
		this.characterType = characterType;
		imageView.setFitHeight(Game.MARIO_SIZE);
		imageView.setFitWidth(Game.MARIO_SIZE);
		if (characterType == 0) {
			int offsetX = 96;
			int offsetY = 32;
			imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
			animation = new SpriteAnimation(this.imageView, Duration.millis(200), count, columns, offsetX, offsetY,
					width, height);
		}
		if (characterType == 1) {
			animation = null;
			imageView.setImage(null);
			imageView = new ImageView(marioImg);
			int width = 32;
			int height = 32;
			int offsetX = 96;
			int offsetY = 0;
			imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
			imageView.setFitHeight(80);
			imageView.setFitWidth(80);
			animation = new SpriteAnimation(this.imageView, Duration.millis(200), count, columns, offsetX, offsetY,
					width, height);
		}
		getChildren().addAll(this.imageView);
	}

	public void grou() {
		getChildren().remove(this);
		animation.stop();
		animation = null;
		imageView.setImage(null);
		imageView = null;
		imageView = new ImageView(marioImg);
		int width = 16;
		int height = 32;
		int offsetX = 96;
		int offsetY = 0;
		imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		imageView.setFitHeight(Game.MARIO_SIZE * 2);
		imageView.setFitWidth(Game.MARIO_SIZE);
		animation = new SpriteAnimation(this.imageView, Duration.millis(200), count, columns, offsetX, offsetY, width,
				height);
		getChildren().addAll(this.imageView);
		setCharacterType(1);
	}

	public void diminish() {
		getChildren().remove(this);
		animation.stop();
		animation = null;
		imageView.setImage(null);
		imageView = null;
		setCharacterType(0);
		imageView = new ImageView(marioImg);
		this.setHeight(Game.MARIO_SIZE);
		int width = 16;
		int height = 16;
		int offsetX = 96;
		int offsetY = 32;
		imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		imageView.setFitHeight(Game.MARIO_SIZE);
		imageView.setFitWidth(Game.MARIO_SIZE);
		animation = new SpriteAnimation(this.imageView, Duration.millis(200), count, columns, offsetX, offsetY, width,
				height);
		this.imageView.setTranslateY(this.imageView.getTranslateY() - 10);
		this.playerVelocity.add(0, -10);
		this.setHeight(40);
		getChildren().addAll(this.imageView);
	}

	
	
	
	
	
	public void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node platform : Game.platforms) {
				if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingRight) {
						if (this.getTranslateX() + Game.MARIO_SIZE == platform.getTranslateX()) {
							this.setTranslateX(this.getTranslateX() - 1);
							return;
						}
					} else {
						if (this.getTranslateX() == platform.getTranslateX() + Game.BLOCK_SIZE_WIDTH) {
							this.setTranslateX(this.getTranslateX() + 1);
							return;
						}
					}
				}
			}
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
		}
	}

	public void moveY(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Block platform : Game.platforms) {
				if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingDown) {
						if (this.getTranslateY() + this.imageView.getFitHeight() == platform.getTranslateY()
								&& this.getTranslateY() + this.imageView.getFitHeight() <= platform.getTranslateY()) {
							this.setTranslateY(this.getTranslateY() - 1);
							canJump = true;
							return;
						}
					} else {
						if (this.getTranslateY() == platform.getTranslateY() + Game.BLOCK_SIZE_HEIGHT) {
							this.setTranslateY(this.getTranslateY() + 1);
							playerVelocity = new Point2D(0, 10);
							return;
						}
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			ifFalls();
			/*
			 * if((this.getTranslateX()>9180.0)){ Game.isFinish = true; }
			 */
		}
	}

	public boolean ifFalls() {
		boolean falls = false;
		if (this.getTranslateY() > 640) {
			/*
			 * this.setTranslateX(0); this.setTranslateY(400);
			 */
			// Game.gameRoot.setLayoutX(0);
			// new Sound ("resources\\falls.wav");

			// lives--;
			falls = true;
			deleteCharacter();
		}
		return falls;
	}

	public void deleteCharacter() {
		// this.imageView.setImage(null);
		this.imageView = null;
		Game.gameRoot.getChildren().remove(this);
		this.setAlive(false);
	}

	public void death() {
		if (imageView != null) {
			if (this.getCharacterType() == 1) {
				// this.setCharacterType(0);
				// deleteCharacter();
				// diminish();
			} else /* if (!dieOnes) */ {
				lives--;
				this.animation.stop();
				System.out.println("you'r die");
				dieOnes = true;
				int width = 16;
				int height = 16;
				int offsetX = 176;
				int offsetY = 32;
				imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
			}
			isAlive = false;
			setCanJump(false);
		}
	}

	public void jumpPlayer() {
		if (canJump) {
			playerVelocity = playerVelocity.add(0, -30);
			canJump = false;
			// new Sound ("resources\\jump.wav");
		}
	}

	public int getDeadCount() {
		return deadCount;
	}

	public void setDeadCount(int deadCount) {
		this.deadCount = deadCount;
	}

	public boolean isDieOnes() {
		return dieOnes;
	}

	public void setDieOnes(boolean dieOnes) {
		this.dieOnes = dieOnes;
	}

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public int getCharacterType() {
		return characterType;
	}

	public void setCharacterType(int characterType) {
		this.characterType = characterType;
	}
}
