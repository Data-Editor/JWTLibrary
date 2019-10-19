package com.Niek125.jwtlibrary.AuthObjectMaker;

public interface IAuthObjectMaker<T> {
    T getAuthObject();

    void makeAuthObject(String payload);
}
