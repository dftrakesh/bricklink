package com.dft.bricklink.auth;

import com.dft.bricklink.constantcode.OAuthConstant;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class BLAuthSigner {
    private static final String CHARSET = "UTF-8";
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String EMPTY_STRING = "";
    private static final String CARRIAGE_RETURN = "\r\n";

    private final String signMethod = "HMAC-SHA1";
    private final String version = "1.0";
    private final String consumerKey;
    private final String consumerSecret;
    private final Map<String, String> oauthParameters;
    private final Map<String, String> queryParameters;
    private final Timer timer;
    private String tokenValue;
    private String tokenSecret;
    private String url;
    private String verb;

    public BLAuthSigner(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.oauthParameters = new HashMap<>();
        this.queryParameters = new HashMap<>();
        this.timer = new Timer();
    }

    public void setToken(String tokenValue, String tokenSecret) {
        this.tokenValue = tokenValue;
        this.tokenSecret = tokenSecret;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public void addParameter(String key, String value) {
        queryParameters.put(key, value);
    }

    public Map<String, String> getFinalOAuthParams() throws Exception {
        String signature = computeSignature();

        Map<String, String> params = new HashMap<>();
        params.putAll(oauthParameters);
        params.put(OAuthConstant.SIGNATURE, signature);

        return params;
    }

    public String computeSignature() throws Exception {
        addOAuthParameter(OAuthConstant.VERSION, version);
        addOAuthParameter(OAuthConstant.TIMESTAMP, getTimestampInSeconds());
        addOAuthParameter(OAuthConstant.NONCE, getNonce());
        addOAuthParameter(OAuthConstant.TOKEN, tokenValue);
        addOAuthParameter(OAuthConstant.CONSUMER_KEY, consumerKey);
        addOAuthParameter(OAuthConstant.SIGN_METHOD, signMethod);

        String baseString = getBaseString();
        String keyString = OAuthEncoder.encode(consumerSecret) + '&' + OAuthEncoder.encode(tokenSecret);
        String signature = doSign(baseString, keyString);
        return signature;
    }

    private void addOAuthParameter(String key, String value) {
        oauthParameters.put(key, value);
    }

    private String getTimestampInSeconds() {
        Long ts = timer.getMilis();
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(ts));
    }

    private String getNonce() {
        Long ts = timer.getMilis();
        return String.valueOf(ts + Math.abs(timer.getRandomInteger()));
    }

    private String getBaseString() throws Exception {
        List<String> params = new ArrayList<>();

        for (Entry<String, String> entry : oauthParameters.entrySet()) {
            String param = OAuthEncoder.encode(entry.getKey())
                                       .concat("=")
                                       .concat(entry.getValue());
            params.add(param);
        }

        for (Entry<String, String> entry : queryParameters.entrySet()) {
            String param = OAuthEncoder.encode(entry.getKey())
                                       .concat("=")
                                       .concat(entry.getValue());
            params.add(param);
        }

        Collections.sort(params);

        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append('&').append(param);
        }

        String formUrlEncodedParams = OAuthEncoder.encode(builder.substring(1));
        String sanitizedURL = OAuthEncoder.encode(url.replaceAll("\\?.*", "")
                                          .replace("\\:\\d{4}", ""));

        return String.format("%s&%s&%s", verb, sanitizedURL, formUrlEncodedParams);
    }

    private String doSign(String toSign, String keyString) throws Exception {
        SecretKeySpec key = new SecretKeySpec((keyString).getBytes(CHARSET), HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        byte[] bytes = mac.doFinal(toSign.getBytes(CHARSET));
        return bytesToBase64String(bytes).replace(CARRIAGE_RETURN, EMPTY_STRING);
    }

    private String bytesToBase64String(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

    static class Timer {
        private final Random rand = new Random();

        Long getMilis() {
            return System.currentTimeMillis();
        }

        Integer getRandomInteger() {
            return rand.nextInt();
        }
    }

    static class OAuthEncoder {
        private static final Map<String, String> ENCODING_RULES;

        static {
            Map<String, String> rules = new HashMap<>();
            rules.put("*", "%2A");
            rules.put("+", "%20");
            rules.put("%7E", "~");
            ENCODING_RULES = Collections.unmodifiableMap(rules);
        }

        public static String encode(String plain) throws UnsupportedEncodingException {
            String encoded = URLEncoder.encode(plain, CHARSET);

            for (Entry<String, String> rule : ENCODING_RULES.entrySet()) {
                encoded = encoded.replaceAll(Pattern.quote(rule.getKey()), rule.getValue());
            }
            return encoded;
        }
    }
}
