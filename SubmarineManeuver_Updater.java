package componentASW;

import GenCol.entity;
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
public class SubmarineManeuver_Updater extends ViewableAtomic {

	private double iINTERPRETATION = 0;

	private entity move_cmd_ent;
	
	private CombatEnt cmd_info_ent;


	// Add Default Constructor
	public SubmarineManeuver_Updater() {
		this("Maneuver_Updater");
	}

	// Add Parameterized Constructors
	public SubmarineManeuver_Updater(String name) {
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
	public void initialize() {
		super.initialize();
		phase = "WAIT"; // WAIT INTERPRETATION
		sigma = INFINITY; //
		move_cmd_ent = new entity();
		cmd_info_ent = new CombatEnt();
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("WAIT")) {
				if (messageOnPort(x, "move_cmd", i)) {
					move_cmd_ent = new CombatEnt((CombatEnt) x.getValOnPort("move_cmd", i));
					holdIn("INTERPRETATION", iINTERPRETATION);
				}
			} else if (phaseIs("INTERPRETATION")) {
				if (messageOnPort(x, "move_cmd", i)) {
					move_cmd_ent = new CombatEnt((CombatEnt) x.getValOnPort("move_cmd", i));
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {
		if (phaseIs("INTERPRETATION")) {
			// execute om function：
			cmd_info_ent = OM_Maneuver.Cmd_Inerpreter("submarine",move_cmd_ent.getName());

			holdIn("WAIT", INFINITY);
		}
	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("INTERPRETATION")) {

			content con = makeContent("cmd_info",  cmd_info_ent);
			m.add(con);
		}
		return m;
	}

	// Add Show State function

}
