package com.example.recyclerviewtest;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/23.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter implements View.OnTouchListener {
    private static final String TAG = "RecyclerViewAdapter";


    private MainActivity mainActivity;
    private LayoutInflater inflater;
    private int count = 3;
    private int TYPE_ONE = 1;
    private int TYPE_TWO = 2;
    private int TYPE_THREE = 3;
    private int TYPE_FOUR = 4;

    private List<Fruit> oneList;
    private List<Fruit> twoList;
    private HeadAdapter mHeadAdapter;
    private int mDistance;
    private Handler mHandler;
    private float mViewPagerX;
    private List<Fruit> mHeadData;


    public RecyclerViewAdapter(MainActivity a) {
        mainActivity = a;
        inflater = LayoutInflater.from(mainActivity);

        //初始化数据源
        oneList = new ArrayList<>();
        twoList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View viewonew = inflater.inflate(R.layout.tip_layout, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewonew.getLayoutParams();
            params.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
            viewonew.setLayoutParams(params);
            return new TypeOneHolder(viewonew);
        } else if (viewType == TYPE_TWO) {
            View viewtwo = inflater.inflate(R.layout.recycler_view, parent, false);
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) viewtwo.getLayoutParams();
            params.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
            viewtwo.setLayoutParams(params);
            return new TypeTwoHolder(viewtwo);
        } else if (viewType == TYPE_THREE) {
            return new TypeFourHolder(inflater.inflate(R.layout.fruit_item, parent, false));
        } else {
            return new TypeFourHolder(inflater.inflate(R.layout.fruit_item, parent, false));
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mViewPagerX = motionEvent.getX();
                mHandler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_CANCEL:
                mHandler.sendEmptyMessageDelayed(0, 3000);
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mViewPagerX - motionEvent.getX()) < 20) {
                    Toast.makeText(mainActivity, "ViewPager被点击", Toast.LENGTH_SHORT).show();
                }
                mHandler.sendEmptyMessageDelayed(0, 3000);
                break;

        }
        return false;
    }

    public class TypeTwoHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerview;

        public TypeTwoHolder(View view) {
            super(view);
            recyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        }


    }

    public class TypeFourHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;

        public TypeFourHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }

    public class TypeOneHolder extends RecyclerView.ViewHolder {
        ViewPager homePageViewpager;
        LinearLayout homeRectangleWhite;
        View homeRectangleRed;

        public TypeOneHolder(View view) {
            super(view);
            homePageViewpager = (ViewPager) view.findViewById(R.id.home_page_viewpager);
            homeRectangleWhite = (LinearLayout) view.findViewById(R.id.home_rectangle_white);
            homeRectangleRed = (View) view.findViewById(R.id.home_rectangle_red);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            initOne((TypeOneHolder) holder);
        } else if (position == 1) {
            initTwo((TypeTwoHolder) holder);
        } else if (position == 2) {
            initFour((TypeFourHolder) holder, position - 2);
        } else {
            initFour((TypeFourHolder) holder, position - 3);
        }
    }

    private void initTwo(TypeTwoHolder holder) {
        holder.recyclerview.setLayoutManager(new GridLayoutManager(mainActivity, 4));
        FruitAdapter fruitAdapter = new FruitAdapter(oneList);
        holder.recyclerview.setAdapter(fruitAdapter);
    }

    private void initFour(TypeFourHolder holder, int position) {
        Fruit fruit = twoList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    private void initOne(final TypeOneHolder holder) {
        mHeadData = new ArrayList<>();
        mHeadData.add(new Fruit("dd", R.drawable.cherry_pic));
        mHeadData.add(new Fruit("dd", R.drawable.apple_pic));
        mHeadData.add(new Fruit("dd", R.drawable.banana_pic));
        mHeadData.add(new Fruit("dd", R.drawable.strawberry_pic));
        mHeadData.add(new Fruit("dd", R.drawable.cherry_pic));
        mHeadData.add(new Fruit("dd", R.drawable.apple_pic));
        mHeadAdapter = new HeadAdapter(mainActivity, mHeadData);
        holder.homePageViewpager.setAdapter(mHeadAdapter);
        holder.homePageViewpager.setCurrentItem(1);
        holder.homeRectangleWhite.removeAllViews();
        for (int i = 0; i < 4; i++) {
            View rectangleWhite = new View(mainActivity);
            rectangleWhite.setBackgroundResource(R.drawable.home_rectangle_white);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtils.dp2px(mainActivity, 20), DensityUtils.dp2px(mainActivity, 2));
            if (i > 0) {
                params.leftMargin = DensityUtils.dp2px(mainActivity, 10);
            }
            rectangleWhite.setLayoutParams(params);
            holder.homeRectangleWhite.addView(rectangleWhite);
        }
        holder.homeRectangleWhite.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // layout执行结束后回调的方法
                    @Override
                    public void onGlobalLayout() {
                        if (holder.homeRectangleWhite.getViewTreeObserver().isAlive()) {
                            mDistance = holder.homeRectangleWhite.getChildAt(1).getLeft()
                                    - holder.homeRectangleWhite.getChildAt(0).getLeft();
                        }

                    }

                });
        holder.homePageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0 && position < 5) {
                    int len = (position - 1) * mDistance;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.homeRectangleRed
                            .getLayoutParams();
                    layoutParams.leftMargin = len;// 求出左边距
                    holder.homeRectangleRed.setLayoutParams(layoutParams);// 重现设置的布局参数
                }
                if (position == 0) {
                    int len = (3) * mDistance;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.homeRectangleRed
                            .getLayoutParams();
                    layoutParams.leftMargin = len;// 求出左边距
                    holder.homeRectangleRed.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int position = holder.homePageViewpager.getCurrentItem();
                    if (position == 0) {
                        holder.homePageViewpager.setCurrentItem(4, false);
                    }
                    if (position == 5) {
                        holder.homePageViewpager.setCurrentItem(1, false);
                    }
                }
            }
        });
        // 自动轮播条显示
        if (mHandler == null) {
            mHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    int currentItem = holder.homePageViewpager.getCurrentItem();

                    if (currentItem < 5) {
                        currentItem++;
                    }
                    holder.homePageViewpager.setCurrentItem(currentItem);// 切换到下一个页面
                    mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,
                    // 形成循环
                }
            };
            mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
        }
        holder.homePageViewpager.setOnTouchListener(this);
    }

    @Override
    public int getItemCount() {
        return count;
    }


    public void setOneListData(List<Fruit> list) {
        this.oneList = list;
        notifyDataSetChanged();
    }

    public void setTwoListData(List<Fruit> list) {
        this.twoList = list;
        this.count += list.size();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_ONE;
        } else if (position == 1) {
            return TYPE_TWO;
        } else if (position == 2) {
            return TYPE_THREE;
        } else {
            return TYPE_FOUR;
        }
    }
}
