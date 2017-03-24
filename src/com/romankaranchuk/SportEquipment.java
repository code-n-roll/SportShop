package com.romankaranchuk;

/**
 * Created by roman on 24.3.17.
 */
public class SportEquipment {
//    private Category category;
    private String title;
    private int price;


    public SportEquipment(String title, int price){
//        this.category = category;
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString(){
        String[] data = {title, Integer.toString(price)};
        return String.format("%-20s%-20s", (Object[]) data);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
