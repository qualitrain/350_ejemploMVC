<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
	  <meta http-equiv="Pragma" content="no-cache">
	  <meta http-equiv="Expires" content="0">	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans:ital,wght@0,200;1,600&display=swap" rel="stylesheet">
  	<link rel="stylesheet" href="../css/estiloFormularioCaptura.css" type="text/css" />
  	<title>Abc de artículos (JSP)</title>
  	<script type="text/javascript">
		function asignarActionAForm()
		{
			document.getElementById("idDeseaRegresar").value='SI';
		}
  	</script>
</head>

<body>
   <h2>Alta de artículos</h2>
   <form name="f_menu" action="../SJSP_RuteadorServlet" method="get"> 
        <label for="idCveArticulo">Clave:</label>  
        <input type="text" id="idCveArticulo" name="fmCveArticulo" 
               value="${articulo.cveArticulo}">               
		<span class="mensajeError">${tablaErrores["fmCveArticulo"]}</span>
		<br>
        <label for="idDescripcion">Descripci&oacute;n:</label>
        <input type="text" id="idDescripcion" name="fmDescripcion" 
               value="${articulo.descripcion}">
        <span class="mensajeError">${tablaErrores["fmDescripcion"]}</span>
        <br> 
        <label for="idCostoProv1">Costo Prov 1:</label>
        <input type="text" id="idCostoProv1" name="fmCostoProv1" 
               value="${articulo.costoProv1}">
        <span class="mensajeError">${tablaErrores["fmCostoProv1"]}</span>
        <br>
        <label for="idPrecioLista">Precio de Lista:</label>
        <input type="text" id="idPrecioLista" name="fmPrecioLista" 
               size="15" value="${articulo.precioLista}">
        <span class="mensajeError">${tablaErrores["fmPrecioLista"]}</span>
        <br>
        <input type="hidden" name="fmIdVista" value="AltaArticulo">
        <input type="hidden" id="idDeseaRegresar"  name="fmDeseaRegresar" value="NO">
        <div class="divBotones">
		    <input type="submit" value="Dar de Alta">
		    <input type="submit" value="Regresar" 
		           onclick="asignarActionAForm()" >
        </div>
   </form> 
   <p class="mensajeError">${hayErrores}</p>
   <p class="mensajeError">${tablaErrores["BD"]}</p>
</body>
</html>