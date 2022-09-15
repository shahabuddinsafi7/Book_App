package af.shahabuddin.book;


import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import af.shahabuddin.book.database.Database;
import af.shahabuddin.book.database.FavoriteDatabase;

public class Search_Activity extends AppCompatActivity {
    Database myDbHelper;
    SQLiteDatabase db;
    private List<Product_List> product_lists;
    private RecyclerView recyclerView;
    Search_Adopter adapter;
    public static FavoriteDatabase favoriteDatabase;
    private int[] id;
    private String[] title;
    private String[] dari;
    private String[] arabic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView=findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        product_lists = new ArrayList<>();

        Load_Database();

        db = myDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from data_text2 where cat = "+2, null);

        int ii = 0;
        id = new int[c.getCount()];
        title = new String[c.getCount()];
        dari = new String[c.getCount()];
        arabic=new String[c.getCount()];
        if (c.moveToFirst()) {

            do {

                @SuppressLint("Range") int i_d = c.getInt(c.getColumnIndex("id"));
                id[ii] = i_d;
                @SuppressLint("Range") String ti_tle = c.getString(c.getColumnIndex("title"));
                title[ii] = ti_tle;
                @SuppressLint("Range") String arabic2 = c.getString(c.getColumnIndex("arabic"));
                arabic[ii] = arabic2;
                @SuppressLint("Range") String maintext = c.getString(c.getColumnIndex("dari"));
                dari[ii] = maintext;
                ii++;
            } while (c.moveToNext());

        }
        c.close();
        db.close();


        adapter = new Search_Adopter(new ArrayList<Product_List>());

        ArrayList<Product_List> mSports = new ArrayList<>();
        int[] ID = id;
        String[] Title = title;
        String[] MainText = dari;
        String[] Arabic=arabic;

        for (int i = 0; i < id.length; i++) {

            mSports.add(new Product_List( ID[i], Title[i],Arabic[i],MainText[i]));
        }
        adapter.addItems(mSports);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void Load_Database() throws Error {

        myDbHelper = new Database(Search_Activity.this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }


    }
}









