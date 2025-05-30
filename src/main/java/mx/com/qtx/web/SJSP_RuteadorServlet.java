package mx.com.qtx.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.com.qtx.coreNegocio.IGestorArticulos;
import mx.com.qtx.coreNegocio.IGestorPersonas;
import mx.com.qtx.coreNegocio.NegocioException;
import mx.com.qtx.dominio.Articulo;
import mx.com.qtx.dominio.GrupoPersonas;
import mx.com.qtx.dominio.Persona;

@WebServlet("/SJSP_RuteadorServlet")
public class SJSP_RuteadorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IGestorArticulos gestorArticulos;
	private IGestorPersonas gestorPersonas;
       
    public SJSP_RuteadorServlet() throws Exception {
        super();
        System.out.println(this.getClass().getName() + " instanciado ");
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
     	super.init(config);
     	String nomClaseGestorArticulos = config.getServletContext().getInitParameter("gestorArticulos");
     	System.out.println("nomClaseGestorArticulos = " + nomClaseGestorArticulos);
     	String nomClaseGestorPersonas = config.getServletContext().getInitParameter("gestorPersonas");
     	System.out.println("nomClaseGestorPersonas = " + nomClaseGestorPersonas);
     	this.gestorArticulos = (IGestorArticulos) instanciarClase(nomClaseGestorArticulos);
     	this.gestorPersonas =  (IGestorPersonas) instanciarClase(nomClaseGestorPersonas);
    }
    
	private Object instanciarClase(String nomClaseGestorArticulos) {
		Object instancia = null;
		try {
			Class<?> clase = Class.forName(nomClaseGestorArticulos);
			Constructor<?> constructor = clase.getDeclaredConstructor();
			instancia = constructor.newInstance();
		} 
		catch (ClassNotFoundException | 
				 NoSuchMethodException | 
				 SecurityException | 
				 InstantiationException | 
				 IllegalAccessException | 
				 IllegalArgumentException | 
				 InvocationTargetException e) {
			e.printStackTrace();
		}
		return instancia;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion=request.getSession(true);
		
		String idVista = request.getParameter("fmIdVista");
		switch(idVista) {
			case "Menu"->{
				String opcion = request.getParameter("fmOpcion");
				switch(opcion) {
					case "AltaArticulo"->{
						redirigirA(response, "formularios/abc_articulo.jsp");
						return;
					}
					case "ConsultaPersonas"->{
						GrupoPersonas listaPersonas;
						try{
							listaPersonas = this.gestorPersonas.obtenerPersonas();
						}
						catch (NegocioException e) { //Falló la recuperacion de personas
							sesion.setAttribute("mensajeError", this.gestorPersonas.getMsgError(e));
							redirigirA(response, "formularios/paginaError.jsp");
							return;
						}
						ArrayList<Persona> arrListaPersonas =  
							new ArrayList<Persona>(listaPersonas.obtenerListaPersonas());
						sesion.setAttribute("listaPersonas",arrListaPersonas ); 
						redirigirA(response, "formularios/" + request.getParameter("jsp"));
						return;
					}
					default->{
						sesion.setAttribute("mensajeError", "Opción inválida. No hay funcionalidad implementada aún");
						redirigirA(response, "formularios/paginaError.jsp");
						return;
					}
				}
			}
			case "AltaArticulo" ->{
				String deseaRegresar = request.getParameter("fmDeseaRegresar");
				if(deseaRegresar.equals("SI")){
					reIniciarConversacion(response, sesion);
					return;
				}
				
				int numErrores=0;
				HashMap<String,String> tablaErrores = new HashMap<String,String>();
				Articulo nuevoArticulo=new Articulo("","",0.0f,0.0f);
				numErrores=this.gestorArticulos.validarCamposFormulario(request, tablaErrores, nuevoArticulo);
				
				if(numErrores>0){//Se encontraron errores
					agregarErroresAltaArticuloAsesion(sesion, tablaErrores, nuevoArticulo);
					redirigirA(response, "formularios/abc_articulo.jsp");
					return;
				}
				
				try{
					this.gestorArticulos.insertarArticulo(nuevoArticulo);
				}
				catch (NegocioException e) { //Falló la inserción en la base de datos
					sesion.setAttribute("mensajeError", this.gestorArticulos.getMsgError(e));
					redirigirA(response, "formularios/paginaError.jsp");
					return;
				}
				String mensajeExito="El registro ha sido dado de alta " +
								"en la base de datos de manera exitosa";
				sesion.setAttribute("mensajeExito", mensajeExito);
				redirigirA(response, "formularios/paginaExito.jsp");
				return;
			}
			case "PaginaError"->{
				reIniciarConversacion(response, sesion);
			}
			case "PaginaExito"->{
				reIniciarConversacion(response, sesion);
			}
			case "ConsultaPersonas"->{
				reIniciarConversacion(response, sesion);
			}
		}
	}

	private void agregarErroresAltaArticuloAsesion(HttpSession sesion, HashMap<String, String> tablaErrores,
			Articulo nuevoArticulo) {
		sesion.setAttribute("tablaErrores", tablaErrores);
		sesion.setAttribute("articulo", nuevoArticulo);
		sesion.setAttribute("hayErrores", "Formulario con errores");
	}

	private void redirigirA(HttpServletResponse response, String destino) throws IOException {
		String url = response.encodeRedirectURL(destino);
		response.sendRedirect(url);
	}

	private void reIniciarConversacion(HttpServletResponse response, HttpSession sesion) throws IOException {
		sesion.invalidate();
		String raizAplicacion ="/"+ this.getServletContext().getServletContextName();
		redirigirA(response, raizAplicacion);
	}
	

}
