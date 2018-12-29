package componentASW.om;

/**
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:18:17
 */
public class OM_Sensor {

	// stores other platforms’ physical information
	public static void Data_Integrator(CombatEnt move_result) {
		
		MessageBus.getSingleton().updateEntity(move_result);
	}

	// conducts the detection algorithm designed here
	public static CombatEnt Detection_Algorithm(String entity) {
		int currLoc = 999999;
		CombatEnt threatInfo = new CombatEnt();
		for(CombatEnt e : MessageBus.getSingleton().getCombatEntList()) {
			if(e.eq(entity)){
				currLoc = e.getY();
			}
		}
		if(currLoc>100000) return threatInfo;
		
		if(entity.equals("warship")) {
			for(CombatEnt e : MessageBus.getSingleton().getCombatEntList()) {
				if(Math.abs(currLoc-e.getY())<3000 && e.getBelong() == -1)
					threatInfo = e;
			}
		}
		if(entity.equals("submarine")) {
			for(CombatEnt e : MessageBus.getSingleton().getCombatEntList()) {
				if(Math.abs(currLoc-e.getY())<15000 && e.getBelong() == 1)
					threatInfo =  e;
			}
		}
		
		return threatInfo;
	}
}
