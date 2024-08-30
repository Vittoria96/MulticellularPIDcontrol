package PID_Controller_new;

import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;

public class I_Cells_Initialisation {
	
	double N_init;                         //bacRng is a random number
	BSim sim;
	
	BSimChemicalField que_field;
	BSimChemicalField qxe_field; 
	
	//Parameters
	double beta_I;
	double mu;
	double theta;
    double gamma_z;
    double gamma;
	double Y;
	double eta;

	//Simulation time
	double time;
	
	//Coefficient of variation when applying parameter perturbations
	double CV;
	boolean applyPerturbation;
	
	//Chamber volume
	double V;
	
	ArrayList<IntegralBacterium> bacteriaI;
	ArrayList<BSimCapsuleBacterium> bacteriaAll;
	
	public I_Cells_Initialisation(BSim sim, double N_init, BSimChemicalField que_field, BSimChemicalField qxe_field,  
			double gamma_z, double gamma, double Y, double eta, double time, double CV, double V, boolean applyPerturbation,
			ArrayList<IntegralBacterium> bacteriaI, ArrayList<BSimCapsuleBacterium> bacteriaAll) {
		
		this.N_init=N_init;
		this.sim=sim;
		this.que_field=que_field;
		this.qxe_field=qxe_field; 
//		this.beta_I=beta_I;
// 		this.mu=mu;
// 		this.theta=theta;
 		this.gamma_z=gamma_z;
 		this.gamma=gamma;
		this.eta=eta;
		this.Y=Y; 
		this.time=time;
		this.CV=CV;
		this.V=V;
		this.applyPerturbation=applyPerturbation;
		this.bacteriaI=bacteriaI;
		this.bacteriaAll=bacteriaAll;
		
	}
	
	public void initial_I(double N_init){
		
    Random bacRng = new Random(); 
    
    /* Initialising the vector of bacteria called "bacteriaD"*/

	for(int i=0;  i<N_init; i++) {
		        
		double bL = 1. + 0.1*(bacRng.nextDouble() - 0.5);;       //length
		double angle = 0.1;                                       // angle
		
		Vector3d pos = new Vector3d(sim.getBound().x/4+i+0.3,sim.getBound().y/2,
				bacRng.nextDouble()*0.1*(sim.getBound().z - 0.1)/2.0);               //cells are positioned at the center of the chamber along a horizontal stripe
		
	//	Vector3d pos = new Vector3d(Math.random()*sim.getBound().x,Math.random()*sim.getBound().y,Math.random()*sim.getBound().z); //cells are positioned randomly
		  
	
		// Creates a new bacterium with defined position within the boundaries
		

		IntegralBacterium bac = new IntegralBacterium(sim,
				new Vector3d(pos.x - bL*Math.sin(angle), pos.y - bL*Math.cos(angle), pos.z),
				new Vector3d(bL*Math.sin(angle) + pos.x, bL*Math.cos(angle) + pos.y, pos.z), que_field, qxe_field, gamma_z, gamma, Y,
				eta, time, CV, V, applyPerturbation);
		bac.L = bL;
		bacteriaI.add(bac);   //adding bac to the vector   
		bacteriaAll.add(bac);
	}

	
}
}