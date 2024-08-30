package PID_Controller_new;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.ode.BSimOdeSolver;

//New class for the Controller population

public class ProportionalBacterium extends BSimCapsuleBacterium{
	
	public GRN_P grn_p;        // Instance of internal class
	public double[] y, yNew;   // Local values of ODE variables
    BSimChemicalField que_field;
    BSimChemicalField qxe_field;
   
    double que;
    double qxe;
    
	// Parameters
	double mu;      
	double theta; 
	double gamma; 
    double eta;
  
    // Control gaihn
    double beta_p;
    
    // Reference signal
    double Y;
    
    // Simulation time
     double time;
      
    // Parameter perturbations
     
     double CV;
	
    // Chamber volume
	 double V;  
	
	 //Parameter perturbations
	 boolean applyPerturbation;
	 
	/*
	* Constructor for a bacterium.
    */		           
			
	public ProportionalBacterium(BSim sim, Vector3d p1, Vector3d p2, BSimChemicalField que_field, BSimChemicalField qxe_field,
			 double gamma, double eta, double Y, double time, double CV, double V, boolean applyPerturbation) {
	
	
		super(sim, p1, p2);
		
		this.que_field = que_field;
		this.qxe_field = qxe_field;
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
		
		grn_p = new GRN_P(que_field, qxe_field, gamma, eta, Y, time, CV, applyPerturbation);      //Initialisation of the ODE system
		y = grn_p.getICs();                                                                       //Initialisation of the state variables
     	}
	

/******************************************************
 * Action each time step: iterate ODE system for one time-step.
 */
public void action() {                                 //what it will be executed at each time step for each bacterium in the ticker

		
		super.action();	
	  
		//get the concentration of the external QS molecule
		que = que_field.totalQuantity()/V;
		qxe = qxe_field.totalQuantity()/V;
		
		grn_p.setExternalFieldLevel(que,qxe);       //set the external concentrations of the QS molecules as they were get
		
		
		yNew = BSimOdeSolver.rungeKutta23(grn_p, sim.getTime(), y, sim.getDt());   //compute the new state vector value solving the ODE system
		y = yNew;     //the state vector is the new state vector                           
		

	}

public void Update_Chem_Field() {
	
double quDelta;	
double qxDelta;

//que = que_field.getQty(position);                          //get the concentration of the external QS molecule
//qxe = qxe_field.getQty(position);                          //get the concentration of the external QS molecule
 
quDelta  = que - y[0];	                                  //difference between external and internal QS concentrations
qxDelta  = qxe - y[1];	                                  //difference between external and internal QS concentrations

que_field.addQuantity(position, V*eta*(-quDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment    		
qxe_field.addQuantity(position, V*eta*(-qxDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment


}



	
	@Override
	public ProportionalBacterium divide() {
		BSimCapsuleBacterium bac1=super.divide();												//Create a new bacterium
		ProportionalBacterium child =new ProportionalBacterium(sim,bac1.x1,bac1.x2,que_field, qxe_field, gamma, 
				eta, Y, time, CV, V, applyPerturbation);	//Create a controlled bacterium based on the newborn characteristics
		for(int i = 0; i < grn_p.getNumEq(); i ++){
			
            // order dependent!
            child.y[i] = this.y[i] ; //the values of concentrations in the newborn are equal to that of the generator bacterium
           

        }

        return child;
    }
	
	

	}

