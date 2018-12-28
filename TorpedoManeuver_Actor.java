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
 * @DATATIME 2018年12月25日 下午4:19:36
 */
public class TorpedoManeuver_Actor extends ViewableAtomic {

	private double t_MOVE = 5;
	private double t_FUEL = 0;

	private CombatEnt cmd_info_ent;
	private CombatEnt env_info_ent;
	private CombatEnt engage_result_ent;

	private boolean cmd_check;

	private boolean move_finished;

	private boolean fuel_check;

	private boolean fuel_exhausted;

	// Add Default Constructor
	public TorpedoManeuver_Actor() {
		this("Maneuver_Actor");
	}

	// Add Parameterized Constructors
	public TorpedoManeuver_Actor(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("engage_result");
		addInport("scen_info");
		addInport("env_info");
		addInport("cmd_info");

		// Add output port names
		addOutport("move_finished");
		addOutport("fuel_exhasuted");
		addOutport("move_result");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();
		phase = "IDLE";
		sigma = INFINITY;
		cmd_info_ent = null;
		env_info_ent = null;
		engage_result_ent = null;
		cmd_check = false;
		move_finished = false;
		fuel_check = false;
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("IDLE")) {
				if (messageOnPort(x, "scen_info", i)) {
					// do nothing ...
				} else if (messageOnPort(x, "cmd_info", i)) {
					cmd_info_ent = new CombatEnt((CombatEnt) x.getValOnPort("cmd_info", i)); // 包含机动指令
					holdIn("MOVE", t_MOVE);
				}
			} else if (phaseIs("MOVE")) {
				if (messageOnPort(x, "cmd_info", i)) {
					cmd_info_ent = new CombatEnt((CombatEnt) x.getValOnPort("cmd_info", i)); // 包含机动指令
					holdIn("MOVE", t_MOVE);
				} else if (messageOnPort(x, "env_info", i)) {
					env_info_ent = new CombatEnt((CombatEnt) x.getValOnPort("env_info", i)); // 环境信息
					holdIn("MOVE", t_MOVE);
				} else if (messageOnPort(x, "engage_result", i)) {
					engage_result_ent = new CombatEnt((CombatEnt) x.getValOnPort("engage_result", i)); // 交战结果
					holdIn("IDLE", INFINITY);
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {

		if (phaseIs("MOVE")) {
			cmd_info_ent = OM_Maneuver.Motion_Equation(cmd_info_ent);//
			move_finished = OM_Maneuver.Cmd_Check(cmd_info_ent);
			if (move_finished) {
				holdIn("IDLE", INFINITY);
			} else {
				cmd_info_ent = OM_Maneuver.Motion_Equation(cmd_info_ent); // move_result
				holdIn("FUEL", 0);
			}
		} else if (phaseIs("FUEL")) {
			fuel_check = OM_Maneuver.Fuel_Check(cmd_info_ent);
			fuel_exhausted = fuel_check;
			if (fuel_check) {
				holdIn("IDLE", INFINITY);
			} else {
				holdIn("MOVE", t_MOVE);
			}
		}

	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("FUEL")) {
			content con = makeContent("fuel_exhasuted", new entity(fuel_check + ""));
			m.add(con);

		} else if (phaseIs("MOVE")) {
			content con = makeContent("move_finished", new entity(move_finished + ""));
			m.add(con);

			content con2 = makeContent("move_result", new CombatEnt(cmd_info_ent));
			m.add(con2);
		}
		return m;
	}

	public double getT_MOVE() {
		return t_MOVE;
	}

	public void setT_MOVE(double t_MOVE) {
		this.t_MOVE = t_MOVE;
	}

	// Add Show State function

}
