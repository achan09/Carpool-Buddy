package com.example.carpoolbuddy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VehiclesViewHolder extends RecyclerView.ViewHolder {

    protected TextView nameTitle;
    protected TextView ownerTitle;
    protected TextView owner;
    protected TextView capacityTitle;
    protected TextView capacity;
    protected TextView costTitle;
    protected TextView cost;

    public VehiclesViewHolder(@NonNull View itemView, VehicleAdapter.OnItemClickListener listener)
    {
        super(itemView);

        nameTitle = itemView.findViewById(R.id.nameTextView);
        ownerTitle = itemView.findViewById(R.id.ownerTitleTextView);
        owner = itemView.findViewById(R.id.ownerTextView);
        capacityTitle = itemView.findViewById(R.id.capacityTitleTextView);
        capacity = itemView.findViewById(R.id.capacityTextView);
        costTitle = itemView.findViewById(R.id.costTitleTextView);
        cost = itemView.findViewById(R.id.costTextView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
