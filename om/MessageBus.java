package componentASW.om;

import java.util.ArrayList;

public class MessageBus {
	
	
	private static volatile MessageBus singleton = null;
	private  ArrayList<CombatEnt> CombatEntList;
	
    private MessageBus(){
    	CombatEntList = new ArrayList<CombatEnt>();
    	CombatEntList.add(new CombatEnt());
    }
    
    public static MessageBus getSingleton(){
        if(singleton == null){
            synchronized (MessageBus.class){
                if(singleton == null){
                    singleton = new MessageBus();
                }
            }
        }
        return singleton;
    }    
    /**
     * 增加实体
     * @param ent
     * @return
     */
	public CombatEnt addCombatEnt(CombatEnt ent) {
		CombatEntList.add(ent);
		return ent;
	}
	
	
	/**
	 * 获取实体大小
	 * @return
	 */
	public  int getSize() {
		return CombatEntList.size();
	}
	/**
	 * 查找实体
	 * @param name
	 * @return
	 */
	public  CombatEnt getEntityByName(String name) {
		for(CombatEnt e : CombatEntList) {
			if(e.eq(name)){
				return e;
			}
		}
		return new CombatEnt();
	}
	/**
	 * 更新实体
	 * @param ent
	 * @return
	 */
	public  CombatEnt updateEntity(CombatEnt ent) {
		synchronized (ent) {
			for(CombatEnt e : CombatEntList) {
				if(e.eq(ent.getName())){
					
					e.set_type(ent.get_type());
					e.setBelong(ent.getBelong());
					e.setDetect_range(ent.getDetect_range());
					//e.setId(ent.getId());
					e.setLive_time(ent.getLive_time());
					e.setOrderStr(ent.getOrderStr());
					e.setSpeed(ent.getSpeed());
					e.setStatus(ent.getStatus());
					e.setX(ent.getX());
					e.setY(ent.getY());
				}
			}
		}
		return ent;
	}
	/**
	 * 删除实体
	 * @param name
	 */
	public  void deleteEntityByName(String name) {
		synchronized (name){
			for(CombatEnt e : CombatEntList) {
				if(e.eq(name)){
					CombatEntList.remove(e);
				}
			}
		}
	}
	public ArrayList<CombatEnt> getCombatEntList() {
		return CombatEntList;
	}

	public void setCombatEntList(ArrayList<CombatEnt> combatEntList) {
		CombatEntList = combatEntList;
	}

}
