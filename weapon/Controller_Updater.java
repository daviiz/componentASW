package componentASW.weapon;

import componentASW.om.CombatEnt;
import componentASW.om.OM_WP_Controller;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;
/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:25
 */
public class Controller_Updater extends ViewableAtomic {
	
	private double tIDENTIFY;
	
	private CombatEnt _threatInfo;
	
	// Add Default Constructor
    public Controller_Updater()
    {
        this("wpController_Updater") ;   
    }
	
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
        phase = "WAIT"; //WAIT IDENTIFICATION
        sigma = INFINITY;
        tIDENTIFY = 10.0;
        _threatInfo = null;
    }

    // Add external transition function
    public void deltext(double e, message x){
    	Continue(e);
    	for(int i=0; i< x.size();i++) {
    		if(phaseIs("WAIT"))
    		{
    			if(messageOnPort(x, "scen_info", i))
				{
    				
				}else if(messageOnPort(x, "threat_info", i))
				{
					_threatInfo =new CombatEnt( (CombatEnt)x.getValOnPort("threat_info", i));
					holdIn("IDENTIFICATION", tIDENTIFY);
				}
    		}else if(phaseIs("IDENTIFICATION"))
    		{
    			if(messageOnPort(x, "threat_info", i)) 
    			{
    				_threatInfo =new CombatEnt( (CombatEnt)x.getValOnPort("threat_info", i));
					holdIn("IDENTIFICATION", tIDENTIFY);
    			}
    		}
    	}
    }

    // Add internal transition function
    public void deltint(){
    	if(phaseIs("IDENTIFICATION"))
    	{
    		_threatInfo = OM_WP_Controller.Idntify(_threatInfo);
    	}
    }

    // Add confluent function
//    public void deltcon(double e, message x){
//    }

    // Add output function
    public message out(){
    	message m = new message();
    	if(phaseIs("IDENTIFICATION"))
    	{
    		content c = makeContent("target_info", _threatInfo);
    		m.add(c);
    	}
    	return m;
    }

	public double gettIDENTIFY() {
		return tIDENTIFY;
	}

	public void settIDENTIFY(double tIDENTIFY) {
		this.tIDENTIFY = tIDENTIFY;
	}

    // Add Show State function

}
