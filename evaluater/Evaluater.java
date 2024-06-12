import java.util.*;
import parser.Parser;
public class Evaluater{
    Object sine(String operand){
        return new Object();
    }
    String simplify(String expression, ArrayList<Integer> operators, 
    ArrayList<Integer> operands, 
    ArrayList<Character> operatorNames, 
    ArrayList<StringBuffer> operandNames) throws Exception{
        StringBuffer finalExpression = new StringBuffer(expression);
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
                        if(operandNames.get(j).toString().contains(".")){
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
                        if(operandNames.get(j).toString().contains("."))
                        doubleDividend = Double.parseDouble(operandNames.get(j).toString());
                        else intDividend = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains("."))
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
                        if(operandNames.get(j).toString().contains("."))
                        doubleMul1 = Double.parseDouble(operandNames.get(j).toString());
                        else intMul1 = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains("."))
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
               // System.out.println(" Addition found");
                Integer intAdd1 = null, intAdd2 = null;
                Double doubleAdd1= null, doubleAdd2 = null;
                int init = -1, fin = -1;
                int mainOperandIndex = -1;
                for(int j = 0; j < operands.size(); j++){
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operators.get(i)){
                        // System.out.println(" j1: " + j);
                        // System.out.println(" operand1: " + operandNames.get(j) + " : " + operands.get(j));
                        init = operands.get(j);
                        mainOperandIndex = j;
                        if(operandNames.get(j).toString().contains(".")){
                            doubleAdd1 = Double.parseDouble(operandNames.get(j).toString());
                        }
                        else intAdd1 = Integer.parseInt(operandNames.get(j).toString());
                    }
                    else if(operands.get(j) == operators.get(i) + 1){
                        // System.out.println(" j2: " + j);
                        // System.out.println(" operand2: " + operandNames.get(j) + " : " + operands.get(j));
                        fin = finalIndex;
                        if(operandNames.get(j).toString().contains("."))
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
            if(operandNames.get(i).toString().contains("-")){
                //System.out.println(" i: " + i);
                //System.out.println(" operand2: " + operandNames.get(i) + " : " + operands.get(i));
                if(operands.get(i) == 0)continue;
                Integer intSub1 = null, intSub2 = null;
                Double doubleSub1 = null, doubleSub2 = null;
                int mainOperandIndex = -1;
                Integer intDiff = null;
                Double doubleDiff = null;
                for(int j = 0; j < operands.size(); j++){
                    if(j == i)continue;
                    int finalIndex = operands.get(j) + operandNames.get(j).length();
                    if(finalIndex == operands.get(i)){
                        //System.out.println(" j1: " + j);
                        //System.out.println(" operand1: " + operandNames.get(j) + " : " + operands.get(j));
                        mainOperandIndex = j;
                        if(operandNames.get(i).toString().contains(".")){
                            doubleSub1 = Double.parseDouble(operandNames.get(i).toString());
                            //System.out.println(" operand1 is double: " + doubleSub1);
                        }
                        else intSub1 = Integer.parseInt(operandNames.get(i).toString());
                        if(operandNames.get(j).toString().contains(".")){
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
    public Evaluater(){

    }
    public static void main(String[] args) throws Exception {
        Evaluater eval = new Evaluater();
        Parser parse = new Parser("1*2*3.2-0.4+0.9/3^200%");
        parse.basicParse();
        System.out.println(" The simplifed expression is: " + eval.simplify(parse.getExpression(), parse.getOperators(), parse.getOperands(), parse.getOperatorNames(), parse.getOperandNames()));
    }
}