/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import util.Header;
import util.Status;
import java.util.ArrayList;

/**
 *
 * @author adrian.stoicescu
 */
public class DefaultHandler {

    private String version;

    private ArrayList<String> headers = new ArrayList<>();

    private byte[] body;
    private Header header;
    private Status status;
    
    public DefaultHandler(Status status) {
        header = new Header();
        this.status = status;
        this.headers = header.getHeaders();
        this.addResponse(status.toString());
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    
    private void addResponse(String response) {
        body = response.getBytes();        
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
