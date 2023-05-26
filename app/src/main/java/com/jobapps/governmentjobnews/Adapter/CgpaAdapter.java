package com.jobapps.governmentjobnews.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobapps.governmentjobnews.Model.CgpaModel;
import com.jobapps.governmentjobnews.R;

import java.util.ArrayList;

public class CgpaAdapter extends RecyclerView.Adapter<CgpaAdapter.ViewHolder> {

    private final ArrayList<CgpaModel> cgpaModelArrayList;

    public CgpaAdapter(ArrayList<CgpaModel> cgpaModelArrayList) {
        this.cgpaModelArrayList = cgpaModelArrayList;
    }

    @NonNull
    @Override
    public CgpaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_cgpa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CgpaAdapter.ViewHolder holder, int position) {
        holder.subNameTV.setText(cgpaModelArrayList.get(position).getSubName());
        holder.subGradeTV.setText(cgpaModelArrayList.get(position).getSubGrade());
        holder.subCreditTV.setText(cgpaModelArrayList.get(position).getSubCredit());
        holder.subTotalTV.setText(cgpaModelArrayList.get(position).getSubTotal());
    }

    @Override
    public int getItemCount() {
        return cgpaModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView subNameTV;
        private final TextView subGradeTV;
        private final TextView subCreditTV;
        private final TextView subTotalTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subNameTV = itemView.findViewById(R.id.subNameTVID);
            subGradeTV = itemView.findViewById(R.id.subGradeTVID);
            subCreditTV = itemView.findViewById(R.id.subCreditTVID);
            subTotalTV = itemView.findViewById(R.id.subTotalTVID);

        }
    }
}
