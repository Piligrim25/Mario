package model.creatures;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import model.blocks.Block;

public class Mushroom extends Creature{

	private boolean rightSide;
	private ArrayList<Block> platforms;
	private int blockSizeWidth;
	private int blockSizeHeight;
    boolean isOne;

	public Mushroom(Image creatureImage, int width, int height, int lives, int count, int columns, int offsetX,
			int offsetY, Point2D gravity, double animationDuration, ArrayList<Block> gamePlatforms,
			int gameBlockSizeWidth, int gameBlockSizeHeight) {
		super(creatureImage, width, height, lives, count, columns, offsetX, offsetY, gravity, animationDuration);
		platforms = gamePlatforms;
		getImageView().setFitHeight(gameBlockSizeHeight);
		getImageView().setFitWidth(gameBlockSizeWidth);
		blockSizeWidth = gameBlockSizeWidth;
		blockSizeHeight = gameBlockSizeHeight;
		isOne = false;
		rightSide = true;
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
							setGravity(new Point2D(0, 10));
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
	
	public boolean isRightSide() {
        return rightSide;
    }
    public void setRightSide(boolean rightSide) {
        this.rightSide = rightSide;
    }
    public boolean getIsOne() {
        return isOne;
    }
    public void setIsOne(boolean one) {
        isOne = one;
    }
}
