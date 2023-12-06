package qa.com.tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends TestBase {
    TestBase testBase;
    String serviceUrl;
    String apiUrl;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    @BeforeMethod
    public void setUp() throws ClientProtocolException, IOException {
        testBase = new TestBase();
        serviceUrl = prop.getProperty("URL");
        apiUrl = prop.getProperty("serviceURL");
        //https://reqres.in/api/users

        url = serviceUrl + apiUrl;

    }


    @Test
    public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException{
        restClient = new RestClient();
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        //jackson API:
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("morpheus", "leader"); //expected users obejct

        //object to json file:
        mapper.writeValue(new File("src/main/java/com/qa/data/users.json"), users);

        //object to json in String
        String usersJsonString = mapper.writeValueAsString(users);
        System.out.println(usersJsonString);

        closeableHttpResponse = restClient.post(url, usersJsonString, headerMap); //post call
        //validate response from API
        //1. status code:
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);

        //2. json string:
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());

        JSONObject responseJson = new JSONObject();
        System.out.println("The response from API is: "+ responseString);

        // json to java object
        Users userResObj = mapper.readValue(responseString, Users.class); // actual users object
        System.out.println(userResObj);

        Assert.assertTrue(users.getName().equals(userResObj.getName()));

        Assert.assertTrue(users.getJob().equals(userResObj.getJob()));

        System.out.println(userResObj.getId());
        System.out.println(userResObj.getCreatedAt());








    }




}
