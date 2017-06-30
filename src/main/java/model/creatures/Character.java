package model.creatures;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import model.blocks.Block;
import model.sounds.Sound;

public class Character extends Creature {
	private ArrayList<Block> platforms;
	private int blockSizeWidth;
	private int blockSizeHeight;
	private boolean canJump;
	private boolean isGrewUp;
	private boolean jumpOnes;

	public Character(Image creatureImage, int width, int height, int lives, int count, int columns, int offsetX,
			int offsetY, Point2D gravity, double animationDuration, ArrayList<Block> gamePlatforms,
			int gameBlockSizeWidth, int gameBlockSizeHeight) {
		super(creatureImage, width, height, lives, count, columns, offsetX, offsetY, gravity, animationDuration);
		getImageView().setFitHeight(gameBlockSizeHeight);
		getImageView().setFitWidth(gameBlockSizeWidth);
		platforms = gamePlatforms;
		blockSizeWidth = gameBlockSizeWidth;
		blockSizeHeight = gameBlockSizeHeight;
		isGrewUp = false;
		canJump = true;
		jumpOnes = false;
		getChildren().add(getImageView());
	}

	public void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node platform : platforms) {
				if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingRight) {
						if (this.getTranslateX() + blockSizeWidth == platform.getTranslateX()) {
							this.setTranslateX(this.getTranslateX() - 1);
							return;
						}
					} else {
						if (this.getTranslateX() == platform.getTranslateX() + blockSizeWidth) {
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
			for (Block platform : platforms) {
				if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingDown) {
						if (this.getTranslateY() + getImageView().getFitHeight() == platform.getTranslateY()) {
								//&& this.getTranslateY() + getImageView().getFitHeight() <= platform.getTranslateY()) {
							this.setTranslateY(this.getTranslateY() - 1);
							canJump = true;
							return;
						}
					} else {
						if (this.getTranslateY() == platform.getTranslateY() + blockSizeHeight) {
							this.setTranslateY(this.getTranslateY() + 1);
							setGravity(new Point2D(0, 10));
							return;
						}
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			// ifFalls();
			/*
			 * if((this.getTranslateX()>9180.0)){ Game.isFinish = true; }
			 */
		}
	}

	public void growUp() {
		getChildren().remove(getImageView());		
		getAnimation().stop();
		setAnimation(null);
		
		Image marioImg = getImageView().getImage();
		setImageView(null);
		setImageView(new ImageView(marioImg));
		
		int count = 3;
		int columns = 3;
		int width = 16;
		int height = 32;
		int offsetX = 96;
		int offsetY = 0;
		getImageView().setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		getImageView().setFitHeight(blockSizeHeight * 2);
		getImageView().setFitWidth(blockSizeWidth);
		setAnimation(new SpriteAnimation(getImageView(), Duration.millis(200), count, columns, offsetX, offsetY, width,
				height));
		getChildren().addAll(getImageView());
		isGrewUp = true;
		new Sound("Powerup.wav", 0.8);
	}

	public void diminish() {
		getChildren().remove(getImageView());
		getAnimation().stop();
		setAnimation(null);

		Image marioImg = getImageView().getImage();
		//setImageView(null);
		setImageView(new ImageView(marioImg));
		
		//this.setHeight(blockSizeHeight);
		int count = 3;
		int columns = 3;
		int width = 16;
		int height = 16;
		int offsetX = 96;
		int offsetY = 32;
		getImageView().setViewport(new Rectangle2D(offsetX, offsetY, width, height));
		getImageView().setFitHeight(blockSizeHeight);
		getImageView().setFitWidth(blockSizeWidth);
		setAnimation(new SpriteAnimation(getImageView(), Duration.millis(200), count, columns, offsetX, offsetY, width,
				height));
		//getImageView().setTranslateY(getImageView().getTranslateY() - 10);
		//getGravity().add(0, -10);
		//this.setHeight(40);
		getChildren().addAll(getImageView());
		isGrewUp = false;
		
		new Sound("Warp.wav", 0.8);
	}

	public void death() {
		if (getImageView() != null) {
			setLives(getLives() - 1);
			getAnimation().stop();
			System.out.println("you'r die");
			isGrewUp = false;
			int width = 16;
			int height = 16;
			int offsetX = 176;
			int offsetY = 32;
			getImageView().setViewport(new Rectangle2D(offsetX, offsetY, width, height));
			setCanJump(false);
		}
	}

	public void jumpPlayer() {
		if (canJump) {
			setGravity(getGravity().add(0, -30));
			canJump = false;
			new Sound ("Jump.wav", 0.8);
		}
	}

	public boolean ifFalls() {
		boolean falls = false;
		if (this.getTranslateY() > 640) {
			new Sound("Die.wav", 0.8);
			falls = true;
		}
		return falls;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public boolean isGrewUp() {
		return isGrewUp;
	}

	public void setGrewUp(boolean isGrewUp) {
		this.isGrewUp = isGrewUp;
	}

	public boolean isJumpOnes() {
		return jumpOnes;
	}

	public void setJumpOnes(boolean jumpOnes) {
		this.jumpOnes = jumpOnes;
	}
}
