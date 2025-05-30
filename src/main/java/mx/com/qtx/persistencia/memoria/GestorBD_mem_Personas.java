package mx.com.qtx.persistencia.memoria;

import java.util.Hashtable;
import java.util.Map;

import mx.com.qtx.dominio.GrupoPersonas;
import mx.com.qtx.dominio.Persona;
import mx.com.qtx.servNegocio.IGestorBD_Personas;
import mx.com.qtx.util.Fecha;

public class GestorBD_mem_Personas implements IGestorBD_Personas{
	Map<Integer, Persona> mapPersonas;
	
	public GestorBD_mem_Personas() {
		this.mapPersonas = new Hashtable<>();
		this.cargarPersonas();
	}
	public GrupoPersonas recuperarPersonasTodas() {
		GrupoPersonas grupoPersonas = new GrupoPersonas();
		for(Persona personaI:this.mapPersonas.values()) {
			grupoPersonas.agregarPersona(personaI);
		}
		return grupoPersonas;
	}

	private void cargarPersonas() {
		Persona personaI = new Persona(1,"Alejandro Ramirez De la Huerta","Av. Insurgentes Sur 456, col. Roma", Fecha.crearDate(1970, 9, 11));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(2,"Jorge Fernandez Menendez","Salvador Diaz Miron 456, col. Del Valle", Fecha.crearDate(1954, 12, 31));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(3,"Maricela de la Fuente Perez","Margaritas 45, col. Villa de la Rueda", Fecha.crearDate(1980, 2, 23));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(4,"Miguel Montes De la Paz","Benito Juárez 567 int 401, col. Héroes de Chapultepec", Fecha.crearDate(1977, 11, 22));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(5,"Brenda Berenice Torres Marquez","Av. Independencia 45, col. Centro", Fecha.crearDate(1983, 4, 7));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(6,"Diego Cruz Vazquez","Zacatecas 99, col. Herreros", Fecha.crearDate(2018, 6, 27));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(7,"Ramiro Sosa Matus","Paseo de las Orquideas 99-201, col. Lomas de Sotelo", Fecha.crearDate(2010, 11, 17));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
		personaI = new Persona(8,"Lorena De la Merced Simon","Rafael Villafuerte 1203-321, col. Los tejados", Fecha.crearDate(2001, 10, 22));
		this.mapPersonas.put(personaI.getIdPersona(), personaI);
	}

}
