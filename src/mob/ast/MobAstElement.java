package mob.ast;

public interface MobAstElement {
	Boolean is(Object o);
	default void accept(MobInterpretableVisitor visitor) {}
}
