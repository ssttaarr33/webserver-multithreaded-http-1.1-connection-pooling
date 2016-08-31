/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import http.HttpRequest;
import http.HttpResponse;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian.stoicescu
 */
public class RequestHandler {
    private Socket socket;

    public RequestHandler(Socket socket) throws SocketException {
        socket.setSoTimeout(10000);
        socket.setKeepAlive(true);
        socket.setReuseAddress(true);
        this.socket = socket;        
    }
    
    public void handleConnection(){
        try {
            HttpRequest request = new HttpRequest(socket.getInputStream());
            HttpResponse response = RequestDispatch.dispatch(request);
            response.write(socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            System.out.println("Runtime Error: " + e);
        } catch (UnsupportedOperationException e) {
            try {
                System.out.println("Unsupported protocol found");
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
