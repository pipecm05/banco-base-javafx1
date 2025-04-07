package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransferenciaControlador {

    @FXML private TextField txtBilleteraDestino;
    @FXML private TextField txtMonto;
    @FXML private ComboBox<Categoria> cbCategoria;
    @FXML private Label lblMensaje;

    private final Banco banco;
    private String billeteraOrigen;

    public TransferenciaControlador() {
        banco = Banco.getInstancia();
    }

    public void inicializarDatos(String billeteraOrigen) {
        this.billeteraOrigen = billeteraOrigen;
        cbCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));
    }

    @FXML
    private void realizarTransferencia() {
        try {
            // Validar campos
            if (txtBilleteraDestino.getText().isEmpty() || txtMonto.getText().isEmpty() ||
                    cbCategoria.getValue() == null) {
                lblMensaje.setText("Todos los campos son obligatorios");
                lblMensaje.setVisible(true);
                return;
            }

            String billeteraDestino = txtBilleteraDestino.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            Categoria categoria = cbCategoria.getValue();

            // Realizar transferencia
            banco.realizarTransferencia(billeteraOrigen, billeteraDestino, monto, categoria);

            // Mostrar éxito y cerrar ventana
            lblMensaje.setText("Transferencia realizada con éxito");
            lblMensaje.setStyle("-fx-text-fill: green");
            lblMensaje.setVisible(true);

            // Cerrar ventana después de 1 segundo
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
                    1000
            );

        } catch (NumberFormatException e) {
            lblMensaje.setText("El monto debe ser un número válido");
            lblMensaje.setVisible(true);
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
            lblMensaje.setVisible(true);
        }
    }
}