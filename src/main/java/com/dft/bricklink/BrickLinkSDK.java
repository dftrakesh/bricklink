package com.dft.bricklink;

import com.dft.bricklink.auth.BLAuthSigner;
import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import static com.dft.bricklink.constantcode.ConstantCode.API_BASE_URL;

@Log4j2
public class BrickLinkSDK {

    private final String consumerKey;
    private final String consumerSecret;
    private final String tokenValue;
    private final String tokenSecret;
    private final HttpClient client;

    public BrickLinkSDK(AccessCredentials accessCredentials) {
        this.consumerKey = accessCredentials.getConsumerKey();
        this.consumerSecret = accessCredentials.getConsumerSecret();
        this.tokenValue = accessCredentials.getTokenValue();
        this.tokenSecret = accessCredentials.getTokenSecret();
        this.client = HttpClient.newHttpClient();
    }

    public String getJsonResponse(Method method, String uri) {
        return getJsonResponse(method, uri, null);
    }

    public String getJsonResponse(Method method, String uri, Map<String, String> parameters) {
        String baseUrl = API_BASE_URL + uri;

        if (parameters != null) {
            StringBuilder params = new StringBuilder();
            parameters.forEach((key, value) -> params.append("&" + key + "=" + value));
            baseUrl = baseUrl + "?" + params.substring(1);
        }

        String encodedUrl = buildUrl(method, baseUrl, parameters);
        String jsonResponse = null;
        try {
            jsonResponse = sendRequest(method, encodedUrl);
        } catch (Exception exception) {
            log.error("Exception while callRestService method. Error Message: {}", exception.getMessage(), exception);
        }
        return jsonResponse;
    }

    private String sendRequest(Method method, String url){
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .method(method.name(), HttpRequest.BodyPublishers.noBody())
                                         .build();

        String responseBody = null;
        try {
            responseBody = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                                 .body();
        } catch (Exception exception) {
            log.error("Exception while send API request. Error Message: {}", exception.getMessage(), exception);
        }
        return responseBody;
    }

    private Map<String, String> encodeParameters(Method method, String url, Map<String, String> parameters) {

        BLAuthSigner signer = new BLAuthSigner(consumerKey, consumerSecret);
        signer.setToken(tokenValue, tokenSecret);
        signer.setVerb(method.name());
        signer.setURL(url);
        if (parameters != null) {
            parameters.forEach(signer::addParameter);
        }

        Map<String, String> finalOAuthParams = null;
        try {
            finalOAuthParams = signer.getFinalOAuthParams();
        } catch (Exception exception) {
            log.error("Exception while get finalOAuthParams. Error Message: {}", exception.getMessage(), exception);
        }
        return finalOAuthParams;
    }

    private String buildUrl(Method method, String url, Map<String, String> parameters) {
        Map<String, String> fullParameters = encodeParameters(method, url, parameters);

        String finalUrl = null;
        String parameterString;
        ObjectMapper mapper = new ObjectMapper();
        try {
            parameterString = mapper.writeValueAsString(fullParameters);
            if (url.contains("?")) {
                finalUrl = url + "&Authorization=" + URLEncoder.encode(parameterString, StandardCharsets.UTF_8);
            } else {
                finalUrl = url + "?Authorization=" + URLEncoder.encode(parameterString, StandardCharsets.UTF_8);
            }
        } catch (Exception exception) {
            log.error("Exception while convert parameter to string in buildUrl method. Error Message: {}", exception.getMessage(), exception);
        }

        return finalUrl;
    }
}