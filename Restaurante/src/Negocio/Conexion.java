package Negocio;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Conexion {

	private static Connection Singleton = null;

	private Conexion() {
	}

	// @SuppressWarnings("CallToPrintStackTrace")
	private static void conectar() {
		try {
			Singleton = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/probandojavamylsq?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
			System.out.println("Conexion Exitosá");
		} catch (Exception e) {
			System.out.println("Error de Conexion: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Connection Db() {
		if (Singleton == null) {
			conectar();
		}
		return Singleton;
	}

	public static boolean doesObjectContainField(Object object, String fieldName) {
		return Arrays.stream(object.getClass().getFields()).anyMatch(f -> f.getName().equals(fieldName));
	}

	public static boolean ExisteEnLista(String Texto, List<String> Lista) {
		for (String str : Lista) {
			if (str.equals(Texto)) {
				return true;
			}

		}
		return false;
	}

	public static String ContruirSpSQL(Object obj, String NombreSp, List<String> parametrosIgnorados) {

		try {
			String sp = "{ CALL " + NombreSp + "(";
			Field[] campos = obj.getClass().getFields();
			List<String> interrogantes = new ArrayList<>();

			for (int i = 0; i < campos.length; i++) {

				Field atributo = campos[i];
				String tipo = atributo.getAnnotatedType().getType().getTypeName();
				if (!ExisteEnLista(atributo.getName(), parametrosIgnorados)) {
					if (tipo.equals("int") && (int) atributo.get(obj) != -1) {
						// stmt.setInt(("_"+atributo.getName()),(int)atributo.get(obj));
						interrogantes.add("?");
						System.out.println(
								"ADMITIDO : " + (atributo.getName()) + " con valor " + (int) atributo.get(obj));
					} else if (tipo.equals("java.lang.String") && !"".equals((String) atributo.get(obj))
							&& ((String) atributo.get(obj)) != null) {
						// stmt.setString(("_"+atributo.getName()),(String)atributo.get(obj));
						interrogantes.add("?");
						System.out.println(
								"ADMITIDO : " + (atributo.getName()) + " con valor " + (String) atributo.get(obj));
					} else if (tipo.equals("double") && (double) atributo.get(obj) != 0.0) {
						// stmt.setDouble("_"+atributo.getName(),(double)atributo.get(obj));
						interrogantes.add("?");
						// System.out.println( "ADMITIDO : "+(atributo.getName())+" con valor
						// "+(double)atributo.get(obj));
					} else if (tipo.equals("boolean")) {
						// stmt.setDouble("_"+atributo.getName(),(double)atributo.get(obj));
						interrogantes.add("?");
						// System.out.println( "ADMITIDO : "+(atributo.getName())+" con valor
						// "+atributo.get(obj));
					}
				} else {

					System.out.println("NO ADMITIDO : " + (atributo.getName()) + " con valor " + atributo.get(obj)
							+ " TYPE " + atributo.getAnnotatedType().getType().getTypeName());
				}

			}
			sp += String.join(",", interrogantes);
			sp += ")}";
			System.out.println("");
			System.out.println("SP:" + sp);
			System.out.println("");
			return sp;
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return "";
		}

	}

	private static CallableStatement PrepararSpSQL(Object obj, String NombreSp, List<String> parametrosIgnorados) {
		CallableStatement stmt = null;
		try {
			Db();
			stmt = Db().prepareCall(ContruirSpSQL(obj, NombreSp, parametrosIgnorados));
			Field[] campos = obj.getClass().getFields();
			for (Field atributo : campos) {
				String tipo = atributo.getAnnotatedType().getType().getTypeName();
				if (!parametrosIgnorados.contains(atributo.getName())) {
					if (tipo.equals("int") && (int) atributo.get(obj) != -1) {
						stmt.setInt(("_" + atributo.getName()), (int) atributo.get(obj));
					} else if (tipo.equals("java.lang.String") && !"".equals((String) atributo.get(obj))
							&& ((String) atributo.get(obj)) != null) {
						System.out.println(
								"ADMITIDO PARAMETRO :" + (atributo.getName()) + " con valor " + atributo.get(obj));
						stmt.setString(("_" + atributo.getName()), (String) atributo.get(obj));
					} else if (tipo.equals("double") && (double) atributo.get(obj) != 0.0) {
						stmt.setDouble("_" + atributo.getName(), (double) atributo.get(obj));
					} else if (tipo.equals("boolean")) {
						stmt.setBoolean("_" + atributo.getName(), (boolean) atributo.get(obj));
						System.out.println(
								"ADMITIDO PARAMETRO :" + (atributo.getName()) + " con valor " + atributo.get(obj));
					} else {
						System.out.println("NO ADMITIDO PARAMETRO : " + (atributo.getName()) + " con valor "
								+ atributo.get(obj) + " TYPE " + atributo.getAnnotatedType().getType().getTypeName());
					}
				} else {
					System.out.println("NO ADMITIDO PARAMETRO : " + (atributo.getName()) + " con valor "
							+ atributo.get(obj) + " TYPE " + atributo.getAnnotatedType().getType().getTypeName());
				}
			}
			return stmt;
		} catch (SQLException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// System.out.println("Error en [PrepararSpSQL]: "+e.getMessage());
			e.printStackTrace();
			return stmt;
		}
	}

	public static ResultSet EjecutarConsultaSpSQL(Object obj, String NombreSp, List<String> parametrosIgnorados) {

		List<String> parametrosIgnoradosExistentes = new ArrayList<String>();
		parametrosIgnorados.stream().filter((parametroIgnorado) -> (doesObjectContainField(obj, parametroIgnorado)))
				.forEach((parametroIgnorado) -> {
					parametrosIgnoradosExistentes.add(parametroIgnorado);
				});

		CallableStatement stmt = PrepararSpSQL(obj, NombreSp, parametrosIgnoradosExistentes);
		ResultSet Resultado = null;
		try {
			Resultado = stmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Error ejecutar Q:"+e.getMessage());
		}
		return Resultado;
	}

	public static ResultSet EjecutarConsultaSpSQL(Object obj, String NombreSp) {
		CallableStatement stmt = PrepararSpSQL(obj, NombreSp, new ArrayList<String>());
		ResultSet Resultado = null;
		try {
			Resultado = stmt.executeQuery();
		} catch (Exception e) {
			System.out.println("Error ejecutar Q(2):" + e.getMessage());
		}
		return Resultado;
	}

	public static int EjecutarActualizacionSpSQL(Object obj, String NombreSp, List<String> parametrosIgnorados) {
		CallableStatement stmt = PrepararSpSQL(obj, NombreSp, parametrosIgnorados);
		int Resultado = -2;
		try {
			Resultado = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en EjecutarActualizacionSpSQL:" + e.getMessage());
		}
		return Resultado;
	}

	public static int EjecutarActualizacionSpSQL(Object obj, String NombreSp) {
		CallableStatement stmt = PrepararSpSQL(obj, NombreSp, new ArrayList<String>());
		int Resultado = -2;
		try {
			Resultado = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error en EjecutarActualizacionSpSQL(2):" + e.getMessage());
		}
		return Resultado;
	}

}
