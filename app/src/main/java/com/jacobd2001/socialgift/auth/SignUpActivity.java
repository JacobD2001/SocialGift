package com.jacobd2001.socialgift.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jacobd2001.socialgift.R;
import com.jacobd2001.socialgift.databinding.ActivitySignUpBinding;
import com.jacobd2001.socialgift.home.HomeActivity;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signUpButton.setOnClickListener(view -> signUp());
        binding.signInLink.setOnClickListener(view -> openSignInScreen());
    }

    private void signUp() {
        final String username = binding.usernameInput.getEditText() != null ? binding.usernameInput.getEditText().getText().toString() : "";
        final String email = binding.emailInput.getEditText() != null ? binding.emailInput.getEditText().getText().toString() : "";
        final String password = binding.passwordInput.getEditText() != null ? binding.passwordInput.getEditText().getText().toString() : "";
        final String repeatPassword = binding.repeatPasswordInput.getEditText() != null ? binding.repeatPasswordInput.getEditText().getText().toString() : "";

        if (validateFields(username, email, password, repeatPassword)) openHomeScreen();
    }

    private void openSignInScreen() {
        Intent intent = new Intent(this, SignInActivity.class);
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

    private boolean validateFields(String username, String email, String password, String repeatPassword) {
        cleanErrors();

        if (username.isEmpty()) {
            binding.usernameInput.setError(getString(R.string.username_empty_error));
            return false;
        }

        if (username.length() < 3) {
            binding.usernameInput.setError(getString(R.string.username_length_error));
            return false;
        }

        if (!username.matches("[a-zA-Z0-9_]*")) {
            binding.usernameInput.setError(getString(R.string.username_invalid_error));
            return false;
        }

        if (email.isEmpty()) {
            binding.emailInput.setError(getString(R.string.email_empty_error));
            return false;
        }

        if (!email.contains("@")) {
            binding.emailInput.setError(getString(R.string.email_at_error));
            return false;
        }

        if (password.isEmpty()) {
            binding.passwordInput.setError(getString(R.string.password_empty_error));
            return false;
        }

        if (password.length() < 8) {
            binding.passwordInput.setError(getString(R.string.password_length_error));
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            binding.passwordInput.setError(getString(R.string.password_number_error));
            return false;
        }

        if (!password.equals(repeatPassword)) {
            binding.repeatPasswordInput.setError(getString(R.string.password_match_error));
            return false;
        }

        return true;
    }

    private void cleanErrors() {
        binding.usernameInput.setError(null);
        binding.emailInput.setError(null);
        binding.passwordInput.setError(null);
        binding.repeatPasswordInput.setError(null);

        binding.usernameInput.setErrorEnabled(false);
        binding.emailInput.setErrorEnabled(false);
        binding.passwordInput.setErrorEnabled(false);
        binding.repeatPasswordInput.setErrorEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
