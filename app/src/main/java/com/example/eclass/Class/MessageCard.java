package com.example.eclass.Class;


/**
 * Created by Enjoy life on 2017/10/14.
 */

public class MessageCard {
    private String message;
    private int imageId;

    public MessageCard(String message,int imageId){
        this.message = message;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getMessage() {
        return message;
    }
}
