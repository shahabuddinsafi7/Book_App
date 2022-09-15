package af.shahabuddin.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;

import java.util.List;

import af.shahabuddin.book.database.FavoriteList;

public class FavoriteListActivity extends AppCompatActivity {
      private RecyclerView rv;
      private FavoriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
        rv=(RecyclerView)findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        getFavData();
    }

    private void getFavData() {
        List<FavoriteList>favoriteLists=MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();

        adapter=new FavoriteAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(adapter);
    }
}
