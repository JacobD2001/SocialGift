package com.jacobd2001.socialgift.home.wishlists;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jacobd2001.socialgift.R;
import com.jacobd2001.socialgift.databinding.FragmentWishlistsBinding;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.drag.ItemTouchCallback;
import com.mikepenz.fastadapter.swipe.SimpleSwipeCallback;
import com.mikepenz.fastadapter.swipe_drag.SimpleSwipeDragCallback;

import java.util.ArrayList;
import java.util.List;

public class WishlistsFragment extends Fragment implements MenuProvider, SimpleSwipeCallback.ItemSwipeCallback, ItemTouchCallback {

    private FragmentWishlistsBinding binding;
    private final List<WishlistItem> wishlists = new ArrayList<>();
    private final ItemAdapter<WishlistItem> itemAdapter = new ItemAdapter<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = FragmentWishlistsBinding.inflate(inflater, container, false);
        final MenuHost menuHost = getActivity();
        if (menuHost != null)
            menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.STARTED);

        wishlists.add(new WishlistItem("Test", "Test"));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final FastAdapter<WishlistItem> fastAdapter = FastAdapter.with(itemAdapter);

        binding.wishlistsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.wishlistsRecyclerView.setLayoutManager(layoutManager);
        binding.wishlistsRecyclerView.setAdapter(fastAdapter);

        if (!wishlists.isEmpty()) {
            binding.wishlistsRecyclerView.setVisibility(View.VISIBLE);
            binding.noWishlistsLayout.setVisibility(View.GONE);
        }

        setupItemSwipe();

        for (WishlistItem item : wishlists) itemAdapter.add(item);
    }

    private void setupItemSwipe() {
        if (getActivity() == null) return;

        final Resources.Theme theme = getActivity().getTheme();
        final Drawable trashDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.trash_can_outline, theme);
        final Drawable pencilDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.pencil_outline, theme);

        if (trashDrawable == null || pencilDrawable == null) return;

        final SimpleSwipeDragCallback touchCallback = new SimpleSwipeDragCallback(
                this,
                this,
                trashDrawable,
                ItemTouchHelper.LEFT,
                0x0f000000
        )
                .withBackgroundSwipeRight(0x0f000000)
                .withLeaveBehindSwipeRight(pencilDrawable)
                .withNotifyAllDrops(true)
                .withSensitivity(10f)
                .withSurfaceThreshold(0.3f);

        final ItemTouchHelper touchHelper = new ItemTouchHelper(touchCallback);
        touchHelper.attachToRecyclerView(binding.wishlistsRecyclerView);
    }

    @Override
    public void itemSwiped(int index, int direction) {
        final WishlistItem item = removeItem(index);

        if (direction == 8) {
            restoreItem(index, item);
            EditWishlistDialog dialog = new EditWishlistDialog(item.getName(), item.getDescription(), ((name, description) -> {
                removeItem(index);
                addNewItem(name, description);
            }));
            dialog.show(getParentFragmentManager(), "newWishlist");
        } else {
            Snackbar.make(binding.getRoot(), getString(R.string.deleted_wishlist, item.getName()), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo), view -> restoreItem(index, item))
                    .setActionTextColor(Color.WHITE)
                    .show();
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.wishlist_app_bar, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add_wishlist) {
            NewWishlistDialog dialog = new NewWishlistDialog(this::addNewItem);
            dialog.show(getParentFragmentManager(), "newWishlist");
            return true;
        }

        return false;
    }

    private void addNewItem(String name, String description) {
        final WishlistItem item = new WishlistItem(name, description);
        wishlists.add(item);
        itemAdapter.add(item);

        if (wishlists.size() == 1) {
            binding.wishlistsRecyclerView.setVisibility(View.VISIBLE);
            binding.noWishlistsLayout.setVisibility(View.GONE);
        }
    }

    private WishlistItem removeItem(int index) {
        final WishlistItem item = itemAdapter.getAdapterItem(index);
        itemAdapter.remove(index);
        wishlists.remove(item);

        if (wishlists.isEmpty()) {
            binding.wishlistsRecyclerView.setVisibility(View.GONE);
            binding.noWishlistsLayout.setVisibility(View.VISIBLE);
        }
        return item;
    }

    private void restoreItem(int index, WishlistItem item) {
        final WishlistItem newItem = item.copy();
        itemAdapter.add(index, newItem);
        wishlists.add(index, newItem);

        if (wishlists.size() == 1) {
            binding.wishlistsRecyclerView.setVisibility(View.VISIBLE);
            binding.noWishlistsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void itemTouchDropped(int i, int i1) {
    }

    @Override
    public boolean itemTouchOnMove(int i, int i1) {
        return false;
    }

    @Override
    public void itemTouchStartDrag(@NonNull RecyclerView.ViewHolder viewHolder) {
    }

    @Override
    public void itemTouchStopDrag(@NonNull RecyclerView.ViewHolder viewHolder) {
    }
}