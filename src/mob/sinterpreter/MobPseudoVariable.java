package mob.sinterpreter;

import mob.ast.MobAstElement;
import mob.model.MobMethodRunner;
import mob.model.MobObject;

public class MobPseudoVariable extends MobVariable implements MobDataAccess, MobMethodRunner, MobAstElement {
	
	public MobPseudoVariable(String name, MobAstElement value) {
		super(name, value);
	}
	
	@Override
	public void lookupAndRun(MobContext ctx, String signature) {
		Boolean superflag = this.name().equals("super");
		((MobObject) this.value()).run(ctx, signature, superflag);
	}
	
	@Override
	public void pushInto(MobContext context) {
		context.push((MobAstElement) this);
	}
	
	@Override
	public Boolean is(Object o) {
		return false;
	}

}
