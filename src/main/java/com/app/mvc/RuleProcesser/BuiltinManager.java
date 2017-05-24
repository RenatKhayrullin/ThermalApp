package com.app.mvc.RuleProcesser;

import com.clarkparsia.pellet.rules.builtins.BuiltInRegistry;
import com.clarkparsia.pellet.rules.builtins.GeneralFunction;
import com.clarkparsia.pellet.rules.builtins.GeneralFunctionBuiltIn;
import org.mindswap.pellet.ABox;
import org.mindswap.pellet.Literal;

public class BuiltinManager {
    public static final String BUILTIN_PREFIX = "urn:ruleprocesser:builtin#";

    //builtin for checking if math expr is true or false (e.g. inequality)
    //format: mathcheck(<formula>, ...<values in top-bottom order of using vars in formula>)
    //we don't need argument for result 'cause builtin already has bool return value
    public static class MathExprCheckerBuiltin implements GeneralFunction {
        public static final String URI = BUILTIN_PREFIX + "mathcheck";

        public boolean apply(ABox abox, Literal[] args) {


            return true;
        }

        public boolean isApplicable(boolean[] boundPositions) {
            return boundPositions.length >= 2;
        }
    }

    public static void registerBuiltins() {
        BuiltInRegistry.instance.registerBuiltIn(MathExprCheckerBuiltin.URI, new GeneralFunctionBuiltIn(new MathExprCheckerBuiltin()));
    }
}