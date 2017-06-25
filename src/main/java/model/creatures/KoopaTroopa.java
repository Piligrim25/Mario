package model.creatures;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import model.blocks.Block;

public class KoopaTroopa extends Enemy {

	public KoopaTroopa(Image enemyImage, int width, int height, int lives, int count, int columns, int offsetX,
			int offsetY, Point2D gravity, int x, int y, ArrayList<Block> gamePlatforms, int gameBlockSizeWidth,
			int gameBlockSizeHeight, double animationDuration) {
		super(enemyImage, width, height, lives, count, columns, offsetX, offsetY, gravity, x, y, gamePlatforms,
				gameBlockSizeWidth, gameBlockSizeHeight, animationDuration);
	}
}
