package com.example.madpart1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public EditText editTextEmail,editTextPassword,editTextPhoneNo,editTextNationality,editTextFullName;
    public Button doneBut;
    public FirebaseAuth mAuth;
    public String uid, onlineStatus;
    public Button btnPickDate;
    public TextView clickToLogin, selectedDate;
    public RadioGroup genderButton;
    public FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        this.doneBut = findViewById(R.id.done);
        this.clickToLogin = findViewById(R.id.clickToLogin);
        this.btnPickDate= findViewById(R.id.btnPickDate);
        this.selectedDate = findViewById(R.id.tvSelectedDate);
        this.editTextFullName = findViewById(R.id.editTextFullName);
        this.editTextPhoneNo = findViewById(R.id.editTextPhoneNumber);
        this.editTextNationality = findViewById(R.id.editTextNationality);
        this.genderButton = findViewById(R.id.radioGroup);


        btnPickDate.setOnClickListener(view -> {
            Calendar calender= Calendar.getInstance();
            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int day = calender.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dataPickerDialog = new DatePickerDialog(Register.this,(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) -> {
                String date = selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear;
                selectedDate.setText(date);
            }, year, month, day);

            dataPickerDialog.show();
        });


        clickToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserOnlineStatus(true);
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });

        doneBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //email
                String email = String.valueOf(editTextEmail.getText());
                //password
                String password = String.valueOf(editTextPassword.getText());
                //fullName
                String fullName = String.valueOf(editTextFullName.getText());
                //selectedGender
                int selectedId = genderButton.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedGender = selectedRadioButton.getText().toString();
                //nationality
                String nationality = String.valueOf(editTextNationality.getText());
                //phoneNumber
                String phoneNo =  String.valueOf(editTextPhoneNo.getText());
                String dateOfBirth = String.valueOf(selectedDate.getText());


                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        uid = user.getUid();

                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("Email", email);
                                        userData.put("UID", uid);
                                        userData.put("Password", password);
                                        userData.put("Full name", fullName);
                                        userData.put("Gender",selectedGender);
                                        userData.put("Nationality",nationality);
                                        userData.put("Phone Number", phoneNo);
                                        userData.put("Date of Birth",dateOfBirth);
                                        userData.put("Online Status", onlineStatus);

                                        firestore.collection("users")
                                                .document(uid)
                                                .set(userData)
                                                .addOnSuccessListener(aVoid -> Toast.makeText(Register.this, "User data stored successfully.",
                                                        Toast.LENGTH_SHORT).show())
                                                .addOnFailureListener(e -> Toast.makeText(Register.this, "Failed to store user data: " + e.getMessage(),
                                                        Toast.LENGTH_SHORT).show());
                                        Toast.makeText(Register.this, "Account created.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    public void updateUserOnlineStatus(boolean isOnline) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("users").document(uid)
                .update("Online Status", isOnline)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("User online status updated.");
                })
                .addOnFailureListener(e -> {
                    System.err.println("Failed to update status: " + e.getMessage());
                });
    }
}