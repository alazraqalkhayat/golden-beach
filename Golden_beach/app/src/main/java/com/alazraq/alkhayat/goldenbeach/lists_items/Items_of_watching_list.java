package com.alazraq.alkhayat.goldenbeach.lists_items;

public class Items_of_watching_list {

    private String name;
    private int session;

    public Items_of_watching_list(String name, int session) {
        this.name = name;
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public int getSession() {
        return session;
    }
}
