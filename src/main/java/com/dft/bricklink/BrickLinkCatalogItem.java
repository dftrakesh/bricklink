package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.catalogitem.ColorsList;
import com.dft.bricklink.model.catalogitem.Item;
import com.dft.bricklink.model.catalogitem.PriceGuide;
import com.dft.bricklink.model.catalogitem.SubsetEntryList;
import com.dft.bricklink.model.catalogitem.SupersetEntryList;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.common.Type;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j2;
import static com.dft.bricklink.constantcode.ConstantCode.CATALOG_ITEM;
import static com.dft.bricklink.constantcode.ConstantCode.CATALOG_ITEM_IMAGE;
import static com.dft.bricklink.constantcode.ConstantCode.CATELOG_ITEM_COLOR;
import static com.dft.bricklink.constantcode.ConstantCode.CATELOG_ITEM_PRICE;
import static com.dft.bricklink.constantcode.ConstantCode.CATELOG_ITEM_SUBSET;
import static com.dft.bricklink.constantcode.ConstantCode.CATELOG_ITEM_SUPERSET;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

@Log4j2
public class BrickLinkCatalogItem extends BrickLinkSDK {

    public BrickLinkCatalogItem(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public Item getItem(Type type, String itemId) {
        String uri = CATALOG_ITEM + type.name() + "/" + itemId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Item item = handleJsonBody(jsonResponse, new TypeReference<>() {});
        return item;
    }

    public Item getItemImage(Type type, String no, Integer colorId) {
        String uri = CATALOG_ITEM + type.name() + "/" + no + CATALOG_ITEM_IMAGE + colorId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Item itemImage = handleJsonBody(jsonResponse, new TypeReference<>() {
        });
        return itemImage;
    }

    public SubsetEntryList getSubsets(Type type, String no) {
        String uri = CATALOG_ITEM + type.name() + "/" + no + CATELOG_ITEM_SUBSET;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        SubsetEntryList subsetEntries = handleJsonBody(jsonResponse, new TypeReference<>() {
        });
        return subsetEntries;
    }

    public SupersetEntryList getSupersets(Type type, String no) {
        String uri = CATALOG_ITEM + type.name() + "/" + no + CATELOG_ITEM_SUPERSET;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        SupersetEntryList supersetEntries = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return supersetEntries;
    }

    public PriceGuide getPrice(Type type, String no) {
        String uri = CATALOG_ITEM + type.name() + "/" + no + CATELOG_ITEM_PRICE;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        PriceGuide priceGuide = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return priceGuide;
    }

    public ColorsList getKnownColors(Type type, String no) {
        String uri = CATALOG_ITEM + type.name() + "/" + no + CATELOG_ITEM_COLOR;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        ColorsList colorsList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return colorsList;
    }
}
