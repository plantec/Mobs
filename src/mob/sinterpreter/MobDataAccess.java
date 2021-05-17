package mob.sinterpreter;

import mob.ast.MobAstElement;

public interface MobDataAccess {
	MobAstElement value();
	public void setValue(MobAstElement mobExp);

}
