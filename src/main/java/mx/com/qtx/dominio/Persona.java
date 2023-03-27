package mx.com.qtx.dominio;

import java.sql.SQLException;
import java.util.Date;

import mx.com.qtx.persistencia.GestorBD;

public class Persona {
	private int idPersona;
	private String nombre;
	private String direccion;
	private Date fechaNacimiento;

	public Persona(int idPersona, String nombre, String direccion,
			Date fechaNacimiento) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.direccion = direccion;
		this.fechaNacimiento = fechaNacimiento;
	}
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", nombre=" + nombre
				+ ", direccion=" + direccion + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}
	public static Persona recuperaXid(int idPersona)throws SQLException{
		return GestorBD.recuperarPersonaXid(idPersona);
	}


}
