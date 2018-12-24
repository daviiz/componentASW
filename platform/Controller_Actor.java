package componentASW.platform;

import model.modeling.message;
import view.modeling.ViewableAtomic;
/**
	Then the Actor model operates proper tactical processes from target 
	tracking to tactical evasion with identified target information.
 * 
 * */

 public class Controller_Actor extends ViewableAtomic {
	
	 private double   tRECON; 
	 private double    tAPPRCH; 
	 private double     tCOMBAT;
	 private double    tEVASION;
	 private double     tCTRL ;
	 private double  tEND = INFINITY; 
	
	// Add Default Constructor
    public Controller_Actor(){
        this("Controller_Actor") ;   }
	
	// Add Parameterized Constructors
    public Controller_Actor(String name){
        super(name);
// Structure information start
        // Add input port names
        addInport("move_finishied");
        addInport("engage_result");
        addInport("scen_info");
        addInport("target_info");
        addInport("guidance_info");

        // Add output port names
        addOutport("move_cmd");
        addOutport("wp_launch");
        addOutport("wp_guidance");

//add test input ports:

// Structure information end
        initialize();
    }

    // Add initialize function
    public void initialize(){
        super.initialize();
        //S = {IDLE, RECONNNAISSANCE, APPROACH, COMBAT, EVASION, CONTROL, END} 状态集合：{ 空闲-侦查-接近-战斗-逃逸-结束 }
        phase = "IDLE";
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
    public message out(){return null;
    }

    // Add Show State function

}
