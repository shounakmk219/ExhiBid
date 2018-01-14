package com.example.admin.miniproject;

/**
 * Created by shounak on 6/10/17.
 */

public class My_UploadsInfo {
    private String UploadArt,UploadPrice,UploadStatus;

    My_UploadsInfo(String UploadArt,String UploadPrice,String UploadStatus)
    {
        this.UploadArt=UploadArt;
        this.UploadPrice=UploadPrice;
        this.UploadStatus=UploadStatus;
    }

    public String getUploadArt() {
        return UploadArt;
    }

    public void setUploadArt(String uploadArt) {
        UploadArt = uploadArt;
    }

    public String getUploadPrice() {
        return UploadPrice;
    }

    public void setUploadPrice(String uploadPrice) {
        UploadPrice = uploadPrice;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
    }
}
