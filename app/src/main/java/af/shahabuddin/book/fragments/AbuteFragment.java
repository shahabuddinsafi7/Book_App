package af.shahabuddin.book.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import af.shahabuddin.book.FavoriteAdapter;
import af.shahabuddin.book.MainActivity;
import af.shahabuddin.book.R;
import af.shahabuddin.book.database.FavoriteList;

public class AbuteFragment extends Fragment {
    private RecyclerView rv;
    private FavoriteAdapter adapter;

    public AbuteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_abute, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.about));


        return view;
    }
}