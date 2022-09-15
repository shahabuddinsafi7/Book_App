package af.shahabuddin.book;

import java.util.Collection;

public class Product_List {
    private int id;
    private String title;
    private String main_text;
    private String arabic;

    public Product_List(int id, String title,String arabic,String main_text) {
        this.id = id;
        this.title = title;
        this.main_text = main_text;
        this.arabic = arabic;
    }

    public  int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getMain_text() {
        return main_text;
    }
    public String getArabi(){
        return arabic;
    }
}
