import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music { // Clase con la que se gestiona la m�sica y los SFX del juago.
    private Clip clip; // Objeto clip que contendr� la m�sica.
    
    public Music(String fileName) { // Constructor de la clase Music
        
    	try { 	// Recogemos la ruta del audio y si existe la asignamos a un objeto de audio que a su vez asignamos al clip.
    			// En caso de que el archivo no existe o haya algun problema lanzamos una excepci�n con el mismo.
            File file = new File("Sounds/" + fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);

                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else { // Excepci�n en caso de que el archivo no exista.
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) { // Excepci�n en caso de que el archivo de audio est� corrompido.
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) { // Excepci�n en caso de que el archivo de audo no sea soportado.
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) { // Excepci�n en caso de que no se haya podido cargar.
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) { // Excepcion en caso de que no se pueda utulizar el archivo por que ya est� siendo usado.
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }
    
    public void play(){ // M�todo que reproduce una sola vez el clip.
        clip.setFramePosition(0); 
        clip.start();
    }
    public void loop(){ // M�todo que reproduce de forma continuada el clip.
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() throws InterruptedException{ // M�todo que detiene la reproduci�n del clip en caso de que sea necesario.
            clip.stop();
        }
    }