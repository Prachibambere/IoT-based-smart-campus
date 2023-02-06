package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class timer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<h1>The Window Object</h1>\n");
      out.write("<h2>The setInterval() and clearInterval() Methods</h2>\n");
      out.write("\n");
      out.write("<p id=\"demo\"></p>\n");
      out.write("\n");
      out.write("<button onclick=\"myStop()\">Stop the time</button>\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("const myInterval = setInterval(myTimer, 1000);\n");
      out.write("var seconds=300;\n");
      out.write("var fiveMin=0;\n");
      out.write("function myTimer() {\n");
      out.write("    var timeleft =seconds -fiveMin; // let's say now is 01:30, then current seconds is 60+30 = 90. And 90%300 = 90, finally 300-90 = 210. That's the time left!\n");
      out.write(" \n");
      out.write("    fiveMin=fiveMin+1;\n");
      out.write("    var result = parseInt(timeleft / 60) + ':' + timeleft % 60; //formart seconds back into mm:ss \n");
      out.write("    document.getElementById('demo').innerHTML = result;\n");
      out.write("    if(timeleft<=0)\n");
      out.write("    {\n");
      out.write("         clearInterval(myInterval);\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("function myStop() {\n");
      out.write("  clearInterval(myInterval);\n");
      out.write("}\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
