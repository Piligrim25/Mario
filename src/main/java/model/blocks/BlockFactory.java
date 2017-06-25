package model.blocks;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class BlockFactory {
	Image blocksImg = new Image(getClass().getResourceAsStream("blocks.png"));

	public Block createBlock(BlockType blockType, int x, int y, int blockSizeWidth, int blockSizeHeight) {
		Block block = null;

		switch (blockType) {
		case PLATFORM:
			block = new Platform(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(0, 0, 16, 16));
			break;
		case BRICK:
			block = new Brick(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(16, 0, 16, 16));
			break;
		case BONUS:
			block = new Bonus(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(384, 0, 16, 16));
			break;
		case PIPE_TOP:
			block = new PipeTop(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(0, 128, 32, 16));
			block.getBlockImage().setFitWidth(blockSizeWidth * 2);
			break;
		case PIPE_BOTTOM:
			block = new PipeBottom(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(0, 145, 32, 14));
			block.getBlockImage().setFitWidth(blockSizeWidth * 2);
			break;
		case INVISIBLE_BLOCK:
			block = new InvisibleBlock(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(0, 0, 16, 16));
			block.getBlockImage().setOpacity(0);
			break;
		case STONE:
			block = new Stone(x, y, blockSizeWidth, blockSizeHeight, blocksImg);
			block.getBlockImage().setViewport(new Rectangle2D(0, 16, 16, 16));
			break;
		}

		return block;
	}
}
