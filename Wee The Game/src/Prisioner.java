/*======================================================================================================================//
 * 																													    *
 *  		Clase prisionero: Hereda de Personaje.																	    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Prisioner extends Character 
{	
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	Map m_map;															//mapa por el que se mueve
	static public enum prisionerStates {PRISIONED, ESCAPE, FREE};		//Estados de los prisioneros
	prisionerStates m_prisionerState;									//Estado actual del prisionero
	
	//===================================================================================================================//
	// 											**Constructores**														 //
	//===================================================================================================================//
	
	public Prisioner(Map map)
	{
    	super();
    	this.m_map = map;
    	m_prisionerState = prisionerStates.PRISIONED;
    }
    
    public Prisioner(String imagen, int posX, int posY, int despX, int despY, Map map){
    	super(imagen, posX, posY, despX, despY);
    	this.m_map = map;
    	m_prisionerState = prisionerStates.PRISIONED;
    }
    
    public Prisioner(String imagen, int posX, int posY, int despX, int despY, Map map, int numFrames, int frameSize, int id)
    {
    	super(imagen, posX, posY, despX, despY, numFrames, frameSize, id);
    	this.m_map = map;
    	m_prisionerState = prisionerStates.PRISIONED;
    }
 
	//===================================================================================================================//
	// 						**Método que indica que el prisionero está escapando**										 //
	//===================================================================================================================//
    
    public void Rescued()
    {
    	this.m_prisionerState = prisionerStates.ESCAPE;
    }
    
	//===================================================================================================================//
	// 						**Método que mueve al prisionero**															 //
	//===================================================================================================================//
    
    public boolean move(Point p, Player j)
    {
    	setXPrisionero(p.x,j);
    	setYPrisionero(p.y,j);
		if(m_map.matriz[p.x][p.y].id == 1 && m_map.matriz[p.x][p.y].walkable)
		{
			m_prisionerState = prisionerStates.FREE;
			m_map.matriz[p.x][p.y].walkable = false;
			return true;
		}
		return false;
    }
}
