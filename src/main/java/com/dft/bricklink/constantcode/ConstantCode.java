package com.dft.bricklink.constantcode;

public interface ConstantCode {
    String API_BASE_URL = "https://api.bricklink.com/api/store/v1";
    String CATALOG_ITEM = "/items/";
    String CATALOG_ITEM_IMAGE = "/images/";
    String CATELOG_ITEM_SUBSET = "/subsets";
    String CATELOG_ITEM_SUPERSET = "/supersets";
    String CATELOG_ITEM_PRICE = "/price";
    String CATELOG_ITEM_COLOR = "/colors";

    String ORDER = "/orders";
    String ORDER_ITEM = "/items";

    String INVENTORY = "/inventories";

    String CATEGORY = "/categories";

    String COLOR = "/colors";

    String ITEM_MAPPING = "/item_mapping";


    int MAX_ATTEMPTS = 50;
    int TIME_OUT_DURATION = 1000;
}
