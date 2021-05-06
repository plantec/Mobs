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

	default void visitUnaryMessage(MobUnaryMessage mobUnaryMessage) {
	}

	default void visitBinaryMessage(MobBinaryMessage mobBinaryMessage) {
	}

	default void visitKeywordMessage(MobKeywordMessage mobKeywordMessage) {
	}

	default void visitSequence(MobSequence mobSequence) {
	}

	default void visitReturn(MobReturn mobReturn) {
	}

	default void visitUnit(MobUnit mobUnit) {
	}

	default void visitQuoted(MobQuoted mobQuoted) {
	}

	default void visitParameterList(MobParameterList mobParameterList) {
	}
}
