package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobUnit extends MobObject {
	private List<String> arguments;
	private MobEntity contents;
	
	public MobUnit(MobObjectDef def) {
		super(def);
		this.arguments = new ArrayList<>();
	}
	
	public MobUnit(MobObjectDef def, MobEntity contents) {
		this(def);
		this.setContents(contents);
	}
	
	public void addAllArguments(List<String> arguments) {
		this.arguments.addAll(arguments);
	}
	
	public MobEntity contents() {
		return this.contents;
	}
	
	public List<String> arguments() {
		return this.arguments;
	}
	
	private void setContents(MobEntity contents) {
		this.contents = contents;
		contents.setParent(this);
	}
	
	public Boolean hasArguments() {
		return ! this.arguments.isEmpty();
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitUnit(this);
	}

}
