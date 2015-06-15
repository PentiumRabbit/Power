package com.storm.powerimprove.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.storm.powerimprove.R;
import com.storm.powerimprove.activity.MainActivity;

import java.util.List;

/**
 * @author ----zhaoruyang----
 * @data: 2015/6/11
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int postion;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        postion = getArguments().getInt(ARG_SECTION_NUMBER);
        ((MainActivity) activity).onSectionAttached(
                postion);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // On Lollipop, the action bar shadow is provided by default, so have to remove it explicitly
//            ((ActionBarActivity) getActivity()).getSupportActionBar().setElevation(0);
//            getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (postion == 2) {
            inflater.inflate(R.menu.refresh, menu);
        } else if (postion == 3) {
            inflater.inflate(R.menu.issue_view, menu);
        }
//            super.onCreateOptionsMenu(menu, inflater);
    }
}
