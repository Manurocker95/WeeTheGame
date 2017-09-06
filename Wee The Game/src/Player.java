import java.awt.event.KeyEvent;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Jugador: Hereda de personaje. Se controla por las flechas del rat�n 								    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodr�guez Matesanz, Marcos L�pez Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Player extends Character 
{
	
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	Map m_map;					//Referencia al mapa			
	Point[] m_LastPositions;	//�ltimas posiciones por las que ha pasado el jugador. Los prisioneros se mueven por ahi.
	int m_iCurrentLives;		//Vidas actuales
	int m_iMaxLives = 3;		//M�ximo n�mero de vidas
	int m_iScore = 0;			//Puntuaci�n actual
	
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
	// 									**M�todos para cuando presionas teclas**										 //
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
	// 									**M�todo para el movimiento**													 //
	//===================================================================================================================//
    
    public void move()
    {
    	if( (getDx() != 0 || getDy() != 0) &&
    		super.getX()+super.getDx() >= 0 && super.getX()+super.getDx() < m_map.m_mapWidth &&
    		super.getY()+super.getDy() >= 0 && super.getY()+super.getDy() < m_map.m_mapHeight &&
    				m_map.matriz[super.getX()+super.getDx()][super.getY()+super.getDy()].walkable)	//Comprueba si puede pasar
    	{
    		addLastPosition(new Point(getX(), getY()));	//Si puede se mueve ahi y a�ade la actual a �ltima posicion
			super.setX(getX() + getDx());
			super.setY(getY() + getDy());
    	}
    }
 
	//===================================================================================================================//
	// 									**Properties para la puntuaci�n**												 //
	//===================================================================================================================//
    
    public void setScore(int addScore)
    {
    	m_iScore += addScore; 				//A�adimos puntuaci�n
    }
    
    public int getScore()
    {
    	return m_iScore;					//Devolvemos la puntuaci�n actual
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
	// 									**M�todos para quitar y a�adir vidas**											 //
	//===================================================================================================================//
    
    public void takeLives(int m_iLivesLess)
    {
    	m_iCurrentLives-= m_iLivesLess;		//Quitamos vidas
    	
    	if (m_iCurrentLives < 0)
    		m_iCurrentLives = 0;			//Con cero ya ha muerto, hehe
    }
    
    public void addLives(int m_iLivesMore)
    {
    	m_iCurrentLives+=m_iLivesMore;		//A�adimos vidas
    	if (m_iCurrentLives > m_iMaxLives)
    		m_iCurrentLives = m_iMaxLives;	//Si supera el m�ximo, se ponen al m�ximo
    }
 
	//===================================================================================================================//
	// 								**M�todo que comprueba que el jugador ha muerto**									 //
	//===================================================================================================================//
    
    public boolean hasDied()
    {
    	return (m_iCurrentLives == 0)? true : false;
    }
 
	//===================================================================================================================//
	// 						**M�todo que a�ade al array de �ltimas posiciones la posici�n deseada**						 //
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
