package com.alengeorge.bonjour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private Button updateAccountSettings;
    private EditText userName, userStatus;
    private CircleImageView userProfileImage;

    private String currentUserId;
    private FirebaseAuth auth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference();
        InitializeFields();

        updateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });
    }

    private void InitializeFields() {
        updateAccountSettings = findViewById(R.id.update_settings_button);
        userName = findViewById(R.id.set_user_name);
        userStatus = findViewById(R.id.set_profile_status);
        userProfileImage = findViewById(R.id.set_profile_image);

    }


    private void UpdateSettings() {
        String setUserName = userName.getText().toString();
        String setStatus = userStatus.getText().toString();

        if(TextUtils.isEmpty(setUserName)){
            Toast.makeText(this,"Please type your username...",Toast.LENGTH_LONG).show();

        }
        if(TextUtils.isEmpty(setStatus)){
            Toast.makeText(this,"Please type your status...",Toast.LENGTH_LONG).show();

        }else{
            HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid",currentUserId);
            profileMap.put("name",setUserName);
            profileMap.put("status",setStatus);

            rootRef.child("Users").child(currentUserId).setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    SendUserToMainActivity();
                                Toast.makeText(SettingsActivity.this,"Profile updated successfully..",Toast.LENGTH_LONG).show();
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(SettingsActivity.this,"Error: " + message,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(SettingsActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
