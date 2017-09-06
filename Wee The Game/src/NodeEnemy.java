import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class NodeEnemy extends Enemy implements ActionListener{
	
	int m_nodeNumber;
	Point[] m_nodes;
	int [][] m_navigationTab;
	int m_actualNode, m_nextNode, m_finalNode;
	public enum charaState { NAVIGATING, PURSUIT, CALCULATING, GOING_BACK }
	charaState m_actualState;
	static final int DURSUIT_DISTANCE = 7;
	Character m_victim;
	
	int m_step, m_maxSteps;
	Point[] m_route;
	
	// Timer para el cambio de frame y variable que indica el tiempo del cambio.
	private Timer timer;
    private final int DELAY = 1000;
	
	public NodeEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, Character victima)
	{
		super(imagen, posX, posY, despX, despY, map);
    	m_nodeNumber = 6;
    	m_nodes = new Point[6];
    	m_nodes[0] = new Point(27, 10);
    	m_nodes[1] = new Point(29, 11);
    	m_nodes[2] = new Point(31, 13);
    	m_nodes[3] = new Point(31, 15);
    	m_nodes[4] = new Point(29, 13);
    	m_nodes[5] = new Point(26, 15);
    	m_navigationTab = new int[6][6];
    	m_navigationTab [0] = new int[] {-1,1,1,1,1,5};
    	m_navigationTab [1] = new int[] {0,-1,2,2,2,0};
    	m_navigationTab [2] = new int[] {1,1,-1,3,3,3};
    	m_navigationTab [3] = new int[] {2,2,2,-1,4,5};
    	m_navigationTab [4] = new int[] {3,3,3,3,-1,3};
    	m_navigationTab [5] = new int[] {0,0,3,3,3,-1};
    	this.m_victim = victima;
    	nodePreparation(m_nodeNumber, m_nodes, m_navigationTab);
    }
	
	public NodeEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, Character victima, int numFrames, int frameSize, int id)
	{
		super(imagen, posX, posY, despX, despY, map, numFrames, frameSize, id);
    	m_nodeNumber = 6;
    	m_nodes = new Point[6];
    	m_nodes[0] = new Point(27, 10);
    	m_nodes[1] = new Point(29, 11);
    	m_nodes[2] = new Point(31, 13);
    	m_nodes[3] = new Point(31, 15);
    	m_nodes[4] = new Point(29, 13);
    	m_nodes[5] = new Point(26, 15);
    	m_navigationTab = new int[6][6];
    	m_navigationTab [0] = new int[] {-1,1,1,1,1,5};
    	m_navigationTab [1] = new int[] {0,-1,2,2,2,0};
    	m_navigationTab [2] = new int[] {1,1,-1,3,3,3};
    	m_navigationTab [3] = new int[] {2,2,2,-1,4,5};
    	m_navigationTab [4] = new int[] {3,3,3,3,-1,3};
    	m_navigationTab [5] = new int[] {0,0,3,3,3,-1};
    	this.m_victim = victima;
    	nodePreparation(m_nodeNumber, m_nodes, m_navigationTab);
    	
    	// Inicializamos el timer pasandole el tiempo y comenzamos la cuenta.
    	timer = new Timer(DELAY, this);
    	timer.start();	
    }
    
    @Override
	public void actionPerformed(ActionEvent e) // Método que se llama cada vez que el timer concluye.
    { 
		
    	// Creamos un número random para indicar el frame actual de la animación.
    	Random rand = new Random();

        int randomNum = rand.nextInt((1 - 0) + 1) + 0;
        
        m_currentFrame = randomNum;
		
	}

	public NodeEnemy(Map map, Character victim, int nodeNumber, Point[] nodes, int navigationTab[][])
	{
    	super(map);
    	this.m_victim = victim;
    	nodePreparation(nodeNumber, nodes, navigationTab);
    }
    
    public NodeEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, Character victim, int nodeNumber, Point[] nodes, int navigationTab[][])
    {
    	super(imagen, posX, posY, despX, despY, map);
    	this.m_victim = victim;
    	nodePreparation(nodeNumber, nodes, navigationTab);
    }
    
    void nodePreparation(int nodeNumber, Point[] nodes, int navigationTab[][]){
    	this.m_nodeNumber = nodeNumber;
    	this.m_nodes = new Point[nodeNumber];
    	for(int i = 0; i < nodeNumber; i++){
    		this.m_nodes[i] = nodes[i]; 
    	}
    	this.m_navigationTab = new int[nodeNumber][nodeNumber];
    	for(int i = 0; i < nodeNumber; i++){
    		for(int j = 0; j < nodeNumber; j++){
    			this.m_navigationTab[i][j] = navigationTab[i][j];
    		}
    	}
    	m_actualState = charaState.CALCULATING;
    }
    
    public void move(){
    	/*System.out.println("Nodo ACTUAL: " + nodoActual);
    	System.out.println("Nodo SIGUIENTE: " + nodoSiguiente);
    	System.out.println("Nodo DESTINO: " + nodoDestino);
    	System.out.println("-----------------------------------------");*/
    	switch(m_actualState){
    	case NAVIGATING:
    		if(calculateDistance(new Point(m_victim.getX(), m_victim.getY()))<=DURSUIT_DISTANCE){
    			m_actualState = charaState.PURSUIT;
    		} else{
	    		if(m_step < m_maxSteps){
		    		setX(m_route[m_step].x);
		    		setY(m_route[m_step].y);
		    		m_step++;
	    		} 
	    		if(m_step == m_maxSteps) {
		    		m_actualNode = m_nextNode;
	    	    	if(getX() == m_nodes[m_finalNode].x && getY() == m_nodes[m_finalNode].y){
	        	    	int aux;
	        	    	do{
	        				aux = MainGame.GetRandomInt(m_nodeNumber);
	        			}while(aux == this.m_actualNode);
	        	    	m_finalNode = aux;
	    	    	}
	    	    	m_nextNode = m_navigationTab[m_actualNode][m_finalNode];
	    			calculateRoute(m_nodes[m_nextNode]);
	    		}
    		}
    		break;
    	case PURSUIT:
    		if(calculateDistance(new Point(m_victim.getX(), m_victim.getY()))>DURSUIT_DISTANCE){
    			m_actualState = charaState.CALCULATING;
    		} else {
    			if (getX() > m_victim.getX())
	        		setDx(-1);
	    		else if (getX() < m_victim.getX())
	    			setDx(1);
	    		else{
	    			setDx(0);
	    		}
	    		if (getY() > m_victim.getY())
	    			setDy(-1);
	    		else if (getY() < m_victim.getY())
	    			setDy(1);
	    		else{
	    			setDy(0);
	    		}
	        	super.move();
    		}
    		break;
    	case CALCULATING:
    		Point aux = new Point(-1, -1);
    		double distance = Double.MAX_VALUE;
    		int index = 0, auxIndex = 0;
    		for(Point p1 : m_nodes){
    			double d = calculateDistance(p1);
    			if(d < distance){
    				aux = p1;
    				distance = d;
    				auxIndex = index;
    			}
    			index++;
    		}
    		m_actualNode = auxIndex;
    		calculateRoute(aux);
    		m_actualState = charaState.GOING_BACK;
    	case GOING_BACK:
    		if(calculateDistance(new Point(m_victim.getX(), m_victim.getY()))<=DURSUIT_DISTANCE){
    			m_actualState = charaState.PURSUIT;
    		} else {
	    		if(m_step < m_maxSteps){
		    		setX(m_route[m_step].x);
		    		setY(m_route[m_step].y);
		    		m_step++;
	    		} 
	    		if(m_step == m_maxSteps) {
	    			int aux1;
        	    	do{
        				aux1 = MainGame.GetRandomInt(m_nodeNumber);
        			}while(aux1 == this.m_actualNode);
        	    	m_finalNode = aux1;
	    	    	m_nextNode = m_navigationTab[m_actualNode][m_finalNode];
	    			calculateRoute(m_nodes[m_nextNode]);
	        		m_actualState = charaState.NAVIGATING;
	    		}
    		}
    		break;
    	}
    }
    
    double calculateDistance(Point p){
    	return (Math.sqrt(Math.pow(Math.abs(p.x - getX()),2)+Math.pow(Math.abs(p.y - getY()), 2)));
    }
    
    void calculateRoute(Point nodoASeguir){
    	m_step = 0;
    	int coorX, coorY, ancho, alto, longest, shortest, despX, despY, acum = 0;
    	char ejeLargo;
    	
    	if(nodoASeguir.x > getX()){
    		ancho = nodoASeguir.x - getX();
    		despX = 1;
    	}
    	else {
    		ancho = getX() - nodoASeguir.x;
    		despX = -1;
    	}
    	if(nodoASeguir.y > getY()){
    		alto = nodoASeguir.y - getY();
    		despY = 1;
    	}
    	else {
    		alto = getY() - nodoASeguir.y;
    		despY = -1;
    	}
    	
    	if(ancho > alto){
    		longest = ancho;
    		shortest = alto;
    		ejeLargo = 'x';
    	} else {
    		longest = alto;
    		shortest = ancho;
    		ejeLargo = 'y';
    	}
    	
    	int rutaDistancia = longest +1;
    	
    	coorX = getX();
    	coorY = getY();
    	this.m_route = new Point[rutaDistancia];
    	this.m_route[0] = new Point(coorX, coorY);
    	
    	acum = longest >> 1;
    		
    	for(int i = 1; i < rutaDistancia; i++){
    		acum += shortest;
    		if(acum >= longest){
    			if(ejeLargo == 'x')
    				coorY += despY;
    			else
    				coorX += despX;
    			acum -= longest;
    		}
    		
    		if(ejeLargo == 'x')
    			coorX += despX;
    		else 
    			coorY += despY;

        	this.m_route[i] = new Point(coorX, coorY);
    	}
    	
    	m_maxSteps = rutaDistancia;
    }
}
