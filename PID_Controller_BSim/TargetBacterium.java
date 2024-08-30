package PID_Controller_new;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.ode.BSimOdeSolver;

public class TargetBacterium extends BSimCapsuleBacterium{
	
	public GRN_T grn_t;     // Instance of internal class
	public double[] y, yNew;   // Local values of ODE variables
	
	BSimChemicalField que_field;
	BSimChemicalField  qxe_field;

    double que;
    double qxe;

    // Parameters
 	double gamma;
 	double beta_c;
 	double beta_u;
    double beta_x;
    double eta;
    
    // Simulation time
    double time;

    //Parameters perturbation
    double CV;
	
    // Chamber volume
	double V;
	
	//Parameters perturbation
	boolean applyPerturbation;
	
	/*
	* Constructor for a bacterium.
    */		           
			
	public TargetBacterium(BSim sim, Vector3d p1, Vector3d p2,BSimChemicalField que_field, BSimChemicalField qxe_field, double gamma, double beta_c, 
			double beta_u, double beta_x, double eta, double time, double CV, double V, boolean applyPerturbation) {
		super(sim, p1, p2);
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
		this.gamma=gamma;
		this.beta_c=beta_c;
		this.beta_u=beta_u;
		this.beta_x=beta_x;
		this.eta=eta;
		this.time=time;
		this.CV=CV;
		this.V=V;
		this.applyPerturbation=applyPerturbation;
		
		
		grn_t = new GRN_T(que_field, qxe_field, gamma, eta, time, CV, applyPerturbation);             //Initialisation of the ODE system
		y = grn_t.getICs();                                                                                //Initialisation of the state variables
     	}
	


/******************************************************
 * Action each time step: iterate ODE system for one time-step.
 */
public void action() {                                 //what it will be executed at each time step and for each bacterium in the ticker

		
		super.action();	
	      
		//get the concentration of the external QS molecule
		
		que = que_field.totalQuantity()/V;
		qxe = qxe_field.totalQuantity()/V;
		
		grn_t.setExternalFieldLevel(que,qxe);       //set the external concentrations of the QS molecules as they were get
	

		yNew = BSimOdeSolver.rungeKutta23(grn_t, sim.getTime(), y, sim.getDt());   //compute the new state vector value solving the ODE system
		y = yNew;   //the state vector is the new state vector   
		
	}

public void Update_Chem_Field() {
	
double quDelta;	
double qxDelta;

//que = que_field.getQty(position);                          //get the concentration of the external QS molecule
//qxe = qxe_field.getQty(position);                          //get the concentration of the external QS molecule


quDelta  = que - y[2];	                                  //difference between external and internal QS concentrations
qxDelta  = qxe - y[3];	                                  //difference between external and internal QS concentrations

que_field.addQuantity(position, V*eta*(-quDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment    		
qxe_field.addQuantity(position, V*eta*(-qxDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment


}


	
	@Override
	public TargetBacterium divide() {
		BSimCapsuleBacterium bac1=super.divide();												//Create a new bacterium
		TargetBacterium child =new TargetBacterium(sim,bac1.x1,bac1.x2,que_field, qxe_field, gamma, beta_c, beta_u, 
				beta_x, eta, time, CV, V, applyPerturbation);	//Create a controlled bacterium based on the newborn characteristics
		for(int i = 0; i < grn_t.getNumEq(); i ++){
			
			    
            // order dependent!
            child.y[i] = this.y[i] ; //the values of concentrations in the newborn are equal to that of the generator bacterium
           
		 
        }

        return child;
    }
	


	}



