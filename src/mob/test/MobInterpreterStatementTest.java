package mob.test;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import mob.ast.MobAstElement;
import mob.ast.MobQuoted;
import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobUnit;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobInterpreterStatementTest {

	
	@Test
	void testBinaryMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result = interpreter.run("(1 + 1)");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(2)));
		
		result = interpreter.run("(1 + (1))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(2)));
		
		result = interpreter.run("((1) + ((1)))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(2)));
		
		result = interpreter.run("(1 + (1 + 1))");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(3)));
		
		result = interpreter.run("(1 + (1 + 1.5))");
		assertTrue(result.size()==1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("Float")));
		MobObject f = (MobObject) result.get(0);
		assertTrue (f.primValue().equals((float)3.5));
		
		result = interpreter.run("( (2 * (10 - (4 / 2))) negated )");
		assertTrue(result.size()==1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(-16)));
		
		result = interpreter.run("( (10 > 0) )");
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("True")));
		result = interpreter.run("( (10 = 10) )");
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("True")));
		result = interpreter.run("( (10 = 10.0) )");
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("False")));
		result = interpreter.run("( (10.0 = 10.0) )");
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("True")));
		result = interpreter.run("( (10.0 = 10) )");
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("False")));
	}
	
	@Test
	void testKeywordMessageSend1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result = interpreter.run("(true ifTrue: [ 'TRUE' println ] )");
		assertTrue(result.size() == 1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("String")));
		result = interpreter.run("(true ifTrue: [ 'TRUE' println ]  ifFalse: [ 'FALSE' println] ) ");
		assertTrue(result.size() == 1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("String")));
		result = interpreter.run("(false ifFalse: [ 'FALSE' println ] )");
		assertTrue(result.size() == 1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("String")));
		result = interpreter.run("(false ifTrue: ['TRUE' println ] ifFalse: [ 'FALSE' println ] )");
		assertTrue(result.size() == 1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("String")));
		result = interpreter.run("( false ifTrue: [ 'TRUE' println ] ifFalse: [ 'FALSE' println ] )");
		assertTrue(result.size() == 1);
		assertTrue(((MobObject)result.get(0)).isKindOf(env.getClassByName("String")));
	}
	
	@Test
	void testUnit1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result = interpreter.run("(10 println)");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(10)));
		result = interpreter.run("( [ 10 println ] value )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(10)));
		result = interpreter.run("( [ 10 println ] )");
		assertTrue(result.size() == 1);
		MobObject u = ((MobObject) result.get(0)).definition();
		assertTrue(u instanceof MobUnit);
	}
	
	@Test
	void testUnit2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result;
		result = interpreter.run("( [ v |  v println  ] value: 10 )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(10)));
		
		result = interpreter.run("( [ v | (var X := 1) ((v + X) println)  ] value: 10 )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(11)));
		
		result = interpreter.run("( [ v | (var X := 1) ((v + X) println)  ] value: (9 + 1) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(11)));
		
		result = interpreter.run("( (var p := 9 + 1) ( [ v | (var X := 1) ((v + X) println)  ] value: p ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(11)));
		
		result = interpreter.run("( [ 9 + 1 ] ) ");
		assertTrue(result.size() == 1);
		MobObject u = ((MobObject) result.get(0)).definition();
		assertTrue(u instanceof MobUnit);
		
		result = interpreter.run("( (var p2 := [ 9 + 1 ] ) ( [ v | (var X := 1) (((v value) + X) println)  ] value: p2 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(11)));
		
		result = interpreter.run("( [ u v | (var X := 1) (((v + X) + u) println)  ] value: 10 value: 5)");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(16)));
		
		result = interpreter.run("( [ u v | (var X := 1) (((v + X) + u) println)  ] values: `( 10 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(16)));
		
		result = interpreter.run("( [ u v | (var X := 1) (((v + X) + (u value)) println)  ] values: `( [9 + 1] 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobObject);
		assertTrue((((MobObject)result.get(0)).primValue().equals(16)));
	}

	@Test
	void testQuoted() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> result;
		result = interpreter.run("( `( 10 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobQuoted);
		MobClass def = ((MobObject)((MobQuoted) result.get(0)).entity()).definition();
		assertTrue(def instanceof MobSequence);
		MobObject seq = (MobObject) ((MobQuoted) result.get(0)).entity();
		assertTrue(((MobObject)seq.primValueAt(0)).primValue().equals(10));
		assertTrue(((MobObject)seq.primValueAt(1)).primValue().equals(5));
		
		result = interpreter.run("( `( [9 + 1] 5 ) )");
		assertTrue(result.size() == 1);
		assertTrue(result.get(0) instanceof MobQuoted);
		def = ((MobObject)((MobQuoted) result.get(0)).entity()).definition();
		assertTrue(def instanceof MobSequence);
		seq = (MobObject) ((MobQuoted) result.get(0)).entity();
		assertTrue(((MobObject) seq.primValueAt(0)).definition() instanceof MobUnit);
		assertTrue(((MobObject)seq.primValueAt(1)).primValue().equals(5));
	}
	
	@Test
	void testAssign1() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		List<MobAstElement> res;

		res = interpreter.run("( (var X) (X := nil) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue(((MobObject) res.get(0)).definition().name().equals("UndefinedObject"));
		
		res = interpreter.run("( X := 9 )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((((MobObject)res.get(0)).primValue().equals(9)));
		
		res = interpreter.run("( (var Y := 1) (Y := 9 + (X + Y ) ) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((((MobObject)res.get(0)).primValue().equals(19)));
		
		res = interpreter.run("( (var Z := 1) ([ (var y := 2) (Z := Z + y) ] value) )");
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) instanceof MobObject);
		assertTrue((((MobObject)res.get(0)).primValue().equals(3)));
	}

	@Test
	void testAssign2() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);

		interpreter.run("( (var X) (var Y := 1) (X := Y + Y) ) ");
		assertTrue(interpreter.result().size() == 1);
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		assertTrue((((MobObject)interpreter.result().get(0)).primValue().equals(2)));
	}

}
