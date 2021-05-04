package mob.model;

import java.util.ArrayList;
import java.util.List;

import mob.sinterpreter.MobContext;

public class MobObject extends MobEntity  {
	List<MobEntity> mobs;
	private MobObjectDef def;
	
	public MobObject(MobObjectDef def) {
		this.def = def;
		this.mobs = new ArrayList<>();
	}
		
	public MobObjectDef def() {
		return this.def;
	}
		
	public void add(MobEntity mobExp) {
		this.mobs.add(mobExp);
		mobExp.setParent(this);
	}
	
	public void addAll(List<MobEntity> mobExpList) {
		for (MobEntity e : mobExpList) {
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

	public MobEntity get(int pos) {
		return this.mobs.get(pos);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}

	public List<MobEntity> children() {
		return this.mobs;
	}
	
	public void run (MobContext ctx, String signature) {
		MobMethod m = this.def.methodNamed(signature);
		m.run(ctx, this);
	}

	
}
