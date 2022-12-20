package com.dft.bricklink;

import com.dft.bricklink.auth.BLAuthSigner;
import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import static com.dft.bricklink.constantcode.ConstantCode.API_BASE_URL;
import static com.dft.bricklink.constantcode.ConstantCode.HTTP_HEADER_CONTENT_TYPE_VALUE;
import static com.dft.bricklink.constantcode.ConstantCode.HTTP_HEADER_KEY_CONTENT_TYPE;
import static com.dft.bricklink.constantcode.ConstantCode.MAX_ATTEMPTS;
import static com.dft.bricklink.constantcode.ConstantCode.TIME_OUT_DURATION;

@Log4j2
public class BrickLinkSDK {

    private final String consumerKey;
    private final String consumerSecret;
    private final String tokenValue;
    private final String tokenSecret;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public BrickLinkSDK(AccessCredentials accessCredentials) {
        this.consumerKey = accessCredentials.getConsumerKey();
        this.consumerSecret = accessCredentials.getConsumerSecret();
        this.tokenValue = accessCredentials.getTokenValue();
        this.tokenSecret = accessCredentials.getTokenSecret();
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String getJsonResponse(Method method, String uri) {
        return getJsonResponse(method, uri, null, null);
    }

    public String getJsonResponse(Method method, String uri, Map<String, String> parameters, String body) {
        String baseUrl = API_BASE_URL + uri;

        if (parameters != null) {
            StringBuilder params = new StringBuilder();
            parameters.forEach((key, value) -> params.append("&" + key + "=" + value));
            baseUrl = baseUrl + "?" + params.substring(1);
        }

        String encodedUrl = buildUrl(method, baseUrl, parameters);
        String jsonResponse = null;
        try {
            jsonResponse = sendRequest(method, encodedUrl, body);
        } catch (Exception exception) {
            log.error("Exception while callRestService method. Error Message: {}", exception.getMessage(), exception);
        }
        return jsonResponse;
    }

    private String sendRequest(Method method, String url, String body) {
        HttpRequest.BodyPublisher bodyPublisher = body == null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(body);

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .header(HTTP_HEADER_KEY_CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE_VALUE)
                                         .method(method.name(), bodyPublisher)
                                         .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers
                                                               .ofString(StandardCharsets.UTF_8);

        return getRequestWrapped(request, handler);
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

    @SneakyThrows
    private <T> T getRequestWrapped(HttpRequest request, HttpResponse.BodyHandler<T> handler) {

        return client.sendAsync(request, handler)
                     .thenComposeAsync(response -> tryResend(client, request, handler, response, 1))
                     .get()
                     .body();
    }

    @SneakyThrows
    private <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client,
                                                             HttpRequest request,
                                                             HttpResponse.BodyHandler<T> handler,
                                                             HttpResponse<T> resp,
                                                             int count) {

        if (resp.statusCode() == 503 && count < MAX_ATTEMPTS) {
            Thread.sleep(TIME_OUT_DURATION);
            return client.sendAsync(request, handler)
                         .thenComposeAsync(response -> tryResend(client, request, handler, response, count + 1));
        }
        return CompletableFuture.completedFuture(resp);
    }

    @SneakyThrows
    protected String getString(Object body) {
        return objectMapper.writeValueAsString(body);
    }
}
