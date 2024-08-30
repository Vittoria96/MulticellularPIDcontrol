package PID_Controller_new;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import bsim.BSim;
import bsim.capsule.BSimCapsuleBacterium;
import bsim.draw.BSimP3DDrawer;
import processing.core.PConstants;
import processing.core.PGraphics3D;

public class Chamber_Drawer extends BSimP3DDrawer {

	private ArrayList<ProportionalBacterium> bacteriaP;
	private ArrayList<IntegralBacterium> bacteriaI;
	private ArrayList<TargetBacterium> bacteriaT;
	private ArrayList<DerivativeBacterium>bacteriaD;
	
	
	public Chamber_Drawer(BSim sim, int width, int height, ArrayList<ProportionalBacterium> bacteriaP,ArrayList<IntegralBacterium> bacteriaI,
			ArrayList<DerivativeBacterium> bacteriaD,ArrayList<TargetBacterium> bacteriaT) {
		super(sim, width, height);
		this.bacteriaP=bacteriaP;
		this.bacteriaT=bacteriaT;
		this.bacteriaI=bacteriaI;
		this.bacteriaD=bacteriaD;
	}

	/***
	 *  Draw the default cuboid boundary of the simulation as a partially transparent box
	 * with a wireframe outline surrounding it.
	 */
	
	@Override
	public void boundaries() {
		p3d.noFill();
	    p3d.stroke(0, 0, 0);
	    p3d.pushMatrix();
	    p3d.translate((float)boundCentre.x,(float)boundCentre.y,(float)boundCentre.z);
	    p3d.box((float)bound.x, (float)bound.y, (float)bound.z);
	    p3d.popMatrix();
	    p3d.noStroke();
	}

	@Override
	
	//Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color 
	//management,and text layout. This is the fundamental class for rendering2-dimensional shapes, text and images on the Java(tm) platform.
	
	public void draw(Graphics2D g) {
		p3d.beginDraw();

		//if(!cameraIsInitialised){
			//p3d.camera(-(float)bound.x*0.7f, -(float)bound.y*0.3f,-(float)bound.z*0.5f, 
		    	//	(float)bound.x, (float)bound.y, (float)bound.z,
		    		//0,1,0);
		            //cameraIsInitialised = true;
	               // }
		
		
		if(!cameraIsInitialised){
			p3d.camera((float)bound.x*0.5f, (float)bound.y*0.5f,
					// Set the Z offset to the largest of X/Y dimensions for a reasonable zoom-out distance:
					sim.getBound().x > sim.getBound().y ? (float)sim.getBound().x : (float)sim.getBound().y,
							(float)bound.x*0.5f, (float)bound.y*0.5f, 0,
							0,1,0);
			cameraIsInitialised = true;
	                }

		p3d.textFont(font);
	    p3d.textMode(PConstants.SCREEN);
        p3d.sphereDetail(10);
        p3d.noStroke();
        p3d.background(255, 255,255);

        scene(p3d);
        boundaries();
        time();
        p3d.endDraw();
   
		
 
        g.drawImage(p3d.image, 0,0, null);
   
	}
	
	
	            
    public void scene(PGraphics3D p3d) {
 
    	p3d.ambientLight(128, 128, 128);
        p3d.directionalLight(128, 128, 128, 1, 1, -1);
        
		for(BSimCapsuleBacterium b : bacteriaP) {
			draw(b, new Color(171,205,239));
			//draw(b, new Color(226,231,250));
		}
		
		for(BSimCapsuleBacterium b : bacteriaI) {
			draw(b, new Color(255,182,193));
			//draw(b, new Color(255,231,244));
		}
		
		for(BSimCapsuleBacterium b : bacteriaD) {
			draw(b, new Color(223,183,255));
			//draw(b, new Color(244,233,251));
		}
		
		
		for(TargetBacterium b1 : bacteriaT) {
			
			/**draw(b1, new Color(207	, (int) 235, 156	));
			//draw(b1, new Color(212,241,217));
		}*/
			if(b1.y[1]/2>255) {
				draw(b1, new Color(20	, (int) 255, 140	));
			}else {
				if(b1.y[1]/2>130) {
					draw(b1, new Color(91	, (int)b1.y[1]/2 , 140	));
				}else{
					draw(b1, new Color(91	, (int) 130, 140	));
				}
				
				}
		
	
		
    }}}
    

