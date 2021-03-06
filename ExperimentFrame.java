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

import componentASW.ef.Generator;
import componentASW.ef.Transducer;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:40
 */
public class ExperimentFrame extends ViewableDigraph {

	private ViewableAtomic transducer_1_0;
	private ViewableAtomic generator_1_0;

	// Add Default Constructor
	public ExperimentFrame() {
		this("ExperimentFrame");
	}

	// Add Parameterized Constructor
	public ExperimentFrame(String name) {
		super(name);

// Structure information start
		// Add input port names
		addInport("wp_launch");
		addInport("engage_result");

		// Add output port names
		addOutport("scen_info");
		addOutport("entity_info");

//add test input ports:
		// String name,int x,int y,String status,int belong,int _type,String sendorder
		// addTestInput("wp_launch", new CombatEnt("aaa", 0, 0, "live", 1, 1, "sss"));

		// Initialize sub-components
		transducer_1_0 = new Transducer("t");
		generator_1_0 = new Generator("g");

		// Add sub-components
		add(transducer_1_0);
		add(generator_1_0);

		// Add Couplings
		addCoupling(this, "engage_result", transducer_1_0, "engage_result");
		addCoupling(this, "wp_launch", generator_1_0, "wp_launch");

		addCoupling(generator_1_0, "entity_gen", this, "entity_info");// 启动武器系统后，开始发送平台模型信息到武器系统中
		addCoupling(generator_1_0, "scen_gen", this, "scen_info");// 初始化想定，开始仿真指令--简化处理，想定场景信息内置于各个模型共享

// Structure information end
		initialize();
	}

	/**
	 * Automatically generated by the SimView program. Do not edit this manually, as
	 * such changes will get overwritten.
	 */
	public void layoutForSimView() {
		preferredSize = new Dimension(300, 180);
		((ViewableComponent) withName("t")).setPreferredLocation(new Point(-80, 15));
		((ViewableComponent) withName("g")).setPreferredLocation(new Point(-80, 100));
	}

}
