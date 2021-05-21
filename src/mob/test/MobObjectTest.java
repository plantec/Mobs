package mob.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

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
		interpreter.run("( Object addSubclassNamed: 'MyObject' )");
		System.out.println(interpreter.result().size());
		interpreter.run("( MyObject )");
		System.out.println(interpreter.result().size());
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		MobClass myObject = (MobClass) interpreter.result().get(0);
		assertTrue(myObject.name().equals("MyObject"));
		assertTrue(myObject.definition() instanceof MobClass);
		interpreter.run("( MyObject new )");
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		System.out.println(interpreter.result().size());
		MobObject myObjInstance = (MobObject) interpreter.result().get(0);
		assertTrue(myObjInstance.definition() instanceof MobClass);
		assertTrue(myObjInstance.definition().name().equals("MyObject"));
		assertTrue(myObjInstance.definition().definition() instanceof MobMetaClass);
		System.out.println("myObjInstance.definition(): " + myObjInstance.definition().name());
		System.out.println("myObjInstance.definition().definition(): " + myObjInstance.definition().definition().name());
		System.out.println("myObjInstance.definition().definition().definition(): " + myObjInstance.definition().definition().definition().name());
		System.out.println("myObjInstance.definition().definition().definition().definition(): " + myObjInstance.definition().definition().definition().definition().name());
		assertTrue(myObjInstance.definition().definition().definition() instanceof MobClass);
		interpreter.run("( ( MyObject addMethod: [ i | 0 + i ] named: '+' ) )");
		interpreter.run("( MyObject new )");
		System.out.println(interpreter.result().size());
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		interpreter.run("( MyObject addSubclassNamed: 'MySubObject' )");
		interpreter.run("( (MySubObject new ) + 5)");
		System.out.println(interpreter.result().size());
		assertTrue(interpreter.result().get(0) instanceof MobObject);
		MobObject i = (MobObject) interpreter.result().get(0);
		assertTrue(i.primValue().equals(5));
		interpreter.run("( ( (MySubObject class) addMethod: [ ^ 'stuff returned'  ] named: 'stuff' ) )");
		interpreter.run("( MySubObject stuff )");
		System.out.println(interpreter.result().size());
		assertTrue(((MobObject)interpreter.result().get(0)).isKindOf(env.getClassByName("String")));
		MobObject s = (MobObject) interpreter.result().get(0);
		assertTrue(s.primValue().equals("stuff returned"));
	}

}
