package cn.xfakir.saber.core.exception;

public class SaberException extends RuntimeException{

    public SaberException() {
        super();
    }

    public SaberException(Throwable cause) {
        super(cause);
    }

    public SaberException(String message) {
        super(message);
    }

    public SaberException(String message, Throwable cause) {
        super(message, cause);
    }
}
