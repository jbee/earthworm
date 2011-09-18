package de.jbee.earthworm.v2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EarthwormServlet extends HttpServlet {

	String javascript = "<script type=\"text/javascript\">"
			+ "	function AddEventHandlersToInputs() {"
			+ "	var nodelist = document.getElementsByTagName(\"input\");"

			+ "	for (var i=0; i<nodelist.length; i++) {"
			+ "	if (nodelist[i].className == \"focussable\") {"
			+ "	nodelist[i].onfocus = focus;" + "	nodelist[i].onblur = blur;"
			+ "	}" + "	}" + "	}"

			+ "	function focus() {" + "	this.parentNode.className='focus';"
			+ "	}"

			+ "	function blur() {" + "	this.parentNode.className ='';" + "	}"

			+ "	</script>";

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Earthworm</title>");
		writer.println("<style type='text/css'>\ndiv.focus { background-color:red; }\n</style>");
		writer.println("</head>");
		writer.println("<script type='text/javascript'> function tabs() { if (history.length == 1) { alert('Neuer Tab'); } }</script>");
		writer.println(javascript);
		writer.println("<body onload='tabs(); AddEventHandlersToInputs();'>");
		writer.println("	<h1>Earthworm 0.1</h1>");
		appendRequestParameters(request, writer);
		writer.println("<a href='sub/bla'>link</a>");
		writer.println("REF:" + request.getHeader("Referer") + "<br>");
		writer.println("CON:" + request.getHeader("Connection") + "<br>");
		writer.println(request.getQueryString());
		writer.println(request.getContextPath() + "<br>");
		writer.println(request.getServletPath() + "<br>");
		writer.println(request.getRequestURI() + "<br>");
		writer.println("<form method='post'><div><input type='text' name='hallo' value='bla' class='focussable'/> </div></form>");
		writer.println("<body>");
		writer.println("</html>");
		writer.close();
	}

	private void appendRequestParameters(HttpServletRequest request,
			PrintWriter writer) {
		Map<?, ?> params = request.getParameterMap();
		Iterator<?> i = params.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String[] value = ((String[]) params.get(key));
			writer.println(key + " = " + toString(value, " "));
		}
		writer.println("<br>");
	}

	private String toString(String[] params, String delimiter) {
		StringBuilder b = new StringBuilder();
		for (String param : params) {
			b.append("'" + param + "'");
			b.append(delimiter);
		}
		b.setLength(b.length() - delimiter.length());
		return b.toString();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
