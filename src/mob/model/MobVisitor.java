package mob.model;

import mob.model.primitives.MobFalse;
import mob.model.primitives.MobFloat;
import mob.model.primitives.MobInteger;
import mob.model.primitives.MobNil;
import mob.model.primitives.MobString;
import mob.model.primitives.MobSymbol;
import mob.model.primitives.MobTrue;

public interface MobVisitor {

	default void visitTrue(MobTrue mobTrue) {
	}

	default void visitFalse(MobFalse mobFalse) {
	}

	default void visitFloat(MobFloat mobFloat) {
	}

	default void visitInteger(MobInteger mobInteger) {
	}

	default void visitNil(MobNil mobNil) {
	}

	default void visitString(MobString mobString) {
	}

	default void visitSymbol(MobSymbol mobSymbol) {
	}

	default void visitAssign(MobAssign mobAssign) {

	}

	default void visitVarDecl(MobVarDecl mobVarDecl) {
	}

	default void visitObject(MobObject mobObject) {
	}

	default void visitUnaryMessageSend(MobUnaryMessageSend mobUnaryMessageSend) {
		
	}
	default void visitBinaryMessageSend(MobBinaryMessageSend mobBinaryMessageSend) {
		
	}
	default void visitKeywordMessageSend(MobKeywordMessageSend mobKeywordMessageSend) {
		
	}



}
