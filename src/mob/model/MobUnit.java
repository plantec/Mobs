package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobUnit extends MobEntity {
	List<MobExp> mobs;
	
	public MobUnit(MobEntityDef def) {
		super(def);
		this.mobs = new ArrayList<>();
	}
	
	public void add(MobExp mobExp) {
		this.mobs.add(mobExp);
		mobExp.setParent(this);
	}
	
	public void addAll(List<MobExp> mobExpList) {
		for (MobExp e : mobExpList) {
			this.add(e);
		}
	}

	public int size() {
		return this.mobs.size();
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitUnit(this);
	}

	public MobExp get(int pos) {
		return this.mobs.get(pos);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}

	public List<MobExp> children() {
		return this.mobs;
	}

}
