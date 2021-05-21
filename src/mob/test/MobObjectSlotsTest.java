package mob.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.MobObject;
import mob.model.primitives.MobInteger;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobObjectSlotsTest {

	@Test
	void testSlotAccessing() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( (Object addSubclassNamed: 'MyObject') )");
		interpreter.run("( (MyObject addSlotNamed: 'x') )");
		interpreter.run("( (var o ) )");
		interpreter.run("( (o := MyObject new) )");
		interpreter.run("( (o instVarAt: 0 put: 9) )");
		interpreter.run("( (o instVarAt: 0) )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject i = (MobObject) interpreter.result().get(0);
		assertTrue(i.primValue().equals(9));
	}
	
	@Test
	void testSlotWithinMethod() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object addSubclassNamed: 'MyObject' )");
		interpreter.run("( MyObject addSlotNamed: 'x' )");
		interpreter.run("( MyObject addMethod: [ x := 0 ] named: 'initialize' )");
		interpreter.run("( MyObject addMethod: [ ^ x ] named: 'x' )");
		interpreter.run("( MyObject addMethod: [ v | x := v ] named: 'x:' )");
		interpreter.run("( var o := MyObject new ) ");
		interpreter.run("( o initialize )");
		interpreter.run("( o x )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(0));
		interpreter.run("( o x: 9)");
		interpreter.run("( o x )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(9));
	}
	
	@Test
	void testInheritance() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object addSubclassNamed: 'MyObject' )");
		interpreter.run("( MyObject addMethod: [ ] named: 'initialize' )");
		interpreter.run("( (MyObject addSubclassNamed: 'MySubObject') )");
		interpreter.run("( MySubObject addMethod: [ super initialize ] named: 'initialize' )");
		interpreter.run("( (MySubObject new) initialize )");
	}
	
	@Test
	void testInheritanceWithSlots() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object addSubclassNamed: 'MyObject' )");
		interpreter.run("( MyObject addSlotNamed: 'x' )");
		interpreter.run("( MyObject addMethod: [ x := 1 ] named: 'initialize' )");
		interpreter.run("( MyObject addMethod: [ ^ x ] named: 'x' )");
		interpreter.run("( MyObject addMethod: [ v | x := v ] named: 'x:' )");
		interpreter.run("( (MyObject addSubclassNamed: 'MySubObject') )");
		interpreter.run("( MySubObject addSlotNamed: 'y' )");
		interpreter.run("( MySubObject addMethod: [ (super initialize) (y := 2) ] named: 'initialize' )");
		interpreter.run("( MySubObject addMethod: [ ^ y ] named: 'y' )");
		interpreter.run("( MySubObject addMethod: [ v | y := v ] named: 'y:' )");
		interpreter.run("( var o := MySubObject new ) ");
		interpreter.run("( o initialize )");
		interpreter.run("( o x )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		MobObject x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(1));
		interpreter.run("( o y )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		MobObject y = (MobObject) interpreter.result().get(0);
		assertTrue(y.primValue().equals(2));
		interpreter.run("( o x: 9)");
		interpreter.run("( o x )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(9));
		interpreter.run("( o y: 10)");
		interpreter.run("( o y )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(10));
	}
	
	@Test
	void testClassSlotWithinMethod() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object addSubclassNamed: 'MyObject' )");
		interpreter.run("( (MyObject class) addSlotNamed: 'X' )");
		interpreter.run("( (MyObject class) addMethod: [ X := 0 ] named: 'initialize' )");
		interpreter.run("( (MyObject class) addMethod: [ ^ X ] named: 'X' )");
		interpreter.run("( (MyObject class) addMethod: [ v | X := v ] named: 'X:' )");
		interpreter.run("( MyObject initialize )");
		interpreter.run("( MyObject X )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		MobObject x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(0));
		interpreter.run("( MyObject X: 9 )");
		interpreter.run("( MyObject X )");
		assertTrue(((MobObject)interpreter.result().get(0)).definition() instanceof MobInteger);
		x = (MobObject) interpreter.result().get(0);
		assertTrue(x.primValue().equals(9));
	}

}
