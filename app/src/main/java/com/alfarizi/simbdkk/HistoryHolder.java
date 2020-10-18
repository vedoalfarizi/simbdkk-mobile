package com.alfarizi.simbdkk;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class HistoryHolder extends RecyclerView.ViewHolder {

    private TextView tvId;
    private TextView tvTitle;
    private TextView tvStatus;
    private TextView tvUpdatedAt;


    public HistoryHolder(@NonNull View itemView) {
        super(itemView);
        tvId = itemView.findViewById(R.id.tv_id);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvStatus = itemView.findViewById(R.id.tv_status);
        tvUpdatedAt = itemView.findViewById(R.id.tv_updatedAt);
    }

    public TextView getTvId() {
        return tvId;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvStatus() {
        return tvStatus;
    }

    public TextView getTvUpdatedAt() {
        return tvUpdatedAt;
    }
}
