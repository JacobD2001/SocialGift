package com.jacobd2001.socialgift.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jacobd2001.socialgift.databinding.ActivitySignInBinding;
import com.jacobd2001.socialgift.home.HomeActivity;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButton.setOnClickListener(view -> signIn());
        binding.signUpLink.setOnClickListener(view -> openSignUpScreen());
    }

    private void signIn() {
        final String email = binding.emailInput.getEditText() != null ? binding.emailInput.getEditText().getText().toString() : "";
        final String password = binding.passwordInput.getEditText() != null ? binding.passwordInput.getEditText().getText().toString() : "";

        if (validateFields(email, password)) openHomeScreen();
    }

    private void openSignUpScreen() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private boolean validateFields(String email, String password) {
        cleanErrors();

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

    private void cleanErrors() {
        binding.emailInput.setError(null);
        binding.passwordInput.setError(null);

        binding.emailInput.setErrorEnabled(false);
        binding.passwordInput.setErrorEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
