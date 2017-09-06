import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class WayEnemy extends Enemy implements ActionListener {
	
	int actualDirection = 4;
	
	// Timer para el cambio de frame y variable que indica el tiempo del cambio.
	private Timer timer;
    private final int DELAY = 1000;
	
	public WayEnemy(Map map){
    	super(map);
    }
    
    public WayEnemy(String imagen, int posX, int posY, int despX, int despY, Map map)
    {
    	super(imagen, posX, posY, despX, despY, map);
    }
    
    public WayEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, int numFrames, int frameSize, int id){
    	super(imagen, posX, posY, despX, despY, map, numFrames, frameSize, id);
    	
    	// Inicializamos el timer pasandole el tiempo y comenzamos la cuenta.
    	timer = new Timer(DELAY, this);
    	timer.start();	
    }
    
    @Override
	public void actionPerformed(ActionEvent e) 
    { // Método que se llama cada vez que el timer concluye.
		
    	// Creamos un número random para indicar el frame actual de la animación.
    	Random rand = new Random();

        int randomNum = rand.nextInt((2 - 0) + 1) + 0;
        
        m_currentFrame = randomNum;
		
	}
    
    public void move()
    {
    	int [] posibilities = new int[8];
    	int pre = actualDirection -1, post = actualDirection +1, contra = actualDirection +4;
    	if(pre < 0)
    		pre += 8;
    	if(post > 7)
    		post -= 8;
    	if(contra > 7)
    		contra -= 8;
    	for(int i = 0; i < 8; i++){
    		posibilities[i] = 0;
    	}
    	posibilities[pre]++;
    	posibilities[actualDirection]+=2;
    	posibilities[post]++;
    	posibilities[contra]--;
    	
    	if(m_map.m_mapWidth > getX()+1 && 
    			m_map.matriz[getX()+1][getY()+0].walkable){
    		posibilities[0]+=10;
    	}
    	if(m_map.m_mapWidth > getX()+1 &&
    			m_map.m_mapHeight > getY()+1 &&
    			m_map.matriz[getX()+1][getY()+1].walkable){
    		posibilities[1]+=10;
    	}
    	if(m_map.m_mapHeight > getY()+1 &&
    			m_map.matriz[getX()+0][getY()+1].walkable){
    		posibilities[2]+=10;
    	}
    	if(0 <= getX()-1 &&
    			m_map.m_mapHeight > getY()+1 &&
    			m_map.matriz[getX()-1][getY()+1].walkable){
    		posibilities[3]+=10;
    	}
    	if(0 <= getX()-1 &&
    			m_map.matriz[getX()-1][getY()+0].walkable){
    		posibilities[4]+=10;
    	}
    	if(0 <= getX()-1 &&
		   0 <= getY()-1 &&
				   m_map.matriz[getX()-1][getY()-1].walkable){
    		posibilities[5]+=10;
    	}
    	if(0 <= getY()-1 &&
    			m_map.matriz[getX()+0][getY()-1].walkable)
    	{
    		posibilities[6]+=10;
    	}
    	if(m_map.m_mapWidth > getX()+1 &&
    	   0 <= getY()-1 &&
    			   m_map.matriz[getX()+1][getY()-1].walkable){
    		posibilities[7]+=10;
    	}
    	
    	int maxDirection = 0;
    	for(int i = 1; i < 8; i++){
    		if(posibilities[maxDirection] < posibilities[i]){
    			maxDirection = i;
    		}
    	}
    	
    	switch(maxDirection){
    	case 0: 
    		setDx(1);
    		setDy(0);
    		break;
    	case 1:
    		setDx(1);
    		setDy(1);
    		break;
    	case 2:
    		setDx(0);
    		setDy(1);
    		break;
    	case 3:
    		setDx(-1);
    		setDy(1);
    		break;
    	case 4:
    		setDx(-1);
    		setDy(0);
    		break;
    	case 5:
    		setDx(-1);
    		setDy(-1);
    		break;
    	case 6:
    		setDx(0);
    		setDy(-1);
    		break;
    	case 7:
    		setDx(1);
    		setDy(-1);
    		break;
		default:
			break;
    	}
    	actualDirection = maxDirection;
    	super.move();
    }
}
