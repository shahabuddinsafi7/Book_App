package af.shahabuddin.book.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="favoritelist")
public class FavoriteList {

    @PrimaryKey
    private int id;

  @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "dari")
    private String dari;

    @ColumnInfo(name = "arabic")
    private String arabic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDari() {
        return dari;
    }
    public void setDari(String dari) {
        this.dari = dari;
    }

    public String getArabic() {
        return arabic;
    }
    public void setArabic(String arabic) {
        this.arabic = arabic;
    }
}
