<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="../css/estiloFormularioCaptura.css" type="text/css" />
  <title>Operación exitosa</title>
</head>
<body>
<p class="mensajeExito">
${mensajeExito}
</p>
<form  action="../SJSP_RuteadorServlet"  method="get">
    <input type="hidden" name="fmIdVista" value="PaginaExito">
	<input type="submit" value="Regresar">
</form>
</body>
</html>