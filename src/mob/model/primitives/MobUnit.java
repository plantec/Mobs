package mob.model.primitives;

import java.util.ArrayList;
import java.util.List;

import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;

public class MobUnit extends MobObject implements MobAstElement {

	private List<String> parameters;
	private List<MobAstElement> code;
	
	public MobUnit(MobClass def) {
		super(def);
		parameters = new ArrayList<>();
		code = new ArrayList<>();
	}

	public List<String> parameters() {
		return this.parameters;
	}
	
	public void addParameter(String name) {
		this.parameters.add(name);
	}
	
	public List<MobAstElement> code() {
		return this.code;
	}
	
	public void addCode(MobAstElement code) {
		this.code.add(code);
	}
	
	public Boolean hasParameters() {
		return !this.parameters.isEmpty();
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitUnit(this);
	}

}
