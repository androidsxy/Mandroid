package com.zzptc.sxy.baiduweishii.DialogFragment;


import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.adapter.UrgentAdapter;
import com.zzptc.sxy.baiduweishii.contant.Contant;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JianJiQuiJuiFragment extends Fragment {

    private ImageView image_an;
    private ListView list_phone_t;
    public JianJiQuiJuiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_jian_ji_qui_jui, container, false);
        image_an = (ImageView) view.findViewById(R.id.image_an);
        image_an.setImageResource(R.drawable.animation_anxia);
        list_phone_t = (ListView) view.findViewById(R.id.list_phone_t);

        AnimationDrawable animationDrawable = (AnimationDrawable) image_an.getDrawable();
        animationDrawable.start();
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("usb.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        DbManager dbManager = x.getDb(daoConfig);
        try {
            List<Contant> list = dbManager.selector(Contant.class).findAll();
            UrgentAdapter urgentAdapter = new UrgentAdapter(list,getActivity());
            list_phone_t.setAdapter(urgentAdapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return view;
    }

}
