package com.jacobd2001.socialgift.home.wishlists;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jacobd2001.socialgift.R;
import com.jacobd2001.socialgift.databinding.DialogNewWishlistBinding;

public class NewWishlistDialog extends DialogFragment {

    private final OnSubmitClickListener listener;
    private DialogNewWishlistBinding binding;

    public NewWishlistDialog(OnSubmitClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogNewWishlistBinding.inflate(getLayoutInflater());

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        final AlertDialog dialog = builder.setTitle(R.string.create_new_wishlist)
                .setCancelable(false)
                .setView(binding.getRoot())
                .setPositiveButton(android.R.string.ok, null).create();

        dialog.setOnShowListener(dialogInterface -> dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view -> onSubmitClicked(dialog)));

        return dialog;
    }

    private void onSubmitClicked(AlertDialog dialog) {
        final String name = binding.wishlistNameInput.getEditText() != null ? binding.wishlistNameInput.getEditText().getText().toString() : "";
        final String description = binding.wishlistDescriptionInput.getEditText() != null ? binding.wishlistDescriptionInput.getEditText().getText().toString() : "";

        cleanErrors();
        if (!validateFields(name, description)) return;

        listener.onSubmitClicked(name, description);
        dialog.dismiss();
    }

    private boolean validateFields(String name, String description) {
        if (name.isEmpty()) {
            binding.wishlistNameInput.setError("Name cannot be empty");
            return false;
        }

        if (description.isEmpty()) {
            binding.wishlistDescriptionInput.setError("Description cannot be empty");
            return false;
        }
        return true;
    }

    private void cleanErrors() {
        binding.wishlistNameInput.setError(null);
        binding.wishlistDescriptionInput.setError(null);

        binding.wishlistNameInput.setErrorEnabled(false);
        binding.wishlistDescriptionInput.setErrorEnabled(false);
    }

    public interface OnSubmitClickListener {
        void onSubmitClicked(String name, String description);
    }
}
