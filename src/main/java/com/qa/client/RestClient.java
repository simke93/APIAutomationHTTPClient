package com.qa.client;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    // Get method without Headers:
    public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); //http get request
        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // hit the GET
        // URL

        return closeableHttpResponse;

    }

    // Get method with Headers:
    public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException,
            IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url); //http get request

        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // hit the GET
        // URL
        return closeableHttpResponse;

    }

    // POST method

    //3. POST Method:
    public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url); //http post request
        httppost.setEntity(new StringEntity(entityString)); //for payload

        //for headers:
        for(Map.Entry<String,String> entry : headerMap.entrySet()){
            httppost.addHeader(entry.getKey(), entry.getValue());
        }

        CloseableHttpResponse closebaleHttpResponse = httpClient.execute(httppost);
        return closebaleHttpResponse;


    }
}





