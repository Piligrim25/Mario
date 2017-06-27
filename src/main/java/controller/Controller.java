package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.blocks.Block;
import model.blocks.LevelData;
import model.creatures.Character;
import model.creatures.Enemy;
import model.creatures.EnemyFactory;
import model.creatures.EnemyType;

public class Controller {
	
	private int BLOCK_SIZE_WIDTH;
	private int BLOCK_SIZE_HEIGHT;
	private Character player;
	private EnemyFactory enemyFactory;
	private ArrayList<Enemy> enemyList;
	private HashMap<KeyCode, Boolean> keys;
	private ArrayList<Block> platforms;
	private Pane gameRoot;
	

	public Controller(int bLOCK_SIZE_WIDTH, int bLOCK_SIZE_HEIGHT, Character player,
			EnemyFactory enemyFactory, ArrayList<Enemy> enemyList, HashMap<KeyCode, Boolean> keys,
			ArrayList<Block> platforms, Pane gameRoot) {
		super();
		BLOCK_SIZE_WIDTH = bLOCK_SIZE_WIDTH;
		BLOCK_SIZE_HEIGHT = bLOCK_SIZE_HEIGHT;
		this.player = player;
		this.enemyFactory = enemyFactory;
		this.enemyList = enemyList;
		this.keys = keys;
		this.platforms = platforms;
		this.gameRoot = gameRoot;
	}

	public void playerControll() {
		if ((isPressed(KeyCode.UP) || isPressed(KeyCode.W)) && player.getTranslateY() >= 5) {
			player.jumpPlayer();
			player.setCanJump(false);
		}
		if ((isPressed(KeyCode.LEFT) || isPressed(KeyCode.A)) && player.getTranslateX() >= 5) {
			player.setScaleX(-1);
			player.getAnimation().play();
			player.moveX(-5);
		}
		if ((isPressed(KeyCode.RIGHT) || isPressed(KeyCode.D)) && player.getTranslateX() + 40 <= 
				(LevelData.levels[0][0].length() * BLOCK_SIZE_WIDTH) - 5) {
			player.setScaleX(1);
			player.getAnimation().play();
			player.moveX(5);
		}
		if (isPressed(KeyCode.E) || isPressed(KeyCode.Q)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			Enemy e = null;
			if (isPressed(KeyCode.E))
				e = enemyFactory.createEnemy(EnemyType.GOOMBA, (int) (player.getTranslateX() + 100), 100,
					BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms);
			if (isPressed(KeyCode.Q))
				e = enemyFactory.createEnemy(EnemyType.KOOPA_TROOPA, (int) (player.getTranslateX() + 100), 100,
						BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT, platforms);
			enemyList.add(e);
			gameRoot.getChildren().add(e);
			//getChildren().add(e);
		}
		if (player.getGravity().getY() < 10) {
			player.setGravity(player.getGravity().add(0, 1));
		} else {
			player.setCanJump(false);
		}
		player.moveY((int) player.getGravity().getY());
	}
	
	private boolean isPressed(KeyCode key) {
		return keys.getOrDefault(key, false);
	}
}
