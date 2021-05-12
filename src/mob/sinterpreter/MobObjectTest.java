package mob.sinterpreter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.model.MobObject;
import mob.model.MobProtoObject;
import mob.model.primitives.MobInteger;

class MobObjectTest {

	@Test
	void testProtoObject() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( ProtoObject )");
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		assertTrue(interpreter.result().get(0) instanceof MobProtoObject);
		interpreter.run("( (ProtoObject definition) )");
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
		assertTrue(((MobMetaClass) interpreter.result().get(0)).name().equals("ProtoObject class"));
		interpreter.run("( (( ProtoObject definition) definition ) println )");
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
	}
	
	@Test
	void testObject() throws IOException {
		MobEnvironment env = new MobEnvironment();
		MobInterpreter interpreter = new MobInterpreter(env);
		interpreter.run("( Object )");
		System.out.println(interpreter.result().get(0));
		assertTrue(interpreter.result().get(0) instanceof MobClass);
		interpreter.run("( (Object definition) )");
		System.out.println(interpreter.result().get(0));
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
		assertTrue(((MobMetaClass) interpreter.result().get(0)).name().equals("Object class"));
		interpreter.run("( (( Object definition) definition ) println )");
		assertTrue(interpreter.result().get(0) instanceof MobMetaClass);
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
		System.out.println(myObject.definition().name());
		assertTrue(myObject.definition().name().equals("MyObject class"));
		assertTrue(myObject.definition().definition().name().equals("MetaClass"));
		System.out.println(myObject);
		interpreter.run("( MyObject new )");
		MobObject myObjInstance = (MobObject) interpreter.result().get(0);
		assertTrue(myObjInstance.definition() instanceof MobClass);
		assertTrue(myObjInstance.definition().name().equals("MyObject"));
		assertTrue(myObjInstance.definition().definition() instanceof MobMetaClass);
		assertTrue(myObjInstance.definition().definition().name().equals("MyObject class"));
		assertTrue(myObjInstance.definition().definition().definition().name().equals("MetaClass"));
		assertTrue(myObjInstance.definition().definition().definition().definition().name().equals("MetaClass"));
		interpreter.run("( ( MyObject addMethod: [ i | 0 + i ] named: '+' ) )");
		interpreter.run("( (MyObject new ) + 5)");
		assertTrue(interpreter.result().get(0) instanceof MobInteger);
		MobInteger i = (MobInteger) interpreter.result().get(0);
		assertTrue(i.rawValue() == 5);
	}

}
