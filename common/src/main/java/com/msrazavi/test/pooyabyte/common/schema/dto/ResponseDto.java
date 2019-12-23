package com.msrazavi.test.pooyabyte.common.schema.dto;

import java.util.List;

public class ResponseDto<T> {
    private T response;
    private boolean hasError;
    private List<String> errors;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public static <T> ResponseDto<T> ofSuccess(T response) {
        ResponseDto<T> r = new ResponseDto<>();
        r.setResponse(response);
        r.setHasError(false);
        return r;
    }

    public static <T> ResponseDto<T> ofError(List<String> errors) {
        ResponseDto<T> r = new ResponseDto<>();
        r.setResponse(null);
        r.setHasError(true);
        r.setErrors(errors);
        return r;
    }
}
