package componentASW.platform;

import GenCol.entity;
import model.modeling.content;
import model.modeling.message;
import view.modeling.ViewableAtomic;

/**
 * Then the Actor model operates proper tactical processes from target 
 * tracking to tactical evasion with identified target information.
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:44
 */
 public class Controller_Actor extends ViewableAtomic {
	
	 private double   tRECON; 
	 private double    tAPPRCH; 
	 private double     tCOMBAT;
	 private double    tEVASION;
	 private double     tCTRL ;
	 private double  tEND = INFINITY; 
	
	// Add Default Constructor
    public Controller_Actor(){
        this("Controller_Actor") ;   }
	
	// Add Parameterized Constructors
    public Controller_Actor(String name){
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
    public void initialize(){
        super.initialize();
        //S = {IDLE, RECONNNAISSANCE, APPROACH, COMBAT, EVASION, CONTROL, END} 状态集合：{ 空闲-侦查-接近-战斗-逃逸-结束 }
        phase = "IDLE";
        sigma = INFINITY;
    }

    // Add external transition function
    public void deltext(double e, message x){
    	Continue(e);
		for (int i = 0; i < x.size(); i++) {
			if (phaseIs("IDLE")) {
				if (messageOnPort(x, "scen_info", i)) {
					holdIn("RECONNNAISSANCE", tRECON);
				} else if (messageOnPort(x, "move_finished", i)) {
					holdIn("RECONNNAISSANCE", tRECON);
				} else if (messageOnPort(x, "target_info", i)) {
					holdIn("APPROACH", tAPPRCH);
				} else if (messageOnPort(x, "guidance_info", i)) {
					holdIn("CONTROL", tCTRL);
				} else if (messageOnPort(x, "engage_result", i)) {
					holdIn("END", INFINITY);
				}
			} else if (phaseIs("COMBAT")) {
				if (messageOnPort(x, "target_info", i)) {
					holdIn("COMBAT", tCOMBAT);
				}
			} else if (phaseIs("CONTROL")) {
				if (messageOnPort(x, "target_info", i)) {
					holdIn("CONTROL", tCTRL);
				} else if (messageOnPort(x, "engage_result", i)) {
					holdIn("END", INFINITY);
				}
			} else if (phaseIs("EVASION")) {
				if (messageOnPort(x, "target_info", i)) {
					holdIn("EVASION", tEVASION);
				}
			}
		}
    }

    // Add internal transition function
    public void deltint(){
    	if (phaseIs("RECONNAISSANCE")) {
    		
    		holdIn("IDLE",INFINITY);
    		
    	}else if(phaseIs("APPROACH")) {
    		
    		holdIn("IDLE",INFINITY);
    		//?红蓝区分吗?
    		//holdIn("COMBAT",tCOMBAT);
    		
    	}else if(phaseIs("COMBAT")) {
    		
    		holdIn("EVASION",tEVASION);
    		
    	}else if(phaseIs("EVASION")) {
    		
    		holdIn("IDLE",INFINITY);
    	}else if(phaseIs("CONTROL")) {
    		
    		holdIn("IDLE",INFINITY);
    	}
    }

    // Add confluent function
//    public void deltcon(double e, message x){
//    }

    // Add output function
    public message out(){
    	message m = new message();
    	if(phaseIs("RECONNAISSANCE")) {
			//message m = new message();
			content con = makeContent("move_cmd", new entity(""));
			m.add(con);
			return m;
		}
    	else if(phaseIs("APPROACH")){
    		content con = makeContent("move_cmd", new entity(""));
			m.add(con);
    		return m;
		}
		else if(phaseIs("COMBAT")){
			content con = makeContent("wp_launch", new entity(""));
			m.add(con);
			return m;
		}
		else if(phaseIs("EVASION")){
			content con = makeContent("move_cmd", new entity(""));
			m.add(con);
			return m;
		}
		else if(phaseIs("CONTROL")){
			content con = makeContent("wp_guidance", new entity(""));
			m.add(con);
			return m;
		}
		else {
			return null;
		}
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
