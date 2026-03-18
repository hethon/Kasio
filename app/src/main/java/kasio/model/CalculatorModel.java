package kasio.model;

import org.mariuszgromada.math.mxparser.Expression;

public class CalculatorModel {
    private String expressionBuilder;
    
    public CalculatorModel() {
        this.expressionBuilder = "";
    }

    public void appendInput(String value) {
        this.expressionBuilder += value;
    }

    public void wrapCurrentExpression(String prefix) {
        this.expressionBuilder = String.format("%s(%s)", prefix, this.expressionBuilder);
    }

    public void deleteLast() {
        if (this.expressionBuilder.length() == 0) return;
        this.expressionBuilder = this.expressionBuilder.substring(0, this.expressionBuilder.length() - 1);
    }
    
    public void clear() {
        this.expressionBuilder = "";
    }

    public String getCurrentExpression() {
        return this.expressionBuilder;
    }

    public void evaluate() {
        if (this.expressionBuilder.length() == 0) return;

        Expression e = new Expression(this.expressionBuilder);
        
        double result = e.calculate();

        if (Double.isNaN(result)) {
            this.expressionBuilder = "Syntax Error";
        } else {
            String resultStr = String.valueOf(result);
            
            // If the result is "x.0", cut off the ".0" so it looks like a real calculator
            if (resultStr.endsWith(".0")) {
                resultStr = resultStr.substring(0, resultStr.length() - 2);
            }
            
            this.expressionBuilder = resultStr;
        }
    }
}