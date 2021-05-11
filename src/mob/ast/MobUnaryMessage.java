package mob.ast;

public class MobUnaryMessage extends MobMessage {

	private String keyword;
	
	public String keyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitUnaryMessage(this);
	}

}
