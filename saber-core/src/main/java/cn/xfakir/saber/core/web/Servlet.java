package cn.xfakir.saber.core.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface Servlet {
    void init();

    void service(ServletRequest request, ServletResponse response);

}
