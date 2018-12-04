package com.manage.drone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.manage.drone.R;
import com.manage.drone.models.ItemConnect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuggestAdapter extends ArrayAdapter<ItemConnect> {
    private List<ItemConnect> mItems;
    private List<ItemConnect> clones;
    private ItemClick itemClick;

    public SuggestAdapter(@NonNull Context context, int resource, @NonNull List<ItemConnect> objects) {
        super(context, resource);
        mItems = objects;
        clones = new ArrayList<>(objects);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface ItemClick {
        void onItemClick(ItemConnect user);
    }

    public void replace(List<ItemConnect> list) {
        mItems.clear();
        clones.clear();
        mItems.addAll(list);
        clones.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connect, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        final ItemConnect connect = mItems.get(position);
        if (connect != null) {
            holder.text1.setText(connect.getTitle());
        }
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) itemClick.onItemClick(connect);
            }
        });

        return row;
    }

    public class ViewHolder {
        @BindView(R.id.text1)
        TextView text1;


        public ViewHolder(View row) {
            ButterKnife.bind(this, row);

        }

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<ItemConnect> suggestions = new ArrayList<>();
                if (charSequence != null) {
                    if (clones != null && !clones.isEmpty()) {
                        for (ItemConnect user : clones) {
                            if (user.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                suggestions.add(user);
                            }
                        }
                    }
                }
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mItems.clear();
                if (filterResults != null && filterResults.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    for (Object object : (List<?>) filterResults.values) {
                        if (object instanceof ItemConnect) {
                            ItemConnect user = (ItemConnect) object;
                            mItems.add(user);
                        }
                    }
                    notifyDataSetChanged();
                } else if (charSequence == null) {
                    // no filter, add entire original list back in
                    mItems.addAll(clones);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

}
