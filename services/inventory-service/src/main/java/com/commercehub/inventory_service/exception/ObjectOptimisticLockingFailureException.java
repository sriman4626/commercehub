package com.commercehub.inventory_service.exception;

public class ObjectOptimisticLockingFailureException extends RuntimeException{

    public  ObjectOptimisticLockingFailureException(String message){
        super(message);
    }
}
