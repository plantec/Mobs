package mob.sinterpreter;

import java.io.IOException;
import java.util.List;

import mob.model.MobEntity;
import mob.model.MobKeywordMessage;
import mob.model.MobQuoted;
import mob.model.MobReturn;
import mob.model.MobSequence;
import mob.model.MobUnaryMessage;
import mob.model.MobUnit;
import mob.model.MobVarDecl;
import mob.model.MobAssign;
import mob.model.MobBinaryMessage;
import mob.model.MobVisitor;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;
import stree.parser.SNode;
import stree.parser.SParser;

public class MobInterpreter implements MobVisitor {	
	private MobContext context;

	public MobInterpreter(MobEnvironment env) {
		this.context = new MobTopContext(env, this);
	}

	public List<MobEntity> result() {
		return this.context.result();
	}

	public List<MobEntity> run(List<SNode> sexps) {
		this.context = new MobTopContext(this.context.environment(), this);
		List<MobEntity> progs = new MobTreeBuilder(this.context.environment()).run(sexps);
		progs.forEach(e->e.accept(this));
		return this.result();
	}

	public List<MobEntity> run(String input) {
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
	
	private void push(MobEntity exp) {
		this.context.push(exp);
	}
	
	private MobEntity pop() {
		return this.context.pop();
	}
	
	@Override
	public void visitTrue(MobTrue mobTrue) {
		MobVisitor.super.visitTrue(mobTrue);
		this.push(mobTrue);
	}

	@Override
	public void visitFalse(MobFalse mobFalse) {
		MobVisitor.super.visitFalse(mobFalse);
		this.push(mobFalse);
	}

	@Override
	public void visitFloat(MobFloat mobFloat) {
		MobVisitor.super.visitFloat(mobFloat);
		this.push(mobFloat);
	}

	@Override
	public void visitInteger(MobInteger mobInteger) {
		MobVisitor.super.visitInteger(mobInteger);
		this.push(mobInteger);
	}

	@Override
	public void visitSymbol(MobSymbol mobSymbol) {
		MobVisitor.super.visitSymbol(mobSymbol);
		MobVariable v = context.lookupVariableByName(mobSymbol.rawValue());
		if (v == null) {
			throw new Error("Undeclared variable '" + mobSymbol.rawValue() + "'");
		}
		this.push(v.value());
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobVisitor.super.visitAssign(mobAssign);
		mobAssign.right().accept(this);
		MobSymbol n = (MobSymbol) mobAssign.left();
		MobVariable var = context.lookupVariableByName(n.rawValue());
		var.setValue(this.pop());
		this.push(var.value());
	}
	
	@Override
	public void visitNil(MobNil mobNil) {
		MobVisitor.super.visitNil(mobNil);
		this.push(mobNil);
	}

	@Override
	public void visitString(MobString mobString) {
		MobVisitor.super.visitString(mobString);
		this.push(mobString);
	}

	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobVisitor.super.visitUnit(mobUnit);
		this.push(mobUnit);
	}

	@Override
	public void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
		MobVisitor.super.visitUnaryMessage(mobUnaryMessage);
		String name = mobUnaryMessage.keyword();
		MobEntity receiver = mobUnaryMessage.receiver();
		receiver.accept(this);
		this.pop().run(this.context, name);
	}

	@Override
	public void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
		MobVisitor.super.visitBinaryMessage(mobBinaryMessage);
		String name = mobBinaryMessage.operator();
		MobEntity receiver = mobBinaryMessage.receiver();
		MobEntity arg = mobBinaryMessage.argument();
		arg.accept(this);
		receiver.accept(this);
		MobEntity e = this.pop();
		e.run(this.context, name);
	}

	@Override
	public void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
		MobVisitor.super.visitKeywordMessage(mobKeywordMessage);
		for (MobEntity e : mobKeywordMessage.arguments())
			e.accept(this);
		String selector = mobKeywordMessage.selector();
		MobEntity receiver = mobKeywordMessage.receiver();
		receiver.accept(this);
		this.pop().run(this.context, selector);
	}

	@Override
	public void visitSequence(MobSequence mobSequence) {
		MobVisitor.super.visitSequence(mobSequence);
		for (MobEntity e : mobSequence.children()) {
			e.accept(this);
		}
	}

	@Override
	public void visitReturn(MobReturn mobReturn) {
		MobVisitor.super.visitReturn(mobReturn);
	}

	@Override
	public void visitVarDecl(MobVarDecl mobVarDecl) {
		MobVisitor.super.visitVarDecl(mobVarDecl);
		MobEntity val;
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
		MobVisitor.super.visitQuoted(mobQuoted);
		this.push(mobQuoted);
	}

	
	
	
}
