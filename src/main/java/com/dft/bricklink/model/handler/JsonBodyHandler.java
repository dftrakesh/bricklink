package com.dft.bricklink.model.handler;

import com.dft.bricklink.model.common.DataInterface;
import com.dft.bricklink.model.common.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonBodyHandler {

    public static <T extends DataInterface> T handleJsonBody(String responseBody,
                                                             TypeReference<Response<T>> reference) {
        ObjectMapper mapper = new ObjectMapper();
        Response<T> response = null;
        try {
            response = mapper.readValue(responseBody, reference);
        } catch (Exception exception) {
            log.error("Exception while map json body to object. Error Message: {}", exception.getMessage(), exception);
        }
        return response.getData();
    }
}