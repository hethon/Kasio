package kasio.controller;

import kasio.model.CalculatorModel;
import kasio.view.CalculatorView;

public class CalculatorController {
  private final CalculatorModel model;
  private final CalculatorView view;

  public CalculatorController(CalculatorModel model, CalculatorView view) {
    this.model = model;
    this.view = view;
    addListeners();
  }

  private void addListeners() {
    view.addAppendButtonListener(
        (evalText) -> {
          model.appendInput(evalText);
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addWrapButtonListener(
        (wrapPrefix) -> {
          model.wrapCurrentExpression(wrapPrefix);
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addAllClearButtonListener(
        () -> {
          model.clear();
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addDelButtonListener(
        () -> {
          model.deleteLast();
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addEqualsButtonListener(
        () -> {
          model.evaluate();
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addAnsButtonListener(
        () -> {
          model.appendInput(model.getlastExpressionResult());
          view.setDisplayValue(model.getCurrentExpression());
        });

    view.addExitButtonListener(
        () -> {
          model.clear();
          view.setDisplayValue(model.getCurrentExpression());
          System.exit(0);
        });
  }
}
