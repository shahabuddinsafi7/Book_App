package af.shahabuddin.book;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import af.shahabuddin.book.fragments.data_text_Fragment;

public class ListAdopter extends RecyclerView.Adapter<ListAdopter.ViewHolder> implements Filterable{
    private List<Product_List> mSportList;
    private ArrayList<Product_List> FullList;
    Context mContext;

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public ListAdopter(List<Product_List> sportList) {
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
    public ListAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdopter.ViewHolder holder, int position) {
        try {
            final Product_List mSport = mSportList.get(position);
            holder.dari.setText(mSport.getTitle());
            holder.title.setText(mSport.getMain_text());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new data_text_Fragment();
                    bundle.putString("dari",mSport.getMain_text());
                    bundle.putString("arabic",mSport.getArabi());
                    bundle.putString("title",mSport.getTitle());
                    bundle.putInt("id",mSport.getId());

                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.frg_container, fragment).
                            addToBackStack(null).commit();
                }
            });

        }catch (Exception e){
            holder.dari.setText("نتیجه (0)");
            holder.title.setVisibility(View.GONE);
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
                        filteredList.add(item);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,dari,arabic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dari=itemView.findViewById(R.id.text);
            title=itemView.findViewById(R.id.text2);
//            arabic=itemView.findViewById(R.id.arabic);
        }

    }

}
