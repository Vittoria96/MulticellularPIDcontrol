package PID_Controller_new;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.ode.BSimOdeSolver;

//New class for the Controller population

public class IntegralBacterium extends BSimCapsuleBacterium{
	
	public GRN_I grn_i;     // Instance of internal class
	public GRN_P grn_p;
	public GRN_T grn_t;
	public double[] y, yNew;   // Local values of ODE variables

	protected BSimChemicalField  que_field;
	protected BSimChemicalField  qxe_field;
	
	double que;	
	double qxe;	
	 
	
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
	
    //Parameters perturbation
    double CV;

    //Chamber volume
	double V;
	
   //Parameters perturbation
	boolean applyPerturbation;
	
	/*
	* Constructor for a bacterium.
    */		           
			
	public IntegralBacterium(BSim sim, Vector3d p1, Vector3d p2, BSimChemicalField que_field, BSimChemicalField qxe_field,
			double gamma_z, double gamma, double Y, double eta, double time, double CV, double V, boolean applyPerturbation){
		
		super(sim, p1, p2);

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
		this.V=V;
		this.applyPerturbation=applyPerturbation;
		
		
		grn_i = new GRN_I(que_field,qxe_field, gamma_z, gamma, Y, eta,
				time, CV, applyPerturbation);   //Initialisation of the ODE system
		y = grn_i.getICs();                     //Initialisation of the state variables
     	}
	

/******************************************************
 * Action each time step: iterate ODE system for one time-step.
 */
public void action() {                                 //what it will be executed at each time step for each bacterium in the ticker

		super.action();	
	  
		//get the concentration of the external QS molecule
		
		que = que_field.totalQuantity()/V;
		qxe = qxe_field.totalQuantity()/V;
		
		//que=que_field.getQty(position);
		//qxe=qxe_field.getQty(position);  
		
		grn_i.setExternalFieldLevel(que,qxe);       //set the external concentrations of the QS molecules as they were get
	   
		yNew = BSimOdeSolver.rungeKutta23(grn_i, sim.getTime(), y, sim.getDt());   //compute the new state vector value solving the ODE system
		y = yNew;     //the state vector is the new state vector                           
		

	}

public void Update_Chem_Field(double time) {
	
	this.time=sim.getTime();
	
double quDelta;	
double qxDelta;

//que = que_field.totalQuantity()/V;                          //get the concentration of the external QS molecule
//qxe = qxe_field.totalQuantity()/V;                          //get the concentration of the external QS molecule
 
quDelta  = que - y[2];	                                  //difference between external and internal QS concentrations
qxDelta  = qxe - y[3];                                      //difference between external and internal QS concentrations

que_field.addQuantity(position,V*eta*(-quDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment    		
qxe_field.addQuantity(position,V* eta*(-qxDelta)*sim.getDt());     //Add the cell's contribution to the new value of the QS molecule in the environment

}

	
	@Override
	public IntegralBacterium divide() {
		BSimCapsuleBacterium bac1=super.divide();												//Create a new bacterium
		IntegralBacterium child =new IntegralBacterium(sim, bac1.x1, bac1.x2, que_field, qxe_field,  gamma_z, gamma, Y, eta, time, CV,
				V, applyPerturbation);	//Create a controlled bacterium based on the newborn characteristics
		for(int i = 0; i < grn_i.getNumEq(); i ++){
            // order dependent!
            child.y[i] = this.y[i] ; //the values of concentrations in the newborn are equal to that of the generator bacterium
        }

        return child;
    }
	
	

	}

