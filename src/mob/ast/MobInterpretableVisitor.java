package mob.ast;

import mob.model.MobObject;

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

	default void visitReturn(MobReturn mobReturn) {
	}

	default void visitQuoted(MobQuoted mobQuoted) {
	}

	default  void visitObject(MobObject mob) {
	}

}
