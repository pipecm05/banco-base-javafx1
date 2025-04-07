package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PanelClienteControlador {

    @FXML
    private Label lblBienvenida;

    private final Banco banco;
    private String identificacionUsuario;

    public PanelClienteControlador() {
        banco = Banco.getInstancia();
    }

    public void inicializarDatos(String identificacion) {
        this.identificacionUsuario = identificacion;
        // Aquí puedes cargar más datos del usuario si es necesario
        lblBienvenida.setText("Bienvenido, " + banco.buscarUsuario(identificacion).getNombre());
    }
}
