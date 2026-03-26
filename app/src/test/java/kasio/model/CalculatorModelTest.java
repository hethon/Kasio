package kasio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorModelTest {

  private CalculatorModel model;

  @BeforeEach
  void setUp() {
    model = new CalculatorModel();
  }

  @Test
  void testAppendInput() {
    model.appendInput("1");
    model.appendInput("+");
    model.appendInput("2");

    assertEquals("1+2", model.getCurrentExpression());
  }

  @Test
  void testwrapCurrentExpression() {
    model.wrapCurrentExpression("");
    assertEquals("()", model.getCurrentExpression());

    model.clear();
    model.appendInput("1");
    model.appendInput("+");
    model.appendInput("2");
    model.wrapCurrentExpression("");
    assertEquals("(1+2)", model.getCurrentExpression());

    model.clear();
    model.appendInput("1");
    model.appendInput("+");
    model.appendInput("2");
    model.wrapCurrentExpression("-");
    assertEquals("-(1+2)", model.getCurrentExpression());
  }

  @Test
  void testDeleteLast() {
    model.deleteLast();
    assertEquals("", model.getCurrentExpression());

    model.appendInput("1");
    model.appendInput("+");
    model.appendInput("2");
    model.deleteLast();

    assertEquals("1+", model.getCurrentExpression());
  }

  @Test
  void testSyntaxErrorHandling() {
    model.appendInput("+");
    model.appendInput("+");
    model.evaluate();

    assertEquals("Syntax Error", model.getCurrentExpression());
  }

  @Test
  void testClearRemovesErrorState() {
    model.appendInput("+");
    model.appendInput("+");
    model.evaluate();
    model.clear();
    model.appendInput("1");
    model.appendInput("2");

    assertEquals("12", model.getCurrentExpression());
  }

  @Test
  void testAppendInputInErrorState() {
    model.appendInput("+");
    model.appendInput("+");
    model.evaluate();

    // We are now in error state
    // calling appendInput should remove the error state
    model.appendInput("1");
    model.appendInput("2");

    assertEquals("12", model.getCurrentExpression());
  }

  @Test
  void testwrapCurrentExpressionInErrorState() {
    model.appendInput("+");
    model.appendInput("+");
    model.evaluate(); // syntax error

    // We are now in error state
    // Calling wrapCurrentExpression should remove the error state
    // but its effect on the expression is ignored
    model.wrapCurrentExpression(""); // clears error but doesn't affect expression
    model.wrapCurrentExpression(
        ""); // we are out of error state so this should have an effect on expression

    assertEquals("()", model.getCurrentExpression());
  }

  @Test
  void testDecimalFormatting() {
    // Should trim .0
    model.appendInput("10/2");
    model.evaluate();
    assertEquals("5", model.getCurrentExpression());

    model.clear();

    // Should NOT trim actual decimals
    model.appendInput("5/2");
    model.evaluate();
    assertEquals("2.5", model.getCurrentExpression());
  }

  @Test
  void testAnsMemoryChaining() {
    model.appendInput("2");
    model.appendInput("+");
    model.appendInput("3");
    model.evaluate();
    assertEquals("5", model.getCurrentExpression()); // Ans is now 5

    model.clear(); // Press AC

    // Simulate pressing: Ans, +, Ans, =
    model.appendInput(model.getlastExpressionResult());
    model.appendInput("+");
    model.appendInput(model.getlastExpressionResult());
    model.evaluate();

    assertEquals("10", model.getCurrentExpression());
  }

  @Test
  void testCaretWorkAround() {
    model.appendInput("2");
    model.appendInput("∧");
    model.appendInput("3");
    model.evaluate(); // ∧ should be swapped by ^ and we should get the correct result

    assertEquals("8", model.getCurrentExpression());
  }

  @Test
  void testLog10WorkAround() {
    model.appendInput("log");
    model.appendInput("(");
    model.appendInput("1");
    model.appendInput("0");
    model.appendInput(")");
    model.evaluate(); // log should be swapped by log10 and we should get the correct result

    assertEquals("1", model.getCurrentExpression());
  }

  @ParameterizedTest(name = "Test {0} = {1}")
  @CsvSource({
    "2+2, 4",
    "10/2, 5",
    "5/2, 2.5",
    "2∧2, 4",
    "sin(0), 0",
    "log(100), 2",
    "-(-5), 5",
    "(1 + 2)4, 12",
  })
  void testVariousMathExpressions(String inputExpression, String expectedResult) {
    model.appendInput(inputExpression);
    model.evaluate();

    assertEquals(expectedResult, model.getCurrentExpression());
  }
}
