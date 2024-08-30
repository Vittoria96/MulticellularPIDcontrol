package PID_Controller_new;

import java.util.ArrayList;

import bsim.BSim;
import bsim.export.BSimLogger;

public class StateP_Logger extends BSimLogger {

	
	private ArrayList<ProportionalBacterium> bacteriaP;

	
	public StateP_Logger(BSim sim, String filename, ArrayList<ProportionalBacterium> bacteriaP) {
		super(sim, filename);

		this.bacteriaP=bacteriaP;
	
	}

	
	@Override
	public void before() {
        super.before();

        String buffer = "time";


        if(bacteriaP.size() > 0) {
            for (int j = 0; j < bacteriaP.get(0).grn_p.numEq; j++) {
                buffer += ",controller(" + j + ")";                       //what to write as title of each column
               
            }
        }
        
        buffer += ",N, theta, Y, mu, gamma, eta, beta_P, CV";
      
         
        write(buffer);
    }

    @Override
    public void during() {
        String buffer = "" + sim.getFormattedTime();
        double sum0=0;
        double sum1=0;
  
        
//     /** To write the state of each cell  **/ 
//       
//       for(ProportionalBacterium b1 : bacteriaP) {
//    	   for (int j = 0; j < bacteriaP.get(0).grn_p.numEq; j++) {
//     	buffer+=","+b1.y[j];
//    	   }  
//     
//     }
        
        /** To write the mean state over all cells  **/ 
        
        for(ProportionalBacterium b1 : bacteriaP) {
        	sum0=sum0+b1.y[0];
        	sum1=sum1+b1.y[1];
        
        }
        
        /*** Initialisation **/
        
        double mu=0;
        double theta=0;
        double gamma=0;
        double eta=0;
        double CV=0;
        double beta_p=0;
        double Y=0;
     
        for(ProportionalBacterium b1 : bacteriaP) {
        	mu=b1.grn_p.mu; 
    		theta=b1.grn_p.theta; 
    	 	gamma=b1.grn_p.gamma; 
    	 	eta=b1.grn_p.eta;
    	 	beta_p=b1.grn_p.beta_p;
    	 	CV=b1.grn_p.CV;
    	 	Y=b1.grn_p.Y;
            }

        
        buffer += "," + sum0/bacteriaP.size()+","+sum1/bacteriaP.size()+"," +bacteriaP.size(); //what to write (medium value of each state variable of the Target)
	 	
	 	buffer += ","+ theta + ","+ Y+  "," +mu+ ","+gamma+ ","+eta+ ","+beta_p+ ","+CV;
 
      
        write(buffer);
       
    }
    
    
}
    
    


