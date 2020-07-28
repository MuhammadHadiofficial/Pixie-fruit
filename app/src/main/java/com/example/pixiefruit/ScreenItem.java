package com.example.pixiefruit;

public class ScreenItem {
    String Title,Description;
    int screenImg;

    public ScreenItem(String title, String description, int screenImg) {
        this.Title = title;
        this.Description = description;
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        System.out.println(screenImg+"img");
        return screenImg;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }
}
