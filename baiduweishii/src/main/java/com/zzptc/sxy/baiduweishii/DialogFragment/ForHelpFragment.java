package com.zzptc.sxy.baiduweishii.DialogFragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.activity.OneKeyForHelpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForHelpFragment extends Fragment {


    public ForHelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_for_help, container, false);
        Button button = (Button) view.findViewById(R.id.button_hel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyForHelpActivity act = (OneKeyForHelpActivity) getActivity();
                act.er();
            }
        });
        return view;
    }

}
