package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobParameterList extends MobEntity {
	List<String> parameters;
	
	public MobParameterList() {
		this.parameters = new ArrayList<>();
	}
	
	public int size () {
		return this.parameters.size();
	}
	
	public void add(String parameter) {
		this.parameters.add(parameter);
	}
	
	public String get(int pos) {
		return this.parameters.get(pos);
	}
	
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitParameterList(this);

	}

}
