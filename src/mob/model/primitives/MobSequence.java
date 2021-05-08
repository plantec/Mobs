package mob.model.primitives;

import java.util.ArrayList;
import java.util.List;

import mob.ast.MobAstElement;
import mob.ast.MobAstVisitor;

public class MobSequence extends MobObject implements MobAstElement {
	List<MobAstElement> children;
	
	public MobSequence(MobClass def) {
		super(def);
		this.children = new ArrayList<>();
	}
	
	public void add(MobAstElement mobExp) {
		this.children.add(mobExp);
	}
	
	public void addAll(List<MobAstElement> mobExpList) {
		for (MobAstElement e : mobExpList) {
			this.add(e);
		}
	}

	public int size() {
		return this.children.size();
	}

	public MobAstElement get(int pos) {
		return this.children.get(pos);
	}
	
	public void set(int pos, MobAstElement child) {
		children.set(pos, child);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}

	public List<MobAstElement> children() {
		return this.children;
	}
	
	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitSequence(this);
	}
	
}
