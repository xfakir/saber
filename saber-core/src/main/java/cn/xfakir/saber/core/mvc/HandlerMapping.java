package cn.xfakir.saber.core.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request);
}
