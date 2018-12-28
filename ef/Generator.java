/*
*			Copyright Author
* (USE & RESTRICTIONS - Please read COPYRIGHT file)

* Version		: XX.XX
* Date		: 18-11-29 ����11:27
*/

// Default Package
package componentASW.ef;

import componentASW.om.CombatEnt;
import componentASW.om.SimParameter;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:16:31
 */
public class Generator extends ViewableAtomic {

	protected CombatEnt shipEnt;

	// Add Default Constructor
	public Generator() {
		this("Generator");
	}

	// Add Parameterized Constructors
	public Generator(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("wp_launch");
		// Add output port names
		addOutport("scen_gen");
		addOutport("entity_gen");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();
		phase = "active";
		sigma = 0;
		shipEnt = new CombatEnt();
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);

		for (int i = 0; i < x.getLength(); i++) {
			if (messageOnPort(x, "wp_launch", i)) {
				shipEnt = new CombatEnt((CombatEnt) x.getValOnPort("wp_launch", i));
			}
		}
	}

	// Add internal transition function
	public void deltint() {
	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("active")) {
			content con = makeContent("scen_gen", new CombatEnt(1, "warship", 0, 5000, 1, 1, 0, SimParameter.Speed_Ship,
					SimParameter.Detection_range_Ship, 999999,"初始化"));
			m.add(con);
			// new CombatEnt(id, name, x, y, status, belong, _type, speed, detect_range,
			// live_time)
			content con2 = makeContent("scen_gen", new CombatEnt(2, "submarine", 0, 0, 1, -1, 0,
					SimParameter.Speed_Submarine, SimParameter.Detection_range_Submarine, 999999,"初始化"));
			m.add(con2);

//			content con3 = makeContent("entity_gen", new CombatEnt(SimParameter._ship));
//			m.add(con3);
//			
//			content con4 = makeContent("entity_gen", new CombatEnt(SimParameter._submarine));
//			m.add(con4);

//			if (!shipEnt.eq("combatEntBase")) {
//				content con2 = makeContent("entity_gen", shipEnt);
//				m.add(con2);
//			}
			passivate();
		}
		return m;
	}
}
