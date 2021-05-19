package mob.sinterpreter;

import java.io.IOException;

import java.util.List;

import mob.ast.MobAssign;
import mob.ast.MobAstElement;
import mob.ast.MobInterpretableVisitor;
import mob.ast.MobBinaryMessage;
import mob.ast.MobKeywordMessage;
import mob.ast.MobQuoted;
import mob.ast.MobReturn;
import mob.ast.MobUnaryMessage;
import mob.ast.MobVarDecl;
import mob.model.MobMethodRunner;
import mob.model.MobObject;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobUnit;
import stree.parser.SNode;
import stree.parser.SParser;

public class MobInterpreter implements MobInterpretableVisitor {	
	private MobContext context;

	public MobInterpreter(MobEnvironment env) {
		this.context = new MobTopContext(env, this);
	}

	public List<MobAstElement> result() {
		return this.context.result();
	}

	public List<MobAstElement> run(List<SNode> sexps) {
		this.context.resetStack();
		List<MobAstElement> progs = new MobTreeBuilder(this.context.environment()).run(sexps);
		progs.forEach(e->this.accept(e));
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

	public void pushContext(MobContext ctx) {
		ctx.setParent(this.context);
		this.context = ctx;
	}
	
	public MobContext popContext() {
		MobContext previous = this.context;
		this.context = this.context.parent();
		return previous;
	}
	
	private void push(MobAstElement exp) {
		this.context.push(exp);
	}
	
	private MobAstElement pop() {
		return this.context.pop();
	}
	
	public void accept(final MobAstElement e) {
		e.accept(this);
	}
	
	@Override
	public void visitObject(MobObject mob) {
		MobInterpretableVisitor.super.visitObject(mob);
		if (mob.isKindOf(this.topContext().environment().getClassByName("Symbol"))) {
			MobDataAccess v = context.lookupNamedData((String)mob.rawValue());
			if (v == null) {
				throw new Error("Undeclared variable '" + mob.rawValue() + "'");
			}
			v.pushInto(this.context);
			return;
		}
		this.push(mob);
	}

	@Override
	public void visitAssign(MobAssign mobAssign) {
		MobInterpretableVisitor.super.visitAssign(mobAssign);
		this.accept(mobAssign.right());
		MobObject n = (MobObject) mobAssign.left();
		MobDataAccess var = context.lookupNamedData((String)n.rawValue());
		if (var == null) {
			context.lookupNamedData((String)n.rawValue());
		}
		var.setValue(this.pop());
		this.push(var.value());
	}
	
	@Override
	public void visitUnit(MobUnit mobUnit) {
		MobInterpretableVisitor.super.visitUnit(mobUnit);
		this.push(mobUnit);
	}

	@Override
	public void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
		MobInterpretableVisitor.super.visitUnaryMessage(mobUnaryMessage);
		String name = mobUnaryMessage.keyword();
		MobAstElement receiver = mobUnaryMessage.receiver();
		this.accept(receiver);
		MobAstElement actualReceiver = this.pop();
		((MobMethodRunner) actualReceiver).lookupAndRun(this.context, name);
	}

	@Override
	public void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
		MobInterpretableVisitor.super.visitBinaryMessage(mobBinaryMessage);
		String name = mobBinaryMessage.operator();
		MobAstElement receiver = mobBinaryMessage.receiver();
		MobAstElement arg = mobBinaryMessage.argument();
		this.accept(arg);
		this.accept(receiver);
		MobAstElement actualReceiver = this.pop();
		((MobMethodRunner)actualReceiver).lookupAndRun(this.context, name);
	}

	@Override
	public void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
		MobInterpretableVisitor.super.visitKeywordMessage(mobKeywordMessage);
		for (MobAstElement e : mobKeywordMessage.arguments())
			this.accept(e);
		String selector = mobKeywordMessage.selector();
		MobAstElement receiver = mobKeywordMessage.receiver();
		this.accept(receiver);
		MobAstElement actualReceiver = this.pop();
		((MobMethodRunner)actualReceiver).lookupAndRun(this.context, selector);
	}

	@Override
	public void visitSequence(MobSequence mobSequence) {
		MobInterpretableVisitor.super.visitSequence(mobSequence);
		for (MobAstElement e : mobSequence.children()) {
			this.accept(e);
		}
	}

	@Override
	public void visitReturn(MobReturn mobReturn) {
		MobInterpretableVisitor.super.visitReturn(mobReturn);
		mobReturn.returned().accept(this);
		throw new MobReturnExecuted();
	}

	@Override
	public void visitVarDecl(MobVarDecl mobVarDecl) {
		MobInterpretableVisitor.super.visitVarDecl(mobVarDecl);
		MobAstElement val;
		if (mobVarDecl.initialValue() == null) {
			val = context.newNil();
		} else {
			this.accept(mobVarDecl.initialValue());
			val = this.pop();
		}
		MobVariable var = new MobVariable(mobVarDecl.name(), val);
		context.addVariable(var);
	}

	@Override
	public void visitQuoted(MobQuoted mobQuoted) {
		MobInterpretableVisitor.super.visitQuoted(mobQuoted);
		this.push(mobQuoted);
	}
}
