/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import util.Status;
import http.DefaultHandler;
import http.GetHandler;
import http.HttpRequest;
import http.HttpResponse;
import http.PostHandler;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author adrian.stoicescu
 */
public class RequestDispatch {
    public static HttpResponse dispatch(HttpRequest request) throws UnsupportedEncodingException {
        HttpResponse response = new HttpResponse();
        switch (request.getMethod()) {
            case HEAD:
                DefaultHandler handlerHead = new DefaultHandler(Status._200);
                response.setHeaders(handlerHead.getHeaders());
                response.setBody("".getBytes());
                response.setStatus(handlerHead.getStatus());
                break;
            case POST:
                PostHandler handlerPost = new PostHandler(request);
                response.setHeaders(handlerPost.getHeaders());
                response.setBody(handlerPost.getBody());
                response.setStatus(handlerPost.getStatus());
                break;
            case GET:
                GetHandler handlerGet = new GetHandler(request);
                response.setHeaders(handlerGet.getHeaders());
                response.setBody(handlerGet.getBody());
                response.setStatus(handlerGet.getStatus());
                break;
            case UNRECOGNIZED:
                DefaultHandler handlerUnrec = new DefaultHandler(Status._400);
                response.setHeaders(handlerUnrec.getHeaders());
                response.setBody(handlerUnrec.getBody());
                response.setStatus(handlerUnrec.getStatus());
                break;
            default:
                DefaultHandler handlerDefault = new DefaultHandler(Status._501);
                response.setHeaders(handlerDefault.getHeaders());
                response.setBody(handlerDefault.getBody());
                response.setStatus(handlerDefault.getStatus());
                break;
        }
        
        return response;
    }
    
}
