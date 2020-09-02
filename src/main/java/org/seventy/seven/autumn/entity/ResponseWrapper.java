package org.seventy.seven.autumn.entity;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper {
    private ResponseEntity<String> responseEntity;
    private List<Exception> list;

    public ResponseWrapper(Exception e) {
        this.list = new ArrayList<>();
        list.add(e);
    }

    public boolean hasError() {
        return !(this.list == null || this.list.isEmpty());
    }

    public ResponseWrapper(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;

    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public List<Exception> getList() {
        return list;
    }

    public void setList(List<Exception> list) {
        this.list = list;
    }

    public Exception error() {
        return list.get(0);
    }
}
