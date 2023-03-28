<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c"  
            uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="../css/estiloConsultaTabular.css" type="text/css" />
  <title>Consulta de todas las personas</title>
   
</head>
<body>
		<h3>Consulta de Personas</h3>
		<table>
		<thead>
			<tr>
				<th>#</th> <th>Id</th> <th>Nombre</th> <th>Dirección</th> <th>Fecha nacimiento</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="1"/>
			<c:forEach items="${listaPersonas}" var="persona">
			  <c:choose>
			     <c:when test="${i%2==0}">
			         <tr class="renglonPar"> </c:when>
			     <c:otherwise>
			         <tr> </c:otherwise>
			 </c:choose>
						<td class="numRenglon">${i}</td>
				  		<td>${persona.idPersona}</td>
				  		<td>${persona.nombre}</td>
				  		<td>${persona.direccion}</td>
				  		<td>${persona.fecNacDdMmAaaa}</td>
			    	</tr>
			  <c:set var="i" value="${i+1}"/>
      		</c:forEach>
		</tbody>
		</table>  

	<form  action="../SJSP_RuteadorServlet"  method="get">
    	<input type="hidden" name="fmIdVista" value="ConsultaPersonas">
		<input type="submit" value="Regresar">
	</form>
</body>
</html>