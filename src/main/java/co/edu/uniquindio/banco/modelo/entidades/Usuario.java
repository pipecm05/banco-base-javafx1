package co.edu.uniquindio.banco.modelo.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Clase que representa un usuario del banco
 * @version 1.0
 * @autor caflorezvi
 */
@Getter
@Setter
@AllArgsConstructor
public class Usuario implements Serializable {

    private String id, nombre, direccion, email, password;

}
