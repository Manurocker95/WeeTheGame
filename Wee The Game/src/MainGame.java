import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//ActionListener nos permitirá responder a eventos que se producen.
/*======================================================================================================================//
 * 																													    *
 *  		Clase Escenario/Juego principal. Es donde se juega realmente. Todo el core del juego está aquí.			    *
 *  								Es un tipo de escena y tiene escuchadores de eventos y acciones.					*
 *  			El juego consiste en salvar a los prisioneros, dejándolos en las plataformas de salvamento.				*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class MainGame extends Scene implements ActionListener 
{

	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	private final int DELAY = 135;							//retraso para refrescar el JFrame
	
	static public enum Types {BASIC, VISUAL_LINE};			//Tipo de visualización
	static public Types actualType = Types.VISUAL_LINE;
	private static Random m_rnd = new Random();				//Random para generar números aleatorios
	
    private Timer m_timer;									//Timer, permite tener un contador en milisegundos para JFrame
    private Image m_currentTileset;							//imagen del tileset que se está utilizando actualmente en el mapa
    
    boolean m_enemyMovementReduction = true;				//El enemigo tiene una reducción de velocidad para dejar espacio al jugador
    
    private int m_cellSize = 37;							//Tamaño de las celdas. Ya no es fijo por si se desea cambiar en función del tile
    private int m_prisionerNumber = 4;						//Número de prisioneros
    private int m_enemyNumber = 6;   						//Número de enemigos
    private int m_currentMapID = 0;							//ID del mapa en el que se está jugando actualmente
    private int m_solvedPrisioners = 0;						//Número de prisioneros (Amigos) salvados
    private int m_round = 1;								//Cada vez que se repiten mapas la idea es que en un futuro se suba la ronda
    private int m_currentTilesetColumns;					//Número de columnas del tileset actual
    private int m_currentTilesetLines;						//Número de filas del tileset actual			
    
    private String [] offsets = new String [Game.WIDTH*Game.HEIGHT];			//Array de offsets que se guardan en función del tileset
    private String musicPath = "Sounds/music.wav";    							//Ruta de la música que suena mientras juegas								
    private String currentTilesetName = "";										//Nombre del tilesetActual
    private String m_patronEnemyGraphicPath = "Graphics/Sprites/Enemigos.png";	//Ruta de la imagen del enemigo por patrón
    private String m_wayEnemyGraphicPath = "Graphics/Sprites/Enemigos.png";		//Ruta de la imagen del enemigo por camino
    private String m_wallEnemyGraphicPath = "Graphics/Sprites/Enemigos.png";	//Ruta de la imagen del enemigo por paredes
    private String m_nodeEnemyGraphicPath = "Graphics/Sprites/EnemigoPro.png";	//Ruta de la imagen del enemigo por nodos
    private String m_prisionerGraphicPath = "Graphics/Sprites/weeOK.png";		//Ruta de la imagen de los prisioneros
    private String m_playerGraphicPath = "Graphics/Sprites/weeOK.png";			//Ruta de la imagen del jugador
    
    private Player m_player;				//Objeto del jugador
    private Prisioner [] prisioners;		//Array de objetos de los prisioneros
    private Enemy[] enemies;				//Array de objetos de los enemigos
    private Map m_map;						//Objeto del mapa

    private ArrayList <ArrayList> worldInfo;   				//Arraylist con la información del xml
    private ArrayList<Prisioner> m_alSolvedPrisioners;		//Arraylist de los prisioneros salvados
    
    public void setCeldaSize(int value){m_cellSize=value;}				//Property para establecer el tamaño de la celda
    public void setEnemyNumber(int value){m_prisionerNumber=value;}		//Property para establecer el numero de enemigos
    public void setPrisionerNumber(int value){m_enemyNumber=value;}		//Property para establecer el numero de prisioneros
       
    private TAdapter t;						//TAdapter de la clase
    private Color textColor;				//Color del texto
    private Font m_fFont;					//Fuente del texto
    private boolean m_invincible = false;	//Si el jugador es invencible
    private boolean m_activatePrisionerLife = false; //Si es true, cuando están huyendo contigo, si le tocan es como si murieras
    
	//===================================================================================================================//
	// 											**Constructor**														 //
	//===================================================================================================================//
    
    public MainGame(ArrayList <ArrayList> worldInfo) 
    {

    	//Inicializamos los valores necesarios
    	textColor = new Color(255,255,255);
    	m_fFont = new Font("Impact",Font.BOLD,20);
    	
    	m_map = new Map(32, 24);
    	super.setID(1); //id = 1 = juego
    	this.worldInfo = worldInfo;
    	setMapCurrentTiles(worldInfo.get(m_currentMapID)); //Cargamos los tiles según el currentMapID
    	m_rnd.setSeed(System.currentTimeMillis());

    	prisioners = new Prisioner[m_prisionerNumber];
    	m_alSolvedPrisioners = new ArrayList<Prisioner>();
    	enemies = new Enemy[m_enemyNumber];
    	
    	initializeCharas(m_currentMapID);
    	
    	t = new TAdapter();
    	setFoc(true);

    	m_timer = new Timer(DELAY, this); 	// Creamos un Timer, que cada DELAY milisegundos, lanzará una acción, y llamará al método actionPerformed para calcular movimientos y repintar.
    	m_timer.start();						// Activamos el Timer. 
    }


    @Override
    public void paintComponent(Graphics g) 
    {	// Método que será llamado cuando Java determina que hay que pintar el escenario.
        super.paintComponent(g);				// Llamamos al método de su padre...

        doDrawing(g);							// ... y añadimos nuestra función de pintado de elementos del escenario.

        Toolkit.getDefaultToolkit().sync();		// Forzamos el dibujado de todos los elementos de forma adecuada. Necesario para algunos sistemas.
    }
    
    private void resetGame()
    {
    	m_alSolvedPrisioners.clear();
    	setMapCurrentTiles(worldInfo.get(m_currentMapID));
    	setCharasPosition(m_currentMapID);
    }
    
    private void setHigherLevel()
    {
    	m_player.setScore(500);
    	m_currentMapID++;
    	if (worldInfo.size()>m_currentMapID)
    	{
    		setMapCurrentTiles(worldInfo.get(m_currentMapID));
    	}
    	else
    	{
    		//No more levels
    		m_currentMapID=0;
    		setMapCurrentTiles(worldInfo.get(m_currentMapID));
    	}
    	
    	setCharasPosition(m_currentMapID);
    }
    
    public void initializeCharas(int currentMapID)
    {
    	switch (currentMapID)
    	{
    		case 0:
				//Jugador
    			m_player = new Player(m_playerGraphicPath,29,8,0,0, m_map, 5, 37, 0);
				//Prisioneros
				prisioners[0] = new Prisioner(m_prisionerGraphicPath,11,16,0,0, m_map, 5, 37, 5);
				prisioners[1] = new Prisioner(m_prisionerGraphicPath,4,22,0,0, m_map, 5, 37, 10);
				prisioners[2] = new Prisioner(m_prisionerGraphicPath,23,15,0,0, m_map, 5, 37, 15);	
				prisioners[3] = new Prisioner(m_prisionerGraphicPath,29,22,0,0, m_map, 5, 37, 20);
				//Enemigos
				enemies[0] = new PatronEnemy(m_patronEnemyGraphicPath,4,8,0,0, m_map, 0, 3, 37, 0);
		    	enemies[1] = new PatronEnemy(m_patronEnemyGraphicPath,9,7,0,0, m_map, 1, 3, 37, 0);
		    	enemies[2] = new PatronEnemy(m_patronEnemyGraphicPath,25,7,0,0, m_map, 2, 3, 37, 0);
		    	enemies[3] = new WayEnemy(m_wayEnemyGraphicPath,21,22,0,0, m_map, 3, 37, 3);
		    	enemies[4] = new WallEnemy(m_wallEnemyGraphicPath,10,15,0,0, m_map, new Displacement(1, 0), 3, 37, 6);
		    	enemies[5] = new NodeEnemy(m_nodeEnemyGraphicPath,21,14,0,0, m_map, m_player, 3, 49, 0);
		    	break;
    		case 1:
				//Jugador
    			m_player = new Player(m_playerGraphicPath,29,8,0,0, m_map, 5, 37, 0);
				//Prisioneros
				prisioners[0] = new Prisioner(m_prisionerGraphicPath,0,13,0,0, m_map, 5, 37, 5);
				prisioners[1] = new Prisioner(m_prisionerGraphicPath,19,14,0,0, m_map, 5, 37, 10);
				prisioners[2] = new Prisioner(m_prisionerGraphicPath,26,14,0,0, m_map, 5, 37, 15);
				prisioners[3] = new Prisioner(m_prisionerGraphicPath,9,7,0,0, m_map, 5, 37, 20);
				
				//Enemigos
				enemies[0] = new PatronEnemy(m_patronEnemyGraphicPath,3,11,0,0, m_map, 3, 3, 37, 0);
		    	enemies[1] = new PatronEnemy(m_patronEnemyGraphicPath,14,7,0,0, m_map, 4, 3, 37, 0);
		    	enemies[2] = new PatronEnemy(m_patronEnemyGraphicPath,21,10,0,0, m_map, 5, 3, 37, 0);
		    	enemies[3] = new WayEnemy(m_wallEnemyGraphicPath,21,22,0,0, m_map, 3, 37, 3);
		    	enemies[4] = new WallEnemy(m_wallEnemyGraphicPath,10,15,0,0, m_map, new Displacement(1, 0), 3, 37, 6);
		    	enemies[5] = new NodeEnemy(m_nodeEnemyGraphicPath,21,14,0,0, m_map, m_player, 3, 49, 0);
    			break;
    	}
    }
    
    public void setCharasPosition(int currentMapID)
    {
    	int a = GetRandomInt(m_round);
    	
    	switch (currentMapID)
    	{
    		case 0:
    			//Jugador
    			m_player.setX(29);
    			m_player.setY(8);
    			
    			//Prisioneros
    			if (prisioners[0].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[0] = new Prisioner(m_prisionerGraphicPath,11,16,0,0, m_map, 5, 37, 5);
    			if (prisioners[1].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[1] = new Prisioner(m_prisionerGraphicPath,4,22,0,0, m_map, 5, 37, 10);
    			if (prisioners[2].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[2] = new Prisioner(m_prisionerGraphicPath,23,15,0,0, m_map, 5, 37, 15);
    			if (prisioners[3].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[3] = new Prisioner(m_prisionerGraphicPath,29,22,0,0, m_map, 5, 37, 20);
    			
    			//Enemies
    			enemies[0] = new PatronEnemy(m_patronEnemyGraphicPath,4,8,0,0, m_map, 0, 3, 37, 0);
    	    	enemies[1] = new PatronEnemy(m_patronEnemyGraphicPath,9,7,0,0, m_map, 1, 3, 37, 0);
    	    	enemies[2] = new PatronEnemy(m_patronEnemyGraphicPath,25,7,0,0, m_map, 2, 3, 37, 0);
    	    	enemies[3] = new WayEnemy(m_wayEnemyGraphicPath,21,22,0,0, m_map, 3, 37, 3);
    	    	enemies[4] = new WallEnemy(m_wallEnemyGraphicPath,10,15,0,0, m_map, new Displacement(1, 0), 3, 37, 6);
    	    	enemies[5] = new NodeEnemy(m_nodeEnemyGraphicPath,21,14,0,0, m_map, m_player, 3, 49, 0);
    			break;
    		case 1:
    			//Jugador
    			m_player.setX(3);
    			m_player.setY(21);
    			
    			//Prisioneros
    			if (prisioners[0].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[0] = new Prisioner(m_prisionerGraphicPath,0,13,0,0, m_map, 5, 37, 5);
    			if (prisioners[1].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[1] = new Prisioner(m_prisionerGraphicPath,19,14,0,0, m_map, 5, 37, 10);
    			if (prisioners[2].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[2] = new Prisioner(m_prisionerGraphicPath,26,14,0,0, m_map, 5, 37, 15);
    			if (prisioners[3].m_prisionerState != Prisioner.prisionerStates.FREE)
    				prisioners[3] = new Prisioner(m_prisionerGraphicPath,9,7,0,0, m_map, 5, 37, 20);
    			
    			//Enemies
    			enemies[0] = new PatronEnemy(m_patronEnemyGraphicPath,3,11,0,0, m_map, 3, 3, 37, 0);
    	    	enemies[1] = new PatronEnemy(m_patronEnemyGraphicPath,14,7,0,0, m_map, 4, 3, 37, 0);
    	    	enemies[2] = new PatronEnemy(m_patronEnemyGraphicPath,21,10,0,0, m_map, 5, 3, 37, 0);
    	    	enemies[3] = new WayEnemy(m_wayEnemyGraphicPath,21,22,0,0, m_map, 3, 37, 3);
    	    	enemies[4] = new WallEnemy(m_wallEnemyGraphicPath,10,15,0,0, m_map, new Displacement(1, 0), 3, 37, 6);
    	    	enemies[5] = new NodeEnemy(m_nodeEnemyGraphicPath,21,14,0,0, m_map, m_player, 3, 49, 0);
    			break;
    	}
    }
    
	public void setFoc(boolean focusable)
	{
		setFocusable(focusable);
		if (!focusable)
		{
			removeKeyListener(t);
		}
		else
		{
			addKeyListener(t);
		}
	}
	
    private void setMapCurrentTiles(ArrayList<String> data)
    {
    	System.out.println(data.get(7)); //Tile Offset Data
    	
    	offsets = data.get(9).split(",");
    	
    	currentTilesetName = data.get(6);
    	ImageIcon ii = new ImageIcon(currentTilesetName);	
    	m_currentTileset = ii.getImage();

    	m_currentTilesetColumns = Integer.parseInt(data.get(7));
    	m_currentTilesetLines = Integer.parseInt(data.get(8));
    	
    	
    	//Tienen el mismo tileset:
    	
    	if (m_currentMapID == 0 || m_currentMapID == 1)
    	{
    		for (int i = 0; i < offsets.length; i++)
	    	{	
	    		// Walls
	    		if (offsets[i].equals("1") || offsets[i].equals("2") || offsets[i].equals("3") ||
	    			offsets[i].equals("4") || offsets[i].equals("5") || offsets[i].equals("6") ||
	    			offsets[i].equals("7") || offsets[i].equals("8") || offsets[i].equals("9") ||
	    			offsets[i].equals("10") || offsets[i].equals("11") || offsets[i].equals("12") ||
	    			offsets[i].equals("13") || offsets[i].equals("14") || offsets[i].equals("15") ||
	    			offsets[i].equals("16") || offsets[i].equals("28") || offsets[i].equals("18") ||
	    			offsets[i].equals("19") || offsets[i].equals("20") || offsets[i].equals("21") ||
	    			offsets[i].equals("22") || offsets[i].equals("23") || offsets[i].equals("24") ||
	    			offsets[i].equals("25") || offsets[i].equals("230") || offsets[i].equals("27") ||
	    			offsets[i].equals("298") || offsets[i].equals("28") || offsets[i].equals("29") ||
	    			offsets[i].equals("27") || offsets[i].equals("28") || offsets[i].equals("29") || 
	    			offsets[i].equals("33") || offsets[i].equals("34") || offsets[i].equals("35") || 
	    			offsets[i].equals("48") || offsets[i].equals("49") || offsets[i].equals("50") || 
	    			offsets[i].equals("53") || offsets[i].equals("54") || offsets[i].equals("55") || 
	    			offsets[i].equals("68") || offsets[i].equals("69") || offsets[i].equals("70") || 
	    			offsets[i].equals("76") || offsets[i].equals("81") || offsets[i].equals("86") || 
	    			offsets[i].equals("79") || offsets[i].equals("84") || offsets[i].equals("89") || 
	    			offsets[i].equals("96") || offsets[i].equals("101") || offsets[i].equals("106") || 
	    			offsets[i].equals("99") || offsets[i].equals("104") || offsets[i].equals("109") ||
	    			offsets[i].equals("151") || offsets[i].equals("152") || offsets[i].equals("153") ||
	    			offsets[i].equals("154") || offsets[i].equals("155") || offsets[i].equals("156") ||
	    			offsets[i].equals("157") || offsets[i].equals("158") || offsets[i].equals("159") ||
	    			offsets[i].equals("160") || offsets[i].equals("161") || offsets[i].equals("162") ||
	    			offsets[i].equals("163") || offsets[i].equals("164") || offsets[i].equals("165") ||
	    			offsets[i].equals("166") || offsets[i].equals("167") || offsets[i].equals("168") ||
	    			offsets[i].equals("169") || offsets[i].equals("170") || offsets[i].equals("171") ||
	    			offsets[i].equals("172") || offsets[i].equals("173") || offsets[i].equals("174") ||
	    			offsets[i].equals("175") || offsets[i].equals("176") || offsets[i].equals("177") ||
	    			offsets[i].equals("178") || offsets[i].equals("179") || offsets[i].equals("180") ||
	    			offsets[i].equals("181") || offsets[i].equals("182") || offsets[i].equals("183") ||
	    			offsets[i].equals("184") || offsets[i].equals("185") || offsets[i].equals("186") ||
	    			offsets[i].equals("187") || offsets[i].equals("188") || offsets[i].equals("189") ||
	    			offsets[i].equals("190") || offsets[i].equals("191") || offsets[i].equals("192") ||
	    			offsets[i].equals("193") || offsets[i].equals("194") || offsets[i].equals("195") ||
	    			offsets[i].equals("196") || offsets[i].equals("197") || offsets[i].equals("198") ||
	    			offsets[i].equals("199") || offsets[i].equals("200") || offsets[i].equals("201"))
	    		{
	    			m_map.SetMapValues(i, 2, false, offsets[i], 32); //posicion, id = 2 => Muro, no se puede andar por ahí 
	    		}
	    		else if (offsets[i].equals("134"))
	    		{
	    			m_map.SetMapValues(i, 1, true, offsets[i], 32); //posicion, id = 2 => Muro, no se puede andar por ahí 
	    		}
	    		else
	    		{
	    			m_map.SetMapValues2(i,offsets[i]);
	    		}
	    	}
    	}
    }
    
    private int[] setTilesetCoord(Point p)
    {
    	int[] coord = new int [2];
    	
    	coord[0] = (p.offset-1)%5;
    	coord[1] = (p.offset-1)/5;
    	
    	
    	
    	return coord;
    }
    
    // Método para calcular la posición del SpriteSheet que hay que pintar en el frame correspondiente del jugador.
    private int[] setPlayerSheetCoord(Player j){
    	
    	int [] coord = new int [2];
    	
    	coord[0] = (j.getOffsets()[j.getCurrentFrame()]) % j.getNumFrames();
    	coord[1] = (j.getOffsets()[j.getCurrentFrame()]) / j.getNumFrames();
    	
    	return coord;
    }
    
    // Método para calcular la posición del SpriteSheet que hay que pintar en el frame correspondiente de los prisioneros.
    private int[] setPrisioneroSheetCoord(Prisioner p){
    	
    	int [] coord = new int [2];
    	
    	coord[0] = (p.getOffsets()[p.getCurrentFrame()]) % p.getNumFrames();
    	coord[1] = (p.getOffsets()[p.getCurrentFrame()]) / p.getNumFrames();
    	
    	return coord;
    }
   
    // Método para calcular la posición del SpriteSheet que hay que pintar en el frame correspondiente de los enemigos.
    private int[] setEnemigosSheetCoord(Enemy e){
   	
    	int [] coord = new int [2];
   	
	   	coord[0] = (e.getOffsets()[e.getCurrentFrame()]) % e.getNumFrames();
	   	coord[1] = (e.getOffsets()[e.getCurrentFrame()]) / e.getNumFrames();
   	
	   	return coord;
    }
    
    private void doDrawing(Graphics g)
    {
    	Graphics2D g2d = (Graphics2D) g;
    	
        for(Point p : m_map.GetMatrizUnidimensional())
        {
        	g2d.drawImage(m_currentTileset, p.x*m_cellSize, p.y*m_cellSize, p.x*m_cellSize+m_cellSize, p.y*m_cellSize+m_cellSize, setTilesetCoord(p)[0]*m_cellSize, setTilesetCoord(p)[1]*m_cellSize, setTilesetCoord(p)[0]*m_cellSize+m_cellSize, setTilesetCoord(p)[1]*m_cellSize+m_cellSize,this);
        	
        }
    	
        for(Prisioner p : prisioners){ // Pintado de los prisioneros de acuerdo a su posicion en el escenario y a su frame actual.
           
        	g2d.drawImage(p.getSprite(), (p.getX()*m_cellSize), ((p.getY()*m_cellSize)), (p.getX()*m_cellSize+m_cellSize), (p.getY()*m_cellSize+m_cellSize), (setPrisioneroSheetCoord(p)[0]*p.getFrameSize()), (setPrisioneroSheetCoord(p)[1]*p.getFrameSize()), (setPrisioneroSheetCoord(p)[0]*p.getFrameSize()+p.getFrameSize()), (setPrisioneroSheetCoord(p)[1]*p.getFrameSize()+p.getFrameSize()),this);
        	
    	}
        for(Enemy e : enemies){ // Pintado de los enemigos de acuerdo a su posicion en el escenario y a su frame actual.
            //g2d.drawImage(e.getImage(), e.getX()*CELDA_SIZE, e.getY()*CELDA_SIZE, this);
            g2d.drawImage(e.getSprite(), (e.getX()*m_cellSize), ((e.getY()*m_cellSize)), (e.getX()*m_cellSize+m_cellSize), (e.getY()*m_cellSize+m_cellSize), (setEnemigosSheetCoord(e)[0]*e.getFrameSize()), (setEnemigosSheetCoord(e)[1]*e.getFrameSize()), (setEnemigosSheetCoord(e)[0]*e.getFrameSize()+e.getFrameSize()), (setEnemigosSheetCoord(e)[1]*e.getFrameSize()+e.getFrameSize()),this);
        }
        
        // Pintado del jugador de acuerdo a su posicion en el escenario y a su frame actual.
        //g2d.drawImage(jugador.getImage(), jugador.getX()*CELDA_SIZE, jugador.getY()*CELDA_SIZE, this);
        g2d.drawImage(m_player.getSprite(), (m_player.getX()*m_cellSize), (m_player.getY()*m_cellSize), (m_player.getX()*m_cellSize+m_cellSize), (m_player.getY()*m_cellSize+m_cellSize), (setPlayerSheetCoord(m_player)[0]*m_player.getFrameSize()), (setPlayerSheetCoord(m_player)[1]*m_player.getFrameSize()), (setPlayerSheetCoord(m_player)[0]*m_player.getFrameSize()+m_player.getFrameSize()), (setPlayerSheetCoord(m_player)[1]*m_player.getFrameSize()+m_player.getFrameSize()),this);
        
    	g2d.setColor(textColor);
        g2d.setFont(m_fFont);
    	g2d.drawString("Score: "+m_player.getScore(), 10, 30);
    	g2d.drawString("Lives: "+m_player.getCurrentLives(), 10, 70);
    }
    
    @Override
    // Cuando se produce una acción, con cada tic del timer, Java invocará este método. 
    public void actionPerformed(ActionEvent e) {
    	m_player.move();
    	for(int i = 0; i < m_prisionerNumber; i++)
    	{
        	if(m_player.checkCollision(prisioners[i]) && prisioners[i].m_prisionerState == Prisioner.prisionerStates.PRISIONED)
        	{
        		prisioners[i].Rescued();
        		m_alSolvedPrisioners.add(prisioners[i]);
        		Music come = new Music("SFX/YouComeWithMe.wav");
        		come.play();
        		System.out.println("Solved");
        	} else if(prisioners[i].m_prisionerState == Prisioner.prisionerStates.ESCAPE){
        		if(prisioners[i].move(m_player.m_LastPositions[m_alSolvedPrisioners.indexOf(prisioners[i])], m_player))
        		{
        			Music safe = new Music("SFX/Safe.wav");
        			safe.play();
        			m_alSolvedPrisioners.remove(prisioners[i]);
        			m_solvedPrisioners++;
        			m_player.setScore(100);
        		}
        	}
    	}
    	
    	if (m_solvedPrisioners>=4)
    	{
    		m_solvedPrisioners = 0;
    		setHigherLevel(); 		
    	}
    	
        for(Enemy enemigo : enemies)
        {
        	if(m_enemyMovementReduction)
        		enemigo.move();
        	
        	if(m_player.checkCollision(enemigo))
        	{
        		if (!m_invincible)
        		{
	        		m_player.takeLives(1);
	    			Music dead = new Music("SFX/Dead.wav");
	    			dead.play();
	    			
	        		if (m_player.hasDied())
	        		{
	        			try {
							SceneManager.Instance().loadScene(3);
							m_timer.stop();
						} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (FontFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
	        		}
	        		else
	        		{        			
	        			resetGame();
	        		}
        		}

        		//TODO
        	}
        	for(Prisioner p : prisioners){
        		if(p.checkCollision(enemigo))
        		{
        			if (p.m_prisionerState == Prisioner.prisionerStates.ESCAPE)
        			{
                		if (!m_invincible && m_activatePrisionerLife)
                		{
        	        		m_player.takeLives(1);
        	    			Music dead = new Music("SFX/Dead.wav");
        	    			dead.play();
        	    			
        	        		if (m_player.hasDied())
        	        		{
        	        			try {
        							SceneManager.Instance().loadScene(3);
        							m_timer.stop();
        						} catch (ParserConfigurationException | SAXException | IOException | InterruptedException e1) {
        							// TODO Auto-generated catch block
        							e1.printStackTrace();
        						} catch (FontFormatException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
        	        		}
        	        		else
        	        		{        			
        	        			resetGame();
        	        		}
                		}
        			}
        		}
        	}
        }
        m_enemyMovementReduction = m_enemyMovementReduction? false : true;
        repaint();  
    }
    
    private class TAdapter extends KeyAdapter 
    {
    	public TAdapter()
    	{
    		System.out.println("hax");
    	}
    	
	    	@Override
	    	public void keyReleased(KeyEvent e) 
	    	{
	    		m_player.keyReleased(e);
	    	}
	    	@Override
	    	public void keyPressed(KeyEvent e) 
	    	{
	    		
	    		m_player.keyPressed(e);
	    	}
    	}
    
    public static int GetRandomInt(int max)
    {
    	return m_rnd.nextInt(max);
    }
}