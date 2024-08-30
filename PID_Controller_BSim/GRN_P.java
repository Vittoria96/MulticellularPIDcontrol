package PID_Controller_new;

import java.util.Random;

import bsim.BSimChemicalField;
import bsim.ode.BSimOdeSystem;

public class GRN_P implements BSimOdeSystem{
	
	int numEq = 2;       // System of 2 equations
	   
    BSimChemicalField que_field;
    BSimChemicalField qxe_field;
   
    double que;
    double qxe;
    
	// Parameters
	double mu=1;      
	double theta=0.3; 
	double gamma; 
    double eta;
  
    // Control gaihn
    double beta_p=(8 *Math.pow(0.023, 4)* 1)/(0.1*0.06*0.03*0.3);
    
    // Reference signal
    double Y;
    
    // Simulation time
     double time;
      
    
    //Parameters perturbation
    boolean applyPerturbation;
    double CV;
    Random r = new Random();
    
   // Next pseudorandom Gaussian distributed double number with mean 0.0 and standard deviation 1.0
    double rand=r.nextGaussian(); 
    
   	
		public GRN_P (BSimChemicalField que_field, BSimChemicalField qxe_field,  double gamma, double eta, 
				double Y, double time, double CV, boolean applyPerturbation) {
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
//		this.mu=mu;
//		this.theta=theta;
		this.gamma=gamma;
		this.eta=eta;
//		this.beta_p=beta_p;
		this.Y=Y;
		this.time=time;		
		this.CV=CV;
		this.applyPerturbation=applyPerturbation;
		
		if(applyPerturbation) {
				 
			mu=mu+mu*CV*rand;  
			theta=theta+theta*CV*rand; 
			beta_p=beta_p+beta_p*CV*rand;
			
		}
	}
	
		

    
	// The equations
	
	public double[] derivativeSystem(double x, double[] y) {
	double[] dy = new double[numEq];       //defines a vector with length numEq
	

	
	// System
	
	dy[0]=beta_p*Y*mu*Y/(mu*Y+theta*y[1])+eta*(que-y[0])-gamma*y[0];  //qu
	dy[1]=eta*(qxe-y[1])-gamma*y[1];        //qx                             

	
	return dy;
	

	}
	
	// Initial conditions for the ODE system
	public double[] getICs() {
		double[] ics = new double[numEq];
	
		// Start synchronised
		ics[0] = 0;
		ics[1] = 0;
		
	
	return ics;
	
	}

	public int getNumEq() {
	
     return numEq;
     
	}
	public void setExternalFieldLevel(double _que,double _qxe){
    
    this.que = _que;
    this.qxe = _qxe;
    
	}
	
}
