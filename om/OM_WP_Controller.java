package componentASW.om;

import java.util.List;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:22
 */
public class OM_WP_Controller {

	/**
	 * 
	 * @param threatData
	 * @return
	 */
	public static CombatEnt Idntify(CombatEnt threatData) {
		return null;
	}

	/**
	 * Type 1, 2, 3, 4 (4 cases) Type 1 means a straight-running torpedo; Types 2 to
	 * 4 mean pattern- running torpedoes. Type 1: only the straight moving segment
	 * is used, Type 2: straight and winding moving segments are used. Type 3:
	 * winding and circular moving segments are used. Type 4: all three moving
	 * segments are used.
	 * 
	 * @param currentMethod
	 * @return
	 */
	public static int Tactical_Search(int currentMethod) {
		return 0;
	}

	/**
	 * 
	 * @param threatData
	 * @return
	 */
	public static int Apprch(CombatEnt threatData) {
		return 0;
	}

	/**
	 * 
	 */
	public static void Tacticl_Move() {

	}
}
