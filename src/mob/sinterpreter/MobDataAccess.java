package mob.sinterpreter;

import mob.ast.MobAstElement;

public interface MobDataAccess {
	MobAstElement value();
	void setValue(MobAstElement mobExp);
	void pushInto(MobContext context);
}
