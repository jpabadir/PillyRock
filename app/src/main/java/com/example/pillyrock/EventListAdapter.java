package com.example.pillyrock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<Medication> medications;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    EventListAdapter(Context context, List<Medication> medications) {
        this.inflater = LayoutInflater.from(context);
        this.medications = medications;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medication medication = medications.get(position);

        String medicationName = medication.medicationName;
        List<String> times = medication.times;
        String timesText = "";
        if (times.size() > 0) {
            timesText = times.get(0);
        }
        if (times.size() > 1) {
            timesText += "...";
        }
        List<String> days = medication.daysShort;
        String daysText = "";
        int numDays = Math.min(days.size(), 3);
        for (int i = 0; i < numDays; i++) {
            daysText += days.get(i);
            if (i < numDays - 1) {
                daysText += ",";
            }
        }
        String dose = medication.dose;

        holder.medicationName.setText(medicationName);
        holder.times.setText(timesText);
        holder.days.setText(daysText);
        holder.dose.setText(dose);
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView medicationName;
        TextView times;
        TextView days;
        TextView dose;

        ViewHolder(View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medicationName);
            times = itemView.findViewById(R.id.times);
            days = itemView.findViewById(R.id.days);
            dose = itemView.findViewById(R.id.dose);
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
        return medications.get(id).medicationName;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
