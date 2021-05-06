package mob.model.primitives;

import mob.model.MobObjectDef;
import mob.model.MobVisitor;

public class MobSymbol extends MobPrimitive<String> {

	public MobSymbol(MobObjectDef def, String mob) {
		super(def, mob);
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitSymbol(this);
	}
	
	public String toString() {
		return "Symbol("+this.rawValue().toString()+")";
	}
	
}
