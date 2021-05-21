package mob.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mob.model.MobClass;
import mob.model.MobMetaClass;
import mob.sinterpreter.MobEnvironment;
import mob.sinterpreter.MobInterpreter;

class MobEnvironmentTest {
	static MobEnvironment env;
	static MobMetaClass mobMetaCls;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		env = new MobEnvironment();
		new MobInterpreter(env);
		mobMetaCls = (MobMetaClass) env.getClassByName("MetaClass");
	}

	@Test
	void testMetaClass() {
		assertTrue(mobMetaCls instanceof MobClass);
		assertTrue(mobMetaCls.name().equals("MetaClass"));
		assertTrue(mobMetaCls.superclass() instanceof MobClass);
		assertTrue(mobMetaCls.superclass().name().equals("Behavior"));
		assertTrue(mobMetaCls.definition() instanceof MobMetaClass);
		assertTrue(mobMetaCls.definition().name().equals("MetaClass class"));
		assertTrue(((MobMetaClass) mobMetaCls.definition()).soleInstance() == mobMetaCls);
	}

	@Test
	void testObject() {
		MobClass mobCls = env.getClassByName("Object");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Object"));
		assertTrue(mobCls.superclass() == null);
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Object class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}
	
	@Test
	void testClass() {
		MobClass mobCls = env.getClassByName("Class");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Class"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Behavior"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Class class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}
	
	@Test
	void testFloat() {
		MobClass mobCls = env.getClassByName("Float");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Float"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Float class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}
	
	@Test
	void testInteger() {
		MobClass mobCls = env.getClassByName("Integer");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Integer"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Integer class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}
	
	@Test
	void testCharacter() {
		MobClass mobCls = env.getClassByName("Character");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Character"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Character class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testString() {
		MobClass mobCls = env.getClassByName("String");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("String"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("String class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}
	
	@Test
	void testSymbol() {
		MobClass mobCls = env.getClassByName("Symbol");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Symbol"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Symbol class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testBoolean() {
		MobClass mobCls = env.getClassByName("Boolean");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Boolean"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Boolean class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testTrue() {
		MobClass mobCls = env.getClassByName("True");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("True"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Boolean"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("True class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testUndefinedObject() {
		MobClass mobCls = env.getClassByName("UndefinedObject");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("UndefinedObject"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("UndefinedObject class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testSequence() {
		MobClass mobCls = env.getClassByName("Sequence");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Sequence"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Sequence class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testFalse() {
		MobClass mobCls = env.getClassByName("False");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("False"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Boolean"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("False class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

	@Test
	void testUnit() {
		MobClass mobCls = env.getClassByName("Unit");
		assertTrue(mobCls instanceof MobClass);
		assertTrue(mobCls.name().equals("Unit"));
		assertTrue(mobCls.superclass() instanceof MobClass);
		assertTrue(mobCls.superclass() == env.getClassByName("Object"));
		assertTrue(mobCls.definition() instanceof MobMetaClass);
		assertTrue(mobCls.definition().name().equals("Unit class"));
		assertTrue(((MobMetaClass) mobCls.definition()).soleInstance() == mobCls);
		assertTrue(mobCls.definition().definition() == mobMetaCls);
	}

}
