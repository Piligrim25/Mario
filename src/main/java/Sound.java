
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sound{

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	MediaPlayer mediaPlayer;
	//double volume;
	
	public Sound(String url/*, double volume*/){
		//this.volume=volume;
		Media sound = new Media(getClass().getResource(url).toString());
		mediaPlayer = new MediaPlayer(sound);
		//mediaPlayer.setVolume(volume);
		mediaPlayer.play();
		if (url == "menu.wav"){
			mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         mediaPlayer.seek(Duration.ZERO);
		       }
		});
		}
	}

	/*public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}*/
}
