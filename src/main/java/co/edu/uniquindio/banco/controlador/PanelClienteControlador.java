package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PanelClienteControlador {

    @FXML private Label lblBienvenida;
    @FXML private Label lblBilletera;
    @FXML private TableView<Transaccion> tblTransacciones;

    private final Banco banco;
    private String identificacionUsuario;
    private BilleteraVirtual billetera;

    public PanelClienteControlador() {
        banco = Banco.getInstancia();
    }

    public void inicializarDatos(String identificacion) {
        try {
            this.identificacionUsuario = identificacion;
            Usuario usuario = banco.buscarUsuario(identificacion);
            billetera = banco.buscarBilleteraUsuario(identificacion);

            // Configuramos los textos como solicitaste
            lblBienvenida.setText(usuario.getNombre() + ", bienvenido a su banco");
            lblBilletera.setText("Número de cuenta: " + billetera.getNumero());

            // Cargar transacciones
            cargarTransacciones();

        } catch (Exception e) {
            mostrarAlerta("Error al cargar datos", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarTransacciones() {
        ObservableList<Transaccion> transacciones = FXCollections.observableArrayList(
                billetera.obtenerTransacciones()
        );
        tblTransacciones.setItems(transacciones);
    }

    @FXML
    private void consultarSaldo() {
        try {
            float saldo = billetera.consultarSaldo();
            mostrarAlerta("Saldo actual",
                    String.format("Su saldo actual es: $%,.2f", saldo),
                    Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void mostrarTransferencia() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transferencia.fxml"));
            Parent root = loader.load();

            TransferenciaControlador controlador = loader.getController();
            controlador.inicializarDatos(billetera.getNumero());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Realizar Transferencia");
            stage.setResizable(false);
            stage.showAndWait();

            // Actualizar transacciones después de la transferencia
            cargarTransacciones();

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            // Guardar datos antes de cerrar
            banco.guardarDatos();

            Stage stage = (Stage) lblBienvenida.getScene().getWindow();
            stage.close();

            // Opcional: Mostrar ventana de inicio
            new InicioControlador().navegarVentana("/inicio.fxml", "Banco");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron guardar los datos: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}