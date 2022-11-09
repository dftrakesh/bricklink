package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.common.Type;
import com.dft.bricklink.model.itemmapping.ItemMappingList;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.ITEM_MAPPING;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkItemMapping extends BrickLinkSDK {

    public BrickLinkItemMapping(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public ItemMappingList getElementId(Type type, String itemId) {
        String uri = ITEM_MAPPING + "/" + type.name() + "/" + itemId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        ItemMappingList itemMappingList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });
        return itemMappingList;
    }

    public ItemMappingList getItemNumber(String elementId) {
        String uri = ITEM_MAPPING + "/" + elementId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        ItemMappingList itemMappingList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });
        return itemMappingList;
    }
}
