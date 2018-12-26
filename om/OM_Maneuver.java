package componentASW.om;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:06
 */
public class OM_Maneuver {
	/// AM_Updater:

	/**
	 * converts the move_cmd command to physical information for dynamics
	 * 
	 * @param Move_order
	 * @return
	 */
	public static CombatEnt Cmd_Inerpreter(String Move_order) {
		CombatEnt _CombatEnt = null;
		return _CombatEnt;
	}

	/// AM_Actor:
	/**
	 * handles the maneuver algorithm
	 * 
	 * @param physical_Data
	 * @return
	 */
	public static CombatEnt Motion_Equation(CombatEnt physical_Data) {
		return null;
	}

	/**
	 * completes the moving command
	 * 
	 * @param physical_Data
	 * @return
	 */
	public static boolean Cmd_Check(CombatEnt physical_Data) {
		return false;
	}

	/**
	 * computes the operating time for the platform’s endurance
	 * 
	 * @param physical_Data
	 * @return
	 */
	public static boolean Fuel_Check(CombatEnt physical_Data) {
		return false;
	}

}
