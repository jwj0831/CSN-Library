package cir.lab.rest.common;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nfm on 2014. 6. 6..
 */
public class RESTMethodAPI {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Client client;
    private WebResource webResource;
    private ClientResponse response;
    private String output;
    private WebResource.Builder builder;


    public void setUpREST(String url, String input, HttpMethod method ) {
        client = Client.create();
        logger.info("Input URL: {}", url);
        webResource = client.resource(url);

        switch (method) {
            case POST:
            case PUT:
                logger.info("Input Data: {}", input);
                builder = webResource.accept(ServerConnInfo.APP_TYPE_JSON).type(ServerConnInfo.APP_TYPE_JSON);
                break;
            case GET:
            case DELETE:
                builder = webResource.accept(ServerConnInfo.APP_TYPE_JSON);
                break;
            default:
                break;
        }
    }

    public String tearDownREST(HttpMethod method) {
        switch (method) {
            case POST:
            case PUT:
                if (!(response.getStatus() == 200 || response.getStatus() == 201))
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                break;
            case GET:
            case DELETE:
                if (response.getStatus() != 200)
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                break;
            default:
                break;
        }

        output = response.getEntity(String.class);
        logger.info(output);
        return output;
    }

    public String postMethod(String url, String input) {
        setUpREST(url, input, HttpMethod.POST);

        response = builder.post(ClientResponse.class, input);

        return tearDownREST(HttpMethod.POST);
    }


    public String getMethod(String url) {
        setUpREST(url, null, HttpMethod.GET);

        response = builder.get(ClientResponse.class);

        return tearDownREST(HttpMethod.GET);
    }

    public String putMethod(String url, String input) {
        setUpREST(url, input, HttpMethod.PUT);

        response = builder.put(ClientResponse.class, input);

        return tearDownREST(HttpMethod.PUT);
    }

    public String deleteMethod(String url) {
        setUpREST(url, null, HttpMethod.DELETE);

        response = builder.delete(ClientResponse.class);

        return tearDownREST(HttpMethod.DELETE);
    }
}
