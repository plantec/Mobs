package mob.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

import mob.ast.MobAssign;
import mob.ast.MobAstElement;
import mob.ast.MobBinaryMessage;
import mob.ast.MobKeywordMessage;
import mob.ast.MobReturn;
import mob.ast.MobVarDecl;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobPrimitive;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobUnit;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobTreeBuilder;

class MobTreeBuilderTest {

	@Test
	void testPrimitives() {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;

		trees = builder.run("10");
		assertTrue(trees.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) trees.get(0)).rawValue() == 10);

		trees = builder.run("-10");
		assertTrue(trees.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) trees.get(0)).rawValue() == -10);

		trees = builder.run("+10");
		assertTrue(trees.get(0) instanceof MobInteger);
		assertTrue(((MobInteger) trees.get(0)).rawValue() == 10);

		trees = builder.run("10.0");
		assertTrue(trees.get(0) instanceof MobFloat);
		assertTrue(((MobFloat) trees.get(0)).rawValue() == 10.0);

		trees = builder.run("-10.5");
		assertTrue(trees.get(0) instanceof MobFloat);
		assertTrue(((MobFloat) trees.get(0)).rawValue() == -10.5);

		trees = builder.run("1.40e-45f");
		assertTrue(trees.get(0) instanceof MobFloat);

		trees = builder.run("'Hello world'");
		assertTrue(trees.get(0) instanceof MobString);
		assertTrue(((MobString) trees.get(0)).rawValue().equals("Hello world"));

		trees = builder.run("''");
		assertTrue(trees.get(0) instanceof MobString);
		assertTrue(((MobString) trees.get(0)).rawValue().equals(""));

		trees = builder.run("true");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(((MobSymbol) trees.get(0)).is("true"));

		trees = builder.run("false");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(((MobSymbol) trees.get(0)).is("false"));

		trees = builder.run("do:");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(((MobSymbol) trees.get(0)).is("do:"));

		trees = builder.run("counter");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(((MobSymbol) trees.get(0)).is("counter"));
	}

	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		MobAssign assign;

		trees = builder.run("X := nil");
		assertTrue(trees.size() == 3);
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(trees.get(1) instanceof MobSymbol);
		assertTrue(trees.get(2) instanceof MobSymbol);

		trees = builder.run("( X := nil )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.left() instanceof MobSymbol);
		assertTrue(assign.right() instanceof MobSymbol);

		trees = builder.run("( X := 1 )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.right() instanceof MobInteger);
		assertTrue(((MobInteger) assign.right()).is(1));

		trees = builder.run("( X := (1 < z) )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.left() instanceof MobSymbol);
		assertTrue(((MobSymbol) assign.left()).rawValue().equals("X"));
		assertTrue(assign.right() instanceof MobBinaryMessage);
		MobBinaryMessage right = (MobBinaryMessage) assign.right();
		assertTrue(right.operator().toString().equals("<"));
		assertTrue(right.receiver() instanceof MobInteger);
		assertTrue(right.argument() instanceof MobSymbol);

	}

	@Test
	void testMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;

		trees = builder.run("( res set: (1 < z) )");
		assertTrue(trees.get(0) instanceof MobKeywordMessage);
		MobKeywordMessage kwms = (MobKeywordMessage) trees.get(0);
		assertTrue(kwms.receiver() instanceof MobSymbol);
		assertTrue(((MobSymbol) kwms.receiver()).rawValue().equals("res"));
		assertTrue(kwms.keywords().length == 1);
		assertTrue(kwms.keywords()[0].toString().equals("set:"));
		assertTrue(kwms.arguments().length == 1);
		assertTrue(kwms.arguments()[0] instanceof MobBinaryMessage);
		MobBinaryMessage right = (MobBinaryMessage) kwms.arguments()[0];
		assertTrue(right.operator().toString().equals("<"));
		assertTrue(right.receiver() instanceof MobInteger);
		assertTrue(right.argument() instanceof MobSymbol);
	}

	@Test
	void testVarDecl() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;

		trees = builder.run("(decl X)");
		assertTrue(trees.size() == 1);
		assertTrue(trees.get(0) instanceof MobVarDecl);
		MobVarDecl decl = (MobVarDecl) trees.get(0);
		assertTrue(decl.name().equals("X"));
		assertTrue(decl.initialValue() == null);

		trees = builder.run("(decl X := 99)");
		assertTrue(trees.size() == 1);
		assertTrue(trees.get(0) instanceof MobVarDecl);
		decl = (MobVarDecl) trees.get(0);
		assertTrue(decl.name().equals("X"));
		assertTrue(decl.initialValue() instanceof MobPrimitive);
		@SuppressWarnings("rawtypes")
		MobPrimitive init = (MobPrimitive) decl.initialValue();
		assertTrue(init.is(99));
	}

	@Test
	void testMessageSend() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		trees = builder.run("(robi setColor: (Color red))");
		assertTrue(trees.get(0) instanceof MobKeywordMessage);
		trees = builder.run("(((robi x) < (space width)) whileTrue: (robi translate: (1 @ 0)))");
		assertTrue(trees.get(0) instanceof MobKeywordMessage);
	}

	@Test
	void testProg1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		trees = builder.run("[ (decl X := 0) (X := (X + 1)) (^ X) ]");
		assertTrue(trees.get(0) instanceof MobUnit);
		MobUnit unit = (MobUnit) trees.get(0);
		assertFalse(unit.hasParameters());
		MobAstElement e = unit.code();
		assertTrue(e instanceof MobSequence);
		MobSequence seq = (MobSequence) e;
		assertTrue(seq.get(0) instanceof MobVarDecl);
		assertTrue(seq.get(1) instanceof MobAssign);
		assertTrue(seq.get(2) instanceof MobReturn);
		assertTrue(((MobAssign) seq.get(1)).right() instanceof MobBinaryMessage);
	}

	@Test
	void testUnit() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		trees = builder.run("[ a b | ( (a + b) + b ) ]");
		assertTrue(trees.get(0) instanceof MobUnit);
		MobUnit unit = (MobUnit) trees.get(0);
		assertTrue(unit.parameters().size() == 2);
		assertTrue(unit.parameters().get(0).equals("a"));
		assertTrue(unit.parameters().get(1).equals("b"));
		assertTrue(unit.code() instanceof MobBinaryMessage);
		MobBinaryMessage bin0 = (MobBinaryMessage) unit.code();
		MobBinaryMessage bin1 = (MobBinaryMessage) bin0.receiver();
		assertTrue(bin1 instanceof MobBinaryMessage);
		
		trees = builder.run("[ a b | (a + b) + b ]");
		assertTrue(trees.get(0) instanceof MobUnit);
		unit = (MobUnit) trees.get(0);
		assertTrue(unit.parameters().size() == 2);
		assertTrue(unit.parameters().get(0).equals("a"));
		assertTrue(unit.parameters().get(1).equals("b"));
		assertTrue(unit.code() instanceof MobBinaryMessage);
		bin0 = (MobBinaryMessage) unit.code();
		bin1 = (MobBinaryMessage) bin0.receiver();
		assertTrue(bin1 instanceof MobBinaryMessage);

	}

}
