package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.color.Color;
import com.dft.bricklink.model.color.ColorsList;
import com.dft.bricklink.model.common.Method;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.COLOR;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkColor extends BrickLinkSDK {

    public BrickLinkColor(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public ColorsList getColorList() {
        String uri = COLOR;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        ColorsList colorsList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return colorsList;
    }

    public Color getColor(Integer colorId) {
        String uri = COLOR + "/" + colorId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Color color = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return color;
    }
}
