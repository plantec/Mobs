package mob.model;

import mob.sinterpreter.MobContext;

public interface MobRunable {

	default void run(MobContext ctx, MobEntity receiver) { }
}
