/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import util.Status;
import util.Utils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrian.stoicescu
 */
public class HttpResponse {
    
    private Status status;
    private List<String> headers = new ArrayList<>();
    private byte[] body;
    private String version;

    public ArrayList<String> getHeaders() {
        return (ArrayList<String>) headers;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void write(OutputStream os) throws IOException {
        DataOutputStream output = new DataOutputStream(os);
        output.writeBytes(this.version + " " + this.getStatus().toString() + "\r\n");
        for (String header : headers) {
            output.writeBytes(header + "\r\n");
        }
        output.writeBytes("\r\n");
        if (body != null) {
            output.write(body);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

//    public static HashMap<String, Boolean> lights = new HashMap<>();
//    public static final String VERSION = "HTTP/1.0";
//
//    List<String> headers = new ArrayList<>();
//
//    byte[] body;
//
//    public HttpResponse(HttpRequest req) throws IOException {
//
//        switch (req.method) {
//            case HEAD:
//                DefaultHandler handlerHead = new DefaultHandler(Status._200);
//                headers = handlerHead.headers;
//                break;
//            case POST:
//                PostHandler handlerPost = new PostHandler(req);
//                headers = handlerPost.headers;
//                body = handlerPost.body;
//                break;
//            case GET:
//                GetHandler handlerGet = new GetHandler(req);
//                headers = handlerGet.headers;
//                body = handlerGet.body;
//                break;
//            case UNRECOGNIZED:
//                DefaultHandler handlerUnrec = new DefaultHandler(Status._400);
//                headers = handlerUnrec.headers;
//                body = handlerUnrec.body;
//                break;
//            default:
//                DefaultHandler handlerDefault = new DefaultHandler(Status._501);
//                headers = handlerDefault.headers;
//                body = handlerDefault.body;
//                break;
//        }
//    }
//
//    public void write(OutputStream os) throws IOException {
//        DataOutputStream output = new DataOutputStream(os);
//        for (String header : headers) {
//            output.writeBytes(header + "\r\n");
//        }
//        output.writeBytes("\r\n");
//        if (body != null) {
//            output.write(body);
//        }
//        output.writeBytes("\r\n");
//        output.flush();
//    }
}
