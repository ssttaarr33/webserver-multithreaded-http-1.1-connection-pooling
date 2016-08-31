/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import object.Light;
import util.Header;
import util.Status;
import util.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author adrian.stoicescu
 */
public class GetHandler {

    private static HashMap<String, Boolean> lights = new HashMap<>();
    private String version;
    private ArrayList<String> headers = new ArrayList<>();

    private byte[] body;
    private Header header;
    private Status status;    

    public GetHandler(HttpRequest req) {
        header = new Header();
        status = Status._200;
        this.version = req.getVersion();
        try {            
            File file;
            String root = "var/www/";
            switch (req.getUri()) {
                case "/":
                    file = new File(root + "index.html").getCanonicalFile();
                    fileMethod(file);
                    break;
                case "/lightsState":
                    header.addHeader("Content-type: application/json");
                    this.addResponse(getLightsState());
                    break;
                default:
                    file = new File(root + req.getUri()).getCanonicalFile();
                    fileMethod(file);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Response Error " + e);
            status = Status._400;
            this.addResponse(Status._400.toString());
        }
        headers = header.getHeaders();
    }

    private void fileMethod(File file) throws IOException {
        if (file.exists()) {
            String contentType = Utils.getHeaderForExt(Utils.getExtension(file.getPath()));
            if (contentType != null) {
                header.addHeader("Content-Type: " + contentType);
            }
            addResponse(getBytes(file));
        } else {            
            status = Status._404;
            this.addResponse(Status._404.toString());
        }
    }

    private void addResponse(String response) {
        body = response.getBytes();
    }

    private void addResponse(byte[] response) {
        body = response;
    }

    private byte[] getBytes(File file) throws IOException {
        int length = (int) file.length();
        byte[] array = new byte[length];
        InputStream in = new FileInputStream(file);
        int offset = 0;
        while (offset < length) {
            int count = in.read(array, offset, (length - offset));
            offset += count;
        }
        in.close();
        return array;
    }

    private String getLightsState() {
        JSONObject obj = new JSONObject();
        JSONArray tasks = new JSONArray();
        lights = Utils.populateLights();
        for (Map.Entry<String, Boolean> entry : lights.entrySet()) {
            JSONObject object = new JSONObject();
            Light light = new Light(entry.getKey(), entry.getValue(), 1);
            object.put("Light", ((Light) light).getLocation());
            object.put("On", ((Light) light).getState());
            tasks.add(object);
        }
        obj.put("Lights", tasks);
        String response = obj.toJSONString();
        return response;
    }
    
    public static HashMap<String, Boolean> getLights() {
        return lights;
    }

    public static void setLights(HashMap<String, Boolean> aLights) {
        lights = aLights;
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
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
