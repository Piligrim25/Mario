package model.creatures;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import model.blocks.Block;

public class EnemyFactory {
	Image enemyImage = new Image(getClass().getResourceAsStream("enemy.png"));

	public Enemy createEnemy(EnemyType enemyType, int x, int y, int blockSizeWidth, int blockSizeHeight,
			ArrayList<Block> platforms) {
		Enemy enemy = null;

		int width = 16;
		int height = 16;
		int count = 2;
		int columns = 2;
		int offsetX = 0;
		int offsetY = 0;
		int lives = 1;
		Point2D gravity = new Point2D(0, 0);
		double animationDuration = 500.0;

		switch (enemyType) {
		case GOOMBA:
			enemy = new Goomba(enemyImage, width, height, lives, count, columns, offsetX, offsetY, gravity, x, y,
					platforms, blockSizeWidth, blockSizeHeight, animationDuration);
			enemy.getImageView().setViewport(new Rectangle2D(0, 0, 16, 16));
			break;
		case KOOPA_TROOPA:

			height = 22;
			offsetX = 48;
			lives = 2;
			animationDuration = 200.0;

			enemy = new KoopaTroopa(enemyImage, width, height, lives, count, columns, offsetX, offsetY, gravity, x, y,
					platforms, blockSizeWidth, (int)(1.3 * blockSizeHeight), animationDuration);
			enemy.getImageView().setViewport(new Rectangle2D(0, 0, 16, 16));
			break;
		}

		return enemy;
	}

	public void enemyDeath(Enemy enemy) {
		int fitHeight = 0;
		int fitWidth = 40;
		int translateY = 0;
		int offsetX = 0;
		int offsetY = 0;
		int width = 16;
		int height = 16;
		if (enemy instanceof Goomba) {
			fitHeight = 16;
			translateY = 30;
			offsetX = 32;
			offsetY = 0;
		} else if (enemy instanceof KoopaTroopa) {
			fitHeight = 30;
			offsetX = 80;
			offsetY = 5;
			height = 12;
		}
		enemy.death(fitHeight, fitWidth, translateY, offsetX, offsetY, width, height);
	}
}
