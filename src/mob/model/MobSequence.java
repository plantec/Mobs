package mob.model;

import java.util.ArrayList;
import java.util.List;
import mob.sinterpreter.MobEnvironment;

public class MobSequence extends MobObject {
	List<MobEntity> children;
	
	public MobSequence(MobObjectDef def) {
		super(def);
		this.children = new ArrayList<>();
	}
	
	public void add(MobEntity mobExp) {
		this.children.add(mobExp);
		mobExp.setParent(this);
	}
	
	public void addAll(List<MobEntity> mobExpList) {
		for (MobEntity e : mobExpList) {
			this.add(e);
		}
	}

	public int size() {
		return this.children.size();
	}

	public MobEntity get(int pos) {
		return this.children.get(pos);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}

	public List<MobEntity> children() {
		return this.children;
	}
	
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitSequence(this);
	}
	
	public MobUnit asUnit(MobEnvironment env) {
		Boolean hasArg = false;
		for (MobEntity e : this.children)	
			if (e.is("|")) {
				hasArg = true;
				break;
			}
		
		if (! hasArg) 
			return new MobUnit(env.unitDef(), this);
		
		List<String> args = new ArrayList<>();
		int idx = 0;
		MobEntity e;
		while (true) {
			e = this.get(idx++);
			if (e.is("|")) {
				break;
			}
			String a = e.asUnitArg();
			if (a == null) {
				throw new Error("malformed unit argument: " + e.mobString());
			}
			args.add(a);
		}
		List<MobEntity> contentsElements = new ArrayList<>();
		while (idx < this.size()) {
			e = this.get(idx++);
			contentsElements.add(e);
		}
		MobSequence seq = env.newSequence(contentsElements);
		MobUnit res = new MobUnit(env.unitDef(), seq);
		res.addAllArguments(args);
		return res;
	}
	
}
