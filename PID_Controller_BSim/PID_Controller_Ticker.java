package PID_Controller_new;

import java.util.ArrayList;
import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.BSimTicker;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.capsule.IteratorMover;


public class PID_Controller_Ticker extends BSimTicker {    //Extends BSimTicker 

	
	private ArrayList<ProportionalBacterium> bacteriaP;     //list of Controller bacteria
	private ArrayList<IntegralBacterium> bacteriaI;     //list of Controller bacteria
	private ArrayList<DerivativeBacterium> bacteriaD;     //list of Controller bacteria
	private ArrayList<TargetBacterium> bacteriaT;     //list of Target bacteria
	private ArrayList<BSimCapsuleBacterium> bacteriaAll;    //list of All bacteria
	private IteratorMover mover;
	private ArrayList<ProportionalBacterium> pborn;         //list of born bacteria of the Controller
    private ArrayList<ProportionalBacterium> pdead;         //list of dead bacteria of the Controller
    private ArrayList<IntegralBacterium> iborn;         //list of born bacteria of the Controller
    private ArrayList<IntegralBacterium> idead;         //list of dead bacteria of the Controller
    private ArrayList<DerivativeBacterium> dborn;         //list of born bacteria of the Controller
    private ArrayList<DerivativeBacterium> ddead;         //list of dead bacteria of the Controller
    private ArrayList<TargetBacterium> tborn;         //list of born bacteria of the Target
    private ArrayList<TargetBacterium> tdead;         //list of dead bacteria of the Target
	private BSim sim;                   
	private BSimChemicalField que_field;
	private BSimChemicalField qxe_field;
	
 
	//Constructor for the Ticker
	
	public PID_Controller_Ticker(ArrayList<ProportionalBacterium> bacteriaP,ArrayList<IntegralBacterium> bacteriaI, ArrayList<DerivativeBacterium> bacteriaD,
			ArrayList<TargetBacterium> bacteriaT, ArrayList<BSimCapsuleBacterium> bacteriaAll,IteratorMover mover,ArrayList<ProportionalBacterium> pborn,
			ArrayList<IntegralBacterium> iborn, ArrayList<DerivativeBacterium> dborn, ArrayList<TargetBacterium> tborn,ArrayList<ProportionalBacterium> pdead,
			ArrayList<IntegralBacterium> idead, ArrayList<DerivativeBacterium> ddead, ArrayList<TargetBacterium> tdead,BSim sim,BSimChemicalField que_field,
			BSimChemicalField qxe_field) {

	
		this.bacteriaP = bacteriaP;
		this.bacteriaT = bacteriaT;
		this.bacteriaI=bacteriaI;
		this.bacteriaD=bacteriaD;
		this.bacteriaAll = bacteriaAll;
		this.mover = mover;
		this.pborn = pborn;
		this.iborn = iborn;
		this.dborn = dborn;
		this.tborn = tborn;
		this.pdead = pdead;
		this.ddead = ddead;
		this.tdead = tdead;
		this.idead = idead;
		this.sim = sim;
		this.que_field=que_field;
		this.qxe_field=qxe_field;
		
	}

    

