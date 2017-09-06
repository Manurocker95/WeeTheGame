import java.awt.Image;
import javax.swing.ImageIcon;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Personaje: Hereda de Sprite. Todos los personajes heredan de esta clase. Controla el movimiento.	    *
 *  				Algunas clases tienen métodos únicos, tales como el jugador y su movimiento.						*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Character extends Sprite
{

	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
    private int m_dx;	//Desplazamiento en X
    private int m_dy;	//Desplazamiento en Y
    private int m_x;	//Posición X
    private int m_y;	//Posición Y

	//===================================================================================================================//
	// 											**Constructores**														 //
	//===================================================================================================================//
    
    public Character() //Asignamos las vartiables por defecto
    {
        super();
        
        ImageIcon ii = new ImageIcon("Graphics/mario25.png");
        super.setSprite(ii.getImage());		
        
        m_x = 0;
        m_y = 0;
        m_dx = 0;
        m_dy = 0;
    }

    public Character(String imagen, int posX, int posY, int despX, int despY) //Asignamos variables
    {
        
        ImageIcon ii = new ImageIcon(imagen);
        super.setSprite(ii.getImage());
        
        m_x = posX;
        m_y = posY;
        m_dx = despX;
        m_dy = despY;
    }

    public Character(String imagen, int posX, int posY, int despX, int despY, int numFrames, int frameSize, int id) //Asignamos variables
    {
    	super(imagen, numFrames, frameSize, id);   	
           
    	m_x = posX;
    	m_y = posY;
    	m_dx = despX;
    	m_dy = despY;
	  	super.m_frameSize = frameSize;
	  	super.m_numFrames = numFrames;
	  	super.m_id = id;
	  	super.setOffsets();
        
    }
    
	//===================================================================================================================//
	// 										**Método para mover un personaje**											 //
	//===================================================================================================================//
    
    public void move() 
    {
    	m_x += m_dx;	//Modificamos la posición X según el desplazamiento en X
    	m_y += m_dy;	//Modificamos la posición Y según el desplazamiento en Y
    }

	//===================================================================================================================//
	// 										**Properties para X e Y**													 //
	//===================================================================================================================//
    
    public int getX() 
    {
        return m_x;			//Devolvemos la posición X
    }

    public int getY() 
    {
        return m_y;			//Devolvemos la posición Y
    }
    
    public void setX(int i)
    {
    	m_x = i;			//Asignamos la posición X
    }
    
    public void setY(int i)
    {
    	m_y = i;			//Asignamos la posición Y
    }
    
	//===================================================================================================================//
	// 					**Método para establecer el X/Y de los prisioneros. Clase que hereda de ésta.					 //
	//===================================================================================================================//
    
    public void setXPrisionero(int i, Player jugador)
    {
    	m_x = i;										//Asignamos la posición X
    	if (jugador.m_state == spriteState.RIGHT)
    		m_currentFrame = 0;							//Si mira a la derecha el frame de la animación es 0
    	else if (jugador.m_state == spriteState.LEFT)
    		m_currentFrame = 3;							//Si mira a la izquierda el frame de la animación es 3
    }
    
    public void setYPrisionero(int i, Player jugador)
    {
    	m_y = i;										//Asignamos la posición X
    	if (jugador.m_state == spriteState.RIGHT)
    		m_currentFrame = 0;							//Si mira a la derecha el frame de la animación es 0
    	else if (jugador.m_state == spriteState.LEFT)
    		m_currentFrame = 3;							//Si mira a la derecha el frame de la animación es 3
    }

	//===================================================================================================================//
	// 								**Properties para el desplazamiento en X e Y**										 //
	//===================================================================================================================//
    
    public int getDx() 
    {	
        return m_dx;			//Devolvemos el desplazamiento en X
    }

    public int getDy() 
    {
        return m_dy;			//Devolvemos el desplazamiento en Y
    }
    
    public void setDx(int i)
    {
    	this.m_dx = i;			//Asignamos el desplazamiento en X
    }
    
    public void setDy(int i)
    {
    	this.m_dy = i;			//Asignamos el desplazamiento en Y
    }
  
	//===================================================================================================================//
	// 				**Método para establecer el desplazamiento X/Y del Jugador . Clase que hereda de ésta.				 //
    //						Asignamos los frames de la animación ya mire a derecha o izquierda							 //
	//===================================================================================================================//
    
    public void setDxPlayer(int i)
    { 
    	this.m_dx = i;				//Asignamos el desplazamiento en X
    	switch(i)					//Asignamos el frame según la tecla que haya pulsado el jugador
    	{
    		case 0: //No hay tecla pulsada (Suelta la tecla)
    			if(m_state == spriteState.RIGHT) m_currentFrame = 0;
    			else if(m_state == spriteState.LEFT) m_currentFrame = 3;
    			break;
    		
    		case 1: //Se pulsa la tecla Derecha del teclado
    			m_currentFrame = 1;
    			if(m_state != spriteState.RIGHT) m_state = spriteState.RIGHT;
    			break;
    			
    		case -1: //Se pulsa la tecla Izquierda del teclado
    			m_currentFrame = 4;
    			if(m_state != spriteState.LEFT) m_state = spriteState.LEFT;
    			break;
    	}
    }
    
    public void setDyPlayer(int i)
    { 
    	this.m_dy = i;				//Asignamos el desplazamiento en Y				
    	switch(i)					//Asignamos el frame según la tecla que haya pulsado el jugador
    	{
    		case 0: //No hay tecla pulsada (Suelta la tecla)
    			if(m_state == spriteState.RIGHT) m_currentFrame = 0;
    			else if(m_state == spriteState.LEFT) m_currentFrame = 3;
    			break;
    		
    		case 1: ////Se pulsa la tecla Derecha del teclado
    			m_currentFrame = 1;
    			if(m_state != spriteState.RIGHT) m_state = spriteState.RIGHT;
    			break;
    			
    		case -1: //Se pulsa la tecla Izquierda del teclado
    			m_currentFrame = 4;
    			if(m_state != spriteState.LEFT) m_state = spriteState.LEFT;
    			break;
    	}
    }
    
	//===================================================================================================================//
	// 								**Property para recoger desde Personaje la imagen**									 //
	//===================================================================================================================//
    
    public Image getImage() 
    {
        return super.getSprite();	//Devolvemos la imagen
    }

	//===================================================================================================================//
	// 					**Método que comprueba si ha colisionado este personaje con otro**								 //
	//===================================================================================================================//
    
    public boolean checkCollision(Character p)
    {
    	if(p.getX() == getX() && p.getY() == getY())
    	{
    		return true;		//Ha chocado
    	}
    	return false;			//No ha chocado
    }
}