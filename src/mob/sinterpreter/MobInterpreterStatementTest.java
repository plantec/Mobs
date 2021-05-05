package mob.sinterpreter;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.model.MobEntity;
import mob.model.MobUnit;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobTrue;

class MobInterpreterStatementTest {

	
	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobEntity> result = interpreter.run("(1 + 1)");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobInteger);
		MobInteger r = (MobInteger) result.get(0);
		assertTrue (r.rawValue() == 2);
		
		result = interpreter.run("(1 + (1))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobInteger);
		r = (MobInteger) result.get(0);
		assertTrue (r.rawValue() == 2);
		
		result = interpreter.run("((1) + ((1)))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobInteger);
		r = (MobInteger) result.get(0);
		assertTrue (r.rawValue() == 2);
		
		result = interpreter.run("(1 + (1 + 1))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobInteger);
		r = (MobInteger) result.get(0);
		assertTrue (r.rawValue() == 3);
		
		result = interpreter.run("(1 + (1 + 1.5))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobFloat);
		MobFloat f = (MobFloat) result.get(0);
		assertTrue (f.rawValue() == 3.5);
		
		result = interpreter.run("( (2 * (10 - (4 / 2))) negated )");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobInteger);
		r = (MobInteger) result.get(0);
		assertTrue (r.rawValue() == -16);
		
		result = interpreter.run("( (10 > 0) )");
		assertTrue(result.get(0) instanceof MobTrue);
		result = interpreter.run("( (10 = 10) )");
		assertTrue(result.get(0) instanceof MobTrue);
		result = interpreter.run("( (10 = 10.0) )");
		assertTrue(result.get(0) instanceof MobTrue);
		result = interpreter.run("( (10.0 = 10.0) )");
		assertTrue(result.get(0) instanceof MobTrue);
		result = interpreter.run("( (10.0 = 10) )");
		assertTrue(result.get(0) instanceof MobTrue);
	}
	
	@Test
	void testKeywordMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("(true ifTrue: '(99 println))");
	}
	
	@Test
	void testUnit() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobEntity> result = interpreter.run("'(10 println)");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobUnit);
		
		result = interpreter.run("( '(10 println) value)");
		assertTrue(result.size() == 0);
	}

	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		Object res;

		res = interpreter.run("( X := nil )");
		assertTrue(res instanceof MobVariable);
		MobVariable var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobNil);

		res = interpreter.run("( X := 1 )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := (1) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := ((1)) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);

		res = interpreter.run("( X := (Y := 1) )");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("X"));
		assertTrue(var.value() instanceof MobInteger);
		assertTrue(((MobInteger) var.value()).rawValue() == 1);
		//assertTrue(interpreter.context.getVariableByName("X") != null);
		//MobVariable x = (MobVariable) interpreter.context.getVariableByName("X");
		//assertTrue(x != null);
		//MobInteger p = (MobInteger) x.value();
		//assertTrue(p.rawValue() == 1);
	}

	@Test
	void testAssign2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		Object res;
		MobVariable var;

		res = interpreter.run("( (X := 1) (Y := X) ) ");
		assertTrue(res instanceof MobVariable);
		var = (MobVariable) res;
		assertTrue(var.name().equals("Y"));
	}

}
