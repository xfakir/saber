package cn.xfakir.saber.core.avalon;


public interface ServletContext {
    void addServlet(String servletName, AvalonServlet servlet);

    <T extends AvalonServlet> T createServlet(Class<T> clazz);



    Object getAttribute(String attributeName);

    void setAttribute(String attributeName, Object object);

    void initServlet();

    AvalonServlet getServlet(String dispatchServlet);
}
