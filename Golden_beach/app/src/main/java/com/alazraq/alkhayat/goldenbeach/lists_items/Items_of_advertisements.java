package com.alazraq.alkhayat.goldenbeach.lists_items;

public class Items_of_advertisements {

    int add_id;
    String name_of_image_of_add, description,date_of_add,name_of_owner;

    public Items_of_advertisements(int add_id, String name_of_image_of_add, String description, String date_of_add, String name_of_owner) {
        this.add_id = add_id;
        this.name_of_image_of_add = name_of_image_of_add;
        this.description = description;
        this.date_of_add = date_of_add;
        this.name_of_owner = name_of_owner;
    }

    public int getAdd_id() {
        return add_id;
    }

    public String getName_of_image_of_add() {
        return name_of_image_of_add;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_of_add() {
        return date_of_add;
    }

    public String getName_of_owner() {
        return name_of_owner;
    }

}

