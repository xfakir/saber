package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.avalon.ServletContext;
import cn.xfakir.saber.core.exception.SaberException;
import cn.xfakir.saber.core.ioc.SaberContext;
import cn.xfakir.saber.core.mvc.DispatcherServlet;
import cn.xfakir.saber.core.util.BeanUtil;

public class SaberServletInitializer implements SaberInitializer{

    private static final String DISPATHCER_SERVLET = "cn.xfakir.saber.core.mvc.DispatcherServlet";

    @Override
    public void onStartup(ServletContext servletContext) {
        try {
            DispatcherServlet dispatcherServlet = (DispatcherServlet) BeanUtil.instantiateClass(Class.forName(this.DISPATHCER_SERVLET));
            dispatcherServlet.setServletContext(servletContext);
            servletContext.addServlet("dispatcherServlet",dispatcherServlet);
            SaberContext saberContext = (SaberContext) servletContext.getAttribute("saberContext");
            saberContext.registerSingleton("dispatchServlet",dispatcherServlet);
        } catch (ClassNotFoundException e) {
            throw new SaberException(e);
        }
    }
}
