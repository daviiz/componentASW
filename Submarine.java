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

import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;
/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:20:40
 */
public class Submarine extends ViewableDigraph{

    
	private ViewableDigraph pManeuver ;
	private ViewableDigraph pController;
	private ViewableDigraph pSensor ;
	
	protected int x;
    protected int y;
    protected int speed;
    
    
    protected double detect_range = 3000;
	// Add Default Constructor
    public Submarine(){
        this("Platform",0,0,0);
    }

    // Add Parameterized Constructor
    public Submarine(String name,int x,int y, int speed){
        super(name);
        
        this.x = x;
        this.y = y;
        this.speed = speed;
// Structure information start
        // Add input port names
        addInport("scen_info");
        addInport("engage_result");
        addInport("move_result");
        addInport("env_info");
        addInport("guidance_info");

        // Add output port names
        addOutport("move_result");
        addOutport("wp_launch");
        addOutport("wp_guidance");

//add test input ports:

        // Initialize sub-components
         pManeuver =  new SubmarineManeuver("SubmarineManeuver");
         pController =  new SubmarineController("SubmarineController");
         pSensor =  new SubmarineSensor("SubmarineSensor");

        // Add sub-components
        add(pManeuver);
        add(pController);
        add(pSensor);

        // Add Couplings
       
        addCoupling(this, "engage_result", pController, "engage_result");
        addCoupling(this, "engage_result", pManeuver, "engage_result");
        addCoupling(this, "engage_result", pSensor, "engage_result");
        addCoupling(this, "env_info", pManeuver, "env_info");
        addCoupling(this, "env_info", pSensor, "env_info");
        addCoupling(this, "guidance_info", pController, "guidance_info");
        addCoupling(this, "move_result", pSensor, "move_result");
        addCoupling(this, "move_result", pManeuver, "move_result");
        addCoupling(this, "scen_info", pController, "scen_info");
        addCoupling(this, "scen_info", pManeuver, "scen_info");
        addCoupling(this, "scen_info", pSensor, "scen_info");
        addCoupling(this, "wp_guidance", pController, "wp_guidance");
        addCoupling(this, "wp_launch", pController, "wp_launch");
        
        addCoupling(pSensor, "threat_info", pController, "threat_info");
        
        addCoupling(pController, "move_cmd", pManeuver, "move_cmd");
        addCoupling(pController, "wp_launch", this, "wp_launch");
        addCoupling(pController, "wp_guidance", this, "wp_guidance");
        
        addCoupling(pManeuver, "fuel_exhausted", pController, "engage_result");
        addCoupling(pManeuver, "fuel_exhausted", pSensor, "engage_result");
        addCoupling(pManeuver, "move_finished", pController, "move_finished");
        addCoupling(pManeuver, "move_result", this, "move_result");
        

// Structure information end
        initialize();
        }
    public void layoutForSimView() {
		preferredSize = new Dimension(790, 500);
		((ViewableComponent) withName("SubmarineManeuver")).setPreferredLocation(new Point(40, 330));
		((ViewableComponent) withName("SubmarineController")).setPreferredLocation(new Point(20, 165));
		((ViewableComponent) withName("SubmarineSensor")).setPreferredLocation(new Point(0, 15));
	}

	public ViewableDigraph getpSensor() {
		return pSensor;
	}

	public void setpSensor(ViewableDigraph pSensor) {
		this.pSensor = pSensor;
	}


    }
