package mob.sinterpreter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.MobClass;
import mob.model.MobObject;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobString;

class MobObjectTest {
	
	@Test
	void testObject() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		interpreter.run("( (Object class) )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
	}
	
	@Test
	void testSubclassCreation() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object addSubclass: 'MyObject' )");
		interpreter.run("( MyObject )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		MobClass myObject = (MobClass) interpreter.result().get(0);
		assertTrue(myObject.name().equals("MyObject"));
		assertTrue(myObject.definition() instanceof MobClass);
		interpreter.run("( MyObject new )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject myObjInstance = (MobObject) interpreter.result().get(0);
		assertTrue(myObjInstance.definition() instanceof MobClass);
		assertTrue(myObjInstance.definition().name().equals("MyObject"));
		assertTrue(myObjInstance.definition().definition() instanceof MobClass);
		assertTrue(myObjInstance.definition().definition().definition() instanceof MobClass);
		interpreter.run("( ( MyObject addMethod: [ i | 0 + i ] named: '+' ) )");
		interpreter.run("( MyObject new )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		interpreter.run("( MyObject addSubclass: 'MySubObject' )");
		interpreter.run("( (MySubObject new ) + 5)");
		assertTrue(interpreter.result().get(0) instanceof MobInteger);
		MobInteger i = (MobInteger) interpreter.result().get(0);
		assertTrue(i.rawValue() == 5);
		interpreter.run("( ( (MySubObject class) addMethod: [ ^ 'stuff returned'  ] named: 'stuff' ) )");
		interpreter.run("( MySubObject stuff )");
		assertTrue(interpreter.result().get(0) instanceof MobString);
		MobString s = (MobString) interpreter.result().get(0);
		assertTrue(s.rawValue().equals("stuff returned"));
	}

}
