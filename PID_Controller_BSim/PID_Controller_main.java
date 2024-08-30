package PID_Controller_new;

import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.BSimTicker;
import bsim.BSimUtils;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.capsule.IteratorMover;
import bsim.draw.BSimDrawer;
import bsim.export.BSimLogger;
import bsim.export.BSimPngExporter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class PID_Controller_main {
	

public static void main(String[] args) {
	
	Locale.setDefault(new Locale("en", "US"));
	
	/*********************************************************
     * System parameters
     */
	
	double gamma=0.023;
// 	double beta_c=0.1;
// 	double beta_u=0.06;
//    double beta_x=0.03;
    double eta=2;
	//double mu=1;      
	//double theta=0.3;
	double gamma_z=0.01;
	//double beta_A=1.5;
	double gamma_A=1.5;
   // double beta_M=0.417;
	double gamma_M=1.5;
	double k_A=1;
	double k_M=1;	
	
    /** constant reference signal **/
    
    double Y=60;
    
//  /** step reference signal **/
    
//    if(time<1000) {
//    	Y=30;
//    	}else{
//    		if(time<2000) {
//    			Y=60;
//    			}else{
//    				if(time<3000) { 
//    					Y=90;
//    					}else{
//    						Y=30; 
//    						}
//    				}
//    		} 

    
    /** control gains**/
    
  //  double beta_p= (8 * Math.pow(gamma, 4) * mu)/(0.1 * 0.06 * 0.03 * theta);  
   // double beta_i=0.0002;
   // double beta_d=-((beta_p * gamma_M)/(2 * beta_M)) + (9 * Math.pow(gamma,4) * 1.5)/(0.1 * beta_M * 0.06 * 0.03 * theta);
    
 // /** combination of the previous extracted random values of the control gains **/

    //beta_p=0.0420300000000000; beta_d=0.0788400000000000; beta_I=0.000155000000000000;
    //beta_p=0.0385200000000000; beta_d=0.0835200000000000; beta_I=0.000208000000000000;
    //beta_p=0.0373500000000000; beta_d=0.1044000000000000; beta_I=0.000182000000000000;
    //beta_p=0.0445500000000000; beta_d=0.0943200000000000; beta_I=0.000195000000000000;
    //beta_p=0.0355500000000000; beta_d=0.0993600000000000; beta_I=0.000160000000000000;
    //beta_p=0.0393300000000000; beta_d=0.0878400000000000; beta_I=0.000171000000000000;
    //beta_p=0.0429300000000000; beta_d=0.0968400000000000; beta_I=0.000198000000000000;
    //beta_p=0.0391500000000000; beta_d=0.0968400000000000; beta_I=0.000165000000000000;
    //beta_p=0.0413100000000000; beta_d=0.0784800000000000; beta_I=0.000199000000000000;
    //beta_p=0.0394200000000000; beta_d=0.0907200000000000; beta_I=0.000178000000000000;
    
	/*********************************************************
	 * Change parameters when applying parameter perturbations (applyPerturbation=true)
	 */
    
    boolean applyPerturbation_T=true;   //apply perturbation on Target cells
    boolean applyPerturbation_P=true;   //apply perturbation on Proportional cells
    boolean applyPerturbation_I=true;   //apply perturbation on Integral cells
    boolean applyPerturbation_D=true;   //apply perturbation on Integral cells
    
	
    // coefficient of variation    
    double CV=0;
    
    Random r = new Random();
    
    // Next pseudorandom Gaussian distributed double number with mean 0.0 and standard deviation 1.0
    double rand=r.nextGaussian(); 

    
    if(applyPerturbation_T) {
    	
//    	beta_c=beta_c+beta_c*CV*rand; 
//	    beta_u=beta_u+beta_u*CV*rand;
//	    beta_x=beta_x+beta_x*CV*rand;
    }
    
//    if(applyPerturbation_P) {
//		 
//		mu=mu+mu*CV*rand;  
//		theta=theta+theta*CV*rand; 
//		beta_p=beta_p+beta_p*CV*rand;
//		
//	}
//    
//    if(applyPerturbation_I) {
//		 
//	    beta_i=beta_i+beta_i*CV*rand;  
//		mu=mu+mu*CV*rand;
//	 	theta=theta+theta*CV*rand;
//	 }
//    
//    if(applyPerturbation_D) {
//		 
//	    beta_d=beta_d+beta_d*CV*rand;  
//		beta_A=beta_A+beta_A*CV*rand;
//	 	beta_M=beta_M+beta_M*CV*rand;
//	 
//	 }
    
    
	/*********************************************************
	 * Create a new simulation object
	 */
	

	final BSim sim = new BSim();	// New Sim (simulation) object
	sim.setDt(0.001);				// Simulation Timestep
	sim.setSimulationTime(2000);    // Simulation Length (seconds)
	sim.setTimeFormat("0.00");		// Time Format for display
	//sim.setBound(7.52,15,1);		// Simulation Boundaries
	sim.setBound(46.5,15,1);      // Simulation boundaries to have the same targets' number 
    
	double time=sim.getTime();      // Get simulation time
	double V=sim.getBound().x*sim.getBound().y*sim.getBound().z;   // Chamber volume
	
	/*********************************************************
     * Set up the chemical fields
     */
    double external_diffusivity = 800;    //Diffusivity of the molecule

    double external_decay = 0.0023;         //External degradation rate
    
    int []boxes={(int) 1,  1, 1};       //Number of boxes along each dimension
    
   // Initialisation of the chemical fields for the simulator and with a specified number of boxes, chemical diffusivity and decay rate.
	
    final BSimChemicalField que_field = new BSimChemicalField(sim, boxes, external_diffusivity, external_decay);
    final BSimChemicalField qxe_field = new BSimChemicalField(sim, boxes, external_diffusivity, external_decay);
   
	
	// Set up the list of the Controller Bacteria and the list of the Target Bacteria 
			 
	final ArrayList<ProportionalBacterium> bacteriaP = new ArrayList<ProportionalBacterium>();
	final ArrayList<IntegralBacterium> bacteriaI =  new ArrayList<IntegralBacterium>();
	final ArrayList<DerivativeBacterium> bacteriaD =  new ArrayList<DerivativeBacterium>();
    final ArrayList<TargetBacterium> bacteriaT =  new ArrayList<TargetBacterium>();
    
   // Set up a list of All Bacteria of the simulation (to use for example for the mover)
    
    final ArrayList<BSimCapsuleBacterium> bacteriaAll =  new ArrayList<BSimCapsuleBacterium>();

	
	 /*********************************************************
		 * Initialising cells
	     */
	
	double N_init_T=sim.getBound().x/4;     //initial number of target bacteria
	double N_init_P=sim.getBound().x/4;     //initial number of proportional bacteria
	double N_init_I=sim.getBound().x/4;     //initial number of proportional bacteria
	double N_init_D=sim.getBound().x/4;     //initial number of proportional bacteria

	
    // Initialising the vector of bacteria called "bacteriaP"
    
	P_Cells_Initialisation P_cells=new P_Cells_Initialisation(sim, N_init_P, que_field, qxe_field,  gamma, eta, Y, time, CV, V,
			applyPerturbation_P,  bacteriaP,  bacteriaAll);
	
	P_cells.initial_P(N_init_P);
	
	 // Initialising the vector of bacteria called "bacteriaI"
    
		I_Cells_Initialisation I_cells=new I_Cells_Initialisation(sim,N_init_I, que_field, qxe_field, gamma_z, gamma, Y, 
				eta, time,CV, V, applyPerturbation_I, bacteriaI, bacteriaAll);
		
		I_cells.initial_I(N_init_I);
		
	 // Initialising the vector of bacteria called "bacteriaD"
	    
		D_Cells_Initialisation D_cells=new D_Cells_Initialisation(sim, N_init_D, que_field, qxe_field, gamma_A, gamma,  gamma_M,
				 k_A, k_M, Y, eta, time, CV, V, applyPerturbation_D, bacteriaD, bacteriaAll);
			
		D_cells.initial_D(N_init_D);
    
	 // Initialising the vector of bacteria called "bacteriaT"
	
	T_Cells_Initialisation T_cells=new T_Cells_Initialisation(sim, N_init_T, que_field, qxe_field, gamma, eta, time, CV, V,
			applyPerturbation_T, bacteriaT, bacteriaAll);
	
	T_cells.initial_T(N_init_T);
		        	
	final ArrayList<ProportionalBacterium> pborn = new ArrayList<ProportionalBacterium>();  // new born cells
    final ArrayList<ProportionalBacterium> pdead = new ArrayList<ProportionalBacterium>();  // dead cells
    
    final ArrayList<IntegralBacterium> iborn = new ArrayList<IntegralBacterium>();  // new born cells
    final ArrayList<IntegralBacterium> idead = new ArrayList<IntegralBacterium>();  // dead cells
    
    final ArrayList<DerivativeBacterium> dborn = new ArrayList<DerivativeBacterium>();  // new born cells
    final ArrayList<DerivativeBacterium> ddead = new ArrayList<DerivativeBacterium>();  // dead cells
    
    final ArrayList<TargetBacterium> tborn = new ArrayList<TargetBacterium>();
    final ArrayList<TargetBacterium> tdead = new ArrayList<TargetBacterium>();
    
    /*********************************************************
   	 * Set up the mover
        */

    final IteratorMover mover = new IteratorMover(bacteriaAll);
    
    /*********************************************************
	 * Set up the ticker
     */
     	              
   
	BSimTicker ticker = new PID_Controller_Ticker(bacteriaP, bacteriaI, bacteriaD, bacteriaT, bacteriaAll, mover, pborn, iborn, dborn, tborn, pdead, idead, 
		    ddead, tdead, sim, que_field, qxe_field);
	sim.setTicker(ticker); 

    
    /*********************************************************
	 * Set up the drawer
     */
	BSimDrawer drawer = new Chamber_Drawer(sim, 800,600, bacteriaP, bacteriaI, bacteriaD, bacteriaT); 
	sim.setDrawer(drawer);
		

	/*********************************************************
	 * Create a new directory for the simulation results
	*/
		
	String filePath = BSimUtils.generateDirectoryPath("D:\\Vittoria_DATA\\OneDrive - Università di Napoli Federico II\\BSim\\results_PID/paper_PID/CV=" + CV + "_controller/"+BSimUtils.timeStamp() +"/");
	//String filePath = BSimUtils.generateDirectoryPath("D:\\Vittoria_DATA\\OneDrive - Università di Napoli Federico II\\BSim\\results_PID/paper_PID/prova/"+BSimUtils.timeStamp() +"/");

	/*********************************************************
	 *  Create Loggers
	 *  
	 *  setDt() to reduce the amount of data.
	*/
//		
//	BSimLogger stats_Logger = new Setting_Logger(sim, filePath + "Settings.csv", external_decay);
//	sim.addExporter(stats_Logger);
//		
	
	BSimLogger target_logger = new StateT_Logger(sim, filePath + "target.csv",bacteriaT);
	target_logger.setDt(1);			// Set export time step
	sim.addExporter(target_logger);
	
	BSimLogger proportional_logger = new StateP_Logger(sim, filePath + "controllerP.csv", bacteriaP);
	proportional_logger.setDt(1);			// Set export time step
	sim.addExporter(proportional_logger);
	
	BSimLogger Integral_logger = new StateI_Logger(sim, filePath + "controllerI.csv", bacteriaI);
	Integral_logger.setDt(1);			// Set export time step
	sim.addExporter(Integral_logger);
	
	BSimLogger Derivative_logger = new StateD_Logger(sim, filePath + "controllerD.csv", bacteriaD);
	Derivative_logger.setDt(1);			// Set export time step
	sim.addExporter(Derivative_logger);
	
	BSimLogger qe_logger = new Qe_Logger(sim, filePath + "qe.csv",que_field,qxe_field);
	qe_logger.setDt(1);			// Set export time step
	sim.addExporter(qe_logger);
	
	BSimPngExporter im  = new BSimPngExporter(sim, drawer, filePath);
	im.setDt(1);
	sim.addExporter(im);
		
	//BSimMovExporter movExporter = new BSimMovExporter(sim, drawer, filePath + "BSim.avi");
	//movExporter.setDt(1);
	//sim.addExporter(movExporter);	
		
	sim.export();
	
}


}

