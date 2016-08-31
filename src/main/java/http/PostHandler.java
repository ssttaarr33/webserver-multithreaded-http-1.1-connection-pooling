/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import object.Light;
import object.State;
import util.Header;
import util.Status;
import util.Utils;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author adrian.stoicescu
 */
public class PostHandler {

    private static HashMap<String, Boolean> lights = new HashMap<>();
    public static final String VERSION = "HTTP/1.0";

    private ArrayList<String> headers = new ArrayList<>();

    private byte[] body;
    private Header header;
    private Status status;
    
    public static HashMap<String, Boolean> getLights() {
        return lights;
    }

    public static void setLights(HashMap<String, Boolean> aLights) {
        lights = aLights;
    }    

    public PostHandler(HttpRequest req) throws UnsupportedEncodingException {
        header = new Header();
        switch (req.getUri()) {
            case "/switchSingleLight":
                String responseString = Utils.parseQuery(Utils.parseFormData(req.getPostBody())).toJSONString();
                status = Status._200;
                header.addHeader("Content-type: application/json");
                this.addResponse(responseString);
                break;
            case "/turnLightsOff":
                status = Status._200;
                header.addHeader("Content-type: application/json");
                this.addResponse(turnLightsOff(req.getPostBody()));
                break;
            case "/turnLightsOn":
                status = Status._200;
                header.addHeader("Content-type: application/json");
                this.addResponse(turnLightsOn(req.getPostBody()));
                break;
            default:
                status = Status._404;
                this.addResponse(Status._404.toString());
                break;
        }
        headers = header.getHeaders();
    }

    private void addResponse(String response) {
        body = response.getBytes();
    }

    private String turnLightsOff(String body) throws UnsupportedEncodingException {
        JSONObject response = new JSONObject();
        JSONArray tasks = new JSONArray();
        JSONArray results = new JSONArray();
        String decodedString = Utils.decode(body);
        lights = Utils.parseLights(decodedString);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Future<State>> list = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : lights.entrySet()) {
            JSONObject obj = new JSONObject();
            Callable<State> light = new Light(entry.getKey(), entry.getValue(), 1);
            obj.put("Light found", ((Light) light).getLocation());
            obj.put("On", ((Light) light).getState());
            Future<State> future = executor.submit(light);
            list.add(future);
            tasks.add(obj);
        }
        JSONArray newStates = new JSONArray();
        for (Future<State> event : list) {
            try {
                JSONObject obj = new JSONObject();
                JSONObject state = new JSONObject();
                obj.put("task", new Date() + " - " + event.get().getMessage());
                state.put("Light", event.get().getField());
                state.put("On", event.get().getState());
                results.add(obj);
                newStates.add(state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException ex) {
                Logger.getLogger(Provider.Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        executor.shutdown();
        response.put("Lights", tasks);
        response.put("results", results);
        response.put("newStates", newStates);
        String responseString = response.toJSONString();
        return responseString;
    }

    private String turnLightsOn(String body) throws UnsupportedEncodingException {
        JSONObject response = new JSONObject();
        JSONArray tasks = new JSONArray();
        JSONArray results = new JSONArray();
        String decodedString = Utils.decode(body);
        lights = Utils.parseLights(decodedString);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Future<State>> list = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : lights.entrySet()) {
            JSONObject obj = new JSONObject();
            Callable<State> light = new Light(entry.getKey(), entry.getValue(), 2);
            obj.put("Light found", ((Light) light).getLocation());
            obj.put("On", ((Light) light).getState());
            Future<State> future = executor.submit(light);
            list.add(future);
            tasks.add(obj);
        }
        JSONArray newStates = new JSONArray();
        for (Future<State> event : list) {
            try {
                JSONObject obj = new JSONObject();
                JSONObject state = new JSONObject();
                obj.put("task", new Date() + " - " + event.get().getMessage());
                state.put("Light", event.get().getField());
                state.put("On", event.get().getState());
                results.add(obj);
                newStates.add(state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException ex) {
                Logger.getLogger(Provider.Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        executor.shutdown();
        response.put("Lights", tasks);
        response.put("results", results);
        response.put("newStates", newStates);
        String responseString = response.toJSONString();
        return responseString;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
