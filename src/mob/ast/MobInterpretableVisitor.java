package mob.ast;

import mob.model.MobObject;
import mob.model.primitives.MobSequence;
import mob.model.primitives.MobUnit;

public interface MobInterpretableVisitor {

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

	default  void visitObject(MobObject mob) {
	}

}
