package cn.xfakir.saber.core.avalon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AvalonServlet {
    void init();

    void service(HttpServletRequest request, HttpServletResponse response);

    ServletContext getServletContext();

}
