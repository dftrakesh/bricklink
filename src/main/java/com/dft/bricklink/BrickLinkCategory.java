package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.category.Category;
import com.dft.bricklink.model.category.CategoryList;
import com.dft.bricklink.model.common.Method;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.CATEGORY;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkCategory extends BrickLinkSDK {

    public BrickLinkCategory(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public CategoryList getCategoryList() {
        String uri = CATEGORY;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        CategoryList categoryList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return categoryList;
    }

    public Category getCategory(Integer categoryId) {
        String uri = CATEGORY + "/" + categoryId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Category category = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return category;
    }
}
