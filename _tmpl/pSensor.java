/*
*			Copyright Author
* (USE & RESTRICTIONS - Please read COPYRIGHT file)

* Version		: XX.XX
* Date		: 18-11-29 ����11:27
*/

// Default Package
package componentASW._tmpl;

import java.awt.Dimension;
import java.awt.Point;

import componentASW.platform.Sensor_Actor;
import componentASW.platform.Sensor_Updater;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:40
 */
public class pSensor extends ViewableDigraph {

	private ViewableAtomic updater;// 收集存储探测信息
	private ViewableAtomic actor;// 提供探测算法

	protected double processing_time;

	// Add Default Constructor
	public pSensor() {
		this("pSensor");
	}

	// Add Parameterized Constructors
	public pSensor(String name) {
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
		updater = new Sensor_Updater("updater");// 收集存储探测信息
		actor = new Sensor_Actor("actor");// 提供探测算法

		// Add sub-components
		add(updater);
		add(actor);

		// Add Couplings
		addCoupling(this, "engage_result", actor, "engage_result");
		addCoupling(this, "scen_info", actor, "scen_info");
		addCoupling(this, "env_info", actor, "env_info");
		addCoupling(this, "move_result", updater, "move_result");// 更新平台实体 位置信息

		addCoupling(updater, "response", actor, "response");
		addCoupling(actor, "request", updater, "request");

		addCoupling(actor, "threat_info", this, "threat_info");

// Structure information end
		initialize();
	}

	public void layoutForSimView() {
		preferredSize = new Dimension(550, 120);
		((ViewableComponent) withName("updater")).setPreferredLocation(new Point(-60, 15));
		((ViewableComponent) withName("actor")).setPreferredLocation(new Point(100, 45));
	}

}
