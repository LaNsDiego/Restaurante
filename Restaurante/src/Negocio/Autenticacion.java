package Negocio;

import java.sql.ResultSet;
import java.util.ArrayList;

import Entidad.Usuario;

public abstract class Autenticacion {
	@SuppressWarnings("serial")
	public static int Login(Usuario ObjUsuario) {
		int Id = 0;
		try {
			ResultSet r = Conexion.EjecutarConsultaSpSQL(ObjUsuario, "SP_LOGIN", new ArrayList<String>() {
				{
					add("Id");
					add("Nombre");
					add("Activo");
				}
			});
			if (r.first()) {
				Id = r.getInt("Id");
			}
		} catch (Exception e) {
			System.out.println("Error al Agregar Usuario: " + e.getMessage());
			e.printStackTrace();
		}
		return Id;
	}
}