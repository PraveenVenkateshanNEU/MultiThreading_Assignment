package songs;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * 
 *
 * @author Admin
 *
 */
public class FilePlayer {

    public void play(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception e) {
            System.out.println("Error with playing sound.");
            e.printStackTrace();
        }

    }
    private static final CountDownLatch startLatch = new CountDownLatch(1);

    private static void playNotes(String... notes) {
        try {
            startLatch.await(); // Wait for the start signal

            for (String note : notes) {
                playSingleNote(note);
                System.out.println(note);
                Thread.sleep(500); // Adjust the delay between notes as needed
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void playSingleNote(String note) {
        try {
            String filePath = "C:/Users/Admin/Documents/NetBeansProjects/MultiThreading_Assignment/src/songs/Sounds/" + note + ".wav"; // Replace with the actual file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.drain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        FilePlayer fp = new FilePlayer();

        Thread thread1 = new Thread(() -> playNotes("do", "mi", "sol", "si", "do-octave"));
        Thread thread2 = new Thread(() -> playNotes("re", "fa", "la", "do-octave"));

        thread1.start();
        thread2.start();

        Thread twinkleThread = new Thread(() -> playTwinkleTwinkle());
        twinkleThread.start();
        
        startLatch.countDown();

        try {
            // Wait for both threads to finish
            thread1.join();
            thread2.join();
            twinkleThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void playTwinkleTwinkle() {
        playNotes("do", "do", "sol", "sol", "la", "la", "sol", "fa", "fa", "mi", "mi", "re", "re", "do",
                "sol", "sol", "fa", "fa", "mi", "mi", "re", "sol", "sol", "fa", "fa", "mi", "mi", "re",
                "do", "do", "sol", "sol", "la", "la", "sol", "fa", "fa", "mi", "mi", "re", "re", "do");
    }
}

