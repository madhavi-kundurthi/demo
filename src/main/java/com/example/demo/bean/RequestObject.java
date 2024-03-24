package com.example.demo.bean;

public class RequestObject {

    String signature;
    UsersBean params;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public UsersBean getParams() {
        return params;
    }

    public void setParams(UsersBean params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RequestObject{" +
                "signature='" + signature + '\'' +
                ", params=" + params +
                '}';
    }
}
