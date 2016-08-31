/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author adrian.stoicescu
 */
public class Server {
    private static final int port = 8001;

    private static final int nThreads = 5;
    
    ExecutorService executor = Executors.newFixedThreadPool(nThreads);
    
    public static void main(String[] args) throws IOException {
        new Server().run();
    }
    
    public void run() throws IOException {
        ServerSocket server = new ServerSocket(port);        

        while(true) {
            final Socket client = server.accept();
            System.out.println("New connection: "+client);
            executor.submit(() -> {
                try {
                    new RequestHandler(client).handleConnection();
                } catch (IOException exception) {
                    System.err.println("Error when handling request: "+exception.getMessage());
                    exception.printStackTrace(System.err);
                } finally {
                    if (!client.isClosed())
                        client.close();
                }
                return null;
            });
        }
    }
    
    
}
