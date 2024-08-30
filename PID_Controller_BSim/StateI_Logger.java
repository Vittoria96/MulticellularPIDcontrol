package PID_Controller_new;

import java.util.ArrayList;


import bsim.BSim;
import bsim.export.BSimLogger;

public class StateI_Logger extends BSimLogger {

	
	private ArrayList<IntegralBacterium> bacteriaI;

	
	
	
	public StateI_Logger(BSim sim, String filename,ArrayList<IntegralBacterium> bacteriaI) {
		
		super(sim, filename);

		this.bacteriaI=bacteriaI;
	
	}

	
	@Override
	public void before() {
        super.before();

        String buffer = "time";


        if(bacteriaI.size() > 0) {
            for (int j = 0; j < bacteriaI.get(0).grn_i.numEq; j++) {
                buffer += ",controller(" + j + ")";                       //what to write as title of each column
               
            }
        }
        buffer += ",N, beta_I, mu, theta, gamma_z, gamma, eta, CV";  //what to write as title of each column
        
        write(buffer);
    }

    @SuppressWarnings("unused")
	@Override
    public void during() {
        String buffer = "" + sim.getFormattedTime();
 
        double sum0=0;
        double sum1=0;
        double sum2=0;
        double sum3=0;
        
        for(IntegralBacterium b1 : bacteriaI) {
        	sum0=sum0+b1.y[0];
        	sum1=sum1+b1.y[1];
        	sum2=sum2+b1.y[2];
        	sum3=sum3+b1.y[3];
        }
        
        
 		double beta_I=0;
 		double mu=0;
 		double theta=0;
 		double gamma_z=0;
 		double gamma=0;		
        double eta=0;
        double CV=0;
        
        for(IntegralBacterium b1 : bacteriaI) {
        	beta_I=b1.grn_i.beta_I; 
        	mu=b1.grn_i.mu; 
        	theta=b1.grn_i.theta; 
        	gamma_z=b1.grn_i.gamma_z;
	 	    eta=b1.grn_i.eta;
	 	    gamma=b1.grn_i.gamma;
	 	    CV=b1.grn_i.CV;
        }

        buffer += "," + sum0/bacteriaI.size() + ","+sum1/bacteriaI.size() + ","+sum2/bacteriaI.size() 
        + ","+sum3/bacteriaI.size() +","+bacteriaI.size(); //what to write (medium value of each state variable of the Target)
        buffer += "," + beta_I +"," + mu + "," + theta + "," + gamma_z+ "," + gamma + "," + eta + "," + CV;
       
        write(buffer);
    }
    
    
}
    
    


