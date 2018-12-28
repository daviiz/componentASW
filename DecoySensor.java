/*
*			Copyright Author
* (USE & RESTRICTIONS - Please read COPYRIGHT file)

* Version		: XX.XX
* Date		: 18-11-29 ����11:27
*/

// Default Package
package componentASW;

import java.awt.Dimension;
import java.awt.Point;

import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:40
 */
public class DecoySensor extends ViewableDigraph {

	private ViewableAtomic updater, actor;

	protected double processing_time;

	// Add Default Constructor
	public DecoySensor() {
		this("wSensor");
	}

	// Add Parameterized Constructors
	public DecoySensor(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("move_result");
		addInport("scen_info");
		addInport("env_info");
		addInport("engage_result");

		// Add output port names
		addOutport("threat_info");

//add test input ports:

		// Initialize sub-components
		updater = new DecoySensor_Updater("updater");
		actor = new DecoySensor_Actor("actor");

		// Add sub-components
		add(updater);
		add(actor);

		// Add Couplings
		addCoupling(this, "engage_result", actor, "engage_result");
		addCoupling(this, "scen_info", actor, "scen_info");
		addCoupling(this, "env_info", actor, "env_info");
		addCoupling(this, "move_result", updater, "move_result");

		addCoupling(updater, "response", actor, "response");
		addCoupling(actor, "request", updater, "request");

		addCoupling(actor, "threat_info", this, "threat_info");

// Structure information end
		initialize();
	}

	public void layoutForSimView() {
		preferredSize = new Dimension(550, 120);
		((ViewableComponent) withName("updater")).setPreferredLocation(new Point(-80, 15));
		((ViewableComponent) withName("actor")).setPreferredLocation(new Point(100, 45));
	}

}
