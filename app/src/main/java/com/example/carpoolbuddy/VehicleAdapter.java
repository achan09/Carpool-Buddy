package com.example.carpoolbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class VehicleAdapter extends RecyclerView.Adapter<VehiclesViewHolder>
    //implements Filterable
{
    private ArrayList<Vehicle> listOfVehicles;
    private ArrayList<Vehicle> fullVehiclesList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public VehicleAdapter(ArrayList<Vehicle> data)
    {
        listOfVehicles = data;
        fullVehiclesList = new ArrayList<>(listOfVehicles); //independent copy
    }

    @NonNull
    @Override
    public VehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row_view,
                parent, false);

        VehiclesViewHolder holder = new VehiclesViewHolder(view, mListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclesViewHolder holder, int position)
    {
        String nameTitleText = listOfVehicles.get(position).getVehicleID();
        String ownerText  = listOfVehicles.get(position).getOwner();
        int capacityText = listOfVehicles.get(position).getCapacity();
        double costText = listOfVehicles.get(position).getBasePrice();

        holder.nameTitle.setText(nameTitleText);
        holder.owner.setText(ownerText);
        holder.capacity.setText(String.valueOf(capacityText));
        holder.cost.setText(String.valueOf(costText));
    }

    @Override
    public int getItemCount()
    {
        return listOfVehicles.size();
    }

//    @Override
//    public Filter getFilter() {
//        return vehicleFilter;
//    }
//
//    private Filter vehicleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint)
//        {
//            ArrayList<Vehicle> filteredVehicleList = new ArrayList<>();
//
//            if(constraint == null || constraint.length() == 0)
//            {
//                filteredVehicleList.addAll(fullVehiclesList);
//            }
//            else
//            {
//                String filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim();
//
//                for(Vehicle vehicle : fullVehiclesList)
//                {
//                    if(vehicle.getVehicleID().toLowerCase(Locale.ROOT).contains(filterPattern))
//                    {
//                        filteredVehicleList.add(vehicle);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredVehicleList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults filterResults)
//        {
//            listOfVehicles.clear();
//            listOfVehicles.addAll((ArrayList) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };
}
