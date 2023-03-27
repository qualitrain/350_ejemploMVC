<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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