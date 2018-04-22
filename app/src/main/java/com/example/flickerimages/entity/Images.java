
package com.example.flickerimages.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Images {

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("pages")
    @Expose
    public int pages;
    @SerializedName("perpage")
    @Expose
    public int perpage;
    @SerializedName("total")
    @Expose
    public String total;
    @SerializedName("photo")
    @Expose
    public List<Image> photo = new ArrayList<>();

}
