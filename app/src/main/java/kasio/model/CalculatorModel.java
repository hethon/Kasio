package kasio.model;

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
        String text = this.expressionBuilder;
        if (text.length() == 0) return;
        
        String modStr = text.replace("asin", "asi")
                            .replace("acos", "acs")
                            .replace("atan", "atn")
                            .replace("ln", "lan")
                            .replace("√", "srt")
                            .replace("×", "*")
                            .replace("∧", "^");
        try {
            this.expressionBuilder = Parser.fullParse(modStr);
        } catch (Exception err) {
            this.expressionBuilder = "Syntax Error";
        }
    }
}