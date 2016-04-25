/**
 *
 */
package com.goodsnextdoor.pooja.goodsnextdoor.com.goodsnextdoor.pooja.goodsnextdoor.uadomain;
import java.util.List;

/**
 * @author fwu
 *
 */
public class Audience {

    private List<String> deviceToken;

    private List<String> apid;

    private List<String> ios_channel;

    private List<String> android_channel;

    /**
     * @return the deviceToken
     */
    public List<String> getDeviceToken() {
        return deviceToken;
    }

    /**
     * @param deviceToken the deviceToken to set
     */
    public void setDeviceToken(List<String> deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * @return the apid
     */
    public List<String> getApid() {
        return apid;
    }

    /**
     * @param apid the apid to set
     */
    public void setApid(List<String> apid) {
        this.apid = apid;
    }

    public List<String> getIos_channel() {
        return ios_channel;
    }

    public void setIos_channel(List<String> ios_channel) {
        this.ios_channel = ios_channel;
    }

    public List<String> getAndroid_channel() {
        return android_channel;
    }

    public void setAndroid_channel(List<String> android_channel) {
        this.android_channel = android_channel;
    }

}
