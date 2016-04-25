package com.goodsnextdoor.pooja.goodsnextdoor.com.goodsnextdoor.pooja.goodsnextdoor.uadomain;


import android.os.Message;

import com.urbanairship.api.push.model.notification.Notification;

import java.util.Arrays;
import java.util.List;

/**
 * push request for ua v3 api.
 *
 * @author fwu
 *
 */
public class PushRequest {
    private Audience audience;
    private List<String> deviceTypes = Arrays.asList(new String[]{"android","ios"});
    private Notification notification;
    private Message message;

    /**
     * @return the audience
     */
    public Audience getAudience() {
        return audience;
    }
    /**
     * @param audience the audience to set
     */
    public void setAudience(Audience audience) {
        this.audience = audience;
    }
    /**
     * @return the deviceTypes
     */

    /**
     * @return the notification
     */
    public Notification getNotification() {
        return notification;
    }
    /**
     * @param notification the notification to set
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    public Message getMessage() {
        return message;
    }
    public void setMessage(Message message) {
        this.message = message;
    }
    public List<String> getDeviceTypes() {
        return deviceTypes;
    }
    public void setDeviceTypes(List<String> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }


}
