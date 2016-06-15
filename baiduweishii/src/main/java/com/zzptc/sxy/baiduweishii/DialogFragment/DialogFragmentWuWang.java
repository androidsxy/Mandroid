package com.zzptc.sxy.baiduweishii.DialogFragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzptc.sxy.baiduweishii.R;

/**
 * Created by Administrator on 2016/4/25  0025.
 */
public class DialogFragmentWuWang extends DialogFragment {

    private Button button;
    private TextView textView;


    public static DialogFragmentWuWang newInstance(String text){
        DialogFragmentWuWang wuWang = new DialogFragmentWuWang();
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        wuWang.setArguments(bundle);
        return wuWang;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.activity_fragment,container,false);

        button = (Button) view.findViewById(R.id.bu_fragment);
        String text = getArguments().getString("text");

        textView = (TextView) view.findViewById(R.id.text_frament);

        textView.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }

                return false;
            }
        });
        return view;
    }
}
