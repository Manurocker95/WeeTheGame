/*======================================================================================================================//
 * 																													    *
 *  		Clase Punto: Es cada celda de la matriz del escenario. Puede ser andable o no.Tiene un tile asignado.	    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodr�guez Matesanz, Marcos L�pez Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Point 
{	
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	public int x, y, id, offset; //Posici�n X, Posici�n Y, ID �nico, offset del tileset asociado a esa celda
	public boolean walkable; 	 //Es andable o no
	
	//===================================================================================================================//
	// 											**Constructores**														 //
	//===================================================================================================================//
	
	public Point (int x, int y)
	{
		this.x = x;
		this.y = y;
		this.id = 0;
		this.offset = 0;
		this.walkable = true;
	}
	
	public Point (int x, int y, int id, boolean walkable, int offset)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.offset = offset;
		this.walkable = walkable;
	}
}
