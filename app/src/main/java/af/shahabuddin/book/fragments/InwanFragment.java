package af.shahabuddin.book.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ArrayAdapter;

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
import af.shahabuddin.book.Search_Activity;
import af.shahabuddin.book.database.Database;
import af.shahabuddin.book.database.FavoriteDatabase;

public class InwanFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<String> moviesList;
    public static FavoriteDatabase favoriteDatabase;

    public InwanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        favoriteDatabase = Room.databaseBuilder(getContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.home));
        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        moviesList = new ArrayList<>();
        moviesList.add("کتاب وحی");
        moviesList.add("کتاب ایمان");
        moviesList.add("کتاب علم");
        moviesList.add("کتاب وضو");
        moviesList.add("کتاب غسل");
        moviesList.add("کتاب ایمان");
        moviesList.add("کتاب حیض");
        moviesList.add("کتاب تیمم");
        moviesList.add("کتاب نماز");
        moviesList.add("کتاب ابواب ستره نماز گذار");
        moviesList.add("کتاب اوقات نماز");
        moviesList.add("کتاب اذان");
        moviesList.add("کتاب نماز جمعه");
        moviesList.add("کتاب احکام نماز خوف");
        moviesList.add("کتاب ابواب عیدین");
        moviesList.add("کتاب ابواب وتر");
        moviesList.add("کتاب استسقاء");
        moviesList.add("کتاب کسوف یا خورشید گرفتگی");
        moviesList.add("کتاب سجدهٔ تلاوت");
        moviesList.add("کتاب کوتاه خواندن نماز");
        moviesList.add("کتاب نمام تهجد");
        moviesList.add("کتاب نماز در مسجد مکه و مدینه");
        moviesList.add("کتاب انجام دادن کاری در نماز");
        moviesList.add("کتاب سجدهٔ سهو");

        recyclerAdapter = new RecyclerAdapter(moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}