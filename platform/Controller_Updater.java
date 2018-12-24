package componentASW.platform;

import model.modeling.message;
import view.modeling.ViewableAtomic;
/**
 The Updater model receives threat information entering the Controller model,
 and updates and identifies whether it is a target or not. 

X = {‘‘threat_info’’, ‘‘scen_info’’}
Y = {‘‘target_info’’}
S = {WAIT, IDENTIFICATION}

 * 
 * */
 public class Controller_Updater extends ViewableAtomic {
	
	private double  tIDNTFY;
	
	// Add Default Constructor
    public Controller_Updater(){
        this("Controller_Updater") ;   }
	
	// Add Parameterized Constructors
    public Controller_Updater(String name){
        super(name);
// Structure information start
        // Add input port names
        addInport("threat_info");
        addInport("scen_info");

        // Add output port names
        addOutport("target_info");

//add test input ports:

// Structure information end
        initialize();
    }

    // Add initialize function
    public void initialize(){
        super.initialize();
        tIDNTFY = 2;  //2 sec
        phase = "WAIT"; //S=WAIT, IDENTIFICATION
        sigma = INFINITY;
    }

    // Add external transition function
    public void deltext(double e, message x){
    }

    // Add internal transition function
    public void deltint(){
    }

    // Add confluent function
    public void deltcon(double e, message x){
    }

    // Add output function
    public message out(){
    	return null;
    }

    // Add Show State function

}
