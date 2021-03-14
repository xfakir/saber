package cn.xfakir.saber.core.avalon;


public interface AvalonServlet {
    void init();

    void service(HttpRequest request, HttpResponse response);

    ServletContext getServletContext();

}
