
package com.example.flickerimages.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("photos")
    @Expose
    public Images photos;
    @SerializedName("stat")
    @Expose
    public String stat;

}
