import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class PatronEnemy extends Enemy implements ActionListener {
	
	Displacement[] m_displacementArray;
	int m_actualIndex;
	
	// Timer para el cambio de frame y variable que indica el tiempo del cambio.
	private Timer m_timer; 
    private final int DELAY = 1000; 

	public PatronEnemy(Map map, int tipo){
    	super(map);
    	PreparaDesplazamientos(tipo);
    }
    
    public PatronEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, int tipo){
    	super(imagen, posX, posY, despX, despY, map);
    	PreparaDesplazamientos(tipo);
    }
    
    public PatronEnemy(String imagen, int posX, int posY, int despX, int despY, Map map, int tipo, int numFrames, int frameSize, int id){
    	super(imagen, posX, posY, despX, despY, map, numFrames, frameSize, id);
    	PreparaDesplazamientos(tipo);
    	
    	// Inicializamos el timer pasandole el tiempo y comenzamos la cuenta.
    	m_timer = new Timer(DELAY, this); 
    	m_timer.start();	
    }
    
    @Override
	public void actionPerformed(ActionEvent e) { // Método que se llama cada vez que el timer concluye.
		
    	// Creamos un número random para indicar el frame actual de la animación.
    	Random rand = new Random();

        int randomNum = rand.nextInt((2 - 0) + 1) + 0;
        
        m_currentFrame = randomNum;
		
	}
    
    
    void PreparaDesplazamientos(int tipo)
    {
    	m_actualIndex = 0;
    	switch(tipo)
    	{
    		case 0:
    			this.m_displacementArray = new Displacement[4];
    			this.m_displacementArray[0] = new Displacement(1, 0);
    			this.m_displacementArray[1] = new Displacement(0, -1);
    			this.m_displacementArray[2] = new Displacement(-1, 0);
    			this.m_displacementArray[3] = new Displacement(0, 1);
    			break;
    		case 1:  			
    			this.m_displacementArray = new Displacement[64];
    			this.m_displacementArray[0] = new Displacement(1, 0);
    			this.m_displacementArray[1] = new Displacement(1, 0);
    			this.m_displacementArray[2] = new Displacement(1, 0);
    			this.m_displacementArray[3] = new Displacement(1, 0);
    			this.m_displacementArray[4] = new Displacement(1, 0);
    			this.m_displacementArray[5] = new Displacement(0, 1);
    			this.m_displacementArray[6] = new Displacement(0, 1);
    			this.m_displacementArray[7] = new Displacement(0, 1);
    			this.m_displacementArray[8] = new Displacement(0, 1);
    			this.m_displacementArray[9] = new Displacement(0, 1);
    			this.m_displacementArray[10] = new Displacement(0, 1);
    			this.m_displacementArray[11] = new Displacement(0, 1);
    			this.m_displacementArray[12] = new Displacement(0, 1);
    			this.m_displacementArray[13] = new Displacement(1, 0);
    			this.m_displacementArray[14] = new Displacement(1, 0);
    			this.m_displacementArray[15] = new Displacement(1, 0);
    			this.m_displacementArray[16] = new Displacement(1, 0);
    			this.m_displacementArray[17] = new Displacement(1, 0);
    			this.m_displacementArray[18] = new Displacement(1, 0);
    			this.m_displacementArray[19] = new Displacement(1, 0);
    			this.m_displacementArray[20] = new Displacement(0, 1);
    			this.m_displacementArray[21] = new Displacement(0, 1);
    			this.m_displacementArray[22] = new Displacement(0, 1);
    			this.m_displacementArray[23] = new Displacement(0, 1);
    			this.m_displacementArray[24] = new Displacement(0, 1);
    			this.m_displacementArray[25] = new Displacement(0, 1);
    			this.m_displacementArray[26] = new Displacement(0, 1);
    			this.m_displacementArray[27] = new Displacement(-1, 0);
    			this.m_displacementArray[28] = new Displacement(-1, 0);
    			this.m_displacementArray[29] = new Displacement(-1, 0);
    			this.m_displacementArray[30] = new Displacement(-1, 0);
    			this.m_displacementArray[31] = new Displacement(-1, 0);
    			this.m_displacementArray[32] = new Displacement(-1, 0);
    			this.m_displacementArray[33] = new Displacement(-1, 0);
    			this.m_displacementArray[34] = new Displacement(-1, 0);
    			this.m_displacementArray[35] = new Displacement(-1, 0);
    			this.m_displacementArray[36] = new Displacement(-1, 0);
    			this.m_displacementArray[37] = new Displacement(-1, 0);
    			this.m_displacementArray[38] = new Displacement(-1, 0);
    			this.m_displacementArray[39] = new Displacement(-1, 0);
    			this.m_displacementArray[40] = new Displacement(-1, 0);
    			this.m_displacementArray[41] = new Displacement(-1, 0);
    			this.m_displacementArray[42] = new Displacement(-1, 0);
    			this.m_displacementArray[43] = new Displacement(-1, 0);
    			this.m_displacementArray[44] = new Displacement(0, -1);
    			this.m_displacementArray[45] = new Displacement(0, -1);
    			this.m_displacementArray[46] = new Displacement(0, -1);
    			this.m_displacementArray[47] = new Displacement(0, -1);
    			this.m_displacementArray[48] = new Displacement(0, -1);
    			this.m_displacementArray[49] = new Displacement(0, -1);
    			this.m_displacementArray[50] = new Displacement(0, -1);
    			this.m_displacementArray[51] = new Displacement(0, -1);
    			this.m_displacementArray[52] = new Displacement(0, -1);
    			this.m_displacementArray[53] = new Displacement(0, -1);
    			this.m_displacementArray[54] = new Displacement(0, -1);
    			this.m_displacementArray[55] = new Displacement(0, -1);
    			this.m_displacementArray[56] = new Displacement(0, -1);
    			this.m_displacementArray[57] = new Displacement(0, -1);
    			this.m_displacementArray[58] = new Displacement(0, -1);
    			this.m_displacementArray[59] = new Displacement(1, 0);
    			this.m_displacementArray[60] = new Displacement(1, 0);
    			this.m_displacementArray[61] = new Displacement(1, 0);
    			this.m_displacementArray[62] = new Displacement(1, 0);
    			this.m_displacementArray[63] = new Displacement(1, 0);
    			
    			break;
    			
    		case 2:
    			this.m_displacementArray = new Displacement[72];
    			this.m_displacementArray[0] = new Displacement(0, 1);
    			this.m_displacementArray[1] = new Displacement(0, 1);
    			this.m_displacementArray[2] = new Displacement(0, 1);
    			this.m_displacementArray[3] = new Displacement(0, 1);
    			this.m_displacementArray[4] = new Displacement(0, 1);
    			this.m_displacementArray[5] = new Displacement(0, 1);
    			this.m_displacementArray[6] = new Displacement(0, 1);
    			this.m_displacementArray[7] = new Displacement(0, 1);
    			this.m_displacementArray[8] = new Displacement(0, 1);
    			this.m_displacementArray[9] = new Displacement(0, 1);
    			this.m_displacementArray[10] = new Displacement(0, 1);
    			this.m_displacementArray[11] = new Displacement(0, 1);
    			this.m_displacementArray[12] = new Displacement(0, 1);
    			this.m_displacementArray[13] = new Displacement(0, 1);
    			this.m_displacementArray[14] = new Displacement(0, 1);
    			this.m_displacementArray[15] = new Displacement(-1, 0);
    			this.m_displacementArray[16] = new Displacement(-1, 0);
    			this.m_displacementArray[17] = new Displacement(-1, 0);
    			this.m_displacementArray[18] = new Displacement(-1, 0);
    			this.m_displacementArray[19] = new Displacement(-1, 0);
    			this.m_displacementArray[20] = new Displacement(-1, 0);
    			this.m_displacementArray[21] = new Displacement(-1, 0);
    			this.m_displacementArray[22] = new Displacement(-1, 0);
    			this.m_displacementArray[23] = new Displacement(-1, 0);
    			this.m_displacementArray[24] = new Displacement(-1, 0);
    			this.m_displacementArray[25] = new Displacement(-1, 0);
    			this.m_displacementArray[26] = new Displacement(-1, 0);
    			this.m_displacementArray[27] = new Displacement(-1, 0);
    			this.m_displacementArray[28] = new Displacement(-1, 0);
    			this.m_displacementArray[29] = new Displacement(-1, 0);
    			this.m_displacementArray[30] = new Displacement(-1, 0);
    			this.m_displacementArray[31] = new Displacement(-1, 0);
    			this.m_displacementArray[32] = new Displacement(-1, 0);
    			this.m_displacementArray[33] = new Displacement(-1, 0);
    			this.m_displacementArray[34] = new Displacement(-1, 0);
    			this.m_displacementArray[35] = new Displacement(-1, 0);
    			this.m_displacementArray[36] = new Displacement(0, -1);
    			this.m_displacementArray[37] = new Displacement(0, -1);
    			this.m_displacementArray[38] = new Displacement(0, -1);
    			this.m_displacementArray[39] = new Displacement(0, -1);
    			this.m_displacementArray[40] = new Displacement(0, -1);
    			this.m_displacementArray[41] = new Displacement(0, -1);
    			this.m_displacementArray[42] = new Displacement(0, -1);
    			this.m_displacementArray[43] = new Displacement(0, -1);
    			this.m_displacementArray[44] = new Displacement(0, -1);
    			this.m_displacementArray[45] = new Displacement(0, -1);
    			this.m_displacementArray[46] = new Displacement(0, -1);
    			this.m_displacementArray[47] = new Displacement(0, -1);
    			this.m_displacementArray[48] = new Displacement(0, -1);
    			this.m_displacementArray[49] = new Displacement(0, -1);
    			this.m_displacementArray[50] = new Displacement(0, -1);
    			this.m_displacementArray[51] = new Displacement(1, 0);
    			this.m_displacementArray[52] = new Displacement(1, 0);
    			this.m_displacementArray[53] = new Displacement(1, 0);
    			this.m_displacementArray[54] = new Displacement(1, 0);
    			this.m_displacementArray[55] = new Displacement(1, 0);
    			this.m_displacementArray[56] = new Displacement(1, 0);
    			this.m_displacementArray[57] = new Displacement(1, 0);
    			this.m_displacementArray[58] = new Displacement(1, 0);
    			this.m_displacementArray[59] = new Displacement(1, 0);
    			this.m_displacementArray[60] = new Displacement(1, 0);
    			this.m_displacementArray[61] = new Displacement(1, 0);
    			this.m_displacementArray[62] = new Displacement(1, 0);
    			this.m_displacementArray[63] = new Displacement(1, 0);
    			this.m_displacementArray[64] = new Displacement(1, 0);
    			this.m_displacementArray[65] = new Displacement(1, 0);
    			this.m_displacementArray[66] = new Displacement(1, 0);
    			this.m_displacementArray[67] = new Displacement(1, 0);
    			this.m_displacementArray[68] = new Displacement(1, 0);
    			this.m_displacementArray[69] = new Displacement(1, 0);
    			this.m_displacementArray[70] = new Displacement(1, 0);
    			this.m_displacementArray[71] = new Displacement(1, 0);
    			
    			break;
    		case 3:
    			this.m_displacementArray = new Displacement[85];
    			this.m_displacementArray[0] = new Displacement(0, 0);
    			this.m_displacementArray[1] = new Displacement(0, 1);
    			this.m_displacementArray[2] = new Displacement(0, 1);
    			this.m_displacementArray[3] = new Displacement(0, 1);
    			this.m_displacementArray[4] = new Displacement(0, 1);
    			this.m_displacementArray[5] = new Displacement(0, 1);
    			this.m_displacementArray[6] = new Displacement(0, 1);
    			this.m_displacementArray[7] = new Displacement(0, 1);
    			this.m_displacementArray[8] = new Displacement(1, 0);
    			this.m_displacementArray[9] = new Displacement(1, 0);
    			this.m_displacementArray[10] = new Displacement(1, 0);
    			this.m_displacementArray[11] = new Displacement(1, 0);
    			this.m_displacementArray[12] = new Displacement(1, 0);
    			this.m_displacementArray[13] = new Displacement(1, 0);
    			this.m_displacementArray[14] = new Displacement(1, 0);
    			this.m_displacementArray[15] = new Displacement(1, 0);
    			this.m_displacementArray[16] = new Displacement(1, 0);
    			this.m_displacementArray[17] = new Displacement(1, 0);
    			this.m_displacementArray[18] = new Displacement(0, 1);
    			this.m_displacementArray[19] = new Displacement(0, 1);
    			this.m_displacementArray[20] = new Displacement(0, 1);
    			this.m_displacementArray[21] = new Displacement(0, 1);
    			this.m_displacementArray[22] = new Displacement(1, 0);
    			this.m_displacementArray[23] = new Displacement(1, 0);
    			this.m_displacementArray[24] = new Displacement(1, 0);
    			this.m_displacementArray[25] = new Displacement(1, 0);
    			this.m_displacementArray[26] = new Displacement(1, 0);
    			this.m_displacementArray[27] = new Displacement(1, 0);
    			this.m_displacementArray[28] = new Displacement(1, 0);
    			this.m_displacementArray[29] = new Displacement(1, 0);
    			this.m_displacementArray[30] = new Displacement(1, 0);
    			this.m_displacementArray[31] = new Displacement(1, 0);
    			this.m_displacementArray[32] = new Displacement(1, 0);
    			this.m_displacementArray[33] = new Displacement(1, 0);
    			this.m_displacementArray[34] = new Displacement(1, 0);
    			this.m_displacementArray[35] = new Displacement(1, 0);
    			this.m_displacementArray[36] = new Displacement(1, 0);
    			this.m_displacementArray[37] = new Displacement(1, 0);
    			this.m_displacementArray[38] = new Displacement(0, -1);
    			this.m_displacementArray[39] = new Displacement(0, -1);
    			this.m_displacementArray[40] = new Displacement(0, -1);
    			this.m_displacementArray[41] = new Displacement(0, -1);
    			this.m_displacementArray[42] = new Displacement(0, -1);
    			this.m_displacementArray[43] = new Displacement(0, -1);
    			this.m_displacementArray[44] = new Displacement(0, -1);
    			this.m_displacementArray[45] = new Displacement(0, -1);
    			this.m_displacementArray[46] = new Displacement(0, -1);
    			this.m_displacementArray[47] = new Displacement(0, -1);
    			this.m_displacementArray[48] = new Displacement(0, -1);
    			this.m_displacementArray[49] = new Displacement(0, -1);
    			this.m_displacementArray[50] = new Displacement(0, -1);
    			this.m_displacementArray[51] = new Displacement(0, -1);
    			this.m_displacementArray[52] = new Displacement(0, -1);
    			this.m_displacementArray[53] = new Displacement(-1, 0);
    			this.m_displacementArray[54] = new Displacement(-1, 0);
    			this.m_displacementArray[55] = new Displacement(-1, 0);
    			this.m_displacementArray[56] = new Displacement(-1, 0);
    			this.m_displacementArray[57] = new Displacement(-1, 0);
    			this.m_displacementArray[58] = new Displacement(-1, 0);
    			this.m_displacementArray[59] = new Displacement(-1, 0);
    			this.m_displacementArray[60] = new Displacement(-1, 0);
    			this.m_displacementArray[61] = new Displacement(-1, 0);
    			this.m_displacementArray[62] = new Displacement(-1, 0);
    			this.m_displacementArray[63] = new Displacement(-1, 0);
    			this.m_displacementArray[64] = new Displacement(-1, 0);
    			this.m_displacementArray[65] = new Displacement(-1, 0);
    			this.m_displacementArray[66] = new Displacement(-1, 0);
    			this.m_displacementArray[67] = new Displacement(-1, 0);
    			this.m_displacementArray[68] = new Displacement(-1, 0);
    			this.m_displacementArray[69] = new Displacement(-1, 0);
    			this.m_displacementArray[70] = new Displacement(-1, 0);
    			this.m_displacementArray[71] = new Displacement(-1, 0);
    			this.m_displacementArray[72] = new Displacement(-1, 0);
    			this.m_displacementArray[73] = new Displacement(-1, 0);
    			this.m_displacementArray[74] = new Displacement(-1, 0);
    			this.m_displacementArray[75] = new Displacement(-1, 0);
    			this.m_displacementArray[76] = new Displacement(-1, 0);
    			this.m_displacementArray[77] = new Displacement(-1, 0);
    			this.m_displacementArray[78] = new Displacement(-1, 0);
    			this.m_displacementArray[79] = new Displacement(-1, 0);
    			this.m_displacementArray[80] = new Displacement(0, 1);
    			this.m_displacementArray[81] = new Displacement(0, 1);
    			this.m_displacementArray[82] = new Displacement(0, 1);
    			this.m_displacementArray[83] = new Displacement(0, 1);
    			this.m_displacementArray[84] = new Displacement(1, 0);
    			break;
    		case 4:
    			this.m_displacementArray = new Displacement[12];
    			this.m_displacementArray[0] = new Displacement(0, 1);
    			this.m_displacementArray[1] = new Displacement(0, 1);
    			this.m_displacementArray[2] = new Displacement(0, 1);
    			this.m_displacementArray[3] = new Displacement(1, 0);
    			this.m_displacementArray[4] = new Displacement(1, 0);
    			this.m_displacementArray[5] = new Displacement(1, 0);
    			this.m_displacementArray[6] = new Displacement(0, -1);
    			this.m_displacementArray[7] = new Displacement(0, -1);
    			this.m_displacementArray[8] = new Displacement(0, -1);
    			this.m_displacementArray[9] = new Displacement(-1, 0);
    			this.m_displacementArray[10] = new Displacement(-1, 0);
    			this.m_displacementArray[11] = new Displacement(-1, 0);
    			break;
    		case 5:
    			this.m_displacementArray = new Displacement[27];
    			this.m_displacementArray[0] = new Displacement(1, 0);
    			this.m_displacementArray[1] = new Displacement(0, 1);
    			this.m_displacementArray[2] = new Displacement(0, 1);
    			this.m_displacementArray[3] = new Displacement(0, 1);
    			this.m_displacementArray[4] = new Displacement(0, 1);
    			this.m_displacementArray[5] = new Displacement(0, 1);
    			this.m_displacementArray[6] = new Displacement(0, 1);
    			this.m_displacementArray[7] = new Displacement(0, 1);
    			this.m_displacementArray[8] = new Displacement(1, 0);
    			this.m_displacementArray[9] = new Displacement(1, 0);
    			this.m_displacementArray[10] = new Displacement(1, 0);
    			this.m_displacementArray[11] = new Displacement(1, 0);
    			this.m_displacementArray[12] = new Displacement(1, 0);
    			this.m_displacementArray[13] = new Displacement(1, 0);
      			this.m_displacementArray[14] = new Displacement(0, -1);
    			this.m_displacementArray[15] = new Displacement(0, -1);
    			this.m_displacementArray[16] = new Displacement(0, -1);
    			this.m_displacementArray[17] = new Displacement(0, -1);
    			this.m_displacementArray[18] = new Displacement(0, -1);
    			this.m_displacementArray[19] = new Displacement(0, -1);
    			this.m_displacementArray[20] = new Displacement(0, -1);
    			this.m_displacementArray[21] = new Displacement(-1, 0);
    			this.m_displacementArray[22] = new Displacement(-1, 0);
    			this.m_displacementArray[23] = new Displacement(-1, 0);
    			this.m_displacementArray[24] = new Displacement(-1, 0);
    			this.m_displacementArray[25] = new Displacement(-1, 0);
    			this.m_displacementArray[26] = new Displacement(-1, 0);
    			break;
			default:
				this.m_displacementArray = new Displacement[26];
    			this.m_displacementArray[0] = new Displacement(1, 0);
    			this.m_displacementArray[1] = new Displacement(1, 0);
    			this.m_displacementArray[2] = new Displacement(1, 0);
    			this.m_displacementArray[3] = new Displacement(1, 0);
    			this.m_displacementArray[4] = new Displacement(1, 0);
    			this.m_displacementArray[5] = new Displacement(0, 1);
    			this.m_displacementArray[6] = new Displacement(0, 1);
    			this.m_displacementArray[7] = new Displacement(0, 1);
    			this.m_displacementArray[8] = new Displacement(0, 1);
    			this.m_displacementArray[9] = new Displacement(0, 1);
    			this.m_displacementArray[10] = new Displacement(0, 1);
    			this.m_displacementArray[11] = new Displacement(0, 1);
    			this.m_displacementArray[12] = new Displacement(0, 1);
    			this.m_displacementArray[13] = new Displacement(-1, 0);
    			this.m_displacementArray[14] = new Displacement(-1, 0);
    			this.m_displacementArray[15] = new Displacement(-1, 0);
    			this.m_displacementArray[16] = new Displacement(-1, 0);
    			this.m_displacementArray[17] = new Displacement(-1, 0);
    			this.m_displacementArray[18] = new Displacement(0, -1);
    			this.m_displacementArray[19] = new Displacement(0, -1);
    			this.m_displacementArray[20] = new Displacement(0, -1);
    			this.m_displacementArray[21] = new Displacement(0, -1);
    			this.m_displacementArray[22] = new Displacement(0, -1);
    			this.m_displacementArray[23] = new Displacement(0, -1);
    			this.m_displacementArray[24] = new Displacement(0, -1);
    			this.m_displacementArray[25] = new Displacement(0, -1);
				break;
    	}
    }
    
    public void move()
    {
    	
    	setDx(this.m_displacementArray[m_actualIndex].despX);
    	setDy(this.m_displacementArray[m_actualIndex].despY);
    	if(++m_actualIndex >= this.m_displacementArray.length)
    		m_actualIndex -= this.m_displacementArray.length;
    	super.move();
    }
}
