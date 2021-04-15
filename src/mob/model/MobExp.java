package mob.model;

public interface MobExp {
	MobUnit parent();

	void setParent(MobUnit parent);

	int quote();

	void setQuote(int q);

	void accept(MobVisitor visitor);

	default Boolean is(Object o) {
		return false;
	}

}
