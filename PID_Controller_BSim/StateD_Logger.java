package PID_Controller_new;

import java.util.ArrayList;


import bsim.BSim;
import bsim.export.BSimLogger;

public class StateD_Logger extends BSimLogger {

	
	private ArrayList<DerivativeBacterium> bacteriaD;

	
	
	
	public StateD_Logger(BSim sim, String filename,ArrayList<DerivativeBacterium> bacteriaD) {
		
		super(sim, filename);

		this.bacteriaD=bacteriaD;
	
	}

	
	@Override
	public void before() {
        super.before();

        String buffer = "time";


        if(bacteriaD.size() > 0) {
            for (int j = 0; j < bacteriaD.get(0).grn_d.numEq; j++) {
                buffer += ",controller(" + j + ")";                       //what to write as title of each column
               
            }
        }
        buffer += ",N, beta_a, gamma_a, k_a, beta_m, gamma_m, k_m, eta, beta_D, gamma, CV";  //what to write as title of each column
        
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
        
        for(DerivativeBacterium b1 : bacteriaD) {
        	sum0=sum0+b1.y[0];
        	sum1=sum1+b1.y[1];
        	sum2=sum2+b1.y[2];
        	sum3=sum3+b1.y[3];
        }
        
        double gamma=0;
 		double beta_D=0;
 		double beta_A=0;
 		double gamma_A=0;
 		double beta_M=0;
 		double gamma_M=0;
 		double k_A=0;
 		double k_M=0;		
        double eta=0;
        double CV=0;
        
        for(DerivativeBacterium b1 : bacteriaD) {
        	beta_A=b1.grn_d.beta_A; 
        	gamma_A=b1.grn_d.gamma_A; 
        	gamma_M=b1.grn_d.gamma_M; 
        	k_A=b1.grn_d. k_A; 
        	beta_M=b1.grn_d.beta_M;
        	k_M=b1.grn_d.k_M;
	 	    eta=b1.grn_d.eta;
	 	    beta_D=b1.grn_d.beta_D;
	 	    gamma=b1.grn_d.gamma;
	 	    CV=b1.grn_d.CV;
        }

        buffer += "," + sum0/bacteriaD.size() + ","+sum1/bacteriaD.size() + ","+sum2/bacteriaD.size() 
        + ","+sum3/bacteriaD.size() +","+bacteriaD.size(); //what to write (medium value of each state variable of the Target)
        buffer += "," + beta_A +"," + gamma_A + "," + k_A + "," + beta_M + "," + gamma_M + "," + k_M + "," + eta + "," + beta_D +"," + gamma + "," + CV;
       
        write(buffer);
    }
    
    
}
    
    


