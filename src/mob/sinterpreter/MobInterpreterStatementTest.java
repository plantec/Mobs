package mob.sinterpreter;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.ast.MobQuoted;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobString;
import mob.model.primitives.MobTrue;
import mob.model.primitives.MobUnit;

class MobInterpreterStatementTest {

	
	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result = interpreter.run("(1 + 1)");
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
		List<MobAstElement> result = interpreter.run("(true ifTrue: [ \"TRUE\" println ] )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobString);
		result = interpreter.run("(true ifTrue: [ \"TRUE\" println ]  ifFalse: [ \"FALSE\" println] ) ");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobString);
		result = interpreter.run("(false ifFalse: [ \"FALSE\" println ] )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobString);
		result = interpreter.run("(false ifTrue: [\"TRUE\" println ] ifFalse: [ \"FALSE\" println ] )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobString);
		result = interpreter.run("( false ifTrue: [ \"TRUE\" println ] ifFalse: [ \"FALSE\" println ] )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobString);
	}
	
	@Test
	void testUnit1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result = interpreter.run("(10 println)");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(10));
		result = interpreter.run("( [ 10 println ] value )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(10));
		result = interpreter.run("( [ 10 println ] )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobUnit);
	}
	
	@Test
	void testUnit2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result;
		result = interpreter.run("( [ v |  v println  ] value: 10 )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(10));
		
		result = interpreter.run("( [ v | (decl X := 1) ((v + X) println)  ] value: 10 )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(11));
		
		result = interpreter.run("( [ v | (decl X := 1) ((v + X) println)  ] value: (9 + 1) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(11));
		
		result = interpreter.run("( (decl p := 9 + 1) ( [ v | (decl X := 1) ((v + X) println)  ] value: p ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(11));
		
		result = interpreter.run("( (decl p := [ 9 + 1 ] ) ( [ v | (decl X := 1) (((v value) + X) println)  ] value: p ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(11));
		
		result = interpreter.run("( [ u v | (decl X := 1) (((v + X) + u) println)  ] value: 10 value: 5)");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(16));
		
		result = interpreter.run("( [ u v | (decl X := 1) (((v + X) + u) println)  ] values: '( 10 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(16));
		
		result = interpreter.run("( [ u v | (decl X := 1) (((v + X) + (u value)) println)  ] values: '( [9 + 1] 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobInteger);
		assertTrue(result.get(0).is(16));
	}

	@Test
	void testQuoted() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result;
		result = interpreter.run("( '( 10 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobQuoted);
		assertTrue(((MobQuoted) result.get(0)).entity() instanceof MobSequence);
		MobSequence seq = (MobSequence) ((MobQuoted) result.get(0)).entity();
		assertTrue(seq.get(0).is(10));
		assertTrue(seq.get(1).is(5));
		
		result = interpreter.run("( '( [9 + 1] 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobQuoted);
		assertTrue(((MobQuoted) result.get(0)).entity() instanceof MobSequence);
		seq = (MobSequence) ((MobQuoted) result.get(0)).entity();
		assertTrue(seq.get(0) instanceof MobUnit);
		assertTrue(seq.get(1).is(5));
	}
	
	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> res;

		res = interpreter.run("( (decl X) (X := nil) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobNil);
		
		res = interpreter.run("( (decl X) (X := 9) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(res.get(0).is(9));
		
		res = interpreter.run("( (decl X := 1) (X := 9 + X) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(res.get(0).is(10));
		
		res = interpreter.run("( (decl X := 1) ([ (decl y := 2) (X := X + y) ] value) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobInteger);
		assertTrue(res.get(0).is(3));
	}

	@Test
	void testAssign2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);

		interpreter.run("( (decl X) (decl Y := 1) (X := Y + Y) ) ");
		assertTrue(interpreter.result().size() == 1);
		assertTrue(interpreter.result().get(0) instanceof MobInteger);
		MobInteger i = (MobInteger) interpreter.result().get(0);
		assertTrue(i.is(2));
	}

}
