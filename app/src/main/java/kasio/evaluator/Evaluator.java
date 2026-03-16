package kasio.evaluator;

import java.util.ArrayList;

import kasio.parser.Parser;
public class Evaluator{
    public static Integer fact(Integer n){
        return (n == 0 ? 1 : n * fact(n - 1));
    }
    public static Integer perm(Integer n, Integer r){
        return fact(n) / fact(n - r);
    }
    public static Integer comb(Integer n, Integer r){
        return perm(n, r) / fact(r);
    }
    public static Integer evaluateI(int opcode, String operand1, String operand2) throws Exception{
        Integer inte1 = Integer.parseInt(operand1), inte2 = Integer.parseInt(operand2);
        switch(opcode){
            case 11:
            return perm(inte1, inte2);
            case 12:
            return comb(inte1, inte2);
            default:
            return null;
            //case 19:
            //case 20:
        }
    }
    public static Double evaluateD(int opcode, String operand) throws Exception{
        Double doub = Double.parseDouble(operand);
        switch (opcode) {
            case 0:
            return null;
            case 1:
            return Math.sqrt(doub);
            case 2:
            return Math.cbrt(doub);
            case 3:
            return Math.sin(doub);
            case 4:
            return Math.cos(doub);
            case 5:
            return Math.tan(doub);
            case 6:
            return (1/Math.cos(doub));
            case 7:
            return (1/Math.sin(doub));
            case 8:
            return (1/Math.tan(doub));
            case 9:
            return Math.log(doub);
            case 10:
            return (Math.log(doub)/Math.log(Math.E));
            case 11:
            return Math.abs(doub);
            case 12:
            return Math.asin(doub);
            case 13:
            return Math.acos(doub);
            case 14:
            return Math.atan(doub);
            case 15:
            return Math.acos(1/doub);
            case 16:
            return Math.asin(1/doub);
            case 17:
            return Math.atan(1/doub);
            default:
            return null;
            //case 19:
            //case 20:
        }
    }
    public static String simplify(String expression, ArrayList<Integer> operators, 
    ArrayList<Integer> operands, 
    ArrayList<Character> operatorNames, 
    ArrayList<StringBuffer> operandNames) throws Exception{
        StringBuffer finalExpression = new StringBuffer(expression);
        for(int i = 0; i < operatorNames.size(); i++){
            //System.out.println(" factorial check: ");
            if(operatorNames.get(i) == '!'){
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        if
                        (
                            operandNames.get(j).toString().contains(".") ||
                            operandNames.get(j).toString().contains("E") ||
                            operandNames.get(j).toString().contains("-") 
                        )
                        {
                            throw new Exception(" Math error");
                        }
                        Integer inte = Integer.parseInt(operandNames.get(j).toString());
                        inte = Evaluator.fact(inte);
                        String oldString = finalExpression.toString().substring(operands.get(j), operators.get(i) + 1);
                        String newString = inte.toString();
                        int differences = newString.length() - oldString.length();
                        finalExpression.replace(operands.get(j), operators.get(i) + 1, newString);
                        operators.set(i, -1);
                        operandNames.set(j, new StringBuffer(newString));
                        operatorNames.set(i, '~');
                        for(int k = j + 1; k < operands.size(); k++){
                            operands.set(k, operands.get(k) + differences);
                        }
                        for(int k = i + 1; k < operators.size(); k++){
                            operators.set(k, operators.get(k) + differences);
                        }
                    }
                }
            }
        }
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        //System.out.println(" Percentage check: ");
        for(int i = 0; i < operatorNames.size(); i++){
            if(operatorNames.get(i) == '%'){
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        Double doub = Double.parseDouble(operandNames.get(j).toString());
                        doub *= 0.01;
                        String oldString = finalExpression.toString().substring(operands.get(j), operators.get(i) + 1);
                        String newString = doub.toString();
                        int differences = newString.length() - oldString.length();
                        finalExpression.replace(operands.get(j), operators.get(i) + 1, newString);
                        operators.set(i, -1);
                        operandNames.set(j, new StringBuffer(newString));
                        operatorNames.set(i, '~');
                        for(int k = j + 1; k < operands.size(); k++){
                            operands.set(k, operands.get(k) + differences);
                        }
                        for(int k = i + 1; k < operators.size(); k++){
                            operators.set(k, operators.get(k) + differences);
                        }
                    }
                }
            }
        }
        //System.out.println(" Simplified expression: " + finalExpression);
        //System.out.println(" Power check: ");
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        for(int i = 0; i < operatorNames.size(); i++){
            if(operatorNames.get(i) == '^'){
                Integer intBase = null;
                Double doubleBase = null, exponent = null;
                int init = -1, fin = -1;
                int mainOperandIndex = -1;
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        init = operands.get(j);
                        mainOperandIndex = j;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E")){
                            doubleBase = Double.parseDouble(operandNames.get(j).toString());
                        }
                        else intBase = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        fin = finalIndex;
                        exponent = Double.parseDouble(operandNames.get(j).toString());
                        break;
                    }
                }
                Double doub;
                if(intBase != null){
                    doub = Math.pow(intBase, exponent);
                }
                else{
                    doub = Math.pow(doubleBase, exponent);
                }
                String oldString = finalExpression.toString().substring(init, fin);
                String newString = doub.toString();
                int differences = newString.length() - oldString.length();
                finalExpression.replace(init, fin, newString);
                operators.set(i, -1);
                operandNames.set(mainOperandIndex, new StringBuffer(newString));
                operands.remove(mainOperandIndex + 1);
                operandNames.remove(mainOperandIndex + 1);
                operatorNames.set(i, '~');
                for(int k = mainOperandIndex + 1; k < operands.size(); k++){
                    operands.set(k, operands.get(k) + differences);
                }
                for(int k = i + 1; k < operators.size(); k++){
                    operators.set(k, operators.get(k) + differences);
                }
            }
        }
        //System.out.println(" Simplified expression: " + finalExpression);
        ///*
        //System.out.println(" Division check: ");
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        for(int i = operatorNames.size() - 1; i >= 0; i--){
            if(operatorNames.get(i) == '/'){
                Integer intDivisor = null, intDividend = null;
                Double doubleDivisor= null, doubleDividend = null;
                int init = -1, fin = -1;
                int mainOperandIndex = -1;
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        mainOperandIndex = j;
                        init = operands.get(j);
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E"))
                        doubleDividend = Double.parseDouble(operandNames.get(j).toString());
                        else intDividend = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E"))
                        doubleDivisor= Double.parseDouble(operandNames.get(j).toString());
                        else intDivisor = Integer.parseInt(operandNames.get(j).toString());
                    }
                }
                Double doub;
                if(intDividend != null){
                    if(intDivisor != null){
                        doub = (double)(intDividend) / (double)(intDivisor);
                    }
                    else doub = (double)(intDividend) / doubleDivisor;
                }
                else{
                    if(intDivisor != null){
                        doub = doubleDividend / (double) intDivisor;
                    }
                    else doub = doubleDividend / doubleDivisor;
                }
                String oldString = finalExpression.toString().substring(init, fin);
                String newString = doub.toString();
                int differences = newString.length() - oldString.length();
                finalExpression.replace(init, fin, newString);
                operators.set(i, -1);
                operandNames.set(mainOperandIndex, new StringBuffer(newString));
                operands.remove(mainOperandIndex + 1);
                operandNames.remove(mainOperandIndex + 1);
                operatorNames.set(i, '~');
                for(int k = mainOperandIndex + 1; k < operands.size(); k++){
                    operands.set(k, operands.get(k) + differences);
                }
                for(int k = i + 1; k < operators.size(); k++){
                    operators.set(k, operators.get(k) + differences);
                }
            }
        }
        //*/
        //System.out.println(" Simplified expression: " + finalExpression);
        //System.out.println(" Multiplication check: ");
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        for(int i = 0; i < operatorNames.size(); i++){
            if(operatorNames.get(i) == '*'){
                Integer intMul1 = null, intMul2 = null;
                Double doubleMul1= null, doubleMul2 = null;
                int init = -1, fin = -1;
                int mainOperandIndex = -1;
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        init = operands.get(j);
                        mainOperandIndex = j;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E"))
                        doubleMul1 = Double.parseDouble(operandNames.get(j).toString());
                        else intMul1 = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E"))
                        doubleMul2= Double.parseDouble(operandNames.get(j).toString());
                        else intMul2 = Integer.parseInt(operandNames.get(j).toString());
                    }
                }
                Double doub;
                if(intMul1 != null){
                    if(intMul2 != null){
                        doub = (double)(intMul1) * (double)(intMul2);
                    }
                    else doub = (double)(intMul1) * doubleMul2;
                }
                else{
                    if(intMul2 != null){
                        doub = doubleMul1 * (double) intMul2;
                    }
                    else doub = doubleMul1 * doubleMul2;
                }
                String oldString = finalExpression.toString().substring(init, fin);
                String newString = doub.toString();
                int differences = newString.length() - oldString.length();
                finalExpression.replace(init, fin, newString);
                operators.set(i, -1);
                operandNames.set(mainOperandIndex, new StringBuffer(newString));
                operands.remove(mainOperandIndex + 1);
                operandNames.remove(mainOperandIndex + 1);
                operatorNames.set(i, '~');
                for(int k = mainOperandIndex + 1; k < operands.size(); k++){
                    operands.set(k, operands.get(k) + differences);
                }
                for(int k = i + 1; k < operators.size(); k++){
                    operators.set(k, operators.get(k) + differences);
                }
            }
        }
        //System.out.println(" Simplified expression: " + finalExpression);
        //System.out.println(" Addition check: ");
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        /*
        System.out.println(" Operands are: ");
        for(int i = 0; i < operands.size(); i++){
            System.out.println(" " + (i + 1) + ". " + operandNames.get(i) + " : " + operands.get(i));
        }
        //*/
        for(int i = 0; i < operatorNames.size(); i++){
            if(operatorNames.get(i) == '+'){
                //System.out.println(" Addition found");
                Integer intAdd1 = null, intAdd2 = null;
                Double doubleAdd1= null, doubleAdd2 = null;
                int init = -1, fin = -1;
                int mainOperandIndex = -1;
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        //System.out.println(" j1: " + j);
                        //System.out.println(" operand1: " + operandNames.get(j) + " : " + operands.get(j));
                        init = operands.get(j);
                        mainOperandIndex = j;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E")){
                            doubleAdd1 = Double.parseDouble(operandNames.get(j).toString());
                        }
                        else intAdd1 = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        // System.out.println(" j2: " + j);
                        // System.out.println(" operand2: " + operandNames.get(j) + " : " + operands.get(j));
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E"))
                        doubleAdd2= Double.parseDouble(operandNames.get(j).toString());
                        else intAdd2 = Integer.parseInt(operandNames.get(j).toString());
                    }
                }
                Double doub;
                ///*
                if(intAdd1 != null){
                    if(intAdd2 != null){
                        doub = (double)(intAdd1) + (double)(intAdd2);
                    }
                    else doub = (double)(intAdd1) + doubleAdd2;
                }
                else{
                    if(intAdd2 != null){
                        doub = doubleAdd1 + (double) intAdd2;
                    }
                    else doub = doubleAdd1 + doubleAdd2;
                }
                String oldString = finalExpression.toString().substring(init, fin);
                String newString = doub.toString();
                int differences = newString.length() - oldString.length();
                finalExpression.replace(init, fin, newString);
                operators.set(i, -1);
                operandNames.set(mainOperandIndex, new StringBuffer(newString));
                operands.remove(mainOperandIndex + 1);
                operandNames.remove(mainOperandIndex + 1);
                operatorNames.set(i, '~');
                for(int k = mainOperandIndex + 1; k < operands.size(); k++){
                    operands.set(k, operands.get(k) + differences);
                }
                for(int k = i + 1; k < operators.size(); k++){
                    operators.set(k, operators.get(k) + differences);
                }
                // */
            }
        }
        //System.out.println(" Simplified expression: " + finalExpression);
        //System.out.println(" Subtraction check: ");
        operators.clear();
        operands.clear();
        operatorNames.clear();
        operandNames.clear();
        Parser.parseTo(finalExpression.toString(), operators, operands, operatorNames, operandNames);
        for(int i = 0; i < operandNames.size(); i++){
            if(operandNames.get(i).toString().charAt(0) == '-'){
                //System.out.println(" i: " + i);
                //System.out.println(" operand2: " + operandNames.get(i) + " : " + operands.get(i));
                if(operands.get(i) == 0)continue;
                Integer intSub1 = null, intSub2 = null;
                Double doubleSub1 = null, doubleSub2 = null;
                Integer intDiff = null;
                Double doubleDiff = null;
                for(int j = 0; j < operands.size(); j++){
                    if(j == i)continue;
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operands.get(i)){
                        //System.out.println(" j1: " + j);
                        //System.out.println(" operand1: " + operandNames.get(j) + " : " + operands.get(j));
                        if(operandNames.get(i).toString().contains(".") || operandNames.get(i).toString().contains("E") ){
                            doubleSub1 = Double.parseDouble(operandNames.get(i).toString());
                            //System.out.println(" operand1 is double: " + doubleSub1);
                        }
                        else intSub1 = Integer.parseInt(operandNames.get(i).toString());
                        if(operandNames.get(j).toString().contains(".") || operandNames.get(j).toString().contains("E")){
                            doubleSub2 = Double.parseDouble(operandNames.get(j).toString());
                            //System.out.println(" operand2 is double: " + doubleSub2);
                        }
                        else intSub2 = Integer.parseInt(operandNames.get(j).toString());
                        if(intSub1 != null){
                            if(intSub2 != null){
                                intDiff = intSub1 + intSub2;
                            }
                            else doubleDiff = (double)intSub1 + doubleSub2;
                        }
                        else{
                            if(intSub2 != null){
                                doubleDiff = doubleSub1 + (double)intSub2;
                            }
                            else doubleDiff = doubleSub1 + doubleSub2;
                        }
                        // if(intDiff != null)System.out.println(" diff is int: " + intDiff);
                        // else System.out.println(" diff is double: " + doubleDiff);
                        String oldString = finalExpression.toString().substring(operands.get(j), operands.get(i) + operandNames.get(i).length());
                        String newString = (intDiff != null ? intDiff.toString() : doubleDiff.toString());
                        int differences = newString.length() - oldString.length();
                        finalExpression.replace(operands.get(j), operands.get(i) + operandNames.get(i).length(), newString);
                        operandNames.set(j, new StringBuffer(newString));
                        operands.remove(j + 1);
                        operandNames.remove(j + 1);
                        for(int k = j + 1; k < operands.size(); k++){
                            operands.set(k, operands.get(k) + differences);
                        }
                        i = i - 1;
                        break;
                    }
                }
            }
        }
        //System.out.println(" Simplified expression: " + finalExpression);
        return finalExpression.toString();
    }
    public Evaluator(){

    }
    public static void main(String[] args) throws Exception {
        Evaluator eval = new Evaluator();
    }
}