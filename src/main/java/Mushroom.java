import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.blocks.Block;

public class Mushroom extends Pane {
    Image mushroomImg = new Image(getClass().getResourceAsStream("mushroom.png"));
    ImageView imageView = new ImageView(mushroomImg);
    public Point2D playerVelocity = new Point2D(0,0);
    boolean isOne = false;
    boolean rightSide = true;

    public Mushroom(){
        imageView.setFitHeight(Game.MARIO_SIZE);
        imageView.setFitWidth(Game.MARIO_SIZE);
        getChildren().addAll(this.imageView);
    }

    public void moveX(int value){
        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
            for (Node platform : Game.platforms) {
                if(/*this.getBoundsInParent().intersects(platform.getBoundsInParent())*/
                        this.getTranslateX() < platform.getTranslateX() + Game.BLOCK_SIZE_WIDTH
                                && this.getTranslateX() > platform.getTranslateX() - Game.BLOCK_SIZE_WIDTH
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

    public void moveY(int value){
        boolean movingDown = value >0;
        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :Game.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if(this.getTranslateY()+ Game.MARIO_SIZE == platform.getTranslateY()){
                            this.setTranslateY(this.getTranslateY()-1);
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == platform.getTranslateY()+ Game.BLOCK_SIZE_HEIGHT){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }
            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));
            /*if((this.getTranslateX()>9180.0)){
                Game.isFinish = true;
            }*/
        }
    }

    public void delete(){
        //imageView.setImage(null);
        this.imageView = null;
        Game.gameRoot.getChildren().remove(this);
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