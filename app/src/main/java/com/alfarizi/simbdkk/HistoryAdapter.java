package com.alfarizi.simbdkk;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfarizi.simbdkk.model.Proposal;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    ArrayList<Proposal> proposals;
    OnItemClick handler;

    HistoryAdapter(ArrayList<Proposal> proposals){
        this.proposals = proposals;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.histories_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        Proposal proposal = proposals.get(position);

        holder.tvId.setText(proposal.getId());
        holder.tvTitle.setText(proposal.getTitle());
        holder.tvStatus.setText(proposal.getStatus());
        holder.tvUpdatedAt.setText(proposal.getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        if(proposals != null){
            return proposals.size();
        }else{
            return 0;
        }
    }

    class HistoryHolder extends RecyclerView.ViewHolder{

        TextView tvId, tvTitle, tvStatus, tvUpdatedAt;

        HistoryHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvUpdatedAt = itemView.findViewById(R.id.tv_updatedAt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Proposal p = proposals.get(position);
                    handler.click(p);
                }
            });
        }
    }

    public interface OnItemClick{
        void click(Proposal p);
    }
}
