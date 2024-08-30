package PID_Controller_new;

import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;

public class P_Cells_Initialisation {
	
	double N_init;                         //bacRng is a random number
	BSim sim;
	
	BSimChemicalField que_field;
	BSimChemicalField qxe_field; 
	double mu;
	double theta;
	double gamma;
	double eta;
	double beta_p;
	double Y; 
	double time;
	double CV;
	double V;
	boolean applyPerturbation;
	
	ArrayList<ProportionalBacterium> bacteriaP;
	ArrayList<BSimCapsuleBacterium> bacteriaAll;
	
	public P_Cells_Initialisation(BSim sim, double N_init, BSimChemicalField que_field, BSimChemicalField qxe_field,  double gamma,
			double eta,double Y, double time, double CV, double V, boolean applyPerturbation, ArrayList<ProportionalBacterium> bacteriaP,
			ArrayList<BSimCapsuleBacterium> bacteriaAll) {
		
		this.N_init=N_init;
		this.sim=sim;
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
		this.V=V;
		this.applyPerturbation=applyPerturbation;
		this.bacteriaP=bacteriaP;
		this.bacteriaAll=bacteriaAll;
		
	}
	
	public void initial_P(double N_init){
		
    Random bacRng = new Random(); 
    
	for(double i=0;  i<N_init; i++) {
        
		double bL = 1. + 0.1*(bacRng.nextDouble() - 0.5);;        //length
		double angle = 0.1;                                       // angle
		
		//position of each bacteria in the vector "bacteria"
		
		Vector3d pos = new Vector3d(i+0.3,sim.getBound().y/2,
				bacRng.nextDouble()*0.1*(sim.getBound().z - 0.1)/2.0);                 //cells are positioned at the center of the chamber along a horizontal stripe
	  
		//Vector3d pos = new Vector3d(Math.random()*sim.getBound().x,Math.random()*sim.getBound().y,Math.random()*sim.getBound().z);  //cells are positioned randomly
			
		// Creates a new bacterium with defined position within the boundaries

		ProportionalBacterium bac = new ProportionalBacterium(sim,
				new Vector3d(pos.x - bL*Math.sin(angle), pos.y - bL*Math.cos(angle), pos.z),
				new Vector3d(bL*Math.sin(angle) + pos.x, bL*Math.cos(angle) + pos.y, pos.z), que_field, qxe_field, gamma, 
				eta,  Y, time, CV, V, applyPerturbation);
		
		bac.L = bL;
		bacteriaP.add(bac);   //adding bac to the vector   
		bacteriaAll.add(bac);
		
		
	}
	
}
}