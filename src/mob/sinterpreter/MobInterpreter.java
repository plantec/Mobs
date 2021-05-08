package mob.sinterpreter;

import java.io.IOException;
import java.util.List;

import mob.ast.MobAssign;
import mob.ast.MobAstElement;
import mob.ast.MobAstVisitor;
import mob.ast.MobBinaryMessage;
import mob.ast.MobKeywordMessage;
import mob.ast.MobQuoted;
import mob.ast.MobReturn;
import mob.ast.MobUnaryMessage;
import mob.ast.MobVarDecl;
import mob.model.primitives.MobCharacter;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobObject;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobUnit;
import stree.parser.SNode;
import stree.parser.SParser;

public class MobInterpreter implements MobAstVisitor {	
	private MobContext context;

	public MobInterpreter(MobEnvironment env) {
		this.context = new MobTopContext(env, this);
	}

	public List<MobAstElement> result() {
		return this.context.result();
	}

	public List<MobAstElement> run(List<SNode> sexps) {
		this.context = new MobTopContext(this.context.environment(), this);
		List<MobAstElement> progs = new MobTreeBuilder(this.context.environment()).run(sexps);
		progs.forEach(e->e.accept(this));
		return this.result();
	}

	public List<MobAstElement> run(String input) {
		SParser parser = new SParser();
		List<SNode> n = null;
		try {
			n = parser.parse(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.run(n);
	}
	
	public MobContext topContext() {
		return this.context;
	}

	public void pushContext(MobUnit unit) {
		this.context = new MobContext(this.context, unit);
	}
	public void pushContext() {
		this.context = new MobContext(this.context);
	}
	public void popContext() {
		this.context = this.context.parent();
	}
	
	private void push(MobAstElement exp) {
		this.context.push(exp);
	}
	
	private MobAstElement pop() {
		return this.context.pop();
	}
	
	@Override
	public void visitTrue(MobTrue mobTrue) {
		MobAstVisitor.super.visitTrue(mobTrue);
		this.push(mobTrue);
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobAstVisitor.super.visitFalse(mobFalse);
		this.push(mobFalse);
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobAstVisitor.super.visitFloat(mobFloat);
		this.push(mobFloat);
	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobAstVisitor.super.visitInteger(mobInteger);
		this.push(mobInteger);
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobAstVisitor.super.visitSymbol(mobSymbol);
		MobVariable v = context.lookupVariableByName(mobSymbol.rawValue());
		if (v == null) {
			throw new Error("Undeclared variable '" + mobSymbol.rawValue() + "'");
		}
		this.push(v.value());
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobAstVisitor.super.visitAssign(mobAssign);
		mobAssign.right().accept(this);
		MobSymbol n = (MobSymbol) mobAssign.left();
		MobVariable var = context.lookupVariableByName(n.rawValue());
		var.setValue(this.pop());
		this.push(var.value());
	}
	
	@Override
	public void visitNil(MobNil mobNil) {
		MobAstVisitor.super.visitNil(mobNil);
		this.push(mobNil);
	}

	@Override
	public void visitString(MobString mobString) {
		MobAstVisitor.super.visitString(mobString);
		this.push(mobString);
	}

	@Override
	public void visitCharacter(MobCharacter mobCharacter) {
		MobAstVisitor.super.visitCharacter(mobCharacter);
		this.push(mobCharacter);
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobAstVisitor.super.visitUnit(mobUnit);
		this.push(mobUnit);
	}

	@Override
	public void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
		MobAstVisitor.super.visitUnaryMessage(mobUnaryMessage);
		String name = mobUnaryMessage.keyword();
		MobAstElement receiver = mobUnaryMessage.receiver();
		receiver.accept(this);
		((MobObject)this.pop()).run(this.context, name);
	}

	@Override
	public void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
		MobAstVisitor.super.visitBinaryMessage(mobBinaryMessage);
		MobSymbol name = mobBinaryMessage.operator();
		MobAstElement receiver = mobBinaryMessage.receiver();
		MobAstElement arg = mobBinaryMessage.argument();
		arg.accept(this);
		receiver.accept(this);
		MobAstElement e = this.pop();
		((MobObject)e).run(this.context, name.rawValue());
	}

	@Override
	public void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
		MobAstVisitor.super.visitKeywordMessage(mobKeywordMessage);
		for (MobAstElement e : mobKeywordMessage.arguments())
			e.accept(this);
		String selector = mobKeywordMessage.selector();
		MobAstElement receiver = mobKeywordMessage.receiver();
		receiver.accept(this);
		((MobObject)this.pop()).run(this.context, selector);
	}

	@Override
	public void visitSequence(MobSequence mobSequence) {
		MobAstVisitor.super.visitSequence(mobSequence);
		for (MobAstElement e : mobSequence.children()) {
			e.accept(this);
		}
	}

	@Override
	public void visitReturn(MobReturn mobReturn) {
		MobAstVisitor.super.visitReturn(mobReturn);
	}

	@Override
	public void visitVarDecl(MobVarDecl mobVarDecl) {
		MobAstVisitor.super.visitVarDecl(mobVarDecl);
		MobAstElement val;
		if (mobVarDecl.initialValue() == null) {
			val = context.newNil();
		} else {
			mobVarDecl.initialValue().accept(this);
			val = this.pop();
		}
		MobVariable var = new MobVariable(mobVarDecl.name(), val);
		context.addVariable(var);
	}

	@Override
	public void visitQuoted(MobQuoted mobQuoted) {
		MobAstVisitor.super.visitQuoted(mobQuoted);
		this.push(mobQuoted);
	}
}
