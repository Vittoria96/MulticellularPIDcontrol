package PID_Controller_new;

import java.util.ArrayList;

import bsim.BSim;
import bsim.export.BSimLogger;

public class StateT_Logger extends BSimLogger {

	
	private ArrayList<TargetBacterium> bacteriaT;
	
	
	public StateT_Logger(BSim sim, String filename, ArrayList<TargetBacterium> bacteriaT) { 
		
		super(sim, filename);
		
		this.bacteriaT=bacteriaT;
	
		
	}

	
	@Override
	public void before() {
        super.before();

        String buffer = "time";


        if(bacteriaT.size() > 0) {
            for (int j = 0; j < bacteriaT.get(0).grn_t.numEq; j++) {      // what to write as title of the columns
                buffer += ",target(" + j + ")";
            }
        }
        
        buffer += ",N, gamma,beta_c,beta_u,beta_x, eta, CV";
        
//      /** To write the state of each cell  **/ 
//        for (int j = 0; j < bacteriaT.size(); j++) {
//            buffer += ",qx(" + j + ")";
//        }

        write(buffer);
    }

    @Override
    public void during() {
        String buffer = "" + sim.getFormattedTime();
//        
//     /** To write the state of each cell  **/ 
//        for(TargetBacterium b1 : bacteriaT) {
//        	  for (int j = 0; j < bacteriaT.get(0).grn_t.numEq; j++) {
//        	     	buffer+=","+b1.y[j];
//        	    	   }  
//        }
          
        double sum0=0;
        double sum1=0;
        double sum2=0;
        double sum3=0;
        
        /** To write the mean state over all cells  **/ 
        
          for(TargetBacterium b1 : bacteriaT) {
            	sum0=sum0+b1.y[0];
            	sum1=sum1+b1.y[1];
            	sum2=sum2+b1.y[2];
            	sum3=sum3+b1.y[3];
           
        }
          
          double gamma=0;
          double beta_c = 0;
          double beta_u=0;
          double beta_x=0;
          double eta=0;
          double CV=0;
            
            
            for(TargetBacterium b1 : bacteriaT) {
          	
          	gamma=b1.grn_t.gamma;
          	beta_c=b1.grn_t.beta_c;
          	beta_u=b1.grn_t.beta_u;
          	beta_x=b1.grn_t.beta_x;
          	eta=b1.grn_t.eta;
            CV=b1.grn_t.CV;
           
        }
           
            buffer += "," + sum0/bacteriaT.size()+","+sum1/bacteriaT.size() +","+sum2/bacteriaT.size() +
            		","+sum3/bacteriaT.size()+","+bacteriaT.size()+ ",";  //what to write (medium value of each state variable of the Target)
           
            buffer += gamma + "," +beta_c +"," + beta_u +"," + beta_x + "," + eta +"," +CV;
      
         /*   for(TargetBacterium b1 : bacteriaT) {
           // for (int j = 0; j < bacteriaT.size(); j++) {
                buffer +="," +b1.y[3] ;
            //}
            
            }*/
        	
          write(buffer);
    }
    
    
}
    
    


