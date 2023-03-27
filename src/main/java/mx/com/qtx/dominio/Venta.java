package mx.com.qtx.dominio;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import mx.com.qtx.persistencia.GestorBD;

public class Venta {
	private int numVenta;
	private Date fechaVenta;
	private Persona cliente;
	private Persona vendedor;
	private HashMap<Integer,DetalleVenta> detallesVta;

	
	public Venta(int numVenta, Date fechaVenta, Persona cliente,
			Persona vendedor) {
		super();
		this.numVenta = numVenta;
		this.fechaVenta = fechaVenta;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.detallesVta = new HashMap<Integer,DetalleVenta>();
	}
	public void agregarDetalle(int numDetalle, int cantidad, Articulo articulo, float precioUnitario){
		DetalleVenta nuevoDetalleVenta = new DetalleVenta (numDetalle, cantidad, articulo,precioUnitario);
		this.detallesVta.put(numDetalle, nuevoDetalleVenta); 
		}
	public HashMap<Integer, DetalleVenta> getDetallesVta() {
		return detallesVta;
	}
	public void setDetallesVta(HashMap<Integer, DetalleVenta> detallesVta) {
		this.detallesVta = detallesVta;
	}
	public int getNumVenta() {
		return numVenta;
	}
	public void setNumVenta(int numVenta) {
		this.numVenta = numVenta;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public Persona getCliente() {
		return cliente;
	}
	public void setCliente(Persona cliente) {
		this.cliente = cliente;
	}
	public Persona getVendedor() {
		return vendedor;
	}
	public void setVendedor(Persona vendedor) {
		this.vendedor = vendedor;
	}
	@Override
	public String toString() {
		return "Venta [numVenta=" + numVenta + ", fechaVenta=" + fechaVenta
				+ ", cliente=" + cliente + ", vendedor=" + vendedor
				+ ", detallesVta=" + detallesVta + "]";
	}
	public int insertarEnBD_NoTransac() throws SQLException{
		return GestorBD.insertarVentaNoTransaccional(this);
	}
	public int insertarEnBD_Transac() throws SQLException{
		return GestorBD.insertarVentaTransaccional(this);
	}
	public static Venta recuperaXId(int numVenta)throws SQLException{
		return GestorBD.recuperarVentaXid(numVenta);
	}
	public void mostrar() {
		float totalVenta=0;
		float totalDetalleVenta=0;
		if(this.numVenta <= 0){
			System.out.println("======================== Venta no actualizada en BD aún ===================");
			return;
		}
		System.out.println("===================================================================================================");
		System.out.println(" Venta num: "+this.numVenta+"                   Cliente:"+this.cliente.getNombre());
		System.out.println(" Fecha de venta:"+ this.fechaVenta.toString() +"       Direccion:"+this.cliente.getDireccion());
		System.out.println(" Vendedor: "+ this.vendedor.getIdPersona()+"-"+this.vendedor.getNombre());
		System.out.println("====================================================================================================");
		System.out.println(" num   cantidad   articulo                  descripcion                  precio unitario     total   ");
		System.out.println("====================================================================================================");
		for(int i=1;;i++){
			DetalleVenta det = this.detallesVta.get(i);
			if(det == null)
				break;
			totalDetalleVenta = det.getCantidad() * det.getPrecioUnitario();
			totalVenta+=totalDetalleVenta;
			System.out.print("  "+det.getNumDetalle()+"          ");
			System.out.print(+det.getCantidad()+"  ");
			System.out.print(String.format("%-15s",det.getArticuloVendido().getCveArticulo()) );
			System.out.print(" "+String.format("%-40s",det.getArticuloVendido().getDescripcion()));
			System.out.print("      "+String.format("%7.2f",det.getPrecioUnitario()));
			System.out.println("     "+String.format("%8.2f",totalDetalleVenta));
		}
		System.out.println("====================================================================================================");
		System.out.print("                                                                                total    ");
		System.out.println(String.format("%9.2f",totalVenta));
		System.out.println("====================================================================================================");
	}
	public String toHtml(){
		String cadHtmlVenta="";
		cadHtmlVenta+="<table border=\"1\">\n";
		cadHtmlVenta+=this.encabezadosVentaToHtml();
		cadHtmlVenta+=this.detallesVentaToHtml();
		cadHtmlVenta+="</table>\n";
		return cadHtmlVenta;
	}
	private String encabezadosVentaToHtml(){
		String cadHtmlVenta="";
		cadHtmlVenta+="<caption> Venta número:"+this.numVenta+"</caption>\n";
		cadHtmlVenta+="<tr>\n" +
				"<td>Fecha:</td>\n<td>"+this.fechaVenta+"</td>\n";
		cadHtmlVenta+="<td>Num. cliente:</td><td>"+this.cliente.getIdPersona()+"</td>" +
				"<td>"+this.cliente.getNombre()+"</td\n>" +
				"<td>dirección:</td>\n<td>"+this.cliente.getDireccion()+"</td>\n" +
				"</tr>\n";
		cadHtmlVenta+="<tr>\n" +
		"<td>vendedor:</td>\n<td>"+this.vendedor.getIdPersona()+"</td>\n" +
		"<td>"+this.vendedor.getNombre()+"</td>\n"+
		"</tr>\n";
		cadHtmlVenta+="<tr>\n" +
		"<th>num</th>\n<th>cantidad</th>\n<th>artículo</th>\n" +
		"<th>descripción</th>\n<th>precio unitario</th>\n<th>total</th>\n" +
		"</tr>\n";
		return cadHtmlVenta;
		
	}
	private String detallesVentaToHtml(){
		String cadHtmlVenta="";
		float totalVenta=0;
		float totalDetalleVenta=0;
		
		for(int i=1;;i++){
			DetalleVenta det = this.detallesVta.get(i);
			if(det == null)
				break;
			totalDetalleVenta = det.getCantidad() * det.getPrecioUnitario();
			totalVenta+=totalDetalleVenta;
			cadHtmlVenta+="<tr>\n";
			cadHtmlVenta+="<td>"+det.getNumDetalle()+"</td>\n";
			cadHtmlVenta+="<td>"+det.getCantidad()+"</td>\n";
			cadHtmlVenta+="<td>"+String.format("%-15s",det.getArticuloVendido().getCveArticulo())+"</td>\n";
			cadHtmlVenta+="<td>"+String.format("%-40s",det.getArticuloVendido().getDescripcion())+"</td>\n";
			cadHtmlVenta+="<td>"+String.format("%7.2f",det.getPrecioUnitario())+"</td>\n";
			cadHtmlVenta+="<td>"+String.format("%8.2f",totalDetalleVenta)+"</td>\n";
			cadHtmlVenta+="</tr>\n";
		}
		cadHtmlVenta+="<tr>\n";
		cadHtmlVenta+="<td>"+String.format("%9.2f",totalVenta)+"</td>\n";
		cadHtmlVenta+="</tr>\n";
		
		return cadHtmlVenta;
		
	}
	public String toHtmlAjustado(){
		String cadHtmlVenta="";
		cadHtmlVenta+="<table border=\"1\">\n";
		cadHtmlVenta+=this.encabezadosVentaAjustadosToHtml();
		cadHtmlVenta+=this.detallesVentaAjustadosToHtml();
		cadHtmlVenta+="</table>\n";
		return cadHtmlVenta;
	}
	public String toHtmlAjustadoYestilizado(String classAusar){
		String cadHtmlVenta="";
		cadHtmlVenta+="<table border=\"1\">\n";
		cadHtmlVenta+=this.encabezadosVentaAjustadosToHtml();
		cadHtmlVenta+=this.detallesVentaEstilizadosToHtml(classAusar);
		cadHtmlVenta+="</table>\n";
		return cadHtmlVenta;
	}

	private String encabezadosVentaAjustadosToHtml(){
		String cadHtmlVenta="";
		cadHtmlVenta+="<tr><th colspan=\"6\" align=\"center\"><b> Venta número:</b>"+this.numVenta+"</th></tr>\n";
		cadHtmlVenta+="<tr>\n" +
				"<td colspan=\"2\"><b>Fecha:</b> "+this.fechaVenta+"</td>\n";
		cadHtmlVenta+="<td align=\"left\"><b>Num cte:</b> "+this.cliente.getIdPersona()+"</td>\n" +
				"<td>"+this.cliente.getNombre()+"</td>\n" +
				"<td colspan=\"2\" align=\"center\"><b>Dirección:</b></td>\n"+ 
				"</tr>\n";
		cadHtmlVenta+="<tr>\n" +
		"<td><b>Vendedor:</b></td>\n<td>"+this.vendedor.getIdPersona()+"</td>\n" +
		"<td colspan=\"2\">"+this.vendedor.getNombre()+"</td>\n"+
		"<td align=\"left\" colspan=\"2\">"+this.cliente.getDireccion()+"</td>\n"+
		"</tr>\n";
		
		cadHtmlVenta+="<tr>\n" +
		"<td colspan=\"6\">&nbsp</td>" +
		"</tr>\n";

		cadHtmlVenta+="<tr>\n" +
		"<th>num</th>\n<th>cantidad</th>\n<th>artículo</th>\n" +
		"<th>descripción</th>\n<th>precio unitario</th>\n<th>total</th>\n" +
		"</tr>\n";
		return cadHtmlVenta;
		
	}
	private String detallesVentaAjustadosToHtml(){
		String cadHtmlVenta="";
		float totalVenta=0;
		float totalDetalleVenta=0;
		
		for(int i=1;;i++){
			DetalleVenta det = this.detallesVta.get(i);
			if(det == null)
				break;
			totalDetalleVenta = det.getCantidad() * det.getPrecioUnitario();
			totalVenta+=totalDetalleVenta;
			cadHtmlVenta+="<tr>\n";
			cadHtmlVenta+="<td align=\"center\">"+det.getNumDetalle()+"</td>\n";
			cadHtmlVenta+="<td align=\"center\">"+det.getCantidad()+"</td>\n";
			cadHtmlVenta+="<td width=\"100 \" align=\"center\">"+det.getArticuloVendido().getCveArticulo()+"</td>\n";
			cadHtmlVenta+="<td width=\"300 \" align=\"left\">"+det.getArticuloVendido().getDescripcion()+"</td>\n";
			cadHtmlVenta+="<td align=\"right\">"+"$"+String.format("%7.2f",det.getPrecioUnitario())+"</td>\n";
			cadHtmlVenta+="<td align=\"right\">"+"$"+String.format("%8.2f",totalDetalleVenta)+"</td>\n";
			cadHtmlVenta+="</tr>\n";
		}
		cadHtmlVenta+="<tr>\n";
		cadHtmlVenta+="<td colspan=\"6\" align=\"right\"><b>"+"$"+String.format("%9.2f",totalVenta)+"</b></td>\n";
		cadHtmlVenta+="</tr>\n";
		
		return cadHtmlVenta;
		
	}
	private String detallesVentaEstilizadosToHtml(String classAusar){
		String cadHtmlVenta="";
		float totalVenta=0;
		float totalDetalleVenta=0;
		
		int i;
		String cadClass=null;

		for(i=1;;i++){
			DetalleVenta det = this.detallesVta.get(i);
			if(i%2==0)
				cadClass=" class=\""+classAusar+"\" ";
			else
				cadClass=" ";
			if(det == null)
				break;
			totalDetalleVenta = det.getCantidad() * det.getPrecioUnitario();
			totalVenta+=totalDetalleVenta;
			
			cadHtmlVenta+="<tr>\n";
				
			cadHtmlVenta+="<td"+ cadClass +
					"align=\"center\">"+det.getNumDetalle()+"</td>\n";
			cadHtmlVenta+="<td " + cadClass +
					"align=\"center\">"+det.getCantidad()+"</td>\n";
			cadHtmlVenta+="<td" + cadClass +
					" width=\"100 \" align=\"center\">"+det.getArticuloVendido().getCveArticulo()+"</td>\n";
			cadHtmlVenta+="<td" + cadClass +
					" width=\"300 \" align=\"left\">"+det.getArticuloVendido().getDescripcion()+"</td>\n";
			cadHtmlVenta+="<td" + cadClass +
					" align=\"right\">"+"$"+String.format("%7.2f",det.getPrecioUnitario())+"</td>\n";
			cadHtmlVenta+="<td" + cadClass +
					" align=\"right\">"+"$"+String.format("%8.2f",totalDetalleVenta)+"</td>\n";
			cadHtmlVenta+="</tr>\n";
		}
		cadHtmlVenta+="<tr>\n";
		cadHtmlVenta+="<td" + cadClass +
				" colspan=\"6\" align=\"right\"><b>"+"$"+String.format("%9.2f",totalVenta)+"</b></td>\n";
		cadHtmlVenta+="</tr>\n";
		
		return cadHtmlVenta;
		
	}

	public String toHtmlAjustadoConMarcaDeAgua(String urlImagen,String idImagen, int ancho,int alto){
		String cadHtmlVenta="";
		cadHtmlVenta+="<div>\n";
		cadHtmlVenta+=this.marcaDeAguaToHtml(urlImagen, idImagen, ancho, alto);		
		cadHtmlVenta+="<table border=\"1\">\n";
		cadHtmlVenta+=this.encabezadosVentaAjustadosToHtml();
		cadHtmlVenta+=this.detallesVentaAjustadosToHtml();
		cadHtmlVenta+="</table>\n";
		cadHtmlVenta+="</div>\n";
		return cadHtmlVenta;
	}
	private String marcaDeAguaToHtml(String urlImagen,String idImagen, int ancho,int alto){
		String cadMarcaDeAgua="";
		cadMarcaDeAgua+="<img id=\""+idImagen+"\" src=\""+
		urlImagen+"\" " +
		"width=\""+ancho+ "\" " +
		"height=\""+alto+ "\"/>\n";
		return cadMarcaDeAgua;
	}
	public String toHtmlAjustadoConMarcaDeAgua(String urlImagen,String idImagen){
		String cadHtmlVenta="";
		cadHtmlVenta+="<div>\n";
		cadHtmlVenta+=this.marcaDeAguaToHtml(urlImagen, idImagen);		
		cadHtmlVenta+="<table border=\"1\">\n";
		cadHtmlVenta+=this.encabezadosVentaAjustadosToHtml();
		cadHtmlVenta+=this.detallesVentaAjustadosToHtml();
		cadHtmlVenta+="</table>\n";
		cadHtmlVenta+="</div>\n";
		return cadHtmlVenta;
	}
	private String marcaDeAguaToHtml(String urlImagen,String idImagen){
		String cadMarcaDeAgua="";
		cadMarcaDeAgua+="<img id=\""+idImagen+"\" src=\""+
		urlImagen+
		"\"/>\n";
		return cadMarcaDeAgua;
	}


}
