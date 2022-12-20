package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.inventory.Inventory;
import com.dft.bricklink.model.inventory.InventoryList;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.INVENTORY;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkInventory extends BrickLinkSDK {

    public BrickLinkInventory(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public InventoryList getInventories() {
        String uri = INVENTORY;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        InventoryList inventoryList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return inventoryList;
    }

    public Inventory getInventory(Integer inventoryId) {
        String uri = INVENTORY + "/" + inventoryId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Inventory inventory = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return inventory;
    }

    public Inventory updateInventory(Inventory inventory) {
        Integer iInventory = inventory.getInventoryId();
        String uri = INVENTORY + "/" + iInventory;

        String jsonResponse = getJsonResponse(Method.PUT, uri, null, getString(inventory));

        return handleJsonBody(jsonResponse, new TypeReference<>() {
        });
    }
}