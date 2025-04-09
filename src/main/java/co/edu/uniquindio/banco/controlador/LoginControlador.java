package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.vo.SaldoTransaccionesBilletera;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControlador {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMensaje;

    private final Banco banco;

    public LoginControlador() {
        banco = Banco.getInstancia();
    }

    @FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        try {
            String identificacion = txtIdentificacion.getText();
            String password = txtPassword.getText();

            if (identificacion.isEmpty() || password.isEmpty()) {
                mostrarMensaje("Por favor complete todos los campos", false);
                return;
            }

            // Verificar credenciales
            SaldoTransaccionesBilletera saldoTransacciones =
                    banco.consultarSaldoYTransacciones(identificacion, password);

            // Si no hubo excepción, las credenciales son correctas
            cerrarVentana();
            abrirPanelCliente(identificacion);

        } catch (Exception e) {
            mostrarMensaje(e.getMessage(), false);
        }
    }

    private void abrirPanelCliente(String identificacion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            // Obtenemos el controlador y llamamos a inicializarDatos
            PanelClienteControlador controlador = loader.getController();
            controlador.inicializarDatos(identificacion); // ¡Aquí pasamos la identificación!

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel del Cliente");
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje, boolean esExito) {
        lblMensaje.setText(mensaje);
        lblMensaje.setVisible(true);
        lblMensaje.setStyle(esExito ? "-fx-text-fill: green" : "-fx-text-fill: red");
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }

    // Método auxiliar para mostrar alertas (similar al de RegistroControlador)
    private void crearAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}