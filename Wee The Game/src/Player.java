import java.awt.event.KeyEvent;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Jugador: Hereda de personaje. Se controla por las flechas del ratón 								    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Player extends Character 
{
	
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	Map m_map;					//Referencia al mapa			
	Point[] m_LastPositions;	//Últimas posiciones por las que ha pasado el jugador. Los prisioneros se mueven por ahi.
	int m_iCurrentLives;		//Vidas actuales
	int m_iMaxLives = 3;		//Máximo número de vidas
	int m_iScore = 0;			//Puntuación actual
	
	//===================================================================================================================//
	// 											**Constructores**														 //
	//===================================================================================================================//
	
	public Player(Map map)
	{
    	super();
    	this.m_map = map;
    	m_LastPositions = new Point [5];
    	for(int i = 0; i < 5; i++)
    		m_LastPositions[i] = new Point(0, 0);
    	m_iCurrentLives = m_iMaxLives;   	   	
    }
    
    public Player(String image, int posX, int posY, int despX, int despY, Map map)
    {
    	super(image, posX, posY, despX, despY);
    	
    	this.m_map = map;
    	m_LastPositions = new Point [5];
    	for(int i = 0; i < 5; i++)
    		m_LastPositions[i] = new Point(0, 0, 0, true,0);
    	m_iCurrentLives = m_iMaxLives;
    	
    }
    
    public Player(String imagen, int posX, int posY, int despX, int despY, Map map, int numFrames, int frameSize, int id) 
    {
    	super(imagen, posX, posY, despX, despY, numFrames, frameSize, id);
    	
    	this.m_map = map;
    	m_LastPositions = new Point [5];
    	for(int i = 0; i < 5; i++)
    		m_LastPositions[i] = new Point(0, 0, 0, true,0);
    	m_iCurrentLives = m_iMaxLives;
        
    }
  
	//===================================================================================================================//
	// 									**Métodos para cuando presionas teclas**										 //
	//===================================================================================================================//
    
    public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	if (key == KeyEvent.VK_LEFT) super.setDxPlayer(-1);		//Movemos a la izquierda
    	if (key == KeyEvent.VK_RIGHT) super.setDxPlayer(1);		//Movemos a la derecha
    	if (key == KeyEvent.VK_UP) super.setDyPlayer(-1);		//Movemos hacia arriba
    	if (key == KeyEvent.VK_DOWN) super.setDyPlayer(1);		//Movemos hacia abajo
    }
    
    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
    	if (key == KeyEvent.VK_LEFT) super.setDxPlayer(0);		//Dejamos de mover
    	if (key == KeyEvent.VK_RIGHT) super.setDxPlayer(0);		//Dejamos de mover
    	if (key == KeyEvent.VK_UP) super.setDyPlayer(0);		//Dejamos de mover
    	if (key == KeyEvent.VK_DOWN) super.setDyPlayer(0);		//Dejamos de mover
    	Music walk = new Music("SFX/Walk.wav");					//Cuando paramos suena un sonido
    	walk.play();
    }
    
	//===================================================================================================================//
	// 									**Método para el movimiento**													 //
	//===================================================================================================================//
    
    public void move()
    {
    	if( (getDx() != 0 || getDy() != 0) &&
    		super.getX()+super.getDx() >= 0 && super.getX()+super.getDx() < m_map.m_mapWidth &&
    		super.getY()+super.getDy() >= 0 && super.getY()+super.getDy() < m_map.m_mapHeight &&
    				m_map.matriz[super.getX()+super.getDx()][super.getY()+super.getDy()].walkable)	//Comprueba si puede pasar
    	{
    		addLastPosition(new Point(getX(), getY()));	//Si puede se mueve ahi y añade la actual a última posicion
			super.setX(getX() + getDx());
			super.setY(getY() + getDy());
    	}
    }
 
	//===================================================================================================================//
	// 									**Properties para la puntuación**												 //
	//===================================================================================================================//
    
    public void setScore(int addScore)
    {
    	m_iScore += addScore; 				//Añadimos puntuación
    }
    
    public int getScore()
    {
    	return m_iScore;					//Devolvemos la puntuación actual
    }
    
	//===================================================================================================================//
	// 									**Properties para la vida**														 //
	//===================================================================================================================//
    
    public void setCurrentLives(int newLives)
    {
    	m_iCurrentLives = newLives;			//Asignamos las vidas
    }
    
    public int getCurrentLives()
    {
    	return m_iCurrentLives;				//Devolvemos las vidas actuales
    }
    
	//===================================================================================================================//
	// 									**Métodos para quitar y añadir vidas**											 //
	//===================================================================================================================//
    
    public void takeLives(int m_iLivesLess)
    {
    	m_iCurrentLives-= m_iLivesLess;		//Quitamos vidas
    	
    	if (m_iCurrentLives < 0)
    		m_iCurrentLives = 0;			//Con cero ya ha muerto, hehe
    }
    
    public void addLives(int m_iLivesMore)
    {
    	m_iCurrentLives+=m_iLivesMore;		//Añadimos vidas
    	if (m_iCurrentLives > m_iMaxLives)
    		m_iCurrentLives = m_iMaxLives;	//Si supera el máximo, se ponen al máximo
    }
 
	//===================================================================================================================//
	// 								**Método que comprueba que el jugador ha muerto**									 //
	//===================================================================================================================//
    
    public boolean hasDied()
    {
    	return (m_iCurrentLives == 0)? true : false;
    }
 
	//===================================================================================================================//
	// 						**Método que añade al array de últimas posiciones la posición deseada**						 //
	//===================================================================================================================//
    
    void addLastPosition(Point p)
    {
    	for(int i = 4; i > 0; i--)
    	{
    		m_LastPositions[i] = m_LastPositions[i-1];
    	}
    	m_LastPositions[0] = p;
    }
}
