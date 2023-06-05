package com.jacobd2001.socialgift;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jacobd2001.socialgift.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButton.setOnClickListener(view -> {
            final String email = binding.emailInput.getEditText() != null ? binding.emailInput.getEditText().getText().toString() : "";
            final String password = binding.passwordInput.getEditText() != null ? binding.passwordInput.getEditText().getText().toString() : "";

            if (validateFields(email, password)) {
                Toast.makeText(this, "Email: " + email + "\nPassword: " + password, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateFields(String email, String password) {
        if (email.isEmpty()) {
            binding.emailInput.setError("Email cannot be empty");
            return false;
        }

        if (!email.contains("@")) {
            binding.emailInput.setError("Email must contain @");
            return false;
        }

        if (password.isEmpty()) {
            binding.passwordInput.setError("Password cannot be empty");
            return false;
        }

        if (password.length() < 8) {
            binding.passwordInput.setError("Password must be at least 8 characters");
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            binding.passwordInput.setError("Password must contain a number");
            return false;
        }

        return true;
    }
}
