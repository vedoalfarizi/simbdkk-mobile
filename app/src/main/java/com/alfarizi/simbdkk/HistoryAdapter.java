package com.alfarizi.simbdkk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfarizi.simbdkk.model.Proposal;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {

    ArrayList<Proposal> proposals;

    public void setProposals(ArrayList<Proposal> proposals){
        this.proposals = proposals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Proposal proposal = proposals.get(position);

        holder.getTvId().setText(proposal.getId());
        holder.getTvTitle().setText(proposal.getTitle());
        holder.getTvStatus().setText(proposal.getStatus());
        holder.getTvUpdatedAt().setText(proposal.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        if(proposals != null){
            return proposals.size();
        }else{
            return 0;
        }
    }
}
