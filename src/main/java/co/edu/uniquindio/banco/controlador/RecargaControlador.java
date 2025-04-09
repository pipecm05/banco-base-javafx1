package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecargaControlador {

    @FXML private TextField txtBilletera;
    @FXML private TextField txtMonto;
    @FXML private ComboBox<String> cbMetodoPago;
    @FXML private Label lblMensaje;

    private final Banco banco = Banco.getInstancia();

    public void inicializarDatos(String numeroBilletera) {
        txtBilletera.setText(numeroBilletera);
        cbMetodoPago.getItems().addAll("Tarjeta Crédito", "Tarjeta Débito", "Efectivo", "Transferencia Bancaria");
    }

    @FXML
    private void realizarRecarga() {
        try {
            String billetera = txtBilletera.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            String metodoPago = cbMetodoPago.getValue();

            if (monto <= 0) {
                throw new Exception("El monto debe ser mayor a cero");
            }

            if (metodoPago == null || metodoPago.isEmpty()) {
                throw new Exception("Seleccione un método de pago");
            }

            banco.recargarBilletera(billetera, monto);

            lblMensaje.setText("Recarga exitosa de $" + monto);
            lblMensaje.setStyle("-fx-text-fill: green");
            lblMensaje.setVisible(true);

            // Cerrar ventana después de 1.5 segundos
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> {
                                Stage stage = (Stage) lblMensaje.getScene().getWindow();
                                stage.close();
                            });
                        }
                    },
                    1500
            );

        } catch (NumberFormatException e) {
            lblMensaje.setText("Ingrese un monto válido");
            lblMensaje.setVisible(true);
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
            lblMensaje.setVisible(true);
        }
    }
}