package componentASW;

import componentASW.om.MessageBus;

import GenCol.entity;
import componentASW.om.CombatEnt;
import componentASW.om.OM_Sensor;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:19:54
 */
public class SubmarineSensor_Updater extends ViewableAtomic {

	private CombatEnt move_result_ent;

	private entity request_ent;

	private CombatEnt response_ent;

	private double tREQUEST = 2;

	// Add Default Constructor
	public SubmarineSensor_Updater() {
		this("Sensor_Updater");
	}

	// Add Parameterized Constructors
	public SubmarineSensor_Updater(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("move_result");
		addInport("request");

		// Add output port names
		addOutport("response");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();
		phase = "UPDATE"; // S={ UPDATE REQUEST }
		sigma = INFINITY;
		tREQUEST = 0;
		response_ent = new CombatEnt();
		move_result_ent = new CombatEnt();
		request_ent = new entity();
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		move_result_ent = new CombatEnt();
		request_ent = null;
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("UPDATE")) {
				if (messageOnPort(x, "move_result", i)) {
					if(x.getValOnPort("move_result", i).eq("submarine")) {
						move_result_ent = (CombatEnt) x.getValOnPort("move_result", i);
					}
					OM_Sensor.Data_Integrator(move_result_ent);
				} else if (messageOnPort(x, "request", i)) {
					request_ent =  x.getValOnPort("request", i);
					holdIn("REQUEST", tREQUEST);
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {
		if (phaseIs("REQUEST")) {
			holdIn("UPDATE", INFINITY);
		}
	}

	// Add confluent function
//	public void deltcon(double e, message x) {
//	}

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("REQUEST")) {
			content _c = makeContent("response", new CombatEnt(response_ent));
			m.add(_c);
		}
		return m;
	}

	// Add Show State function

}
