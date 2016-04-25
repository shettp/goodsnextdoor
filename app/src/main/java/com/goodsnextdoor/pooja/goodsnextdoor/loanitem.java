package com.goodsnextdoor.pooja.goodsnextdoor;

/**
 * Created by Rath on 11/2/2015.
 */
public class loanitem {

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
    public loanitem() {
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
        mContainerName = "";
        mResourceName = "";
        mImageUri = "";
        mSasQueryString = "";
    }





    public loanitem(String uid, String item, String description,String iid,
                String category ,
                double lat,
                double longitude,
                String city,
                String state,String country,String containerName,
                String resourceName,
                String imageUri,
                String sasQueryString) {
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
        this.setContainerName(containerName);
        this.setResourceName(resourceName);
        this.setImageUri(imageUri);
        this.setSasQueryString(sasQueryString);
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


    @com.google.gson.annotations.SerializedName("imageUri")
    private String mImageUri;

    /**
     * Returns the item ImageUri
     */
    public String getImageUri() {
        return mImageUri;
    }

    /**
     * Sets the item ImageUri
     *
     * @param ImageUri
     *            Uri to set
     */
    public final void setImageUri(String ImageUri) {
        mImageUri = ImageUri;
    }

    /**
     * ContainerName - like a directory, holds blobs
     */
    @com.google.gson.annotations.SerializedName("containerName")
    private String mContainerName;

    /**
     * Returns the item ContainerName
     */
    public String getContainerName() {
        return mContainerName;
    }

    /**
     * Sets the item ContainerName
     *
     * @param ContainerName
     *            Uri to set
     */
    public final void setContainerName(String ContainerName) {
        mContainerName = ContainerName;
    }

    /**
     *  ResourceName
     */
    @com.google.gson.annotations.SerializedName("resourceName")
    private String mResourceName;

    /**
     * Returns the item ResourceName
     */
    public String getResourceName() {
        return mResourceName;
    }

    /**
     * Sets the item ResourceName
     *
     * @param ResourceName
     *            Uri to set
     */
    public final void setResourceName(String ResourceName) {
        mResourceName = ResourceName;
    }

    /**
     *  SasQueryString - permission to write to storage
     */
    @com.google.gson.annotations.SerializedName("sasQueryString")
    private String mSasQueryString;

    /**
     * Returns the item SasQueryString
     */
    public String getSasQueryString() {
        return mSasQueryString;
    }

    /**
     * Sets the item SasQueryString
     *
     * @param SasQueryString
     *            Uri to set
     */
    public final void setSasQueryString(String SasQueryString) {
        mSasQueryString = SasQueryString;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof user && ((loanitem) o).muid == muid;

    }
}
