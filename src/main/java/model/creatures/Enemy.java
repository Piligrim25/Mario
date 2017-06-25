package model.creatures;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.blocks.Block;

public abstract class Enemy extends Creature {

	private boolean rightSide;
	private ArrayList<Block> platforms;
	private int blockSizeWidth;
	private int blockSizeHeight;

	public Enemy(Image enemyImage, int width, int height, int lives, int count, int columns,
			int offsetX, int offsetY, Point2D gravity, int x, int y, ArrayList<Block> gamePlatforms,
			int gameBlockSizeWidth, int gameBlockSizeHeight, double animationDuration) {
		super(enemyImage, width, height, lives, count, columns, offsetX, offsetY, gravity, animationDuration);
		this.setTranslateX(x);
		this.setTranslateY(y);
		getImageView().setFitHeight(gameBlockSizeHeight);
		getImageView().setFitWidth(gameBlockSizeWidth);
		platforms = gamePlatforms;
		blockSizeWidth = gameBlockSizeWidth;
		blockSizeHeight = gameBlockSizeHeight;

		getChildren().add(getImageView());
	}

	public void move(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node platform : platforms) {
				if (this.getTranslateX() < platform.getTranslateX() + blockSizeWidth
						&& this.getTranslateX() > platform.getTranslateX() - blockSizeWidth
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

	public void moveY(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (Block platform : platforms) {
				if (getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingDown) {
						if (this.getTranslateY() + this.getImageView().getFitHeight() == platform.getTranslateY()) {
							this.setTranslateY(this.getTranslateY() - 1);
							return;
						}
					} else {
						if (this.getTranslateY() == platform.getTranslateY() + blockSizeHeight) {
							this.setTranslateY(this.getTranslateY() + 1);
							gravity = new Point2D(0, 10);
							return;
						}
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			if (this.getTranslateY() > 640) {
				this.setImageView(null);
			}
		}
	}

	public void death(int fitHeight, int fitWidth, int translateY, int offsetX, int offsetY, int width, int height) {
		getAnimation().stop();
		
		Image enemyImage = getImageView().getImage();
		
		setImageView(null);
		setImageView(new ImageView(enemyImage));
		
		getImageView().setFitHeight(fitHeight);
		getImageView().setFitWidth(fitWidth);
		getImageView().setTranslateY(getImageView().getTranslateY() + translateY);
		getImageView().setViewport(new Rectangle2D(offsetX, offsetY, width, height));

		getChildren().clear();
		getChildren().add(getImageView());
	}

	public boolean isRightSide() {
		return rightSide;
	}

	public void setRightSide(boolean rightSide) {
		this.rightSide = rightSide;
	}
	
}
