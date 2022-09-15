package af.shahabuddin.book.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import af.shahabuddin.book.R;
import af.shahabuddin.book.database.FavoriteDatabase;
import af.shahabuddin.book.database.FavoriteList;

public class data_text_Fragment extends Fragment {
    TextView titlee, darii, arabicc;
    MenuItem fav;
    int id;
   public Context context;
    String title;
    String dari;
    String arabic;
    public static FavoriteDatabase favoriteDatabase;

    FavoriteList favoriteList = new FavoriteList();

    public data_text_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_text, container, false);

        darii = view.findViewById(R.id.main_text);
        titlee = view.findViewById(R.id.title);
        arabicc = view.findViewById(R.id.arabic);

        title = getArguments().getString("title");
        arabic = getArguments().getString("arabic");
        dari = getArguments().getString("dari");
        id = getArguments().getInt("id");

        titlee.setText("" + title);
        darii.setText("" + dari);
        arabicc.setText("" + arabic);
        favoriteDatabase = Room.databaseBuilder(getContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();

        return view;
    }

    // Handle Toolbar Items in a Fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        fav = menu.findItem(R.id.fav);
        if (favoriteDatabase.favoriteDao().isFavorite(id) == 1)
            fav.setIcon(R.drawable.ic_baseline_turned_in_24);
        else
            fav.setIcon(R.drawable.ic_baseline_turned_in_not_24);

        fav.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                favoriteList.setId(id);
                favoriteList.setTitle(title);
                favoriteList.setDari(dari);
                favoriteList.setArabic(arabic);
                if (favoriteDatabase.favoriteDao().isFavorite(id) != 1) {
                    fav.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_turned_in_24));
                    favoriteDatabase.favoriteDao().addData(favoriteList);

                    Snackbar snackbar = Snackbar.make(titlee, R.string.add_to_favorites, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    int snackbarTextId = R.id.snackbar_text;
                    TextView textView = snackbarView.findViewById(snackbarTextId);
                    textView.setTextColor(Color.WHITE);
                    ViewCompat.setLayoutDirection(snackbarView, ViewCompat.LAYOUT_DIRECTION_RTL);

                    snackbarView.setBackgroundColor(getResources().getColor(R.color.grean));
                    snackbar.show();

                } else {
                    fav.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_turned_in_not_24));
                    favoriteDatabase.favoriteDao().delete(favoriteList);
                    Snackbar snackbar = Snackbar.make(titlee, R.string.unadd_to_favorites, Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    int snackbarTextId = R.id.snackbar_text;
                    TextView textView = snackbarView.findViewById(snackbarTextId);
                    textView.setTextColor(Color.WHITE);
                    ViewCompat.setLayoutDirection(snackbarView, ViewCompat.LAYOUT_DIRECTION_RTL);

                    snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
                    snackbar.show();
                }

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.settings) {
            Dialog d = new Dialog(getContext());
            d.setContentView(R.layout.fragment_setting);
            String title = getResources().getString(R.string.settings);
            d.setTitle(title);
            d.show();
        }
        if (id == R.id.shar) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, darii.getText());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}