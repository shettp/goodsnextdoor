package com.goodsnextdoor.pooja.goodsnextdoor;

/**
 * Created by Rath on 2/8/2016.
 */
public class requestitem {

       @com.google.gson.annotations.SerializedName("userid")
    private String muid;
    @com.google.gson.annotations.SerializedName("owneruserid")
    private String mouid;
    @com.google.gson.annotations.SerializedName("requestername")
    private String mreqname;
    @com.google.gson.annotations.SerializedName("position")
    private String mposition;
    @com.google.gson.annotations.SerializedName("status")
    private String mstatus;

    @com.google.gson.annotations.SerializedName("from")
    private String mfrom;
    @com.google.gson.annotations.SerializedName("to")
    private String mto;
    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("item")
    private String mitem;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("comments")
    private String mcomments;


    @com.google.gson.annotations.SerializedName("category")
    private String mcategory;

    @com.google.gson.annotations.SerializedName("description")
    private String mdescription;

    @com.google.gson.annotations.SerializedName("ownercomments")
    private String mocomments;

    @com.google.gson.annotations.SerializedName("type")
    private String mtype;

    /**
     * Indicates if the item is completed
     */
    //  @com.google.gson.annotations.SerializedName("complete")
    //private boolean mComplete;

    /**
     * ToDoItem constructor
     */
    public requestitem() {
        mitem="";
        mouid="";
        mId="";
        mcategory="";
        muid="";
        mdescription = "";
        mcomments="";
        mreqname="";
        mposition="";
        mstatus="";
        mfrom="";
        mto="";
        mocomments="";
        mtype="";
    }





    public requestitem(String uid,String ouid, String item, String description,String iid,
                String category,String comments,String reqname,String pos, String status,String ocomments,String type) {
        this.setuid(uid);
        this.setid(iid);
        this.setouid(ouid);
        this.setitem(item);
        this.setcategory(category);
        this.setdescription(description);
        this.setcomments(comments);
        this.setrequsetername(reqname);
        this.setposition(pos);
        this.setstatus(status);
        this.setocomments(ocomments);
        this.settype(type);
    }

    /**
     * Returns the item text
     */
    public String gettype()
    {
        return mtype;
    }
    public final void  settype(String type)
    {
        mtype=type;
    }
    public String getocomments()
    {
        return mocomments;
    }
    public final void  setocomments(String com)
    {
        mocomments=com;
    }
    public String getstatus()
    {
        return mstatus;
    }
    public final void  setstatus(String staus)
    {
        mstatus=staus;
    }
    public String getposition()
    {
        return mposition;
    }
    public final void  setposition(String pos)
    {
        mposition=pos;
    }
    public String getrequestername()
    {
        return mreqname;
    }
    public final void  setrequsetername(String nm)
    {
        mreqname=nm;
    }

    public String getId()
    {
        return mId;
    }
    public final void  setid(String id)
    {
        mId=id;
    }

    public String getcomments()
    {
        return mcomments;
    }
    public final void  setcomments(String com)
    {
        mcomments=com;
    }






    public final void setto(String to) {
        mto= to;
    }
    public  String getto()
    {
        return mto;
    }
    public final void setfrom(String from) {
        mfrom= from;
    }
    public  String getfrom()
    {
        return mfrom;
    }
    public final void setuid(String text) {
        muid= text;
    }
    public  String getuid()
    {
        return muid;
    }
    public final void setouid(String text) {
        mouid= text;
    }

    public  String getouid()
    {
        return mouid;
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

    /**
     * Indicates if the item is marked as completed
     */
    /**
     *  imageUri - points to location in storage where photo will go
     */


    /**
     * Returns the item ImageUri
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


}
