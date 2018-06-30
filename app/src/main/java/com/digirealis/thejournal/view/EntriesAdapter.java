package com.digirealis.thejournal.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digirealis.thejournal.R;
import com.digirealis.thejournal.database.model.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by fum on 6/29/18.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder>{

    private Context context;
    private List<Entry> entriesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView entry;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            entry = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public EntriesAdapter(Context context, List<Entry> entriesList) {
        this.context = context;
        this.entriesList = entriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Entry entry = entriesList.get(position);

        holder.entry.setText(entry.getEntry());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(entry.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}// end of class
