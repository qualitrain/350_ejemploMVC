package mx.com.qtx.dominio;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import mx.com.qtx.persistencia.GestorBD;

public class GrupoPersonas {

	private HashMap<Integer,Persona> personas;

	public GrupoPersonas(){
		this.personas = new HashMap<Integer,Persona>();
	}
	public void agregarPersona (Persona unaPersona){
		this.personas.put(unaPersona.getIdPersona(),unaPersona);
	}
	public void mostrar(){
		for(Persona unaPersona:this.personas.values()){
			System.out.println(unaPersona);
		}
	}
	public Persona getPersonaPorID(int id){
		return this.personas.get(id);
	}
	public static GrupoPersonas obtenerPersonas() throws SQLException{
		return GestorBD.recuperarPersonasTodas();
	}
	public Iterator<Integer>getLLaves(){
		return this.personas.keySet().iterator();
	}
	public Collection<Persona> obtenerListaPersonas(){
		return this.personas.values();
	}
}
