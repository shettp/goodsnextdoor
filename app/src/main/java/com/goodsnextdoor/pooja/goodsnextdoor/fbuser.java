package com.goodsnextdoor.pooja.goodsnextdoor;

/**
 * Created by Rath on 11/2/2015.
 */
public class fbuser {

    @com.google.gson.annotations.SerializedName("userid")
    private String muid;
    @com.google.gson.annotations.SerializedName("channelid")
    private String mchannel;

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
    public fbuser() {
        memail="";
        mId="";
        mname="";
        muid="";
        mImageUri = "";
        mchannel="";
    }





    public fbuser(String uid, String name, String email,String iid,
                String imageUri, String channel) {
        this.setuid(uid);
        this.setid(iid);
        this.setemail(email);
        this.setname(name);
        this.setImageUri(imageUri);
        this.setchanelid(channel);
    }

    /**
     * Returns the item text
     */
    public String getchannelid()
    {
        return mchannel;
    }
    public final void  setchanelid(String id)
    {
        mchannel=id;
    }
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



    public boolean equals(Object o) {
        return o instanceof user && ((fbuser) o).muid == muid;

    }
}
