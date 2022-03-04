package com.example.carpoolbuddy;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.bind.ArrayTypeAdapter;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private EditText userID;
    private EditText name;
    private AutoCompleteTextView userTypeMenu;
    private double priceMultiplier;
    private final String STUDENT = "student";
    private final String PARENT = "parent";
    private final String ALUMNI = "alumni";
    private final String TEACHER = "teacher";

    @Override
    protected void onResume() {
        super.onResume();
        String[] userTypesArray = getResources().getStringArray(R.array.userTypes);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, userTypesArray);
        userTypeMenu.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = findViewById(R.id.userIDEditText);
        name = findViewById(R.id.nameEditText);
        userTypeMenu = findViewById(R.id.userTypeAutoCompleteTextView);

//        String[] userTypesArray = getResources().getStringArray(R.array.userTypes);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, userTypesArray);
//        userTypeMenu.setAdapter(arrayAdapter);
    }

    public void saveProfile(View v)
    {
        String userIDString = userID.getText().toString();
        String nameString = name.getText().toString();
        String userTypeString = userTypeMenu.getText().toString();
        String emailString = mAuth.getCurrentUser().getEmail();

        if(userTypeString.equals(STUDENT) || userTypeString.equals(TEACHER))
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "priceMultiplier", 0.5
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "priceMultiplier", 1.0
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }

        //update user info
        if(userIDString != null && nameString != null && userTypeString != null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "userID", userIDString,
                            "name", nameString,
                            "userType", userTypeString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString == null && nameString != null && userTypeString != null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "name", nameString,
                            "userType", userTypeString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString != null && nameString == null && userTypeString != null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "userID", userIDString,
                            "userType", userTypeString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString != null && nameString != null && userTypeString == null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "userID", userIDString,
                            "name", nameString
                            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString != null && nameString == null && userTypeString == null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "userID", userIDString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString == null && nameString != null && userTypeString == null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "name", nameString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
        else if(userIDString == null && nameString == null && userTypeString != null)
        {
            firestore.collection("/users").document(emailString)
                    .update(
                            "userType", userTypeString
                    ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("UPDATE USER INFO", "successfully updated user info");
                        sendToast("user profile update successful");
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        sendToast("user profile update failed");
                    }
                }
            });
        }
    }

    public void signOut(View v)
    {
        System.out.println("sign out");
        //mAuth.signOut();
        Intent authActivityIntent = new Intent(this, AuthenticationActivity.class);
        startActivity(authActivityIntent);
        finish();
    }

    public void seeVehicles(View v)
    {
        Intent vehiclesInfoActivityIntent = new Intent(this, VehiclesInfoActivity.class);
        startActivity(vehiclesInfoActivityIntent);
        finish();
    }

    public void sendToast(String message)
    {
        Toast.makeText(UserProfileActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
