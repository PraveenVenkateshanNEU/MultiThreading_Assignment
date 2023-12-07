/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package songs;

/**
 *
 * @author Admin
 */
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class MusicalThread {
    private static final CountDownLatch startLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> playNotes("C", "E", "G", "B", "C"));
        Thread thread2 = new Thread(() -> playNotes("D", "F", "A", "C"));

        thread1.start();
        thread2.start();

        startLatch.countDown();

        try {
            // Wait for both threads to finish
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void playNotes(String... notes) {
        try {
            startLatch.await(); // Wait for the start signal

            for (String note : notes) {
                playSingleNote(note);
                Thread.sleep(1000); // 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void playSingleNote(String note) {
        try {
            String filePath = "C:/Users/Admin/Documents/NetBeansProjects/MultiThreading_Assignment/src/songs/Sounds/"; // Replace with the actual file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.drain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

