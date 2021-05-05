package mob.model;

public class MobUnaryMessage extends MobMessageSend {

	private String keyword;
	
	public String keyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void accept(MobVisitor visitor) {
		visitor.visitUnaryMessage(this);
	}

}