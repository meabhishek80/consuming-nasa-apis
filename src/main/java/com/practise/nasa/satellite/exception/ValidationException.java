package com.practise.nasa.satellite.exception;

public class ValidationException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 72527627404713079L;

    public ValidationException() {
        super();
        
    }

    public ValidationException(String paramString, Throwable paramThrowable, boolean paramBoolean1,
            boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
        
    }

    public ValidationException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
        
    }

    public ValidationException(String paramString) {
        super(paramString);
        
    }

    public ValidationException(Throwable paramThrowable) {
        super(paramThrowable);
        
    }

}
