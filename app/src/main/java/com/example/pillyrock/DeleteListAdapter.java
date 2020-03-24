package com.example.pillyrock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeleteListAdapter extends RecyclerView.Adapter<DeleteListAdapter.ViewHolder> {

    private List<Medication> medications;
    private List<Boolean> selections;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    DeleteListAdapter(Context context, List<Medication> medications) {
        this.inflater = LayoutInflater.from(context);
        this.medications = medications;
        this.selections = new ArrayList<>();
        for (int i = 0; i < medications.size(); i++) {
            selections.add(false);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.delete_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medication medication = medications.get(position);

        String medicationName = medication.medicationName;
        String dosesPerRefill = medication.dosesPerRefill;
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
        boolean selected = selections.get(position);

        holder.medicationName.setText(medicationName);
        holder.dosesPerRefill.setText(dosesPerRefill);
        holder.times.setText(timesText);
        holder.days.setText(daysText);
        holder.dose.setText(dose);
        holder.selected.setChecked(selected);
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView medicationName;
        TextView dosesPerRefill;
        TextView times;
        TextView days;
        TextView dose;
        CheckBox selected;

        ViewHolder(View itemView) {
            super(itemView);
            medicationName = itemView.findViewById(R.id.medicationName);
            dosesPerRefill = itemView.findViewById(R.id.dosesPerRefill);
            times = itemView.findViewById(R.id.times);
            days = itemView.findViewById(R.id.days);
            dose = itemView.findViewById(R.id.dose);
            selected = itemView.findViewById(R.id.selected);
            selected.setOnCheckedChangeListener(null);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void toggleEventSelected(int id) {
        selections.set(id, !selections.get(id));
        notifyItemChanged(id);
    }

    public boolean isEventSelected(int id) {
        return selections.get(id);
    }

    public void removeEvent(int id) {
        medications.remove(id);
        selections.remove(id);
        notifyItemRemoved(id);
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
