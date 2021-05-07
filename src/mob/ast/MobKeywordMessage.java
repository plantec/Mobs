package mob.ast;

import java.util.ArrayList;
import java.util.List;

import mob.model.primitives.MobSymbol;

public class MobKeywordMessage extends MobMessage {
	private List<MobSymbol> keywords;
	private List<MobAstElement> arguments;

	public MobKeywordMessage() {
		this.keywords = new ArrayList<>();
		this.arguments = new ArrayList<>();
	}

	public void add(MobSymbol keyword, MobAstElement arg) {
		this.keywords.add(keyword);
		this.arguments.add(arg);
	}

	public MobSymbol[] keywords() {
		return this.keywords.toArray(new MobSymbol[this.keywords.size()]);
	}

	public MobAstElement[] arguments() {
		return this.arguments.toArray(new MobAstElement[this.arguments.size()]);
	}

	public String selector() {
		String s = "";
		for (MobSymbol e : this.keywords)
			s = s + e.toString();
		return s;
	}

	@Override
	public void accept(MobAstVisitor visitor) {
		visitor.visitKeywordMessage(this);
	}

}
