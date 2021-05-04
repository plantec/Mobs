package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobKeywordMessageSend extends MobMessageSend {
	private List<String> keywords;
	private List<MobEntity> arguments;
	
	public MobKeywordMessageSend() {
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
	
	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitKeywordMessageSend(this);
	}

}
