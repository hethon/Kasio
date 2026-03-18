package kasio;

import javax.swing.SwingUtilities;

import org.mariuszgromada.math.mxparser.License;

import kasio.controller.CalculatorController;
import kasio.model.CalculatorModel;
import kasio.view.CalculatorView;

public class Main {
    public static void main(String[] args) {
        License.iConfirmNonCommercialUse("Kasio Project Student");
        SwingUtilities.invokeLater(() -> {
            CalculatorView view = new CalculatorView();
            CalculatorModel model = new CalculatorModel();
            new CalculatorController(model, view);
            view.setVisible(true);
        });
    }
}