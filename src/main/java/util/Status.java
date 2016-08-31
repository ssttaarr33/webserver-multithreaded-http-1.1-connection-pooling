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
public enum Status {

    _200("200 OK"),
    _400("400 Bad Request"),
    _404("404 Not Found"),
    _501("501 Not Implemented");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
