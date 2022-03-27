package com.alazraq.alkhayat.goldenbeach.lists_items;

public class Items_of_all_list {

    public String name_of_section, brand_of_section, name_of_category, name, name_of_image, story, trailer;
    public int id, session, year;

    public Items_of_all_list(String name_of_section, String brand_of_section, String name_of_category, String name, String name_of_image, String story, String trailer, int id, int session, int year) {
        this.name_of_section = name_of_section;
        this.brand_of_section = brand_of_section;
        this.name_of_category = name_of_category;
        this.name = name;
        this.name_of_image = name_of_image;
        this.story = story;
        this.trailer = trailer;
        this.id = id;
        this.session = session;
        this.year = year;
    }

    public String getName_of_section() {
        return name_of_section;
    }

    public void setName_of_section(String name_of_section) {
        this.name_of_section = name_of_section;
    }

    public String getBrand_of_section() {
        return brand_of_section;
    }

    public void setBrand_of_section(String brand_of_section) {
        this.brand_of_section = brand_of_section;
    }

    public String getName_of_category() {
        return name_of_category;
    }

    public void setName_of_category(String name_of_category) {
        this.name_of_category = name_of_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_of_image() {
        return name_of_image;
    }

    public void setName_of_image(String name_of_image) {
        this.name_of_image = name_of_image;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
