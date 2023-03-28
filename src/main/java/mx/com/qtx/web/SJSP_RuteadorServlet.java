package mx.com.qtx.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
     	super.init(config);
     	String nomClaseGestorArticulos = config.getServletContext().getInitParameter("gestorArticulos");
     	System.out.println("nomClaseGestorArticulos = " + nomClaseGestorArticulos);
     	String nomClaseGestorPersonas = config.getServletContext().getInitParameter("gestorPersonas");
     	System.out.println("nomClaseGestorPersonas = " + nomClaseGestorPersonas);
     	try {
			Class<?> clase = Class.forName(nomClaseGestorArticulos);
			Constructor<?> constructor = clase.getDeclaredConstructor();
			this.gestorArticulos= (IGestorArticulos) constructor.newInstance();
			
			clase = Class.forName(nomClaseGestorPersonas);
			constructor = clase.getDeclaredConstructor();
			this.gestorPersonas= (IGestorPersonas) constructor.newInstance();
		} catch (ClassNotFoundException | 
				 NoSuchMethodException | 
				 SecurityException | 
				 InstantiationException | 
				 IllegalAccessException | 
				 IllegalArgumentException | 
				 InvocationTargetException e) {
			e.printStackTrace();
		}     	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion=request.getSession(true);
		
		String idVista = request.getParameter("fmIdVista");
		switch(idVista) {
		case "Menu":
			String opcion = request.getParameter("fmOpcion");
			switch(opcion) {
				case "AltaArticulo":
					String url = response.encodeRedirectURL("formularios/abc_articulo.jsp");
					response.sendRedirect(url);
					return;
				case "ConsultaPersonas":
					GrupoPersonas listaPersonas;
					try{
						listaPersonas = this.gestorPersonas.obtenerPersonas();
					}
					catch (NegocioException e) { //Falló la recuperacion de personas
						sesion.setAttribute("mensajeError", this.gestorPersonas.getMsgError(e));
						url = response.encodeRedirectURL("formularios/paginaError.jsp");
						response.sendRedirect(url);
						return;
					}
					ArrayList<Persona> arrListaPersonas =  
						new ArrayList<Persona>(listaPersonas.obtenerListaPersonas());
					sesion.setAttribute("listaPersonas",arrListaPersonas ); 
					url = response.encodeRedirectURL("formularios/" + request.getParameter("jsp"));
					response.sendRedirect(url);
					return;
				default:
					sesion.setAttribute("mensajeError", "Opción inválida. No hay funcionalidad implementada aún");
					url = response.encodeRedirectURL("formularios/paginaError.jsp");
					response.sendRedirect(url);
				return;
			}
		case "AltaArticulo":
			String deseaRegresar = request.getParameter("fmDeseaRegresar");
			if(deseaRegresar.equals("SI")){
				sesion.invalidate();
				String raizAplicacion ="/"+ this.getServletContext().getServletContextName();
				String url = response.encodeRedirectURL(raizAplicacion);
				response.sendRedirect(url);
				return;
			}
			
			int numErrores=0;
			HashMap<String,String> tablaErrores = new HashMap<String,String>();
			Articulo nuevoArticulo=new Articulo("","",0.0f,0.0f);
			numErrores=this.gestorArticulos.validarCamposFormulario(request, tablaErrores, nuevoArticulo);
			
			if(numErrores>0){//Se encontraron errores
				sesion.setAttribute("tablaErrores", tablaErrores);
				sesion.setAttribute("articulo", nuevoArticulo);
				sesion.setAttribute("hayErrores", "Formulario con errores");
				
				String url = response.encodeRedirectURL("formularios/abc_articulo.jsp");
				response.sendRedirect(url);
				return;
			}
			
			try{
				this.gestorArticulos.insertarArticulo(nuevoArticulo);
			}
			catch (NegocioException e) { //Falló la inserción en la base de datos
				sesion.setAttribute("mensajeError", this.gestorArticulos.getMsgError(e));
				String url = response.encodeRedirectURL("formularios/paginaError.jsp");
				response.sendRedirect(url);
				return;
			}
			String mensajeExito="El registro ha sido dado de alta " +
							"en la base de datos de manera exitosa";
			sesion.setAttribute("mensajeExito", mensajeExito);
			String url = response.encodeRedirectURL("formularios/paginaExito.jsp");
			response.sendRedirect(url);
			return;
		case "PaginaError":
		case "PaginaExito":
		case "ConsultaPersonas":
			sesion.invalidate();
			String raizAplicacion ="/"+ this.getServletContext().getServletContextName();
			url = response.encodeRedirectURL(raizAplicacion);
			response.sendRedirect(url);
		}
	}
	

}
