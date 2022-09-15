package af.shahabuddin.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import af.shahabuddin.book.database.FavoriteList;
import af.shahabuddin.book.fragments.data_text_Fragment;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<FavoriteList>favoriteLists;
    Context context;

    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
      final FavoriteList fl=favoriteLists.get(i);

        viewHolder.title.setText(fl.getTitle());
        viewHolder.dari.setText(fl.getDari());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new data_text_Fragment();
                bundle.putString("dari",fl.getDari());
                bundle.putString("arabic",fl.getArabic());
                bundle.putString("title",fl.getTitle());
                bundle.putInt("id",fl.getId());

                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.frg_container, fragment).
                        addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,dari;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dari=itemView.findViewById(R.id.text2);
            title=itemView.findViewById(R.id.text);
        }
    }
}
