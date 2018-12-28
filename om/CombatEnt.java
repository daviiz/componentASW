package componentASW.om;

import GenCol.ExternalRepresentation;

/**
 * 战斗实体基类，用于交战实体之间的消息传输及状态记录
 * 
 * @author daiwenzhi
 * @DATATIME 2018年12月25日 下午4:17:52
 */
public class CombatEnt extends GenCol.entity {

	// 实体消息主键
	private int id;
	// 位置信息
	protected int x;
	protected int y;
	// 实体状态
	private int status; // 1"live" 0"destroyed"
	// 属方
	private int belong; // 1 红方,0 中立方,-1 蓝方
	// 实体类型
	private int _type; // 0:platform, 1:weapon,-1 未定义
	// 实体速度
	private int speed;
	// 侦查范围
	private int detect_range;
	// 生命周期
	private int live_time;

	public CombatEnt() {
		this.id = 0;
		this.name = "combatEntBase";
		this.status = 1;
		this.live_time = 999999;
	}

	public CombatEnt(CombatEnt ent) {
		this.name = ent.name;
		this.id = ent.id;
		this.status = ent.status;
		this.x = ent.x;
		this.y = ent.y;
		this.belong = ent.belong;
		this._type = ent._type;
		this.speed = ent.speed;
		this.detect_range = ent.detect_range;
		this.live_time = ent.live_time;
	}

	public CombatEnt(int id, String name, int x, int y, int status, int belong, int _type, int speed, int detect_range,
			int live_time) {
		this.name = name;
		this.status = status;
		this.x = x;
		this.y = y;
		this.belong = belong;
		this._type = _type;
		this.speed = speed;
		this.detect_range = detect_range;
		this.live_time = live_time;
	}

	public boolean eq(String nm) {
		return getName().equals(nm);
	}

	public Object equalName(String nm) {
		if (eq(nm))
			return this;
		else
			return null;
	}

	public boolean equals(Object o) { // overrides pointer equality of Object
		if (!(o instanceof CombatEnt))
			return false;
		else
			return eq(((CombatEnt) o).getName());
	}

	public ExternalRepresentation getExtRep() {
		return new ExternalRepresentation.ByteArray();
	}

	public String toString() {

		return ("name:" + name + "Loc:(" + x + "," + y + ")");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBelong() {
		return belong;
	}

	public void setBelong(int belong) {
		this.belong = belong;
	}

	public int get_type() {
		return _type;
	}

	public void set_type(int _type) {
		this._type = _type;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDetect_range() {
		return detect_range;
	}

	public void setDetect_range(int detect_range) {
		this.detect_range = detect_range;
	}

	public int getLive_time() {
		return live_time;
	}

	public void setLive_time(int live_time) {
		this.live_time = live_time;
	}

}
/**
 * content con3 = makeContent("scen_gen", new CombatEnt(3, "Decoy1", 0, 0,1,1,1,
 * SimParameter.Speed_of_decoy, 0, SimParameter.Operation_time_Decoy));
 * m.add(con3);
 * 
 * content con4 = makeContent("scen_gen", new CombatEnt(4, "Decoy2", 0, 0,1,1,1,
 * SimParameter.Speed_of_decoy, 0, SimParameter.Operation_time_Decoy));
 * m.add(con4);
 * 
 * content con5 = makeContent("scen_gen", new CombatEnt(5, "Terpedp", 0,
 * 0,1,-1,1, SimParameter.Speed_Torpedo, 0, SimParameter.Live_time_Torpedo));
 * m.add(con5);
 */
