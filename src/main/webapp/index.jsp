<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ro">
<head>
  <meta charset="UTF-8">
  <title>Laborator 3 - Counter Servlet</title>
</head>
<body>
<h1>Contoare GET si POST</h1>
<p>Contoarele sunt separate si se pastreaza in sesiunea utilizatorului.</p>
<p>Valoarea noua = valoarea curenta + inc - dec. Un camp gol este considerat 0.</p>

<p><a href="CounterServlet?inc=1">CounterServlet GET (+1)</a></p>

<h2>GET</h2>
<form action="CounterServlet" method="GET">
  <p><label>inc: <input type="number" name="inc" value="1" step="1"></label></p>
  <p><label>dec: <input type="number" name="dec" value="0" step="1"></label></p>
  <button type="submit">CounterServlet GET</button>
</form>

<h2>POST</h2>
<form action="CounterServlet" method="POST">
  <p><label>inc: <input type="number" name="inc" value="1" step="1"></label></p>
  <p><label>dec: <input type="number" name="dec" value="0" step="1"></label></p>
  <button type="submit">CounterServlet POST</button>
</form>
</body>
</html>
