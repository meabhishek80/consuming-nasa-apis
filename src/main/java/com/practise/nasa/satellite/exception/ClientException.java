package com.practise.nasa.satellite.exception;

public class ClientException extends BusinessException {

    public ClientException() {
        super();
        
    }

    public ClientException(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
        
    }

    public ClientException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
        
    }

    public ClientException(String paramString) {
        super(paramString);
        
    }

    public ClientException(Throwable paramThrowable) {
        super(paramThrowable);
        
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5978888355474202897L;

}
