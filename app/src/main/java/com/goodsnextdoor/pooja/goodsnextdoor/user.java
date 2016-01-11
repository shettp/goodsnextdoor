package com.goodsnextdoor.pooja.goodsnextdoor;

/**
 * Created by Rath on 11/2/2015.
 */
public class user {

    @com.google.gson.annotations.SerializedName("userid")
    private String muid;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("name")
    private String mname;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;


    @com.google.gson.annotations.SerializedName("email")
    private String memail;

    /**
     * Indicates if the item is completed
     */
    //  @com.google.gson.annotations.SerializedName("complete")
    //private boolean mComplete;

    /**
     * ToDoItem constructor
     */
    public user() {
        memail="";
        mId="";
        mname="";
        muid="";
        mContainerName = "";
        mResourceName = "";
        mImageUri = "";
        mSasQueryString = "";
    }





    public user(String uid, String name, String email,String iid,
                String id,
                String containerName,
                String resourceName,
                String imageUri,
                String sasQueryString) {
        this.setuid(uid);
        this.setid(iid);
        this.setname(name);
        this.setuid(id);
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
        return mname;

    }
    public final void setname(String name) {
        mname = name;
    }
    public  String getemail()
    {
        return memail;
    }
    public final void setemail(String email)
    {
        memail=email;
    }
    /**
     * Indicates if the item is marked as completed
     */
    /**
     *  imageUri - points to location in storage where photo will go
     */
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
        return o instanceof user && ((user) o).muid == muid;

    }
}
