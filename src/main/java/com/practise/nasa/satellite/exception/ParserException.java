package com.practise.nasa.satellite.exception;

public class ParserException extends BusinessException {

    public ParserException() {
        super();
        
    }

    public ParserException(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
        
    }

    public ParserException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
        
    }

    public ParserException(String paramString) {
        super(paramString);
        
    }

    public ParserException(Throwable paramThrowable) {
        super(paramThrowable);
    }

    private static final long serialVersionUID = -5241208536812190116L;
}