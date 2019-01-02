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
	 * "forward / back"
	 * @param Move_order
	 * @return
	 */
	public static CombatEnt Cmd_Inerpreter(String entName,String _order) {
		CombatEnt _CombatEnt = MessageBus.getSingleton().getEntityByName(entName);
		if (_order.equals("forward")) {
			_CombatEnt.setY(_CombatEnt.getY() + SimParameter.getSpeedByName(entName));
		} else if (_order.equals("back")){
			_CombatEnt.setY(_CombatEnt.getY() - SimParameter.getSpeedByName(entName));
		}
		// update message in MessageBus about current entity
		//MessageBus.getSingleton().updateEntity(_CombatEnt);
		return _CombatEnt;
	}
	/**
	 * converts the move_cmd command to physical information for dynamics
	 * "forward / back"
	 * @param Move_order
	 * @return
	 */
	public static CombatEnt Cmd_Inerpreter(CombatEnt _ent) {
		CombatEnt _CombatEnt = MessageBus.getSingleton().getEntityByName(_ent.getName());
//		if (_order.equals("forward")) {
//			_CombatEnt.setY(_CombatEnt.getY() + SimParameter.getSpeedByName(entName));
//		} else if (_order.equals("back")){
//			_CombatEnt.setY(_CombatEnt.getY() - SimParameter.getSpeedByName(entName));
//		}
		// update message in MessageBus about current entity
		//MessageBus.getSingleton().updateEntity(_CombatEnt);
		return _CombatEnt;
	}

	/// AM_Actor:
	/**
	 * handles the maneuver algorithm
	 * --do nothing...
	 * @param physical_Data
	 * @return
	 */
	public static CombatEnt Motion_Equation(CombatEnt physical_Data) {
		//physical_Data.setLive_time(physical_Data.getLive_time()-1);
		//CombatEnt _CombatEnt = new CombatEnt(MessageBus.getSingleton().updateEntity(physical_Data));
		return physical_Data;
	}

	/**
	 * completes the moving command
	 * 
	 * @param physical_Data
	 * @return
	 */
	public static boolean Cmd_Check(CombatEnt physical_Data) {
		int _t = (physical_Data.getLive_time()-5);
		if (_t>0) {
			physical_Data.setLive_time(_t);
			if(physical_Data.getName().equals("warship")) {
				physical_Data.setY(physical_Data.getY()-5);
			}else if(physical_Data.getName().equals("submarine")) {
				physical_Data.setY(physical_Data.getY()+3);
			}
			else if(physical_Data.getName().equals("torpedo")) {
				physical_Data.setY(physical_Data.getY()+10);
			}
				
			MessageBus.getSingleton().updateEntity(physical_Data);
			return false;
		}else return false;
	}

	/**
	 * computes the operating time for the platform’s endurance
	 * 
	 * @param physical_Data
	 * @return
	 */
	public static boolean Fuel_Check(CombatEnt physical_Data) {
		if (physical_Data.getLive_time()>0) {
			return false;
		}else {
			return true;
		}
	}

}
