package componentASW.om;

public class OM_PF_Controller {
	/////AM_Updater
	//Behavior description for target identification:Target data = Identification(Threats data)
	public static CombatEnt Identification(CombatEnt threadData) {
		return null;
	}
	/////AM_Actor
	//Behavior description for reconnaissance : Next search-pattern method = Recon(Current method)
	public static int Recon(int CurrentMethod) {
		return 0;
	}
	//OM Apprch : Behavior description for approach to target: Approach method = Apprch(Targets/own data)
	public static int Apprch(CombatEnt data) {
		return 0;
	}
	
	//OM Attack : Behavior description for combat planning
	//Engagement order = Attack(Targets/own data)
	public static int Attack(CombatEnt data) {
		return 0;
	}
	//OM Evasion : Behavior description for evasive action
	//Evasion order = Evasion(Targets/own data)
	public static int Evasion(CombatEnt data) {
		return 0;
	}
	
	
	//OM Ctrl : Behavior description for weapon control
	//Control order = Ctrl(Targets/own weapon data)
	public static int Ctrl(CombatEnt data) {
		return 0;
	}
	
	//Operating pattern of decoys:Pattern 1, 2, 3, 4 (4 cases)
	/*
		Pattern 1 uses only static decoys; pattern 2 to 4 mix static and mobile decoys.
		
		Pattern 1: four static decoys are used.
		Pattern 2: four mobile decoys are used.
		Pattern 3: two static decoys at the front of warship and two mobile decoys at the rear are used.
		Pattern 4: two mobile decoys at the front of warship and two static decoys at the rear are used.
	 */
	public static int Combat() {
		return 0;
	}
}
