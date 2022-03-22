package com.example.medicalreminder.screens.addmedfriend;

public class RequestModel {
    private String senderEmail;
    private String senderName ;
    private String reciverEmail ;
    private String reciverName ;
    private String status ;


    public RequestModel() {
    }
    public RequestModel(String sender, String senderName, String reciver, String reciverName) {
        this.senderEmail = sender;
        this.senderName = senderName;
        this.reciverEmail = reciver;
        this.reciverName = reciverName;

    }
    public RequestModel(String sender, String senderName, String reciver, String reciverName , String status) {
        this.senderEmail = sender;
        this.senderName = senderName;
        this.reciverEmail = reciver;
        this.reciverName = reciverName;
        this.status = status ;

    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReciverEmail() {
        return reciverEmail;
    }

    public void setReciverEmail(String reciverEmail) {
        this.reciverEmail = reciverEmail;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
