package com.goodsnextdoor.pooja.goodsnextdoor;

/**
 * Created by Rath on 11/2/2015.
 */
public class item {

    @com.google.gson.annotations.SerializedName("userid")
    private String muid;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("item")
    private String mitem;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;


    @com.google.gson.annotations.SerializedName("category")
    private String mcategory;

    @com.google.gson.annotations.SerializedName("description")
    private String mdescription;

    @com.google.gson.annotations.SerializedName("latitude")
    private double mlatitude;

    @com.google.gson.annotations.SerializedName("longitude")
    private double mlongitude;

    @com.google.gson.annotations.SerializedName("city")
    private String mcity;

    @com.google.gson.annotations.SerializedName("state")
    private String mstate;

    @com.google.gson.annotations.SerializedName("country")
    private String mcountry;

    /**
     * Indicates if the item is completed
     */
    //  @com.google.gson.annotations.SerializedName("complete")
    //private boolean mComplete;

    /**
     * ToDoItem constructor
     */
    public item() {
        mitem="";
        mId="";
        mcategory="";
        muid="";
        mdescription = "";
        mlatitude = 0.0;
        mlongitude = 0.0;
        mcity = "";
        mstate="";
        mcountry="";
    }





    public item(String uid, String item, String description,String iid,
                String category ,
                double lat,
                double longitude,
                String city,
                String state,String country) {
        this.setuid(uid);
        this.setid(iid);
        this.setitem(item);
        this.setcategory(category);
        this.setdescription(description);
        this.setlatitude(lat);
        this.setlongitude(longitude);
        this.setcity(city);
        this.setstate(state);
        this.setcountry(country);
    }

    /**
     * Returns the item text
     */
    public String getId()
    {
        return mId;
    }
    public final void  setid(String id)
    {
        mId=id;
    }

    public String getstate()
    {
        return mstate;
    }
    public final void  setstate(String state)
    {
        mstate=state;
    }

    public String getcountry()
    {
        return mcountry;
    }
    public final void  setcountry(String country)
    {
        mcountry=country;
    }
    public String getuid() {
        return muid;
    }




    /**
     * Sets the item text
     *
     * @param text
     *            text to set
     */
    public final void setuid(String text) {
        muid= text;
    }

    /**
     * Returns the item id
     */
    public String getname() {
        return mitem;

    }
    public final void setitem(String item) {
        mitem = item;
    }
    public  String getcity()
    {
        return mcity;
    }
    public final void setcity(String city)
    {
        mcity=city;
    }
    /**
     * Indicates if the item is marked as completed
     */
    /**
     *  imageUri - points to location in storage where photo will go
     */


    /**
     * Returns the item ImageUri
     */
    public double getlatitude() {
        return mlatitude;
    }


    public final void setlatitude(double lat) {
        mlatitude = lat;
    }

    /**
     * ContainerName - like a directory, holds blobs
     */


    /**
     * Returns the item ContainerName
     */
    public String getcategory() {
        return mcategory;
    }

    /**
     * Sets the item ContainerName
     *
     */
    public final void setcategory(String category) {
        mcategory= category;
    }

    /**
     *  ResourceName
     */

    /**
     * Returns the item ResourceName
     */
    public String getdescription() {
        return mdescription;
    }


    public final void setdescription(String description) {
        mdescription = description;
    }

    /**
     *  SasQueryString - permission to write to storage
     */


    /**
     * Returns the item SasQueryString
     */
    public double getlongitude() {
        return mlongitude;
    }


    public final void setlongitude(double longitude) {
        mlongitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof user && ((item) o).muid == muid;

    }
}