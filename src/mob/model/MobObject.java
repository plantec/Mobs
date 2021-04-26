package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobObject extends MobEntity  {
	List<MobObject> mobs;
	private MobObjectDef def;
	
	public MobObject(MobObjectDef def) {
		this.def = def;
		this.mobs = new ArrayList<>();
	}
		
	public MobObjectDef def() {
		return this.def;
	}
		
	public void add(MobObject mobExp) {
		this.mobs.add(mobExp);
		mobExp.setParent(this);
	}
	
	public void addAll(List<MobObject> mobExpList) {
		for (MobObject e : mobExpList) {
			this.add(e);
		}
	}

	public int size() {
		return this.mobs.size();
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitObject(this);
	}

	public MobObject get(int pos) {
		return this.mobs.get(pos);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}

	public List<MobObject> children() {
		return this.mobs;
	}

	
}
