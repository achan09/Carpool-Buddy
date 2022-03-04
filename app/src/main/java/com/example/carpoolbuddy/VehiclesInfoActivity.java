package com.example.carpoolbuddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class VehiclesInfoActivity extends AppCompatActivity {

    private RecyclerView vehiclesRecView;
    private VehicleAdapter vehicleAdapter;
    private ArrayList<Vehicle> vehiclesArrayList;
    private FirebaseFirestore firestore;
    private Vehicle vehicleClicked;

    private MenuInflater inflater;
    private MenuItem searchItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        vehiclesRecView = findViewById(R.id.vehiclesRecView);

        firestore = FirebaseFirestore.getInstance();

        vehiclesArrayList = new ArrayList<>();
        vehiclesRecView.setLayoutManager(new LinearLayoutManager(this));

        getAndPopulateData();
        vehicleAdapter = new VehicleAdapter(vehiclesArrayList);
        vehicleAdapter.setOnItemClickListener(new VehicleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                vehicleClicked = vehiclesArrayList.get(position);
                Intent vehicleProfileActivityIntent = new Intent(VehiclesInfoActivity.this, VehicleProfileActivity.class);
                vehicleProfileActivityIntent.putExtra("vehicleID", vehicleClicked.getVehicleID());
                startActivity(vehicleProfileActivityIntent);
                finish();
            }
        });
    }

    public void getAndPopulateData() {
        //Get all of the vehicles from the database that are open.
        //
        //Use document.toObject(Vehicle.class). This will deserialize the contents of the database
        // information and give you a Vehicle object. Add all vehicles to the vehicles ArrayList.
        //
        //On completion of task for fetching all vehicles, set new RecyclerViewAdapter with the
        // list of vehicles fetched.

        firestore.collection("/vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : task.getResult())
                            {
                                Vehicle vehicle = document.toObject(Vehicle.class);
                                if(vehicle.isOpen())
                                {
                                    vehiclesArrayList.add(vehicle);
                                }
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            vehiclesRecView.setAdapter(vehicleAdapter);
                        }
                        else
                        {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void goToAddVehicleActivity(View v)
    {
        Intent addVehicleActivityIntent = new Intent(this, AddVehicleActivity.class);
        startActivity(addVehicleActivityIntent);
        finish();
    }

    public void backToProfileActivity(View v)
    {
        Intent profileActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(profileActivityIntent);
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        searchItem = menu.findItem(R.id.action_search);
//        searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                vehicleAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
}