package com.jacobd2001.socialgift.home.wishlists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jacobd2001.socialgift.R;
import com.jacobd2001.socialgift.databinding.FragmentWishlistsBinding;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class WishlistsFragment extends Fragment implements MenuProvider {

    private FragmentWishlistsBinding binding;
    private List<WishlistItem> wishlists = new ArrayList<>();
    private final ItemAdapter<WishlistItem> itemAdapter = new ItemAdapter<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = FragmentWishlistsBinding.inflate(inflater, container, false);
        final MenuHost menuHost = getActivity();
        if (menuHost != null)
            menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.STARTED);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final FastAdapter<WishlistItem> fastAdapter = FastAdapter.with(itemAdapter);

        binding.wishlistsRecyclerView.setLayoutManager(layoutManager);
        binding.wishlistsRecyclerView.setAdapter(fastAdapter);

        if (!wishlists.isEmpty()) {
            binding.wishlistsRecyclerView.setVisibility(View.VISIBLE);
            binding.noWishlistsLayout.setVisibility(View.GONE);
        }

        for (WishlistItem item : wishlists) itemAdapter.add(item);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.wishlist_app_bar, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add_wishlist) {
            NewWishlistDialog dialog = new NewWishlistDialog((name, description) -> {
                binding.wishlistsRecyclerView.setVisibility(View.VISIBLE);
                binding.noWishlistsLayout.setVisibility(View.GONE);

                final WishlistItem item = new WishlistItem(name, description);
                wishlists.add(item);
                itemAdapter.add(item);
            });
            dialog.show(getParentFragmentManager(), "newWishlist");
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}