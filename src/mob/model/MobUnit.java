package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobUnit extends MobObject {

	private MobParameterList plist;
	private List<MobEntity> code;
	
	public MobUnit(MobObjectDef def) {
		super(def);
		code = new ArrayList<>();
	}

	public MobParameterList plist() {
		return this.plist;
	}
	
	public void setPlist(MobParameterList plist) {
		this.plist = plist;
		this.plist.setParent(this);
	}
	
	public List<MobEntity> code() {
		return this.code;
	}
	
	public void addCode(MobEntity code) {
		this.code.add(code);
		code.setParent(this);
	}
	
	public Boolean hasParameters() {
		return this.plist != null;
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitUnit(this);
	}

}
