package mob.sinterpreter;

import mob.ast.MobAstElement;

public abstract class MobMethod {
	String selector;
	
	public MobMethod(String selector) {
		this.selector = selector;
	}
	
	public String selector () {
		return this.selector;
	}
	
	abstract public void run(MobContext ctx, MobAstElement mobObject);

}
