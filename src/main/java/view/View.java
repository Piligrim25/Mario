package view;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

import model.blocks.Block;
import model.blocks.BlockFactory;
import model.blocks.BlockType;
import model.blocks.LevelData;

public class View {
	
	private BlockFactory blockFactory;

	private ArrayList<Block> platforms;
	private ArrayList<Block> bricks;
	private ArrayList<Block> bonuses;
	private ArrayList<Block> bonusesMushroom;
	
	private int BLOCK_SIZE_WIDTH;
	private int BLOCK_SIZE_HEIGHT;
	
	private Pane gameRoot;
	
	private int levelNumber;
	
	public View(BlockFactory blockFactory, ArrayList<Block> platforms, ArrayList<Block> bricks,
			ArrayList<Block> bonuses, ArrayList<Block> bonusesMushroom, int bLOCK_SIZE_WIDTH, int bLOCK_SIZE_HEIGHT,
			Pane gameRoot, int levelNumber) {
		super();
		this.blockFactory = blockFactory;
		this.platforms = platforms;
		this.bricks = bricks;
		this.bonuses = bonuses;
		this.bonusesMushroom = bonusesMushroom;
		BLOCK_SIZE_WIDTH = bLOCK_SIZE_WIDTH;
		BLOCK_SIZE_HEIGHT = bLOCK_SIZE_HEIGHT;
		this.gameRoot = gameRoot;
		this.levelNumber = levelNumber;
	}

	public void arrangeBlocks() {
		for (int i = 0; i < LevelData.levels[levelNumber].length; i++) {
			String line = LevelData.levels[levelNumber][i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					Block platformFloor = blockFactory.createBlock(BlockType.PLATFORM, j * BLOCK_SIZE_WIDTH,
							i * BLOCK_SIZE_HEIGHT, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(platformFloor);
					gameRoot.getChildren().add(platformFloor);
					break;
				case '2':
					Block brick = blockFactory.createBlock(BlockType.BRICK, j * BLOCK_SIZE_WIDTH, i * BLOCK_SIZE_HEIGHT,
							BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(brick);
					gameRoot.getChildren().add(brick);
					bricks.add(brick);
					break;
				case '3':
					Block bonus = blockFactory.createBlock(BlockType.BONUS, j * BLOCK_SIZE_WIDTH, i * BLOCK_SIZE_HEIGHT,
							BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(bonus);
					gameRoot.getChildren().add(bonus);
					bonuses.add(bonus);
					break;
				case '4':
					Block stone = blockFactory.createBlock(BlockType.STONE, j * BLOCK_SIZE_WIDTH, i * BLOCK_SIZE_HEIGHT,
							BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(stone);
					gameRoot.getChildren().add(stone);
					break;
				case '5':
					Block pipeTop = blockFactory.createBlock(BlockType.PIPE_TOP, j * BLOCK_SIZE_WIDTH,
							i * BLOCK_SIZE_HEIGHT, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(pipeTop);
					gameRoot.getChildren().add(pipeTop);
					break;
				case '6':
					Block pipeBottom = blockFactory.createBlock(BlockType.PIPE_BOTTOM, j * BLOCK_SIZE_WIDTH,
							i * BLOCK_SIZE_HEIGHT, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(pipeBottom);
					gameRoot.getChildren().add(pipeBottom);
					break;
				case '7':
					Block bonusMushroom = blockFactory.createBlock(BlockType.BONUS, j * BLOCK_SIZE_WIDTH,
							i * BLOCK_SIZE_HEIGHT, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(bonusMushroom);
					gameRoot.getChildren().add(bonusMushroom);
					bonusesMushroom.add(bonusMushroom);
					break;
				case '*':
					Block invisibleBlock = blockFactory.createBlock(BlockType.INVISIBLE_BLOCK, j * BLOCK_SIZE_WIDTH,
							i * BLOCK_SIZE_HEIGHT, BLOCK_SIZE_WIDTH, BLOCK_SIZE_HEIGHT);
					platforms.add(invisibleBlock);
					gameRoot.getChildren().add(invisibleBlock);
					break;
				}
			}
		}
	}
}
