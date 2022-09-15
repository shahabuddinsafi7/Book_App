package af.shahabuddin.book.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import af.shahabuddin.book.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    Context mContext;
    List<String> moviesList;
    List<String> moviesListAll;

    public RecyclerAdapter(List<String> moviesList) {
        this.moviesList = moviesList;

        moviesListAll = new ArrayList<>();
        moviesListAll.addAll(moviesList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowCountTextView.setVisibility(View.GONE);
        holder.textView.setText(moviesList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new ListFragment();
                bundle.putInt("data_set",position);
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.frg_container, fragment).
                        addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public Filter getFilter() {

        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(moviesListAll);
            } else {
                for (String movie : moviesListAll) {
                    if (movie.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesList.clear();
            moviesList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView, rowCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.text);
            rowCountTextView = itemView.findViewById(R.id.text2);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }
}