package PID_Controller_new;

import java.util.Calendar;

import bsim.BSim;
import bsim.export.BSimLogger;

public class Setting_Logger extends BSimLogger {

	double tStart = 0;
	double tEnd = 0;
	double external_decay;
	double N_P;
	double N_I;
	double N_T;

	
	public Setting_Logger(BSim sim, String filename, double external_decay, double N_P,double N_I, double N_T) {
		super(sim, filename);
		this.external_decay=external_decay;
		this.N_P=N_P;
		this.N_I=N_I;
		this.N_T=N_T;

	}

	
	@Override
	public void before() {
		super.before();
		tStart = Calendar.getInstance().getTimeInMillis();
		// Write parameters of the simulation
		write("Dt," + sim.getDt()); 
		write("Time (sec)," + sim.getSimulationTime());
        write("Initial Conditions, Uniform");
        write("ge,"+external_decay);
        write("N_P"+ N_P);
        write("N_I"+ N_I);
        write("N_T"+ N_T);
       
	}
	
	@Override
	public final void during() {
		// Ignore this...
	}
	
	
	public void after(){
		// Elapsed time (real time)
		tEnd = Calendar.getInstance().getTimeInMillis();
		write("Elapsed time (sec)," + ((tEnd - tStart)/60));
		super.after();
	}

}
