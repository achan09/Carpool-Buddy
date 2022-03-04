package com.example.carpoolbuddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class VehicleProfileActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseUser user;

    private TextView vehicleType;
    private TextView model;
    private TextView capacity;
    private TextView cost;
    private Switch openSwitch;
    private Button bookRideButton;

    private String vehicleID;
    private boolean isOwner;
    private boolean isCapacityMoreThanZero;
    private int capacityInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        vehicleType = findViewById(R.id.vehicleTypeTextView);
        model = findViewById(R.id.modelTextView);
        capacity = findViewById(R.id.capacityVehiclePfTextView);
        cost = findViewById(R.id.costVehiclePfTextView);
        openSwitch = findViewById(R.id.openVehiclePfSwitch);
        bookRideButton = findViewById(R.id.bookRideButton);

        vehicleID = getIntent().getExtras().getString("vehicleID");

        setUpButton();
    }

    public void setUpButton()
    {
        /*
        Only shows “book ride” button if this user is not the owner of this vehicle.
        Show the correct price in a label depending on the user’s role.
        Teachers and students pay half price. Alumni and Parents pay full price.
        Only shows “open/close” button if the user IS the owner of this vehicle.
         */
        isOwner = false;
        isCapacityMoreThanZero = false;

        firestore.collection("/vehicles").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot vehicle : task.getResult())
                            {
                                if(vehicle.toObject(Vehicle.class).getVehicleID()
                                        .equals(vehicleID)) //find vehicle that is clicked
                                {
                                    vehicleType.setText("vehicle type: " + vehicle.toObject(Vehicle.class).getVehicleType());
                                    model.setText("model: " + vehicle.toObject(Vehicle.class).getModel());
                                    capacityInt = vehicle.toObject(Vehicle.class).getCapacity();
                                    capacity.setText("capacity: " + String.valueOf(capacityInt));

                                    if(vehicle.toObject(Vehicle.class).getCapacity() > 0)
                                        //if capacity > 0
                                    {
                                        isCapacityMoreThanZero = true;
                                    }

                                    //get base price, find user type, multiply by user cost multiplier
                                    double basePrice = vehicle.toObject(Vehicle.class).getBasePrice();
                                    String currentUserEmail = user.getEmail();
                                    firestore.collection("/users").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        for (QueryDocumentSnapshot user : task.getResult())
                                                        {
                                                            if(user.toObject(User.class).getEmail().equals(currentUserEmail))
                                                                //find user
                                                            {
                                                                double priceMultiplier = user.toObject(User.class).getPriceMultiplier();
                                                                cost.setText("cost: " + String.valueOf(basePrice * priceMultiplier));

                                                                for(int i = 0; i < user.toObject(User.class).getOwnedVehicles().size(); i++)
                                                                    //traverse owned vehicles
                                                                {
                                                                    if(vehicleID.equals(user.toObject(User.class).getOwnedVehicles().get(i)))
                                                                        //if vehicle is in user's owned vehicles
                                                                    {
                                                                        isOwner = true;
                                                                        System.out.println("set is owner to true");
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                    else
                                                    {
                                                        Log.d(TAG, "Error getting user documents: ", task.getException());
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                        else
                        {
                            Log.d(TAG, "Error getting vehicle documents: ", task.getException());
                        }
                    }
                });

        if(isOwner)
        {
            System.out.println("IS OWNER");
            bookRideButton.setVisibility(View.GONE); //hide button
            openSwitch.setVisibility(View.VISIBLE); //show switch
            openSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        firestore.collection("/vehicles").document(vehicleID)
                                .update(
                                        "open", true //set open to true
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Log.d("OPEN VEHICLE", "vehicle open for booking");
                                    Toast.makeText(VehicleProfileActivity.this, "vehicle open for booking", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Log.w(TAG, "open vehicle:failure", task.getException());
                                    Toast.makeText(VehicleProfileActivity.this, "open vehicle for booking unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        firestore.collection("/vehicles").document(vehicleID)
                                .update(
                                        "open", false //set open to false
                                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Log.d("OPEN VEHICLE", "vehicle booking closed");
                                    Toast.makeText(VehicleProfileActivity.this, "vehicle booking closed", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Log.w(TAG, "open vehicle:failure", task.getException());
                                    Toast.makeText(VehicleProfileActivity.this, "close vehicle booking unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
        else
        {
            openSwitch.setVisibility(View.GONE); //hide switch
            bookRideButton.setVisibility(View.VISIBLE); //show button
        }
    }

    public void bookRide(View v)
    {
        //Reduce current capacity for this vehicle in the database by 1

        //if capacity > 0, success
        if(isCapacityMoreThanZero)
        {
            firestore.collection("/vehicles").document(vehicleID)
                    .update(
                            "capacity", capacityInt - 1, //subtract capacity in database
                            "ridersUIDs", FieldValue.arrayUnion(user.getEmail()) //add userEmail to riders list
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("BOOK RIDE", "book ride successful");
                        Toast.makeText(VehicleProfileActivity.this, "ride booked successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.w(TAG, "book ride:failure", task.getException());
                        Toast.makeText(VehicleProfileActivity.this, "book ride unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(VehicleProfileActivity.this, "no space left in vehicle", Toast.LENGTH_SHORT).show();
        }

    }

    public void seeVehicles(View v)
    {
        Intent vehiclesInfoActivityIntent = new Intent(this, VehiclesInfoActivity.class);
        startActivity(vehiclesInfoActivityIntent);
        finish();
    }

    public void seeProfile(View v)
    {
        Intent profileActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(profileActivityIntent);
        finish();
    }
}