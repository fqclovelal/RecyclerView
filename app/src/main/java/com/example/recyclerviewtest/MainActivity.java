package com.example.recyclerviewtest;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnRefreshLoadmoreListener, Handler.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerViewAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
    private RelativeLayout relativeLayout;
    private Handler mHandler;
    private View colorStatus;
    private int dimensionPixelSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mHandler = new Handler(this);
//        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        colorStatus = findViewById(R.id.color_status);
        ViewGroup.LayoutParams layoutParams = colorStatus.getLayoutParams();
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId>0){
            dimensionPixelSize = getResources().getDimensionPixelSize(resourceId);
        }
        layoutParams.height=dimensionPixelSize;
        colorStatus.setLayoutParams(layoutParams);
        final StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(new int[2]);
                if (firstVisibleItemPositions[0] != 0) {
                } else {
                    View view = layoutManager.findViewByPosition(0);
                    int top = view.getTop();
                    int height = view.getHeight();
                    int distance = height - Math.abs(top);
                    TypedValue typedValue = new TypedValue();
                    int actionBarHeight=0;
                    if (getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                        actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,getResources().getDisplayMetrics());
                    }
                    if(distance<=actionBarHeight+50){
                        relativeLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
                        colorStatus.setBackgroundColor(Color.parseColor("#999999"));
                    }else {
                        relativeLayout.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                        colorStatus.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                    }

                }
            }
        });

        adapter = new RecyclerViewAdapter(this);

//        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartLayout);
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.action_bar);


        initFruitsOne();
        initFruitsTwo();

    }

    private void initFruitsOne() {
        List<Fruit> fruitList = new ArrayList<Fruit>();
        Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
        fruitList.add(strawberry);

        adapter.setOneListData(fruitList);

    }

    public void initData() {
        List<Fruit> fruitList = new ArrayList<Fruit>();
        Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
        fruitList.add(strawberry);
        Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
        fruitList.add(banana);
        Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
        fruitList.add(pear);
        Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon);


        adapter.setOneListData(fruitList);
    }


    private void initFruitsTwo() {
        List<Fruit> fruitList = new ArrayList<Fruit>();
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit(getRandomLengthName("Grape"), R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit(getRandomLengthName("Mango"), R.drawable.mango_pic);
            fruitList.add(mango);
        }
        adapter.setTwoListData(fruitList);
    }

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mHandler.sendEmptyMessageDelayed(1, 3000);


    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadmore();
                break;
        }

        return false;
    }
}
