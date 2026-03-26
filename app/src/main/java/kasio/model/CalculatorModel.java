package kasio.model;

import org.mariuszgromada.math.mxparser.Expression;

public class CalculatorModel {
    private boolean inErrorState = false;
    private String expressionBuilder;
    private String expressionResult;
    
    public CalculatorModel() {
        this.expressionBuilder = "";
        this.expressionResult = "";
    }

    public void appendInput(String value) {
        if (this.inErrorState) {
            this.clear();
        }
        this.expressionBuilder += value;
    }

    public void wrapCurrentExpression(String prefix) {
        if (this.inErrorState) {
            this.clear();
            return;
        }
        this.expressionBuilder = String.format("%s(%s)", prefix, this.expressionBuilder);
    }

    public void deleteLast() {
        if (this.inErrorState) {
            this.clear();
            return;
        }
        if (this.expressionBuilder.isEmpty()) return;
        this.expressionBuilder = this.expressionBuilder.substring(0, this.expressionBuilder.length() - 1);
    }
    
    public void clear() {
        this.expressionBuilder = "";
        this.inErrorState = false;
    }

    public String getCurrentExpression() {
        return this.expressionBuilder;
    }

    public String getlastExpressionResult() {
        return this.expressionResult;
    }

    public void evaluate() {
        if (this.expressionBuilder.isEmpty()) return;
        if (this.inErrorState) return;

        Expression e = new Expression(
            this.expressionBuilder
            // the display font we used doesn't support ^ 
            // as a work around we used ∧, but replaced it with the correct one before parsing.
            .replace("∧", "^")
            // mXparser expects log10
            .replace("log", "log10")
            // TAN -> tan, SIN -> sin, etc
            .toLowerCase()
        );
        
        double result = e.calculate();

        if (Double.isNaN(result)) {
            this.expressionBuilder = "Syntax Error";
            this.inErrorState = true;
        } else {
            String resultStr = String.valueOf(result);
            
            // If the result is "x.0", cut off the ".0" so it looks like a real calculator
            if (resultStr.endsWith(".0")) {
                resultStr = resultStr.substring(0, resultStr.length() - 2);
            }
            
            this.expressionBuilder = resultStr;
            this.expressionResult = resultStr;
        }
    }
}