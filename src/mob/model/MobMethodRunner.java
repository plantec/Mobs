package mob.model;

import mob.sinterpreter.MobContext;

public interface MobMethodRunner {
	void lookupAndRun(MobContext ctx, String signature);
}
