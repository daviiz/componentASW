package componentASW.weapon;

import GenCol.entity;
import componentASW.om.CombatEnt;
import componentASW.om.OM_WP_Controller;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:20
 */
public class Controller_Actor extends ViewableAtomic {

	private double tAPPRCH;
	private double tCTRL;

	private entity move_finished_Ent;
	private CombatEnt target_info_Ent;
	private entity wp_guidance_Ent;
	private entity engage_result_Ent;

	// Add Default Constructor
	public Controller_Actor() {
		this("wpController_Actor");
	}

	// Add Parameterized Constructors
	public Controller_Actor(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("move_finishied");
		addInport("engage_result");
		addInport("scen_info");
		addInport("target_info");
		addInport("wp_guidance");

		// Add output port names
		addOutport("move_cmd");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();

		phase = "IDLE"; // IDLE SEARCH APRCH_WAIT APPROACH CTRL CTRL_WAIT END
		sigma = INFINITY;
		tAPPRCH = 10;
		tCTRL = 5;

	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		move_finished_Ent = null;
		target_info_Ent = null;
		wp_guidance_Ent = null;
		engage_result_Ent = null;
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("IDLE")) {
				if (messageOnPort(x, "scen_info", i)) {
					holdIn("SEARCH", 0);
				} else if (messageOnPort(x, "move_finishied", i)) {
					move_finished_Ent = x.getValOnPort("move_finishied", i);
					holdIn("SEARCH", 0);
				} else if (messageOnPort(x, "target_info", i)) {

					target_info_Ent = new CombatEnt((CombatEnt) x.getValOnPort("target_info", i));
					holdIn("APPROACH", 0);
				} else if (messageOnPort(x, "wp_guidance", i)) {

					wp_guidance_Ent = x.getValOnPort("wp_guidance", i);
					holdIn("CTRL", 0);
				} else if (messageOnPort(x, "engage_result", i)) {

					engage_result_Ent = x.getValOnPort("engage_result", i);
					holdIn("END", INFINITY);
				}

			} else if (phaseIs("APRCH_WAIT")) {
				if (messageOnPort(x, "target_info", i)) {
					target_info_Ent = new CombatEnt((CombatEnt) x.getValOnPort("target_info", i));
					holdIn("APPROACH", 0);
				}

			} else if (phaseIs("CTRL_WAIT")) {
				if (messageOnPort(x, "wp_guidance", i)) {
					wp_guidance_Ent = x.getValOnPort("wp_guidance", i);
					holdIn("CTRL", 0);
				} else if (messageOnPort(x, "engage_result", i)) {

					engage_result_Ent = x.getValOnPort("engage_result", i);
					holdIn("END", INFINITY);
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {
		//// IDLE SEARCH APRCH_WAIT APPROACH CTRL CTRL_WAIT END
		if (phaseIs("SEARCH")) {
			OM_WP_Controller.Tactical_Search(0);
			holdIn("IDLE", INFINITY);
		} else if (phaseIs("APRCH_WAIT")) {
			holdIn("SEARCH", 0);
		} else if (phaseIs("APPROACH")) {
			OM_WP_Controller.Apprch(target_info_Ent);
			holdIn("APRCH_WAIT", tAPPRCH);
		} else if (phaseIs("CTRL")) {
			holdIn("CTRL_WAIT", 0);
		}
	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("SEARCH") || phaseIs("APPROACH") || phaseIs("CTRL")) {
			content _c = makeContent("move_cmd", new entity("move_cmd"));
			m.add(_c);
		}
		return m;
	}

	public double gettAPPRCH() {
		return tAPPRCH;
	}

	public void settAPPRCH(double tAPPRCH) {
		this.tAPPRCH = tAPPRCH;
	}

	public double gettCTRL() {
		return tCTRL;
	}

	public void settCTRL(double tCTRL) {
		this.tCTRL = tCTRL;
	}

	// Add Show State function

}
