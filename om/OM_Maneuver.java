package componentASW.om;

public class OM_Maneuver {
	///AM_Updater:
	
	//converts the move_cmd command to physical information for dynamics
	 public static void  Cmd_Inerpreter() {}
	 
	///AM_Actor:
	 //handles the maneuver algorithm
	 public static void Motion_Equation() {}
	 
	 // completes the moving command
	 public static void Cmd_Check(){}
	 
	 // computes the operating time for the platform’s endurance
	 public static int Fuel_Check() {
		 return  0;
	 }
	 
	 

}
