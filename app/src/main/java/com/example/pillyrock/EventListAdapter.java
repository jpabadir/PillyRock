package com.example.pillyrock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<String> medicationNames;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    EventListAdapter(Context context, List<String> medicationNames) {
        this.inflater = LayoutInflater.from(context);
        this.medicationNames = medicationNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String medicationName = medicationNames.get(position);
        holder.medicationView.setText(medicationName);
    }

    @Override
    public int getItemCount() {
        return medicationNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView medicationView;

        ViewHolder(View itemView) {
            super(itemView);
            medicationView = itemView.findViewById(R.id.medicationNameTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    String getMedicationName(int id) {
        return medicationNames.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
