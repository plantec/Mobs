package mob.ast;

import java.util.ArrayList;
import java.util.List;

public class MobKeywordMessage extends MobMessage {
	private List<String> keywords;
	private List<MobAstElement> arguments;

	public MobKeywordMessage() {
		this.keywords = new ArrayList<>();
		this.arguments = new ArrayList<>();
	}

	public void add(String keyword, MobAstElement arg) {
		this.keywords.add(keyword);
		this.arguments.add(arg);
	}

	public String[] keywords() {
		return this.keywords.toArray(new String[this.keywords.size()]);
	}

	public MobAstElement[] arguments() {
		return this.arguments.toArray(new MobAstElement[this.arguments.size()]);
	}

	public String selector() {
		String s = "";
		for (String e : this.keywords)
			s = s + e.toString();
		return s;
	}

	@Override
	public void accept(MobInterpretableVisitor visitor) {
		visitor.visitKeywordMessage(this);
	}

}
