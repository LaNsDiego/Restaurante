package Negocio;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Entidad.Usuario;
import Utilidad.Inicializador;

public abstract class NUsuario {

	@SuppressWarnings("unchecked")
	static public List<Usuario> Listar(Usuario usuario) {
		List<Usuario> Lista = null;
		try {
			ResultSet r = Conexion.EjecutarConsultaSpSQL(usuario, "SP_LISTAR_USUARIOS", new ArrayList<String>() {
				{
					add("Activo");
				}
			});
			Lista = (List<Usuario>) (List<?>) Inicializador.IniciarlizarArrayObjetos(r, new Usuario());
			System.out.println("cantidad lista :" + Lista.size());
		} catch (Exception e) {
			System.out.println("Error al SP_LISTAR_USUARIOS Usuario: " + e.getMessage());
		}
		return Lista;
	}

	@SuppressWarnings("serial")
	static public int Agregar(Usuario usuario) {
		try {
			ResultSet r = Conexion.EjecutarConsultaSpSQL(usuario, "SP_AGREGAR_USUARIO", new ArrayList<String>() {
				{
					add("Id");
					add("Activo");
				}
			});

			r.first();
			return r.getInt("lid");
		} catch (Exception e) {
			System.out.println("Error al Agregar Usuario: " + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("serial")
	static public boolean Modificar(Usuario usuario) {
		try {
			int afectados = Conexion.EjecutarActualizacionSpSQL(usuario, "SP_MODIFICAR_USUARIO",
					new ArrayList<String>() {
						{
							add("Activo");
						}
					});
			return afectados > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("serial")
	static public boolean Eliminar(Usuario usuario) {
		try {
			int afectados = Conexion.EjecutarActualizacionSpSQL(usuario, "SP_ELIMINAR_USUARIO",
					new ArrayList<String>() {
						{
							add("Activo");
						}
					});
			return afectados > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
