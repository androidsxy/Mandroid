package com.zzptc.sxy.baiduweishii.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11  0011.
 */
public class GuideAdapter extends PagerAdapter {
    private List<View> veses;
    public GuideAdapter(List<View> vese){
        this.veses = vese;
    }

    @Override
    public int getCount() {
        return veses.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(veses.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = veses.get(position);
        container.addView(view);
        return view;
    }
}
