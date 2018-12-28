package componentASW;

import GenCol.entity;
import componentASW.om.CombatEnt;
import componentASW.om.OM_PF_Controller;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * Then the Actor model operates proper tactical processes from target tracking
 * to tactical evasion with identified target information.
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:44
 */
public class WarshipController_Actor extends ViewableAtomic {

	private double tRECON = 10;
	private double tAPPRCH = 5;
	private double tCOMBAT = 20;
	private double tEVASION = 20;
	private double tCTRL = 15;
	private double tEND = INFINITY;

	private CombatEnt scen_info_ent; // 战舰实体状态信息
	
	
	
	private entity move_finished_ent;
	private entity engage_result_ent;
	private CombatEnt target_info_ent;
	private CombatEnt guidance_info_ent;
	
	// Add Default Constructor
	public WarshipController_Actor() {
		this("Controller_Actor");
	}

	// Add Parameterized Constructors
	public WarshipController_Actor(String name) {
		super(name);
// Structure information start
		// Add input port names
		addInport("move_finishied");
		addInport("engage_result");
		addInport("scen_info");
		addInport("target_info");
		addInport("guidance_info");

		// Add output port names
		addOutport("move_cmd");
		addOutport("wp_launch");
		addOutport("wp_guidance");

//add test input ports:

// Structure information end
		initialize();
	}

	// Add initialize function
	public void initialize() {
		super.initialize();
		// S = {IDLE, RECONNNAISSANCE, APPROACH, COMBAT, EVASION, CONTROL, END} 状态集合：{
		// 空闲-侦查-接近-战斗-逃逸-结束 }
		phase = "IDLE";
		sigma = INFINITY;
		
		scen_info_ent = new CombatEnt();
		move_finished_ent = new entity();
		target_info_ent = new CombatEnt();
		engage_result_ent = new entity();
		guidance_info_ent = new CombatEnt();
	}

	// Add external transition function
	public void deltext(double e, message x) {
		Continue(e);
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("IDLE")) {
				if (messageOnPort(x, "scen_info", i)) {
					if (x.getValOnPort("scen_info", i).getName().equals("warship")) {
						scen_info_ent = new CombatEnt((CombatEnt) x.getValOnPort("scen_info", i));
						holdIn("RECONNNAISSANCE", tRECON);
					}
				} else if (messageOnPort(x, "move_finished", i)) {
					move_finished_ent = x.getValOnPort("move_finished", i);
					holdIn("RECONNNAISSANCE", tRECON);
				} else if (messageOnPort(x, "target_info", i)) {
					target_info_ent =  new CombatEnt((CombatEnt)x.getValOnPort("target_info", i));
					holdIn("APPROACH", tAPPRCH);
				} else if (messageOnPort(x, "guidance_info", i)) {
					guidance_info_ent = new CombatEnt((CombatEnt)x.getValOnPort("guidance_info", i));
					holdIn("CONTROL", tCTRL);
				} else if (messageOnPort(x, "engage_result", i)) {
					engage_result_ent = x.getValOnPort("engage_result", i);
					holdIn("END", INFINITY);
				}
			} else if (phaseIs("COMBAT")) {
				if (messageOnPort(x, "target_info", i)) {
					target_info_ent =  new CombatEnt((CombatEnt)x.getValOnPort("target_info", i));
					holdIn("COMBAT", tCOMBAT);
				}
			} else if (phaseIs("CONTROL")) {
				if (messageOnPort(x, "target_info", i)) {
					target_info_ent =  new CombatEnt((CombatEnt)x.getValOnPort("target_info", i));
					holdIn("CONTROL", tCTRL);
				} else if (messageOnPort(x, "engage_result", i)) {
					engage_result_ent = x.getValOnPort("engage_result", i);
					holdIn("END", INFINITY);
				}
			} else if (phaseIs("EVASION")) {
				if (messageOnPort(x, "target_info", i)) {
					target_info_ent =  new CombatEnt((CombatEnt)x.getValOnPort("target_info", i));
					holdIn("EVASION", tEVASION);
				}
			}
		}
	}

	// Add internal transition function
	public void deltint() {
		if (phaseIs("RECONNNAISSANCE")) {
			scen_info_ent = OM_PF_Controller.Recon(0,scen_info_ent);
			holdIn("IDLE", INFINITY);

		} else if (phaseIs("APPROACH")) {
			
			holdIn("IDLE", INFINITY);
			// ?红蓝区分吗?
			// holdIn("COMBAT",tCOMBAT);

		} else if (phaseIs("COMBAT")) {

			holdIn("EVASION", tEVASION);

		} else if (phaseIs("EVASION")) {

			holdIn("IDLE", INFINITY);
		} else if (phaseIs("CONTROL")) {

			holdIn("IDLE", INFINITY);
		}
	}

	// Add confluent function
//    public void deltcon(double e, message x){
//    }

	// Add output function
	public message out() {
		message m = new message();
		if (phaseIs("RECONNNAISSANCE")) {
			// message m = new message();
			scen_info_ent.setOrderStr("move_cmd");
			content con = makeContent("move_cmd", new CombatEnt(scen_info_ent));
			m.add(con);
		} else if (phaseIs("APPROACH")) {
			scen_info_ent.setOrderStr("move_cmd");
			content con = makeContent("move_cmd", new CombatEnt(scen_info_ent));
			m.add(con);
		} else if (phaseIs("COMBAT")) {
			content con = makeContent("wp_launch", new entity("true"));
			m.add(con);
		} else if (phaseIs("EVASION")) {
			scen_info_ent.setOrderStr("move_cmd");
			content con = makeContent("move_cmd", new CombatEnt(scen_info_ent));
			m.add(con);
		} else if (phaseIs("CONTROL")) {
			content con = makeContent("wp_guidance", new entity("wp_guidance"));
			m.add(con);
		}
		return m;
	}

	public void settRECON(double tRECON) {
		this.tRECON = tRECON;
	}

	public void settAPPRCH(double tAPPRCH) {
		this.tAPPRCH = tAPPRCH;
	}

	public void settCOMBAT(double tCOMBAT) {
		this.tCOMBAT = tCOMBAT;
	}

	public void settEVASION(double tEVASION) {
		this.tEVASION = tEVASION;
	}

	public void settCTRL(double tCTRL) {
		this.tCTRL = tCTRL;
	}

	public void settEND(double tEND) {
		this.tEND = tEND;
	}

	// Add Show State function

}
