package PID_Controller_new;

import javax.vecmath.Vector3d;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.ode.BSimOdeSolver;

//New class for the Controller population

public class DerivativeBacterium extends BSimCapsuleBacterium{
	
	public GRN_D grn_d;     // Instance of internal class

	public double[] y, yNew;   // Local values of ODE variables

	protected BSimChemicalField  que_field;
	protected BSimChemicalField  qxe_field;
	
	double que;	
	double qxe;	
	 
	
	//Parameters
	double beta_D;
	double beta_A;
	double gamma_A;
    double gamma;
	double beta_M;
	double gamma_M;
	double k_A;
	double k_M;		
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
			
	public DerivativeBacterium(BSim sim, Vector3d p1, Vector3d p2, BSimChemicalField que_field, BSimChemicalField qxe_field,
			double gamma_A, double gamma, double gamma_M, double k_A, double k_M, double Y, double eta, double time, double CV, double V,
			boolean applyPerturbation){
		
		super(sim, p1, p2);

		this.que_field=que_field;
		this.qxe_field=qxe_field;
//		this.beta_D=beta_D;
// 		this.beta_A=beta_A;
 		this.gamma_A=gamma_A;
 		this.gamma=gamma;
// 		this.beta_M=beta_M;
 		this.gamma_M=gamma_M;
 		this.k_A=k_A;
 		this.k_M=k_M;
		this.Y=Y;
		this.eta=eta;
		this.time=time;
		this.CV=CV;
		this.V=V;
		this.applyPerturbation=applyPerturbation;
		
		
		grn_d = new GRN_D(que_field,qxe_field, gamma_A, gamma,  gamma_M, k_A, k_M, Y, eta,
				time, CV, applyPerturbation);   //Initialisation of the ODE system
		y = grn_d.getICs();                     //Initialisation of the state variables
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
		
		grn_d.setExternalFieldLevel(que,qxe);       //set the external concentrations of the QS molecules as they were get
	   
		yNew = BSimOdeSolver.rungeKutta23(grn_d, sim.getTime(), y, sim.getDt());   //compute the new state vector value solving the ODE system
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
	public DerivativeBacterium divide() {
		BSimCapsuleBacterium bac1=super.divide();												//Create a new bacterium
		DerivativeBacterium child =new DerivativeBacterium(sim, bac1.x1, bac1.x2, que_field, qxe_field, gamma_A, gamma, gamma_M, k_A, 
				k_M, Y,eta, time, CV, V, applyPerturbation);	//Create a controlled bacterium based on the newborn characteristics
		for(int i = 0; i < grn_d.getNumEq(); i ++){
            // order dependent!
            child.y[i] = this.y[i] ; //the values of concentrations in the newborn are equal to that of the generator bacterium
        }

        return child;
    }
	
	

	}

