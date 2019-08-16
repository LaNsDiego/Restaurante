package Entidad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
	public int Id;
	public String Nombre;
	public String Clave;
	public String Correo;
	public boolean Activo;

	public Usuario() {
		Id = -1;
	}

	public boolean ValidarCorreo() {
		Pattern patron = Pattern.compile(
				"^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		Matcher emparejador = patron.matcher(Correo);
		return emparejador.matches();
	}

	public boolean Validar() {
		return (Id > -1) && (!Nombre.isEmpty()) && (!Clave.isEmpty()) && (!Correo.isEmpty()) && ValidarCorreo();
	}

}
