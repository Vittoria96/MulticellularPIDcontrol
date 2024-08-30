package PID_Controller_new;

import java.util.Random;
import bsim.BSimChemicalField;
import bsim.ode.BSimOdeSystem;

public class GRN_D implements BSimOdeSystem{
    
	int numEq = 4;	// System of 4 equations

	BSimChemicalField que_field;
	BSimChemicalField qxe_field;
	
	double que;
    double qxe;
    
    // Parameters
    double beta_p=(8 *Math.pow(0.023, 4)* 1)/(0.1*0.06*0.03*0.3); 
    double beta_D=-((beta_p* 1.5)/(2*0.417)) + (9*Math.pow(0.023,4)*1.5)/(0.1* 0.417 *0.060* 0.03* 0.3);
    double beta_A=1.5;
    double gamma_A;
	double gamma;
	double beta_M=0.417;
    double gamma_M;
	double k_A;
	double k_M;		
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

    public GRN_D (BSimChemicalField que_field,BSimChemicalField qxe_field, double gamma_A, double gamma, 
    		double gamma_M, double k_A, double k_M, double Y, double eta, double time, double CV, boolean applyPerturbation) {
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
		//this.beta_D=beta_D;
 		//this.beta_A=beta_A;
 		this.gamma_A=gamma_A;
 		this.gamma=gamma;
 		//this.beta_M=beta_M;
 		this.gamma_M=gamma_M;
 		this.k_A=k_A;
 		this.k_M=k_M;
		this.Y=Y;
		this.eta=eta;
		this.time=time;
		this.CV=CV;
		this.applyPerturbation=applyPerturbation;
	
		 if(applyPerturbation) {
			 
			    beta_D=beta_D+beta_D*CV*rand;  
				beta_A=beta_A+beta_A*CV*rand;
			 	beta_M=beta_M+beta_M*CV*rand;
			 	
			 }
    }
		
		// The equations
	
		public double[] derivativeSystem(double x, double[] y) {
		double[] dy = new double[numEq];       //defines a vector with length numEq
		
		
		// System
		
		dy[0]=beta_A*y[1]-gamma_A*y[3]*y[0]/(k_A+y[0])-gamma*y[0];   //A
		dy[1]=beta_M*Y-gamma_M*y[0]*y[1]/(k_M+y[1]);     //M
		dy[2]=beta_D*y[0]+eta*(que-y[2])-gamma*y[2];  //qu
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


