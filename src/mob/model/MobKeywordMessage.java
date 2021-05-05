package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobKeywordMessage extends MobMessageSend {
	private List<String> keywords;
	private List<MobEntity> arguments;
	
	public MobKeywordMessage() {
		this.keywords = new ArrayList<>();
		this.arguments = new ArrayList<>();
	}
	
	public void add(String keyword, MobEntity arg) {
		this.keywords.add(keyword);
		this.arguments.add(arg);
		arg.setParent(this);
	}
	
	public String [] keywords() {
		return this.keywords.toArray(new String[this.keywords.size()]);
	}
	public MobEntity [] arguments() {
		return this.arguments.toArray(new MobEntity[this.arguments.size()]);
	}
	
	public String selector() {
		String s = "";
		for (String e : this.keywords)
			s = s + e;
		return s;
	}
	
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitKeywordMessage(this);
	}

}
