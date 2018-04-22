
package com.example.flickerimages.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("owner")
    @Expose
    public String owner;
    @SerializedName("secret")
    @Expose
    public String secret;
    @SerializedName("server")
    @Expose
    public String server;
    @SerializedName("farm")
    @Expose
    public int farm;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("ispublic")
    @Expose
    public int ispublic;
    @SerializedName("isfriend")
    @Expose
    public int isfriend;
    @SerializedName("isfamily")
    @Expose
    public int isfamily;

    public String getUrl(){
        return String.format("https://farm%d.staticflickr.com/%s/%s_%s.jpg", farm, server,id, secret);
    }

}
