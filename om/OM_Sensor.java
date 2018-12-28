package componentASW.om;

import java.util.ArrayList;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:17
 */
public class OM_Sensor {

	private static  ArrayList<CombatEnt> CombatEntList = new ArrayList<CombatEnt>();
	// stores other platforms’ physical information
	public static void Data_Integrator(CombatEnt move_result) {
		for(CombatEnt e : CombatEntList) {
			if(e.eq(move_result.getName())){
				CombatEntList.remove(e);
			}
		}
		CombatEntList.add(move_result);
	}

	// conducts the detection algorithm designed here
	public static CombatEnt Detection_Algorithm(String entity) {
		CombatEnt currEnt = null; 
		int currLoc = 0;
		CombatEnt threatInfo = null;
		for(CombatEnt e : CombatEntList) {
			if(e.eq(entity)){
				currEnt = e;
				currLoc = e.getY();
			}
		}
		if(entity.equals("warship")) {
			for(CombatEnt e : CombatEntList) {
				if(Math.abs(currLoc-e.getY())<3000 && e.getBelong() == -1)
					threatInfo = e;
			}
		}
		if(entity.equals("submarine")) {
			for(CombatEnt e : CombatEntList) {
				if(Math.abs(currLoc-e.getY())<15000 && e.getBelong() == 1)
					threatInfo =  e;
			}
		}
		
		return threatInfo;
	}

}
