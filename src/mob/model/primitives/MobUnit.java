package mob.model.primitives;

import java.util.ArrayList;
import java.util.List;

import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;
import mob.model.MobClass;
import mob.model.MobObject;

public class MobUnit extends MobObject  {

	private List<String> parameters;
	private MobAstElement code;
	
	public MobUnit(MobClass def) {
		super(def);
		parameters = new ArrayList<>();
	}

	public List<String> parameters() {
		return this.parameters;
	}
	
	public void addParameter(String name) {
		this.parameters.add(name);
	}
	
	public MobAstElement code() {
		return this.code;
	}
	
	public void setCode(MobAstElement code) {
		this.code = code;
	}
	
	public Boolean hasParameters() {
		return !this.parameters.isEmpty();
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitUnit(this);
	}

}
