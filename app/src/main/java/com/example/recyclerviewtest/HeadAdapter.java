package com.example.recyclerviewtest;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class HeadAdapter extends PagerAdapter {
    private static final String TAG = HeadAdapter.class.getSimpleName();
    private List<Fruit> mHeadData;
    private Context context;
    public HeadAdapter(Context context,List<Fruit> mHeadData) {
        this.context = context;
        this.mHeadData=mHeadData;
    }



    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(mHeadData.get(position).getImageId());
        container.addView(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
