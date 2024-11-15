package br.com.managerfinances.api.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super (message);
    }
}
