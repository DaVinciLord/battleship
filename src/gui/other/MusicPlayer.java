package gui.other;

import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 

public class MusicPlayer implements LineListener {
     
    boolean playCompleted;
    boolean isPlaying;
    File audioFile;
    AudioInputStream audioStream;
    AudioFormat format;
    DataLine.Info info;
    Clip audioClip;
     

    public MusicPlayer(String audioFilePath) {
    	audioFile = new File(audioFilePath);
    	initiate();
    }

    private void initiate() {
    	isPlaying = false;
    	try {
	    	audioStream = AudioSystem.getAudioInputStream(audioFile);
	        format = audioStream.getFormat();
	        info = new DataLine.Info(Clip.class, format);
	        audioClip = (Clip) AudioSystem.getLine(info);
	        audioClip.addLineListener(this);
        
    	} catch (UnsupportedAudioFileException e) {
            System.out.println("Format audio non supporté.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Audio indisponible pour la lecture.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur dans la lecture de l'audio.");
            e.printStackTrace();
        }
    }
    
    
    public void start() {
    	if (isPlaying) {
    		System.out.print("La musique est déjà en cours de lecture.");
    	} else {
    		isPlaying = true;
    		try {
                
     
                audioClip.open(audioStream);
                 
                audioClip.start();
                
                 
                while (!playCompleted) {
                    // wait for the playback completes
                    Thread.sleep(1000);
                    
                }
                 
                audioClip.close();
                 
            } catch (LineUnavailableException e) {
                System.out.println("Audio indisponible pour la lecture.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Erreur dans la lecture de l'audio.");
                e.printStackTrace();
            } catch (InterruptedException e) {
            	System.out.println("Thread interrompu.");
    			e.printStackTrace();
    		}
    	}
    }
    
    
    public void stop() {
    }

    
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
 
    }
    
    
 
    public static void main(String[] args) {
        String audioFilePath = "C:\\Users\\Snake76930\\Dropbox\\Bataille Navale\\sounds\\main_music.wav";
        MusicPlayer player = new MusicPlayer(audioFilePath);
        player.start();
    }
 
}