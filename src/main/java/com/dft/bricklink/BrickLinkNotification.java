package com.dft.bricklink;

import com.dft.bricklink.model.AccessCredentials;
import com.dft.bricklink.model.common.Method;
import com.dft.bricklink.model.notification.NotificationList;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.dft.bricklink.constantcode.ConstantCode.NOTIFICATIONS;
import static com.dft.bricklink.model.handler.JsonBodyHandler.handleJsonBody;

public class BrickLinkNotification extends BrickLinkSDK {

    public BrickLinkNotification(AccessCredentials accessCredentials) {
        super(accessCredentials);
    }

    public NotificationList getNotifications() {
        String uri = NOTIFICATIONS;
        String jsonResponse = getJsonResponse(Method.GET, uri);

        NotificationList notificationList = handleJsonBody(jsonResponse, new TypeReference<>() {
        });

        return notificationList;
    }
}