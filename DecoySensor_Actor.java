package componentASW;

import GenCol.entity;
import componentASW.om.CombatEnt;
import componentASW.om.OM_Sensor;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:19:48
 */
public class DecoySensor_Actor extends ViewableAtomic {

	private double tCYCLE = 9;

	private entity engage_result_ent;

	private entity responseEntity;

	private CombatEnt track_info_ent;

	// Add Default Constructor
	public DecoySensor_Actor() {
		this("Sensor_Actor");
	}

	// Add Parameterized Constructors
	public DecoySensor_Actor(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("engage_result");
		addInport("scen_info");
		addInport("env_info");
		addInport("response");

		// Add output port names
		addOutport("threat_info");// track_info
		addOutport("request");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();
		phase = "IDLE"; // s = { IDLE PERIOD DETECT REQUEST }
		sigma = INFINITY;
		
		engage_result_ent = new entity();
		responseEntity = new entity();
		track_info_ent = new CombatEnt();
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		engage_result_ent = null;
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("IDLE")) {
				if (messageOnPort(x, "scen_info", i)) {
					holdIn("PERIOD", tCYCLE);
				}

			} else if (phaseIs("PERIOD")) {
				if (messageOnPort(x, "engage_result", i)) {
					engage_result_ent = x.getValOnPort("engage_result", i);
					holdIn("IDLE", INFINITY);
				} else if (messageOnPort(x, "env_info", i)) {
					holdIn("PERIOD", tCYCLE);
				}
			} else if (phaseIs("DETECT")) {
				if (messageOnPort(x, "engage_result", i)) {
					engage_result_ent = x.getValOnPort("engage_result", i);
					holdIn("IDLE", INFINITY);
				}
			} else if (phaseIs("REQUEST")) {
				if (messageOnPort(x, "response", i)) {
					responseEntity = x.getValOnPort("response", i);
					holdIn("DETECT", 0);
				} else if (messageOnPort(x, "env_info", i)) {
					holdIn("REQUEST", INFINITY);
				}
			}
		}

	}

	// Add internal transition function
	public void deltint() {
		if (phaseIs("DETECT")) {
			track_info_ent = OM_Sensor.Detection_Algorithm("decoy");
			holdIn("PERIOD", tCYCLE);

		} else if (phaseIs("PERIOD")) {
			holdIn("REQUEST", INFINITY);
		}
	}

	// Add confluent function
//	public void deltcon(double e, message x) {
//	}

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("DETECT")) {
			content _cContent = makeContent("track_info", new CombatEnt(track_info_ent));
			m.add(_cContent);
		} else if (phaseIs("PERIOD")) {
			// content _cContent = makeContent("threat_info", new
			// CombatEnt(responseEntity));
			content _cContent = makeContent("threat_info", new CombatEnt());
			m.add(_cContent);
		}
		return m;
	}

	public double gettCYCLE() {
		return tCYCLE;
	}

	public void settCYCLE(double tCYCLE) {
		this.tCYCLE = tCYCLE;
	}

	// Add Show State function

}
