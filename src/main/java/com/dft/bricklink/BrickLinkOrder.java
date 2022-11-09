package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.order.Order;
import com.dft.bricklink.model.order.OrderItemList;
import com.dft.bricklink.model.order.OrderList;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.ORDER;
import static com.dft.bricklink.constantcode.ConstantCode.ORDER_ITEM;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkOrder extends BrickLinkSDK {

    public BrickLinkOrder(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public OrderList getOrders() {
        String uri = ORDER;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        OrderList orderList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return orderList;
    }

    public Order getOrder(Integer orderId) {
        String uri = ORDER + "/" + orderId;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        Order order = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return order;
    }

    public OrderItemList getOrderItems(Integer orderId) {
        String uri = ORDER + "/" + orderId + ORDER_ITEM;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        OrderItemList orderItemList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return orderItemList;
    }
}
