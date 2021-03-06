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
import mob.model.MobObject;
import mob.model.primitives.MobSequence;
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
		assertTrue(trees.get(0) instanceof MobObject);
		assertTrue(((MobObject) trees.get(0)).primValue().equals(10));

		trees = builder.run("-10");
		assertTrue(trees.get(0) instanceof MobObject);
		assertTrue(((MobObject) trees.get(0)).primValue().equals(-10));

		trees = builder.run("+10");
		assertTrue(trees.get(0) instanceof MobObject);
		assertTrue(((MobObject) trees.get(0)).primValue().equals(10));

		trees = builder.run("10.0");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Float")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals((float)10.0));

		trees = builder.run("-10.5");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Float")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals((float)-10.5));

		trees = builder.run("1.40e-45f");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Float")));

		trees = builder.run("'Hello world'");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("String")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals("Hello world"));

		trees = builder.run("''");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("String")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals(""));

		trees = builder.run("true");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals("true"));

		trees = builder.run("false");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals("false"));

		trees = builder.run("do:");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals("do:"));

		trees = builder.run("counter");
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) trees.get(0)).primValue().equals("counter"));
	}

	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		MobAssign assign;

		trees = builder.run("X := nil");
		assertTrue(trees.size() == 3);
		assertTrue(((MobObject)trees.get(0)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject)trees.get(1)).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject)trees.get(2)).isKindOf(env.getClassByName("Symbol")));

		trees = builder.run("( X := nil )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(((MobObject) assign.left()).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) assign.right()).isKindOf(env.getClassByName("Symbol")));

		trees = builder.run("( X := 1 )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.right() instanceof MobObject);
		assertTrue(((MobObject) assign.right()).primValue().equals(1));

		trees = builder.run("( X := (1 < z) )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(((MobObject) assign.left()).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) assign.left()).primValue().equals("X"));
		assertTrue(assign.right() instanceof MobBinaryMessage);
		MobBinaryMessage right = (MobBinaryMessage) assign.right();
		assertTrue(right.operator().toString().equals("<"));
		assertTrue(right.receiver() instanceof MobObject);
		assertTrue(((MobObject) right.argument()).isKindOf(env.getClassByName("Symbol")));

	}

	@Test
	void testMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;

		trees = builder.run("( res set: (1 < z) )");
		assertTrue(trees.get(0) instanceof MobKeywordMessage);
		MobKeywordMessage kwms = (MobKeywordMessage) trees.get(0);
		assertTrue(((MobObject) kwms.receiver()).isKindOf(env.getClassByName("Symbol")));
		assertTrue(((MobObject) kwms.receiver()).primValue().equals("res"));
		assertTrue(kwms.keywords().length == 1);
		assertTrue(kwms.keywords()[0].toString().equals("set:"));
		assertTrue(kwms.arguments().length == 1);
		assertTrue(kwms.arguments()[0] instanceof MobBinaryMessage);
		MobBinaryMessage right = (MobBinaryMessage) kwms.arguments()[0];
		assertTrue(right.operator().toString().equals("<"));
		assertTrue(right.receiver() instanceof MobObject);
		assertTrue(((MobObject) right.argument()).isKindOf(env.getClassByName("Symbol")));
	}

	@Test
	void testVarDecl() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;

		trees = builder.run("(var X)");
		assertTrue(trees.size() == 1);
		assertTrue(trees.get(0) instanceof MobVarDecl);
		MobVarDecl decl = (MobVarDecl) trees.get(0);
		assertTrue(decl.name().equals("X"));
		assertTrue(decl.initialValue() == null);

		trees = builder.run("(var X := 99)");
		assertTrue(trees.size() == 1);
		assertTrue(trees.get(0) instanceof MobVarDecl);
		decl = (MobVarDecl) trees.get(0);
		assertTrue(decl.name().equals("X"));
		assertTrue(decl.initialValue() instanceof MobObject);
		MobObject init = (MobObject) decl.initialValue();
		assertTrue(init.primValue().equals(99));
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
		trees = builder.run("[ (var X := 0) (X := (X + 1)) (^ X) ]");
		MobObject unit = ((MobObject) trees.get(0));
		assertTrue(unit.definition() instanceof MobUnit);
		MobUnit unitCls = (MobUnit) unit.definition();
		assertFalse(unitCls.hasParameters(unit));
		MobAstElement e = unitCls.code(unit);
		assertTrue(((MobObject)e).definition() instanceof MobSequence);
		MobObject seq = (MobObject) e;
		assertTrue(seq.primValueAt(0) instanceof MobVarDecl);
		assertTrue(seq.primValueAt(1) instanceof MobAssign);
		assertTrue(seq.primValueAt(2) instanceof MobReturn);
		assertTrue(((MobAssign) seq.primValueAt(1)).right() instanceof MobBinaryMessage);
	}

	@Test
	void testUnit() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobTreeBuilder builder = new MobTreeBuilder(env);
		List<MobAstElement> trees;
		trees = builder.run("[ a b | ( (a + b) + b ) ]");
		MobObject unit = ((MobObject) trees.get(0));
		assertTrue(unit.definition() instanceof MobUnit);
		MobUnit unitCls = (MobUnit) unit.definition();
		assertTrue(unitCls.formalParameters(unit).size() == 2);
		assertTrue(unitCls.formalParameters(unit).get(0).equals("a"));
		assertTrue(unitCls.formalParameters(unit).get(1).equals("b"));
		assertTrue(unitCls.code(unit) instanceof MobBinaryMessage);
		MobBinaryMessage bin0 = (MobBinaryMessage) unitCls.code(unit);
		MobBinaryMessage bin1 = (MobBinaryMessage) bin0.receiver();
		assertTrue(bin1 instanceof MobBinaryMessage);
		
		trees = builder.run("[ a b | (a + b) + b ]");
		unit = ((MobObject) trees.get(0));
		assertTrue(unit.definition() instanceof MobUnit);
		unitCls = (MobUnit) unit.definition();
		assertTrue(unitCls.formalParameters(unit).size() == 2);
		assertTrue(unitCls.formalParameters(unit).get(0).equals("a"));
		assertTrue(unitCls.formalParameters(unit).get(1).equals("b"));
		assertTrue(unitCls.code(unit) instanceof MobBinaryMessage);
		bin0 = (MobBinaryMessage) unitCls.code(unit);
		bin1 = (MobBinaryMessage) bin0.receiver();
		assertTrue(bin1 instanceof MobBinaryMessage);

	}

}
