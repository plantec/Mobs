package mob.ast;

import mob.sinterpreter.MobReturnExecuted;

public interface MobAstElement {
	Boolean is(Object o);
	default void accept(MobInterpretableVisitor visitor) throws MobReturnExecuted { }
}
