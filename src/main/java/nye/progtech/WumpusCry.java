package nye.progtech;

import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class WumpusCry {
    public static void wumpuszCry() {
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("Wumpus.wav");
            if (inputStream != null) {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(inputStream));
            clip.start();
            Thread.sleep(5000);
            clip.stop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
