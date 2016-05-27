package com.example.hacks_000.newspaper.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hacks_000.newspaper.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToogle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

    public ScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen, container, false);
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolBar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToogle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToogle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToogle.syncState();
            }
        });
    }
}
