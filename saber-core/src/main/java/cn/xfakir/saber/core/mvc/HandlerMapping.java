package cn.xfakir.saber.core.mvc;

import cn.xfakir.saber.core.avalon.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpRequest request);
}
