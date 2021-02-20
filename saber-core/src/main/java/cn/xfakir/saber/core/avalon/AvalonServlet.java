package cn.xfakir.saber.core.avalon;

public abstract class AvalonServlet {
    public void service(AvalonRequest request,AvalonResponse response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    protected abstract void doPost(AvalonRequest request, AvalonResponse response);

    protected abstract void doGet(AvalonRequest request, AvalonResponse response);
}
