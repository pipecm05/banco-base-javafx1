package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActualizarDatosControlador {
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEmail;
    @FXML private Label lblMensaje;

    private final Banco banco = Banco.getInstancia();
    private String idUsuario;
    private Stage stage;

    public void inicializar(String idUsuario, Stage stage) {
        this.idUsuario = idUsuario;
        this.stage = stage;
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = banco.buscarUsuario(idUsuario);
        txtNombre.setText(usuario.getNombre());
        txtDireccion.setText(usuario.getDireccion());
        txtEmail.setText(usuario.getEmail());
    }

    @FXML
    private void guardarCambios() {
        try {
            banco.actualizarUsuario(
                    idUsuario,
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtEmail.getText()
            );

            lblMensaje.setText("Datos actualizados correctamente");
            lblMensaje.setStyle("-fx-text-fill: green");

            // Cerrar ventana después de 1.5 segundos
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> stage.close());
                        }
                    },
                    1500
            );
        } catch (Exception e) {
            lblMensaje.setText(e.getMessage());
        }
    }
}