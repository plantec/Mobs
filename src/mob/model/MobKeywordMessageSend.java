package mob.model;

import java.util.ArrayList;
import java.util.List;

public class MobKeywordMessageSend extends MobMessageSend {
	private List<String> keywords;
	private List<MobEntity> args;
	
	public MobKeywordMessageSend() {
		this.keywords = new ArrayList<>();
		this.args = new ArrayList<>();
	}
	
	public List<MobEntity> args() {
		return this.args;
	}

	public List<String> keywords() {
		return this.keywords;
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitKeywordMessageSend(this);
	}

}
