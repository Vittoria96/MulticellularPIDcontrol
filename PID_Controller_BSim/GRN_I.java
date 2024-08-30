package PID_Controller_new;

import java.util.Random;
import bsim.BSimChemicalField;
import bsim.ode.BSimOdeSystem;

public class GRN_I implements BSimOdeSystem{
    
	int numEq = 4;	// System of 4 equations

	BSimChemicalField que_field;
	BSimChemicalField qxe_field;
	
	double que;
    double qxe;
    
    // Parameters
    double beta_I=0.0002;
    double mu=1;
    double theta=0.3;
	double gamma_z;
	double gamma;
	double Y;
    double eta;
	
	//Simulation time
    double time;
 
	//Parameters perturbation
    double CV;
    boolean applyPerturbation;     
    Random r = new Random();
    
 // Next pseudorandom Gaussian distributed double number with mean 0.0 and standard deviation 1.0
    double rand=r.nextGaussian(); 

    public GRN_I (BSimChemicalField que_field,BSimChemicalField qxe_field, double gamma_z, double gamma, 
    		double Y, double eta, double time, double CV, boolean applyPerturbation) {
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
//		this.beta_I=beta_I;
// 		this.mu=mu;
// 		this.theta=theta;
 		this.gamma_z=gamma_z;
 		this.gamma=gamma;
		this.Y=Y;
		this.eta=eta;
		this.time=time;
		this.CV=CV;
		this.applyPerturbation=applyPerturbation;
	
		 if(applyPerturbation) {
			 
			    beta_I=beta_I+beta_I*CV*rand;  
				mu=mu+mu*CV*rand;
			 	theta=theta+theta*CV*rand;
			 }
    }
		
		// The equations
	
		public double[] derivativeSystem(double x, double[] y) {
		double[] dy = new double[numEq];       //defines a vector with length numEq
		
		
		// System
		
		dy[0]=mu*Y-gamma_z*y[0]*y[1];   //z1
		dy[1]=theta*y[3]-gamma_z*y[0]*y[1];     //z2
		dy[2]=beta_I*y[0]+eta*(que-y[2])-gamma*y[2];  //qu
		dy[3]=eta*(qxe-y[3])-gamma*y[3];   //qx
		
		
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


