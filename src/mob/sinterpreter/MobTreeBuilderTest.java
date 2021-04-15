package mob.sinterpreter;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.model.MobAssign;
import mob.model.MobExp;
import mob.model.MobUnit;
import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

class MobTreeBuilderTest {

	@Test
	void testPrimitives() {
		MobTreeBuilder builder = new MobTreeBuilder();
		List<MobExp> trees;

		trees = builder.run("nil");
		assertTrue(trees.get(0) instanceof MobNil);
		
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
		
		trees = builder.run("\"Hello world\"");
		assertTrue(trees.get(0) instanceof MobString);
		assertTrue( ((MobString) trees.get(0)).rawValue().equals("Hello world"));
		
		trees = builder.run("\"\"");
		assertTrue(trees.get(0) instanceof MobString);
		assertTrue( ((MobString) trees.get(0)).rawValue().equals(""));
		
		trees = builder.run("true");
		assertTrue(trees.get(0) instanceof MobTrue);
		assertTrue( ((MobTrue) trees.get(0)).rawValue());
		
		trees = builder.run("false");
		assertTrue(trees.get(0) instanceof MobFalse);
		assertFalse( ((MobFalse) trees.get(0)).rawValue());
		
		trees = builder.run("do:");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue( ((MobSymbol) trees.get(0)).is("do:"));
		
		trees = builder.run("counter");
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue( ((MobSymbol) trees.get(0)).is("counter"));
	}
	
	@Test
	void testAssign1() throws IOException {
		MobTreeBuilder builder = new MobTreeBuilder();
		List<MobExp> trees;
		MobAssign assign;

		trees = builder.run("X := nil");
		assertTrue(trees.size() == 3);
		assertTrue(trees.get(0) instanceof MobSymbol);
		assertTrue(trees.get(0).parent() == null);
		assertTrue(trees.get(1) instanceof MobSymbol);
		assertTrue(trees.get(1).parent() == null);
		assertTrue(trees.get(2) instanceof MobNil);
		assertTrue(trees.get(2).parent() == null);
		
		trees = builder.run("( X := nil )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.get(0) instanceof MobSymbol);
		assertTrue(assign.get(0).parent() == assign);
		assertTrue(assign.get(1) instanceof MobSymbol);
		assertTrue(assign.get(1).parent() == assign);
		assertTrue(assign.get(2) instanceof MobNil);
		assertTrue(assign.get(2).parent() == assign);

		trees = builder.run("( X := 1 )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(assign.get(2) instanceof MobInteger);
		assertTrue(((MobInteger)assign.get(2)).is(1));
		
		trees = builder.run("( X := (1 < z) )");
		assertTrue(trees.get(0) instanceof MobAssign);
		assign = (MobAssign) trees.get(0);
		assertTrue(((MobUnit)(assign.get(2))).get(0).is(1));
		assertTrue(((MobUnit)(assign.get(2))).get(1).is("<"));
		assertTrue(((MobUnit)(assign.get(2))).get(2).is("z"));
	}

	@Test
	void testMessageSend() throws IOException {
		MobTreeBuilder builder = new MobTreeBuilder();
		List<MobExp> trees;
		trees = builder.run("(robi setColor: (Color red))");
		assertTrue(trees.get(0) instanceof MobUnit);
		trees = builder.run("((robi x < space width) whileTrue: (robi translate: (1 @ 0)))");
		assertTrue(trees.get(0) instanceof MobUnit);
	}

	
}
