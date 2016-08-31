/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import util.Method;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import util.Utils;

/**
 *
 * @author adrian.stoicescu
 */
public class HttpRequest {

    private List<String> headers = new ArrayList<>();

    private Method method;

    private String uri;

    private String version;

    private String postBody;

    public HttpRequest(InputStream is) throws IOException, UnsupportedOperationException {

        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line = in.readLine();
        parseRequestLine(line);
        line = "";
        // look for post data
        int postDataI = -1;
        while ((line = in.readLine()) != null && (line.length() != 0)) {
            parseRequestHeader(line);
            if (line.contains("Content-Length:")) {
                postDataI = Integer.parseInt(line.substring(
                        line.indexOf("Content-Length:") + 16,
                        line.length()));
            }
        }

        // read the post data
        String postData = "";
        if (postDataI > 0) {
            char[] charArray = new char[postDataI];
            in.read(charArray, 0, postDataI);
            postData = new String(charArray);
        }
        if (!postData.isEmpty()) {
            postBody = postData;
        }
    }

    private void parseRequestLine(String str) {
//        System.out.println(str);
        String[] split = str.split("\\s+");
        if(str.contains("HTTP/")){
            version = split[2];            
        }
        if (split.length > 1 && !str.contains("HTTP/")) {
            throw new UnsupportedOperationException();
        }
        try {
            method = Method.valueOf(split[0]);
        } catch (Exception e) {
            method = Method.UNRECOGNIZED;
        }
        uri = split[1];
        version = split[2];
    }

    private void parseRequestHeader(String str) {
//        System.out.println(str);
        headers.add(str);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
}
