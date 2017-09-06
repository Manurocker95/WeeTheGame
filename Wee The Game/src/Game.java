
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Juego: Hereda de JFrame y es la que a�ade cada escena a la pantalla de juego. 					    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodr�guez Matesanz, Marcos L�pez Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Game extends JFrame 
{
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	public static int WIDTH = 1184, HEIGHT=925; //Tama�o y altura de la pantalla de juego
	
	//===================================================================================================================//
	// 											**Constructor**															 //
	//===================================================================================================================//
	
    public Game() throws ParserConfigurationException, SAXException, IOException, InterruptedException, FontFormatException 
    {
    	//Inicializamos las variables necesarias para la pantalla:
    	
        setSize(WIDTH, HEIGHT); 			  // Establecemos el tama�o de la ventana.
        setResizable(true);				  // La ventana no es redimensionable.
        setTitle("Wee The Game");             // Ponemos t�tulo a la ventana
        setLocationRelativeTo(null);		  // Centramos la ventana en la pantalla.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Cuando se pulsa la x de la ventana, se sale del programa y se cierra.
        
    	//Inicializamos el controlador de escenas:
        
    	SceneManager m_sm = new SceneManager(); //Al tener Singleton el manager de escenas, solo lo instanciamos 1 vez
    	m_sm.game(this); 						//Asignamos este objeto al SceneManager
    	m_sm.loadScene(1);					    //Cargamos la primera escena: La portada, desde el SceneManager

    }

	//===================================================================================================================//
	// 					**M�todo que permite a�adir una escena quitando la que est� activa**							 //
	//===================================================================================================================//
    
    public void removeAllScenesAndAdd (Object obj)
    {
    	getContentPane().removeAll(); 				//Quitamos todas las escenas activas en el JFrame
    	add((Component) obj);						//A�adimos la escena que se nos pasa por par�metro desde SceneManager
    	validate();									//Validamos los gr�ficos para que haga el pintado de esa escena
    	((Component) obj).requestFocusInWindow();	//Centramos los KeyListeners para escuchar los Inputs de la nueva escena
    }
    
	//===================================================================================================================//
	// 					**M�todo main que se ejecuta al inicio del programa. Llama a Game**								 //
	//===================================================================================================================//
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() { 	// cogemos la cola de eventos que se est�n ejecutando y coloca al final una llamada a new Runnable, que es una clase con la clase run. 
            @Override								// Sobreescribimos el m�todo run, que es lo que ser� invocado al final de la cola de eventos.
            public void run() {
            	
            	Game ex; 							//Creamos un objeto de tipo Juego
				try {
					ex = new Game();				//Inicializamos el objeto de tipo Juego
					ex.setVisible(true);			//Hacemos visible el juego en JFrame para que se muestre por pantalla
				} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
                			
            }
        });
    }
}