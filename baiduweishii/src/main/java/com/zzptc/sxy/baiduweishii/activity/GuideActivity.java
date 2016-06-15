package com.zzptc.sxy.baiduweishii.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zzptc.sxy.baiduweishii.R;
import com.zzptc.sxy.baiduweishii.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements View.OnClickListener{
    //每个界面布局
    private List<View> views;
    private ViewPager pager;
    private Button button;
    private GuideAdapter adapter;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        pager = (ViewPager) findViewById(R.id.vp_content);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);


        //适配器
        View guide1 = View.inflate(this,R.layout.guide_first_main,null);
        View guide2 = View.inflate(this,R.layout.guide_second_main,null);
        View guide3 = View.inflate(this,R.layout.guide_treet_main,null);
        button = (Button) guide3.findViewById(R.id.button_1);
        button.setOnClickListener(this);
        views = new ArrayList<View>();
        views.add(guide1);
        views.add(guide2);
        views.add(guide3);
        adapter = new GuideAdapter(views);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              switch (position){
                  case 0:
                      imageView1.setImageResource(R.mipmap.dot_selected);
                      imageView2.setImageResource(R.mipmap.dot_unselected);
                      imageView3.setImageResource(R.mipmap.dot_unselected);
                      break;
                  case 1:
                      imageView1.setImageResource(R.mipmap.dot_unselected);
                      imageView2.setImageResource(R.mipmap.dot_selected);
                      imageView3.setImageResource(R.mipmap.dot_unselected);
                      break;
                  case 2:
                      imageView1.setImageResource(R.mipmap.dot_unselected);
                      imageView2.setImageResource(R.mipmap.dot_unselected);
                      imageView3.setImageResource(R.mipmap.dot_selected);
                      break;
              }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                startActivity(new Intent(this,MainUiActivity.class));
                finish();
                break;
        }
    }
}
