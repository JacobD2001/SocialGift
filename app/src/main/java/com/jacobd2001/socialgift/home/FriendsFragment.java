package com.jacobd2001.socialgift.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jacobd2001.socialgift.databinding.FragmentFriendsBinding;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedState) {
        super.onViewCreated(view, savedState);
        binding.textDashboard.setText("This is the friends fragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}