package componentASW.platform;

import componentASW.om.CombatEnt;
import componentASW.om.OM_PF_Controller;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * The Updater model receives threat information entering the Controller model,
 * and updates and identifies whether it is a target or not.
 * 
 * X = {‘‘threat_info’’, ‘‘scen_info’’} Y = {‘‘target_info’’} S = {WAIT,
 * IDENTIFICATION}
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:19:07
 */
public class Controller_Updater extends ViewableAtomic {

	private double tIDNTFY;

	protected CombatEnt threatInfo;

	// Add Default Constructor
	public Controller_Updater() {
		this("Controller_Updater");
	}

	// Add Parameterized Constructors
	public Controller_Updater(String name) {
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
	public void initialize() {
		super.initialize();
		settIDNTFY(2); // 2 sec
		phase = "WAIT"; // S=WAIT, IDENTIFICATION
		sigma = INFINITY;
		tIDNTFY = 120;
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("WAIT")) {
				// for (int i = 0; i < x.size(); i++) {
				if (messageOnPort(x, "scen_info", i)) {
					// do nothing ...
				} else if (messageOnPort(x, "threat_info", i)) {
					holdIn("IDENTIFICATION", tIDNTFY);
				}
				// }
			}
			if (phaseIs("IDENTIFICATION")) {
				threatInfo = null;
				// for (int i = 0; i < x.size(); i++) {
				if (messageOnPort(x, "threat_info", i)) {
					threatInfo = new CombatEnt((CombatEnt) x.getValOnPort("threat_info", i));
					// }
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {
		if (phaseIs("IDENTIFICATION")) {
			threatInfo = OM_PF_Controller.Identification(threatInfo);
			holdIn("WAIT", INFINITY);
		}

	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function --lambda λ
	public message out() {
		if (phaseIs("IDENTIFICATION")) {
			message m = new message();
			content con = makeContent("target_info", threatInfo);
			m.add(con);
			return m;
		} else {
			return null;
		}
	}

	public double gettIDNTFY() {
		return tIDNTFY;
	}

	public void settIDNTFY(double tIDNTFY) {
		this.tIDNTFY = tIDNTFY;
	}

	// Add Show State function

}
