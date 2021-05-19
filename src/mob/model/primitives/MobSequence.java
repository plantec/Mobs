package mob.model.primitives;

import java.util.List;

import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;
import mob.model.MobObject;

public class MobSequence extends MobObject {
	
	public MobSequence(MobClass def) {
		super(def);
	}
	
	public void add(MobAstElement mobExp) {
		this.instVarAtPut(this.valuesCapacity(), mobExp);
	}
	
	public void addAll(List<MobAstElement> mobExpList) {
		for (MobAstElement e : mobExpList) {
			this.add(e);
		}
	}

	public int size() {
		return this.valuesCapacity();
	}

	public MobAstElement get(int pos) {
		return (MobAstElement) this.instVarAt(pos);
	}
	
	public void set(int pos, MobAstElement child) {
		this.instVarAtPut(pos, child);
	}

	public boolean hasChildren() {
		return this.size() > 0;
	}
	
	public MobAstElement[] children() {
		MobAstElement[] children = new MobAstElement[this.size()];
		for (int i = 0; i < this.size(); i++) {
			children[i] = (MobAstElement) this.instVarAt(i);
		}
		return children;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitSequence(this);
	}
	
}
