package componentASW.platform;

import componentASW.om.CombatEnt;
import componentASW.om.OM_Maneuver;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;
/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:19:43
 */
public class Maneuver_Updater extends ViewableAtomic {
	
	private double iINTERPRETATION = 0;
	
	private CombatEnt currEnt;
	
	// Add Default Constructor
    public Maneuver_Updater(){
        this("Maneuver_Updater") ;   }
	
	// Add Parameterized Constructors
    public Maneuver_Updater(String name){
        super(name);
// Structure information start
        // Add input port names
        addInport("move_cmd");

        // Add output port names
        addOutport("cmd_info");

//add test input ports:

// Structure information end
        initialize();
    }

    // Add initialize function
    public void initialize(){
        super.initialize();
        phase = "WAIT"; // WAIT  INTERPRETATION
        sigma = INFINITY; //
        currEnt = null;
    }

    // Add external transition function
    public void deltext(double e, message x){
    	Continue(e);
    	for(int i = 0;i<x.size();i++) {
    		if(phaseIs("WAIT")) {
    			holdIn("INTERPRETATION",iINTERPRETATION);
    		}else if(phaseIs("INTERPRETATION")) {
    			if(messageOnPort(x,"move_cmd",i)) {
    				currEnt = new CombatEnt((CombatEnt)x.getValOnPort("move_cmd", i));
    			}
    		}
    	}
    }

    // Add internal transition function
    public void deltint(){
    	if(phaseIs("INTERPRETATION")) {
    		//execute om function：
    		currEnt = OM_Maneuver.Cmd_Inerpreter(currEnt.getSendorder());
    		
			holdIn("WAIT",INFINITY);
		}
    }

    // Add confluent function
//    public void deltcon(double e, message x){
//    }

    // Add output function
    public message out(){
		if (phaseIs("INTERPRETATION")) {
			message m = new message();
			content con = makeContent("cmd_info", currEnt);
			m.add(con);
			return m;
		} else {
			return null;
		}
	}

    // Add Show State function

}