	@Override
	
	
    public void tick() {

		
        System.out.println(bacteriaP.size());   //Printing the number of the Controller bacteria at each time step
        System.out.println(bacteriaI.size());   //Printing the number of the Target bacteria at each time step
        System.out.println(bacteriaD.size());   //Printing the number of the Target bacteria at each time step
        System.out.println(bacteriaT.size());   //Printing the number of the Target bacteria at each time step
               
    
        for(ProportionalBacterium b : bacteriaP) {
        	
            b.action();   //function to run for each bacteria of the Controller
            b.grow();     //function for the growth
            
            // Divide if grown past threshold
            if(b.L > b.L_th){
            
             pborn.add(b.divide());

             }
        }
        
       
        for(IntegralBacterium b : bacteriaI) {
        	
            b.action();   //function to run for each bacteria of the Controller
            b.grow();     //function for the growth
            
            // Divide if grown past threshold
            if(b.L > b.L_th){
            
             iborn.add(b.divide());

             }
        }
        
        for(DerivativeBacterium b : bacteriaD) {
        	
            b.action();   //function to run for each bacteria of the Controller
            b.grow();     //function for the growth
            
            // Divide if grown past threshold
            if(b.L > b.L_th){
            
             dborn.add(b.divide());

             }
        }
          
          	
    	        
        
         for(TargetBacterium b : bacteriaT) {
        	
        	 b.action();   //function to run for each bacteria of the Target
        	 b.grow();     //function for the growth
            	
            // Divide if grown past threshold
            if(b.L > b.L_th){
            	
             tborn.add(b.divide());
         	  
            } }	
         
         /**
          * Update the Chemical fields
          */
         
         for(ProportionalBacterium b : bacteriaP) {
        	 
        	 b.Update_Chem_Field();
        	 
         }
         

         for(IntegralBacterium b : bacteriaI) {
        	 
        	 b.Update_Chem_Field(sim.getTime());
        	 
         }
         
         for(DerivativeBacterium b : bacteriaD) {
        	 
        	 b.Update_Chem_Field(sim.getTime());
        	 
         }
         
         
         for(TargetBacterium b : bacteriaT) {
        	 
        	 b.Update_Chem_Field();
        	 
         }

     		que_field.update();            //updates que chemical field by diffusing and decaying
     	    qxe_field.update();            //updates qxe chemical field by diffusing and decaying
     	   
    		
     	      	   
        // Adding the born cells to the vector of all bacteria

        bacteriaP.addAll(pborn);
        bacteriaT.addAll(tborn);
        bacteriaI.addAll(iborn);
        bacteriaD.addAll(dborn);
        bacteriaAll.addAll(pborn);
        bacteriaAll.addAll(tborn);
        bacteriaAll.addAll(iborn);
        bacteriaAll.addAll(dborn);

        pborn.clear();
        tborn.clear();
        iborn.clear();
        dborn.clear();

       
        // Neighbour interactions 

        mover.move();

        // Boundaries/removal
    
        // Removal
        for(ProportionalBacterium b : bacteriaP){
            // Kick out if past the boundary
            if((b.x1.y < 0) && (b.x2.y < 0)){
                pdead.add(b);
            }
            if((b.x1.y > sim.getBound().y) && (b.x2.y > sim.getBound().y)){
                pdead.add(b);
            } //the check should be done also for x
            
        }
        
        for(IntegralBacterium b : bacteriaI){
            // Kick out if past the boundary
            if((b.x1.y < 0) && (b.x2.y < 0)){
                idead.add(b);
            }
            if((b.x1.y > sim.getBound().y) && (b.x2.y > sim.getBound().y)){
                idead.add(b);
            } //the check should be done also for x
                   
        }
        
        for(DerivativeBacterium b : bacteriaD){
            // Kick out if past the boundary
            if((b.x1.y < 0) && (b.x2.y < 0)){
                ddead.add(b);
            }
            if((b.x1.y > sim.getBound().y) && (b.x2.y > sim.getBound().y)){
                ddead.add(b);
            } //the check should be done also for x
                   
        }
        
        for(TargetBacterium b : bacteriaT){
            // Kick out if past the boundary
            if((b.x1.y < 0) && (b.x2.y < 0)){
                tdead.add(b);
            }
            if((b.x1.y > sim.getBound().y) && (b.x2.y > sim.getBound().y)){
                tdead.add(b);
            } //the check should be done also for x
            
        }
        
        //Removing the dead cells from the Controller and the Target and form the vector of all bacteria
        
        bacteriaP.removeAll(pdead);
        bacteriaAll.removeAll(pdead);
        bacteriaI.removeAll(idead);
        bacteriaD.removeAll(ddead);
        bacteriaAll.removeAll(ddead);
        bacteriaAll.removeAll(idead);
        bacteriaT.removeAll(tdead);
        bacteriaAll.removeAll(tdead);
        pdead.clear();
        tdead.clear();
        idead.clear();
        ddead.clear();

        
        }
	

	   

}
