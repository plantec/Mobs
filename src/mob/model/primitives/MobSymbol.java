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
	
	public String asUnitArg() {
		String ms = this.mobString();
		if (ms.charAt(0) != ':') return null;
		return ms.substring(1, ms.length());
	}

	public String toString() {
		return "Symbol("+this.rawValue().toString()+")";
	}
	
}
