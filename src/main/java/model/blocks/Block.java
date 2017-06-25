package model.blocks;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Block extends Pane {
	ImageView blockImage;
	
	public Block(int x, int y, int blockSizeWidth, int blockSizeHeight, Image blocksImg) {
		setTranslateX(x);
        setTranslateY(y);
        blockImage = new ImageView(blocksImg);
        blockImage.setFitWidth(blockSizeWidth);
        blockImage.setFitHeight(blockSizeHeight);
        
        getChildren().add(blockImage);
	}

	public ImageView getBlockImage() {
		return blockImage;
	}

	public void setBlockImage(ImageView blockImage) {
		this.blockImage = blockImage;
	}
}
