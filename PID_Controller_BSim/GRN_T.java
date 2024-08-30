package PID_Controller_new;


import java.util.Random;

import bsim.BSimChemicalField;
import bsim.ode.BSimOdeSystem;

public class GRN_T implements BSimOdeSystem{
    
	
	int numEq = 4;				            // System of 4 equations
	
	BSimChemicalField que_field;
	BSimChemicalField  qxe_field;

    double que;
    double qxe;

    // Parameters
 	double gamma;
 	double beta_c=0.1;
 	double beta_u=0.06;
    double beta_x=0.03;
    double eta;
    
    // Simulation time
    double time;

    //Parameters perturbation
    double CV;
    boolean applyPerturbation;     
    Random r = new Random();
    
    // Next pseudorandom Gaussian distributed double number with mean 0.0 and standard deviation 1.0
    double rand=r.nextGaussian(); 

   	
		public GRN_T (BSimChemicalField que_field,BSimChemicalField qxe_field, double gamma, double eta,
				double time, double CV, boolean applyPerturbation) {
			
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
		this.gamma=gamma;
//		this.beta_c=beta_c;
//		this.beta_u=beta_u;
//		this.beta_x=beta_x;
		this.eta=eta;
		this.time=time;
		this.CV=CV;
		this.applyPerturbation=applyPerturbation;
		
		if(applyPerturbation) {
			 
			    beta_c=beta_c+beta_c*CV*rand; 
			    beta_u=beta_u+beta_u*CV*rand;
			    beta_x=beta_x+beta_x*CV*rand;
 }
		

	}
		
		
		// The equations
	
		public double[] derivativeSystem(double x, double[] y) {
		double[] dy = new double[numEq];       //defines a vector with length numEq

		
		// System
		
		dy[0]= -gamma*y[0]+beta_u*y[2];   //x1
		dy[1]=beta_c*y[0]-gamma*y[1];     //xc
		dy[2]=eta*(que-y[2])-gamma*y[2];  //qu
		dy[3]=beta_x*y[1]+eta*(qxe-y[3])-gamma*y[3];   //qx
		
		return dy;
		
	}
	// Initial conditions for the ODE system
	public double[] getICs() {
		double[] ics = new double[numEq];
		
			// Start synchronised
			ics[0] = 0;
			ics[1] = 0;
			ics[2] = 0;
			ics[3] = 0;
		
		
		return ics;
	}
	
	public int getNumEq() {
         return numEq;}


public void setExternalFieldLevel(double _que,double _qxe){
   
    this.que = _que;
    this.qxe = _qxe;}
}


