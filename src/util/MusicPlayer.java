package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {
    private static Clip clip;
    private static long pausePosition = 0;
    private static boolean isPaused = false;

    public static void playMusic(String resourcePath) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }

            URL soundURL = MusicPlayer.class.getClassLoader().getResource(resourcePath);
            if (soundURL == null) {
                System.err.println("Music file not found: " + resourcePath);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            isPaused = false;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void togglePause() {
        if (clip != null) {
            if (isPaused) {
                clip.setMicrosecondPosition(pausePosition);
                clip.start();
                isPaused = false;
            } else {
                pausePosition = clip.getMicrosecondPosition();
                clip.stop();
                isPaused = true;
            }
        }
    }

    public static boolean isPaused() {
        return isPaused;
    }
}
