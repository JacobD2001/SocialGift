package com.jacobd2001.socialgift.home.wishlists;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jacobd2001.socialgift.R;
import com.jacobd2001.socialgift.databinding.ItemWishlistBinding;
import com.mikepenz.fastadapter.binding.AbstractBindingItem;

import java.util.List;

public class WishlistItem extends AbstractBindingItem<ItemWishlistBinding> {
    private final String name;
    private final String description;

    public WishlistItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int getType() {
        return R.id.wishlistItem;
    }

    @Override
    public void bindView(@NonNull ItemWishlistBinding binding, @NonNull List<?> payloads) {
        super.bindView(binding, payloads);

        binding.wishlistItemName.setText(name);
        binding.wishlistItemDescription.setText(description);
    }

    @NonNull
    @Override
    public ItemWishlistBinding createBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
        return ItemWishlistBinding.inflate(inflater, parent, false);
    }
}
