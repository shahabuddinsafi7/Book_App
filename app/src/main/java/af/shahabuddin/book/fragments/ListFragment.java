package af.shahabuddin.book.fragments;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import af.shahabuddin.book.ListAdopter;
import af.shahabuddin.book.Product_List;
import af.shahabuddin.book.R;
import af.shahabuddin.book.database.Database;
import af.shahabuddin.book.database.FavoriteDatabase;

public class ListFragment extends Fragment {
    MenuItem search;
    private List<Product_List> product_lists;
    private RecyclerView recyclerView;
    ListAdopter adapter;
    public static FavoriteDatabase favoriteDatabase;
    Database myDbHelper;
    SQLiteDatabase db;
    private int[] id;
    private String[] title;
    private String[] dari;
    private String[] arabic;
    private int cat=1;
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteDatabase = Room.databaseBuilder(getContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        product_lists = new ArrayList<>();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.home));

        loadDatabase();

        db = myDbHelper.getReadableDatabase();



        cat=getArguments().getInt("data_set");
        cat=cat+1;

        Cursor c = db.rawQuery("select * from data_text2 where cat = "+cat, null);
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


        adapter = new ListAdopter(new ArrayList<Product_List>());

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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
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
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void loadDatabase() {

        myDbHelper = new Database(getContext());

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.open();
        } catch (SQLException sqle) {
            throw sqle;

        }
    }
}