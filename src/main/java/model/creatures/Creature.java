package model.creatures;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Creature extends Pane {

	private ImageView imageView;

	private boolean isAlive;
	private int width;
	private int height;
	private int lives;

	private SpriteAnimation animation;
	private int count;
	private int columns;
	private int offsetX;
	private int offsetY;

	public Point2D gravity;

	public Creature(Image creatureImage, int width, int height, int lives, int count, int columns, int offsetX,
			int offsetY, Point2D gravity, double animationDuration) {
		super();
		this.imageView = new ImageView(creatureImage);
		this.isAlive = true;
		this.width = width;
		this.height = height;
		this.lives = lives;
		this.animation = new SpriteAnimation(this.imageView, Duration.millis(animationDuration), count, columns,
				offsetX, offsetY, width, height);
		this.count = count;
		this.columns = columns;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.gravity = new Point2D(0, 0);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Point2D getGravity() {
		return gravity;
	}

	public void setGravity(Point2D gravity) {
		this.gravity = gravity;
	}

	public SpriteAnimation getAnimation() {
		return animation;
	}

	public void setAnimation(SpriteAnimation animation) {
		this.animation = animation;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

}
