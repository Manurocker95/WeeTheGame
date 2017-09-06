import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*======================================================================================================================//
 * 																													    *
 *  		Clase Lector XML: Lee el XML y guarda esa información. Se usa para establecer los mapas de manera externa.  *
 *  																													*
 * 		 Hecho para Wee por: Manuel Rodríguez Matesanz, Marcos López Tabernero y Diego del Castillo Torguet				*																	*
 *======================================================================================================================*/

public class XMLReader 
{
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	
	ArrayList <String> m_xmlData;		// Arraylist con los datos de cada mapa.
	ArrayList <ArrayList> m_maps;		// Arraylist que guarda los arraylist xmlData que se van generando.
	
	//===================================================================================================================//
	// 											**Constructor**															 //
	//===================================================================================================================//
	
	public XMLReader()
	{
		m_xmlData = new ArrayList<String>();		//Inicializamos el arraylist de datos
		m_maps = new ArrayList<ArrayList>();		//Inicializamos el arraylist de mapas
	}
	
	//===================================================================================================================//
	// 				**Método que parsea el XML y almacena los mapas en el arraylists maps**								 //
	//===================================================================================================================//
	
	public ArrayList parseXML(String XMLPath) throws ParserConfigurationException, SAXException, IOException
	{
		
		File xmlFile = new File (XMLPath);	//Creamos un archivo con la ruta del XML.
		
		if (!xmlFile.exists())				//Comprobamos que exista el XML. Sino, salta un error.
		{
			throw new RuntimeException("El XML no existe. Por favor, comprueba la ruta.");
		}
		else								//Si existe el XML
		{
			
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Usamos librerías de Java para cargar documentos
		    DocumentBuilder builder = factory.newDocumentBuilder();				   
		   
		    Document document = builder.parse(xmlFile);				 	//Cargamos un documento TMX/XML del path que le digamos  
		    NodeList nodeList = document.getElementsByTagName("map");	//Cargamos la lista de nodos del TMX/XML
		    
		    //Vamos a añadir los datos almacenados en cada nodo del documento 
		    for (int i = 0; i < nodeList.getLength(); i++)
		    {		    	
		    	Node node = nodeList.item(i);	//Extraemos cada elemento
		    	
		    	if (node.getNodeType() == Node.ELEMENT_NODE)
		    	{
		    		m_xmlData.clear();			   // Limpiamos el arraylist para que no se añadan datos innecesarios
		    		Element elem = (Element) node; // Para cada elemento añadimos sus datos
		    		m_xmlData.add(node.getAttributes().getNamedItem("ID").getNodeValue()); 							//0
		    		m_xmlData.add(node.getAttributes().getNamedItem("width").getNodeValue()); 						//1
		    		m_xmlData.add(node.getAttributes().getNamedItem("height").getNodeValue()); 						//2
		    		m_xmlData.add(node.getAttributes().getNamedItem("tilesize").getNodeValue()); 						//3
		    		m_xmlData.add(node.getAttributes().getNamedItem("enemyNumber").getNodeValue()); 					//4
		    		m_xmlData.add(node.getAttributes().getNamedItem("prisionerNumber").getNodeValue()); 				//5
		    		m_xmlData.add(node.getAttributes().getNamedItem("tileset").getNodeValue()); 						//6
		    		m_xmlData.add(node.getAttributes().getNamedItem("tilesetColumns").getNodeValue()); 				//7
		    		m_xmlData.add(node.getAttributes().getNamedItem("tilesetLines").getNodeValue()); 					//8
		    		m_xmlData.add(elem.getElementsByTagName("tiles").item(0).getChildNodes().item(0).getNodeValue()); //9	
		    		m_maps.add((ArrayList) m_xmlData.clone()); //Metemos los datos almacenados en xmlData en el arraylist de mapas
		    	}
		    	
		    }
		    
		    return m_maps;	//Devolvemos esa información parseada
		}
	      
	 }    	
	
}
