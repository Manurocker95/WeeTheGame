import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class WallEnemy extends Enemy implements ActionListener{
	
	Displacement desplazamiento;
	
	// Timer para el cambio de frame y variable que indica el tiempo del cambio.
	private Timer timer;
    private final int DELAY = 1000;

	public WallEnemy(Map map, Displacement desplazamiento){
    	super(map);
    	this.desplazamiento = desplazamiento;
    }
    
    public WallEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, Displacement desplazamiento){
    	super(imagen, posX, posY, despX, despY, map);
    	this.desplazamiento = desplazamiento;
    }
    
    public WallEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, Displacement desplazamiento, int numFrames, int frameSize, int id){
    	super(imagen, posX, posY, despX, despY, map, numFrames, frameSize, id);
    	this.desplazamiento = desplazamiento;
    	 
    	// Inicializamos el timer pasandole el tiempo y comenzamos la cuenta.
    	timer = new Timer(DELAY, this);
    	timer.start();	
    }
    
    @Override
	public void actionPerformed(ActionEvent e) { // Método que se llama cada vez que el timer concluye.
		
    	// Creamos un número random para indicar el frame actual de la animación.
    	Random rand = new Random();

        int randomNum = rand.nextInt((2 - 0) + 1) + 0;
        
        m_currentFrame = randomNum;
	}
    
    public Displacement Izquierda(Displacement desplazamiento){
    	Displacement sig = new Displacement(0, 0);
    	if(desplazamiento.despX == 1)
    		sig.despY = -1;
    	else if(desplazamiento.despX == -1)
    		sig.despY = 1;
    	else if(desplazamiento.despY == 1)
    		sig.despX = 1;
    	else if(desplazamiento.despY == -1)
    		sig.despX = -1;
    	return sig;
    }
    
    public Displacement Derecha(Displacement desplazamiento){
    	Displacement sig = new Displacement(0, 0);
    	if(desplazamiento.despX == 1)
    		sig.despY = 1;
    	else if(desplazamiento.despX == -1)
    		sig.despY = -1;
    	else if(desplazamiento.despY == 1)
    		sig.despX = -1;
    	else if(desplazamiento.despY == -1)
    		sig.despX = 1;
    	return sig;
    }
    
    public Displacement Atras(Displacement desplazamiento){
    	Displacement sig = new Displacement(0, 0);
    	if(desplazamiento.despX == 1)
    		sig.despX = -1;
    	else if(desplazamiento.despX == -1)
    		sig.despX = 1;
    	else if(desplazamiento.despY == 1)
    		sig.despY = -1;
    	else if(desplazamiento.despY == -1)
    		sig.despY = 1;
    	return sig;
    }
    
    public void move(){
    	setDx(desplazamiento.despX);
    	setDy(desplazamiento.despY);
    	super.move();

    	if(desplazamiento.despX == 1){
    		if(0 <= getY()-1 && m_map.matriz[getX()][getY()-1].walkable){
    			desplazamiento = Izquierda(desplazamiento);
    			return;
    		}
    		if(m_map.m_mapWidth > getX()+1 && m_map.matriz[getX()+1][getY()].walkable){
    			return;
    		}
    		if(m_map.m_mapHeight > getY()+1 && m_map.matriz[getX()][getY()+1].walkable){
    			desplazamiento = Derecha(desplazamiento);
    			return;
    		}
    		if(0 <= getX()-1 && m_map.matriz[getX()-1][getY()].walkable){
    			desplazamiento = Atras(desplazamiento);
    			return;
    		}
    	}
    	else if(desplazamiento.despX == -1){
    		if(m_map.m_mapHeight > getY()+1 && m_map.matriz[getX()][getY()+1].walkable){
    			desplazamiento = Izquierda(desplazamiento);
    			return;
    		}
    		if(0 <= getX()-1 && m_map.matriz[getX()-1][getY()].walkable){
    			return;
    		}
    		if(0 <= getY()-1 && m_map.matriz[getX()][getY()-1].walkable){
    			desplazamiento = Derecha(desplazamiento);
    			return;
    		}
    		if(m_map.m_mapWidth > getX()+1 && m_map.matriz[getX()+1][getY()].walkable){
    			desplazamiento = Atras(desplazamiento);
    			return;
    		}
    	}
    	else if(desplazamiento.despY == 1){
    		if(m_map.m_mapWidth > getX()+1 && m_map.matriz[getX()+1][getY()].walkable){
    			desplazamiento = Izquierda(desplazamiento);
    			return;
    		}
    		if(m_map.m_mapHeight > getY()+1 && m_map.matriz[getX()][getY()+1].walkable){
    			return;
    		}
    		if(0 <= getX()-1 && m_map.matriz[getX()-1][getY()].walkable){
    			desplazamiento = Derecha(desplazamiento);
    			return;
    		}
    		if(0 <= getY()-1 && m_map.matriz[getX()][getY()-1].walkable){
    			desplazamiento = Atras(desplazamiento);
    			return;
    		}
    	}
    	else if(desplazamiento.despY == -1){
    		if(0 <= getX()-1 && m_map.matriz[getX()-1][getY()].walkable){
    			desplazamiento = Izquierda(desplazamiento);
    			return;
    		}
    		if(0 <= getY()-1 && m_map.matriz[getX()][getY()-1].walkable){
    			return;
    		}
    		if(m_map.m_mapWidth > getX()+1 && m_map.matriz[getX()+1][getY()].walkable){
    			desplazamiento = Derecha(desplazamiento);
    			return;
    		}
    		if(m_map.m_mapHeight > getY()+1 && m_map.matriz[getX()][getY()+1].walkable){
    			desplazamiento = Atras(desplazamiento);
    			return;
    		}
    	}
    }
}
