package com.app.mvc.RuleProcesser;

import com.app.mvc.RuleProcesser.MathMLCalculatorImpl.MathMLServiceWolframAPIImpl;
import com.clarkparsia.pellet.rules.builtins.BuiltInRegistry;
import org.mindswap.pellet.ABox;
import org.mindswap.pellet.Node;
import org.mindswap.pellet.utils.ATermUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SWRLCalculation {
    public static final String ControlFunctionDefinitionIRI =
            ReasonerRuleProcesser.THERMALONT_PREFIX+"ControlFunctionDefinition";

    private static Logger log = LoggerFactory.getLogger(SWRLCalculation.class);
    private static MathMLService calcService;

    public static MathMLService getCalcService() {
        return calcService;
    }

    public static void setCalcService(MathMLService acalcService) {
        calcService = acalcService;
    }

    //builtin for checking if math expr is true or false (e.g. inequality)
    //format: mathcheck(<formula>, ...<values in top-bottom order of first appear vars in formula>)
    //we don't need argument for result 'cause builtin already has bool return value
    public static class MathCheckBuiltin implements CustomSWRLBuiltin.CustomSWRLFunction {
        public static final String IRI = "http://www.ThermalDb.ru#mathcheck";

        @Override
        public boolean isApplicable(boolean[] boundPositions) {
            return boundPositions.length >= 1;
        }

        @Override
        public boolean apply(ABox abox, Node[] args) {
            String formula = args[0].getNameStr();//ATermUtils.getLiteralValue(args[0].getTerm());

            log.debug(formula);
            return true;
            /*
            try {
                List<String> varNames = calcService.getVarsInFormula(formula);
                if (varNames.size() != args.length - 1) {
                    return false;
                }

                Map<String, Double> varValues = new HashMap<String, Double>();
                Iterator<String> it = varNames.iterator();
                int i = 0;
                while (it.hasNext()) {
                    String varName = it.next();
                    String varValue = ATermUtils.getLiteralValue(args[i].getTerm());
                    i++;

                    varValues.put(varName, Double.valueOf(varValue));
                }

                log.debug("processing values "+varValues.toString());
                return calcService.checkExpression(formula, varValues);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
            */
        }
    }

    public static void registerBuiltin () {
        BuiltInRegistry.instance.registerBuiltIn(MathCheckBuiltin.IRI, new CustomSWRLBuiltin(new MathCheckBuiltin()));
    }
}
