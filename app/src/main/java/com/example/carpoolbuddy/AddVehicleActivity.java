package com.example.carpoolbuddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddVehicleActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText vehicleOwnerField;
    private EditText carModelField;
    private EditText capacityField;
    private EditText vehicleIDField;
    private EditText vehicleTypeField;
    private EditText basePriceField;
    private Switch openSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        vehicleOwnerField = findViewById(R.id.ownerEditText);
        carModelField = findViewById(R.id.carModelEditText);
        capacityField = findViewById(R.id.capacityEditText);
        vehicleIDField = findViewById(R.id.vehicleIDEditText);
        vehicleTypeField = findViewById(R.id.vehicleTypeEditText);
        basePriceField = findViewById(R.id.basePriceEditText);
        openSwitch = (Switch) findViewById(R.id.openSwitch);
    }

    public boolean formValid()
    {
        if(vehicleOwnerField == null || carModelField == null || capacityField == null ||
                vehicleIDField == null || vehicleTypeField == null || basePriceField == null)
        {
            return false;
        }
        return true;
    }


    public void addNewVehicle(View v)
    {
        //if formvalid is true
        //create new vehicle object
        //add to user vehicles arraylist
        if(formValid())
        {
            String ownerString = vehicleOwnerField.getText().toString();
            String modelString = carModelField.getText().toString();
            String capacityString = capacityField.getText().toString();
            int capacityInt = Integer.parseInt(capacityString);
            String vehicleIDString = vehicleIDField.getText().toString();
            ArrayList<String> ridersUIDs = new ArrayList<String>();
            String vehicleTypeString = vehicleTypeField.getText().toString();
            String basePriceString = basePriceField.getText().toString();
            Double basePriceDouble = Double.parseDouble(basePriceString);
            Boolean switchState = openSwitch.isChecked();

            Vehicle vehicle = new Vehicle(ownerString, modelString, capacityInt, vehicleIDString,
                    ridersUIDs, switchState, vehicleTypeString, basePriceDouble);

            firestore.collection("/vehicles").document(vehicleIDString).set(vehicle)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("ADD NEW VEHICLE", "added to vehicle collection");
                        sendToast("vehicle added to firestore database");
                    }
                    else
                    {
                        Log.w(TAG, "ADD NEW VEHICLE:failure", task.getException());
                        sendToast("failed to save vehicle to firestore database");
                    }
                }
            });


            firestore.collection("/users").document(mAuth.getCurrentUser().getEmail())
                    .update(
                            "ownedVehicles", FieldValue.arrayUnion(vehicle.getVehicleID()))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("ADD VEHICLE TO USER", "added vehicleID to user");
                        sendToast("vehicle added to user");
                    }
                    else
                    {
                        Log.w(TAG, "addVehicleToUser:failure", task.getException());
                        sendToast("failed to add vehicle to user");
                    }
                }
            }); //update info in firebase
        }
    }


    public void sendToast(String message)
    {
        Toast.makeText(AddVehicleActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void goToProfileActivity(View v)
    {
        Intent profileActivityIntent = new Intent(this, UserProfileActivity.class);
        startActivity(profileActivityIntent);
        finish();
    }
}