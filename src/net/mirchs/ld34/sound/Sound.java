package net.mirchs.ld34.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.mirchs.ld34.Game;

public class Sound implements Runnable {

	public boolean playing;
	private String path;
	public boolean running = false;

	private AudioInputStream audioStream;
	private AudioFormat format;
	private DataLine.Info info;

	private Clip audioClip;

	private Thread thread;

	public Sound(String path) {
		this.path = path;
		File audioFile = new File(path);
		try {
			audioStream = AudioSystem.getAudioInputStream(audioFile);

			format = audioStream.getFormat();

			info = new DataLine.Info(Clip.class, format);

			audioClip = (Clip) AudioSystem.getLine(info);
		} catch (UnsupportedAudioFileException ex) {

		} catch (LineUnavailableException ex) {

		} catch (IOException ex) {

		}
		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void run() {
		while (running) {
				try {
					audioClip.open(audioStream);
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(IllegalStateException e) {
					
				}
			

			if (!playing) {
				audioClip.start();
				playing = true;
				System.out.println("da");
			}
		}
		try {
			thread.join();
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		audioClip.flush();
		audioClip.stop();
		audioClip.close();

	}

}
