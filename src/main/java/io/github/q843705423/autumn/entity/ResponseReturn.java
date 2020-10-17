package io.github.q843705423.autumn.entity;

public class ResponseReturn {
    private boolean isReturn;
    private Object returnValue;


    public static ResponseReturn noReturn() {
        ResponseReturn responseReturn = new ResponseReturn();
        responseReturn.setReturn(false);
        return responseReturn;
    }

    public static ResponseReturn toReturn(Object object) {
        ResponseReturn responseReturn = new ResponseReturn();
        responseReturn.setReturn(true);
        responseReturn.setReturnValue(object);
        return responseReturn;

    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }
}
