package com.alazraq.alkhayat.goldenbeach.lists_items;

public class Items_of_base_chatting {

    public String sender,message,receiver,read;

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public Items_of_base_chatting(String message, String sender, String receiver, String read) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.read=read;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Items_of_base_chatting(){

    }

    public String getReceiver() {
        return receiver;
    }

}
