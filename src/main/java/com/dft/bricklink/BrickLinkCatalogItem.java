package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.catalogitem.Item;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.common.Response;
import com.dft.bricklink.model.common.Type;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import static com.dft.bricklink.constantcode.ConstantCode.CATALOG_ITEM_API;

@Log4j2
public class BrickLinkCatalogItem extends BrickLinkSDK {

    public BrickLinkCatalogItem(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public Item getItem(Type type, String itemId) {

        String itemUrl = CATALOG_ITEM_API + type.name() + "/" + itemId;
        String responseBody = getJsonResponse(Method.GET, itemUrl);

        TypeReference<Response<Item>> reference = new TypeReference<>() {};
        ObjectMapper mapper = new ObjectMapper();
        Response<Item> response = null;
        try {
            response = mapper.readValue(responseBody, reference);
        } catch (Exception exception) {
            log.error("Exception while map json response to Catalog Item object. Error Message: {}", exception.getMessage(), exception);
        }
        return response.getData();
    }
}
