/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;

/**
 *
 * @author adrian.stoicescu
 */
public class Header {

    private ArrayList<String> headers;

    public Header() {
        headers = new ArrayList<>();
        this.addDefaultHeaders();
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public void addDefaultHeaders() {        
        headers.add("Connection: keep-alive");
        headers.add("Keep-Alive: timeout=10, max=5");
        headers.add("Server: wsLow");
    }

    public void addHeader(String header) {
        this.headers.add(header);
    }

}
