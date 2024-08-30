package PID_Controller_new;

import bsim.BSim;
import bsim.BSimChemicalField;
import bsim.export.BSimLogger;

public class Qe_Logger extends BSimLogger {

	BSimChemicalField que_field;
	BSimChemicalField qxe_field;
	
	
	
	public Qe_Logger(BSim sim, String filename, BSimChemicalField que_field, BSimChemicalField qxe_field) {
		
		super(sim, filename);
		
		this.que_field=que_field;
		this.qxe_field=qxe_field;
		
	}

	
	@Override
	public void before() {
        super.before();

        String buffer = "time,qxe,que";

        write(buffer);
    }

    @Override
    public void during() {
        String buffer = "" + sim.getFormattedTime();
      
                buffer +=","+qxe_field.getQty(0,0,0);
                buffer +=","+que_field.getQty(0,0,0);
                
        write(buffer);
    }
    
    
}
    
    


