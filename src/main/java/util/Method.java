/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author adrian.stoicescu
 */
public enum Method {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    UNRECOGNIZED(null);

    private final String method;

    Method(String method) {
        this.method = method;
    }
}
