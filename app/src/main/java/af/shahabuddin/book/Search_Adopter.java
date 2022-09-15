package af.shahabuddin.book;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Search_Adopter extends RecyclerView.Adapter<Search_Adopter.ViewHolder> implements Filterable {
    private List<Product_List> mSportList;
    private ArrayList<Product_List> FullList;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public Search_Adopter(List<Product_List> sportList) {
        mSportList = sportList;
        FullList = new ArrayList<>(sportList);
    }

    public void addItems(List<Product_List> sportList) {
        mSportList.addAll(sportList);
        FullList.addAll(sportList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Search_Adopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view);    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final Search_Adopter.ViewHolder holder, int position) {
        try {
            final Product_List mSport = mSportList.get(position);
            holder.dari.setText(mSport.getTitle());
            holder.title.setText(mSport.getMain_text());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),MainActivity.class);
                    intent.putExtra("dari",mSport.getMain_text());
                    intent.putExtra("arabic",mSport.getArabi());
                    intent.putExtra("title",mSport.getTitle());
                    intent.putExtra("id",mSport.getId());
                    view.getContext().startActivity(intent);
                }
            });
        }catch (Exception e){
            holder.dari.setVisibility(View.GONE);
            holder.title.setText("نتیجه جستجو (0)");
            holder.title.setTextSize(18);
        }
    }

    @Override
    public int getItemCount() {
        if (mSportList != null && mSportList.size() > 0) {
            return mSportList.size();
        } else {
            return 1;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (mSportList != null && mSportList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,dari,arabic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dari=itemView.findViewById(R.id.text);
            title=itemView.findViewById(R.id.text2);
//            arabic=itemView.findViewById(R.id.arabic);
        }

    }
    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }
    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product_List> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(FullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product_List item : FullList) {
                    if (item.getMain_text().toLowerCase().contains(filterPattern)) {


                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSportList.clear();
            mSportList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
