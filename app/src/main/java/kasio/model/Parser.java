package kasio.model;

import java.util.ArrayList;

public class Parser{
    public static final char[] BASIC_OPERATORS= {'!','%', '^', '/', '*', '+', '-'};
    private static final String[] FUNCTIONS = 
    {
        "nul", // 0
        "srt", // 1
        "crt", // 2
        "sin", // 3
        "cos", // 4
        "tan", // 5
        "sec", // 6
        "csc", // 7
        "cot", // 8
        "log", // 9
        "lan", // 10
        "abs", // 11
        "asi", // 12
        "acs", // 13
        "atn", // 14
        "ase", // 15
        "acc", // 16
        "act", // 17
    };
    private String expression;
    private int[] contentArray = new int[4];
    private ArrayList<Integer> funcArray = new ArrayList<>();
    private ArrayList<Integer> funcIndices = new ArrayList<>();
    ArrayList<Integer> operators = new ArrayList<>();
    ArrayList<Integer> operands = new ArrayList<>();
    ArrayList<Character> operatorNames = new ArrayList<>();
    ArrayList<StringBuffer> operandNames = new ArrayList<>();
    public void details(){
        System.out.println(" expression: " + expression);
        System.out.println(" Content:");
        System.out.println(" numbers = " + contentArray[0]);
        System.out.println(" operators = " + contentArray[1]);
        System.out.println(" brackets = " + contentArray[2]);
        System.out.println(" points = " + contentArray[3]);
        System.out.println(" Functions:");
        for(int i = 0; i < funcArray.size(); i++){
            System.out.println(" " + (i + 1) + ". " + FUNCTIONS[funcArray.get(i)] + ": " + funcIndices.get(i));
        }
    }
    int countLeftBrackets(String expression){
        int counter = 0;
        for(int i = 0; i < expression.length(); i++)
        if(expression.charAt(i) == '(')counter++;
        return counter;
    }
    int countRightBrackets(String expression){
        int counter = 0;
        for(int i = 0; i < expression.length(); i++)
        if(expression.charAt(i) == ')')counter++;
        return counter;
    }
    int[] analyzeExpression(String expression){
        if(expression.isEmpty())return new int[]{-1, -1, -1, -1};
        int[] contentArray = {0, 0, 0, 0};
        /*
         * [0] - numbers of digits
         * [1] - numbers of operators
         * [2] - numbers of brackets (both left and right) (must be even)
         * [3] - numbers of points
         */
        for(int i = 0; i < expression.length(); i++){
            if
            (
                expression.charAt(i) == '0' || 
                expression.charAt(i) == '1' || 
                expression.charAt(i) == '2' ||
                expression.charAt(i) == '3' ||
                expression.charAt(i) == '4' ||
                expression.charAt(i) == '5' ||
                expression.charAt(i) == '6' ||
                expression.charAt(i) == '7' ||
                expression.charAt(i) == '8' ||
                expression.charAt(i) == '9'
            )
            contentArray[0]++;
            if
            (
                expression.charAt(i) == '+' || 
                expression.charAt(i) == '-' || 
                expression.charAt(i) == '/' ||
                expression.charAt(i) == '*' ||
                expression.charAt(i) == '%' ||
                expression.charAt(i) == '^' ||
                expression.charAt(i) == '!'
            )
            contentArray[1]++;
            if(expression.charAt(i) == '(' || expression.charAt(i) == ')')contentArray[2]++;
            if(expression.charAt(i) == '.')contentArray[3]++;
        }
        return contentArray;
    }
    int getFunctionIndex(String func){
        if(func.isEmpty())return 0;
        for(int i = 0; i < FUNCTIONS.length; i++){
            if(func.equals(FUNCTIONS[i]))return i;
        }
        return -1;
    }
    ArrayList<Integer> getFunctions(String expression){
        ArrayList<Integer> functions = new ArrayList<>();
        int length = expression.length();
        int adder;
        for(int i = 0; i < length; i += adder){
            if(i + 3 > length - 1)break;
            adder = 1;
            String temp = expression.substring(i, i + 3);
            int index = getFunctionIndex(temp);
            if(index >= 0){
                functions.add(index);
                adder = 3;
            }
        }
        return functions;
    }
    ArrayList<Integer> getFuncIndex(String expression){
        ArrayList<Integer> indices = new ArrayList<>();
        int length = expression.length();
        int adder;
        for(int i = 0; i < length; i += adder){
            if(i + 3 > length - 1)break;
            adder = 1;
            String temp = expression.substring(i, i + 3);
            int index = getFunctionIndex(temp);
            if(index >= 0){
                indices.add(i);
                adder = 3;
            }
        }
        return indices;
    }
    public static String bracketParseTo(String expression) throws Exception{
        Parser parsed = new Parser(expression);
        int noLeftBrackets = parsed.countLeftBrackets(expression);
        int noRightBrackets = parsed.countRightBrackets(expression);
        if(noLeftBrackets != noRightBrackets)throw new Exception(" Syntax error, bracket mismatches");
        StringBuffer finalExpression = new StringBuffer(expression);
        for(int i = finalExpression.length() - 1; i >= 0; i--){
            // System.out.println(" i: " + i);
            if(finalExpression.charAt(i) == '('){
                // System.out.println(" il: " + i);
                for(int j = i + 1; j < finalExpression.length(); j++){
                    // System.out.println(" j: " + j);
                    if(finalExpression.charAt(j) == ')'){
                        // System.out.println(" jr: " + j);
                        ArrayList<Integer> nowOperators = new ArrayList<>();
                        ArrayList<Integer> nowOperands = new ArrayList<>();
                        ArrayList<Character> nowOperatorNames = new ArrayList<>();
                        ArrayList<StringBuffer> nowOperandNames = new ArrayList<>();
                        String nowExpression = finalExpression.toString().substring(i + 1, j);
                        // System.out.println(" nowExpression = " + nowExpression);
                        parseTo(nowExpression, nowOperators, nowOperands, nowOperatorNames, nowOperandNames);
                        StringBuffer simpExpression = new StringBuffer(Evaluator.simplify(nowExpression, nowOperators, nowOperands, nowOperatorNames, nowOperandNames));
                        // System.out.println(" simpExpression = " + simpExpression);
                        int differences = simpExpression.length() - nowExpression.length();
                        if(i != 0){
                            if
                            (
                                finalExpression.charAt(i - 1) != '*' &&
                                finalExpression.charAt(i - 1) != '/' &&
                                finalExpression.charAt(i - 1) != '+' &&
                                finalExpression.charAt(i - 1) != '-' &&
                                finalExpression.charAt(i - 1) != 'E' &&
                                finalExpression.charAt(i - 1) != '('
                            )
                            {
                                String posFunc = finalExpression.substring(i - 3, i);
                                int funcIndex = parsed.getFunctionIndex(posFunc);
                                if(funcIndex < 0){
                                    throw new Exception(" Syntax error, not a function near bracket");
                                }
                                else{
                                    Double doub = Evaluator.evaluateD(funcIndex, simpExpression.toString());
                                    finalExpression.replace(i - 3, j + 1, doub.toString());
                                    i = finalExpression.length();
                                    break;
                                }
                            }
                            else if(
                                finalExpression.charAt(i - 1) == '*' ||
                                finalExpression.charAt(i - 1) == '/' ||
                                finalExpression.charAt(i - 1) == '^' ||
                                finalExpression.charAt(i - 1) == 'E' ||
                                finalExpression.charAt(i - 1) == '('
                            )
                            {
                                // differences -= 2;
                                finalExpression.replace(i, j + 1, simpExpression.toString());
                                // System.out.println(" so far: " + finalExpression);
                                i = finalExpression.length();
                                break;
                            }
                            else if(finalExpression.charAt(i - 1) == '+'){
                                if(Double.parseDouble(simpExpression.toString()) < 0){
                                    // differences -= 3;
                                    finalExpression.replace(i - 1, j + 1, simpExpression.toString());
                                    // System.out.println(" so far: " + finalExpression);
                                    i = finalExpression.length();
                                    break;
                                }
                                else{
                                    differences -= 2;
                                    finalExpression.replace(i, j + 1, simpExpression.toString());
                                    // System.out.println(" so far: " + finalExpression);
                                    i = finalExpression.length();
                                    break;
                                }
                            }
                            else if(finalExpression.charAt(i - 1) == '-'){
                                if(Double.parseDouble(simpExpression.toString()) < 0){
                                    simpExpression.replace(0, 1, "+");
                                    finalExpression.replace(i - 1, j + 1, simpExpression.toString());
                                    // differences -= 3;
                                    // System.out.println(" so far: " + finalExpression);
                                    i = finalExpression.length();
                                    break;
                                }
                                else{
                                    //differences -= 2;
                                    finalExpression.replace(i - 1, j + 1, simpExpression.toString());
                                    // System.out.println(" so far: " + finalExpression);
                                    i = finalExpression.length();
                                    break;
                                }
                            }
                        }
                        else{
                            // differences -= 2;
                            finalExpression.replace(i, j + 1, simpExpression.toString());
                            i = finalExpression.length();
                            // System.out.println(" so far: " + finalExpression);
                            break;
                        }
                    }
                }
            }
        }
        // System.out.println(" Final expression: " + finalExpression);
        return finalExpression.toString();
    }
    public static String fullParse(String expression) throws Exception{
        ArrayList<Integer> nowOperators = new ArrayList<>();
        ArrayList<Integer> nowOperands = new ArrayList<>();
        ArrayList<Character> nowOperatorNames = new ArrayList<>();
        ArrayList<StringBuffer> nowOperandNames = new ArrayList<>();
        String basicExpression = bracketParseTo(expression);
        parseTo(basicExpression, nowOperators, nowOperands, nowOperatorNames, nowOperandNames);
        String finalExpression = Evaluator.simplify(basicExpression, nowOperators, nowOperands, nowOperatorNames, nowOperandNames);
        return finalExpression;
    }
    public static void parseTo(String expression, ArrayList<Integer> operators, ArrayList<Integer> operands, ArrayList<Character> operatorNames, ArrayList<StringBuffer> operandNames) throws Exception{
        int length = expression.length();
        for(int i = 0; i < length; i++){
            // System.out.println(" i: " + i);
            char nowChar = expression.charAt(i);
            if
            (
                nowChar == '-' ||
                nowChar == '0' || 
                nowChar == '1' || 
                nowChar == '2' ||
                nowChar == '3' ||
                nowChar == '4' ||
                nowChar == '5' ||
                nowChar == '6' ||
                nowChar == '7' ||
                nowChar == '8' ||
                nowChar == '9' ||
                nowChar == '.' ||
                nowChar == 'E'
            )
            {
                int pointCounts = 0;
                int EnegCounts = 0;
                int EposCounts = 0;
                int Ecounts = 0;
                int Epos = -1;
                boolean isNegative = false;
                int negCounts = 0;
                /*
                if(i == length - 1){
                    if(expression.charAt(i - 1) == '.')continue;
                }
                */
                if(nowChar == '.'){
                    if(expression.charAt(i - 1) == '0')continue;
                }
                // System.out.println(" found operand");
                operands.add(i);
                StringBuffer numberString = new StringBuffer();
                for(int j = i; j < length; j++){
                    char nowCharj = expression.charAt(j);
                    // System.out.println(" j: " + j + " = " + nowCharj);
                    if(j == i)
                    {
                        if(j == length - 1){
                            // System.out.println(" final char");
                            if(nowCharj == '-'){
                                throw new Exception(" Syntax error, minus sign at end.");
                            }
                        }
                        else if(nowCharj == '-'){
                            // System.out.println(" negative number");
                            numberString.append(nowCharj);
                            isNegative = true;
                            continue;
                        }
                    }
                    if(Ecounts == 1){
                        // System.out.println(" E checks");
                        if(j == Epos + 1){
                            if(
                                expression.charAt(j) != '0' &&
                                expression.charAt(j) != '1' &&
                                expression.charAt(j) != '2' &&
                                expression.charAt(j) != '3' &&
                                expression.charAt(j) != '4' &&
                                expression.charAt(j) != '5' &&
                                expression.charAt(j) != '6' &&
                                expression.charAt(j) != '7' &&
                                expression.charAt(j) != '8' &&
                                expression.charAt(j) != '9' &&
                                expression.charAt(j) != '+' &&
                                expression.charAt(j) != '-'
                            )
                            {
                                throw new Exception(" Syntax error, weirdos next to E");
                            }
                        }
                        if(nowCharj == '-'){
                            // System.out.println(" negs found");
                            if(EposCounts == 0 && j == Epos + 1)EnegCounts++;
                            else{
                                i = j - 1;
                                break;
                            }
                            if(EnegCounts > 1){
                                if
                                    (
                                        expression.charAt(j - 1) != '0' &&
                                        expression.charAt(j - 1) != '1' &&
                                        expression.charAt(j - 1) != '2' &&
                                        expression.charAt(j - 1) != '3' &&
                                        expression.charAt(j - 1) != '4' &&
                                        expression.charAt(j - 1) != '5' &&
                                        expression.charAt(j - 1) != '6' &&
                                        expression.charAt(j - 1) != '7' &&
                                        expression.charAt(j - 1) != '8' &&
                                        expression.charAt(j - 1) != '9'
                                    )
                                    {
                                        throw new Exception(" Syntax error, weirdo before - (in E)");
                                    }
                                    else{
                                        i = j - 1;
                                        break;
                                    }
                            }
                            else{
                                numberString.append(nowCharj);
                                continue;
                            }
                        }
                        else if(nowCharj == '+'){
                            if(EnegCounts == 0 && j == Epos + 1)EposCounts++;
                            else{
                                i = j - 1;
                                break;
                            }
                            if(EposCounts > 1){
                                if
                                    (
                                        expression.charAt(j - 1) != '0' &&
                                        expression.charAt(j - 1) != '1' &&
                                        expression.charAt(j - 1) != '2' &&
                                        expression.charAt(j - 1) != '3' &&
                                        expression.charAt(j - 1) != '4' &&
                                        expression.charAt(j - 1) != '5' &&
                                        expression.charAt(j - 1) != '6' &&
                                        expression.charAt(j - 1) != '7' &&
                                        expression.charAt(j - 1) != '8' &&
                                        expression.charAt(j - 1) != '9'
                                    )
                                    {
                                        throw new Exception(" Syntax error, weirdo before + (in E)");
                                    }
                                    else{
                                        i = j - 1;
                                        break;
                                    }
                            }
                            else{
                                numberString.append(nowCharj);
                                continue;
                            }
                        }
                        else if(nowCharj == '.'){
                            throw new Exception(" Syntax error, point in E");
                        }
                    }
                    if
                    (
                        nowCharj != '0' && 
                        nowCharj != '1' && 
                        nowCharj != '2' &&
                        nowCharj != '3' &&
                        nowCharj != '4' &&
                        nowCharj != '5' &&
                        nowCharj != '6' &&
                        nowCharj != '7' &&
                        nowCharj != '8' &&
                        nowCharj != '9' &&
                        nowCharj != '.' &&
                        nowCharj != 'E'
                    )
                    {
                        i = j - 1;
                        break;
                    }
                    else if(nowCharj == '.'){
                        pointCounts++;
                        if(pointCounts > 1){
                            throw new Exception("Syntax error, double points.");
                        } 
                    }
                    else if(nowCharj == 'E'){
                        Ecounts++;
                        Epos = j;
                        if(Ecounts > 1){
                            throw new Exception(" Syntax error, double Es.");
                        }
                    }
                    numberString.append(nowCharj);
                    if(j == length - 1){
                        i = j;
                        break;
                    }
                }
                operandNames.add(numberString);
            }
            else if(
                nowChar == '+' || 
                nowChar == '-' || 
                nowChar == '/' ||
                nowChar == '*' ||
                nowChar == '%' ||
                nowChar == '^' ||
                nowChar == '!'
            )
            {
                if(i == length - 1 && nowChar != '%' && nowChar != '!')throw new Exception("Syntax Error");
                else if(i == length - 1){
                    operators.add(i);
                    operatorNames.add(nowChar);
                    continue;
                }
                char nextChar = expression.charAt(i + 1);
                if
                (
                    (i == 0)
                    ||
                    nowChar != '%' && nowChar != '!' && 
                    (nowChar != '+' && nowChar != '-') && (nextChar != '+' && nextChar != '-')
                    &&
                    nextChar != '0' && 
                    nextChar != '1' && 
                    nextChar != '2' &&
                    nextChar != '3' &&
                    nextChar != '4' &&
                    nextChar != '5' &&
                    nextChar != '6' &&
                    nextChar != '7' &&
                    nextChar != '8' &&
                    nextChar != '9' &&
                    nextChar != '.' &&
                    nextChar != '(' 
                )
                {
                    throw new Exception("Syntax error");
                }
                else {
                    operators.add(i);
                    operatorNames.add(nowChar);
                }
            }
            
        }
        /* Details
        System.out.println(" Expression: " + expression);
        System.out.println(" Number of operators: " + operators.size());
        System.out.println(" Number of operands: " + operands.size());
        System.out.println(" Operators: ");
        for(int i = 0; i < operators.size(); i++){
            System.out.println(" " + (i + 1) + ". " + operatorNames.get(i) + " : " + operators.get(i));
        }
        System.out.println(" Opearands: ");
        for(int i = 0; i < operands.size(); i++){
            System.out.println(" " + (i + 1) + ". " + operandNames.get(i) + " : " + operands.get(i));
        }
        // */
    }
    public void basicParse(String expression) throws Exception{
        parseTo(expression, operators, operands, operatorNames, operandNames);
    }
    public void basicParse() throws Exception{
        basicParse(this.expression);
    }
    public ArrayList<Integer> getOperators(){
        return operators;
    }
    public ArrayList<Integer> getOperands(){
        return operands;
    }
    public ArrayList<Character> getOperatorNames(){
        return operatorNames;
    }
    public ArrayList<StringBuffer> getOperandNames(){
        return operandNames;
    }
    public String getExpression(){
        return expression;
    }
    public Parser(String expression){
        this.expression = expression;
        contentArray = analyzeExpression(expression);
        funcArray = getFunctions(expression);
        funcIndices = getFuncIndex(expression);
    }
    public Parser(){
        this("");
    }
    
    // public static void main(String args[]) throws Exception{
    //     System.out.println(" final expression: " + Parser.fullParse("(3!*10)%"));
    // }
}