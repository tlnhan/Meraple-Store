package vn.edu.huflit.merapleshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.huflit.merapleshop.activities.LoginActivity;

public class LogOutFragment extends Fragment {

    public LogOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_log_out, container, false);
    }
}