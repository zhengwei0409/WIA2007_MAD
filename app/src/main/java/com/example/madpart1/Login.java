package com.example.madpart1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.madpart1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Login extends AppCompatActivity {

    Button signUp, login;
    EditText email, password;
    TextView forgotPassword;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.lineForEmail);
        password = findViewById(R.id.LineForPassword);
        signUp = findViewById(R.id.SignUpButton);
        login = findViewById(R.id.LoginButton);
        forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            View dialogView = getLayoutInflater().inflate(R.layout.activity_forget_password, null);
            EditText emailBox = dialogView.findViewById(R.id.emailBox);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            // Reset button logic
            dialogView.findViewById(R.id.btnReset).setOnClickListener(v -> {
                String userEmail = emailBox.getText().toString();

                if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(Login.this, "Enter your registered email ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Check your email", Toast.LENGTH_SHORT).show();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("users")
                                        .whereEqualTo("Email", userEmail)
                                        .get()
                                        .addOnCompleteListener(queryTask -> {
                                            if (queryTask.isSuccessful() && !queryTask.getResult().isEmpty()) {
                                                for (QueryDocumentSnapshot document : queryTask.getResult()) {
                                                    db.collection("users")
                                                            .document(document.getId())
                                                            .update("password", "Reset requested")
                                                            .addOnSuccessListener(aVoid -> {
                                                                Toast.makeText(Login.this, "Firestore updated successfully.", Toast.LENGTH_SHORT).show();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(Login.this, "Failed to update Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            });
                                                }
                                            } else {
                                                Toast.makeText(Login.this, "User not found in Firestore.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(Login.this, "Error querying Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                                dialog.dismiss(); // Move dialog dismiss outside the for-loop
                            } else {
                                Toast.makeText(Login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                            }
                        });
            });

            // Cancel button logic
            dialogView.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());

            // Check if the window is null and then apply settings
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            dialog.show();
        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        });

        login.setOnClickListener(v -> {
            String emailText = String.valueOf(email.getText());
            String passwordText = String.valueOf(password.getText());

            if (TextUtils.isEmpty(emailText)) {
                Toast.makeText(Login.this, "Enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordText)) {
                Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUserOnlineStatus(true);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
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
