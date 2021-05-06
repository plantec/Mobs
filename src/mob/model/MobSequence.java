package mob.model;

import java.util.ArrayList;
import java.util.List;

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
	
	public void set(int pos, MobEntity child) {
		children.set(pos, child);
		child.setParent(this);
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
	
}
