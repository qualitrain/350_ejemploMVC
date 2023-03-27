<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="../css/estiloFormularioCaptura.css" type="text/css" />
  <title>Abc de artículos (JSP)</title>
<script language="javascript">
	function asignarActionAForm( str )
	{
		document.getElementById("idDeseaRegresar").value='SI';
	}
</script>

</head>
<body>
<h2>Alta de artículos</h2>
<form name="f_menu" action="../SJSP_RuteadorServlet" method="get"> 
Clave:  <input type="text" name="fmCveArticulo" size="20" value="${articulo.cveArticulo}">
		&nbsp<span class="mensajeError">${tablaErrores["fmCveArticulo"]}</span><br>
Descripción:<input type="text" name="fmDescripcion" size="40" value="${articulo.descripcion}">
        &nbsp<span class="mensajeError">${tablaErrores["fmDescripcion"]}</span><br> 
Costo Prov 1:<input type="text" name="fmCostoProv1" size="15" value="${articulo.costoProv1}">
        &nbsp<span class="mensajeError">${tablaErrores["fmCostoProv1"]}</span><br>
Precio de Lista:<input type="text" name="fmPrecioLista" size="15" value="${articulo.precioLista}">
        &nbsp<span class="mensajeError">${tablaErrores["fmPrecioLista"]}</span><br>
        <input type="hidden" name="fmIdVista" value="AltaArticulo">
        <input type="hidden" id="idDeseaRegresar"  name="fmDeseaRegresar" value="NO">
	<input type="submit" value="Dar de Alta"><input type="submit" value="Regresar" 
	       onclick="asignarActionAForm('salirDeAlta')" >
</form> 
<p class="mensajeError">${hayErrores}</p>
<p class="mensajeError">${tablaErrores["BD"]}</p>
</body>
</html>