import java.util.ArrayList;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Mapa: Conforma la matriz de puntos del escenario por la que se mueven los personajes. 			    *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class Map 
{
	
	public Point[][] matriz;
	public int m_mapWidth, m_mapHeight;
	
	public Map(int ancho, int largo){
		this.m_mapWidth = ancho;
		this.m_mapHeight = largo;
		matriz = new Point[ancho][largo];
		PreparaMatriz();
	}
	
	void PreparaMatriz()
	{
		for(int i = 0; i < m_mapWidth; i++)
		{
			for(int j = 0; j < m_mapHeight; j++)
			{
				matriz[i][j] = new Point(i, j, 0, true,2);
			}
		}
	}
	
	public void SetMapValues(int pos, int cellId, boolean canWalk, String offset, int numberOfColumns)
	{
		matriz[pos%m_mapWidth][pos/m_mapWidth].id=cellId;
		matriz[pos%m_mapWidth][pos/m_mapWidth].walkable = canWalk;
		matriz[pos%m_mapWidth][pos/m_mapWidth].offset = Integer.parseInt(offset);
	}
	
	public void SetMapValues2(int pos, String offset)
	{	
		matriz[pos%m_mapWidth][pos/m_mapWidth].offset = Integer.parseInt(offset);
	}	
	
	public Point[] GetMatrizUnidimensional()
	{
		Point[] aux = new Point[m_mapWidth*m_mapHeight];
		for(int i = 0; i < m_mapWidth; i++){
			for(int j = 0; j < m_mapHeight; j++){
				aux[(j*m_mapWidth)+i] = matriz[i][j];
			}
		}
		return aux;
	}
}
