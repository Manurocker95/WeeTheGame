/*======================================================================================================================//
 * 																													    *
 *  		Clase Enemigo: Los enemigos heredan de esta clase.	Hereda de Personaje.								    *
 *  							Esta clase acaba siendo irrelevante														*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Enemy extends Character 
{
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	Map m_map;
	
	//===================================================================================================================//
	// 											**Constructores**														 //
	//===================================================================================================================//
	
	public Enemy(Map map){
    	super();
    	this.m_map = map;
    }
    
    public Enemy(String imagen, int posX, int posY, int despX, int despY, Map map){
    	super(imagen, posX, posY, despX, despY);
    	this.m_map = map;
    }
    
    public Enemy(String imagen, int posX, int posY, int despX, int despY, Map map, int numFrames, int frameSize, int id){
    	super(imagen, posX, posY, despX, despY, numFrames, frameSize, id);
    	this.m_map = map;
    }
}
