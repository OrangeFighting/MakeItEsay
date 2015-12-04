package mclab.mytestforstickheaderviewpager;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stickheaderlayout.PlaceHoderHeaderLayout;
import com.stickheaderlayout.StickHeaderLayout;
import com.stickheaderlayout.StickHeaderViewPagerManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    class ViewPagerBean{
        View root;
        PlaceHoderHeaderLayout placeHoderHeaderLayout;
        public ViewPagerBean(View root,PlaceHoderHeaderLayout placeHoderHeaderLayout){
            this.root = root;
            this.placeHoderHeaderLayout = placeHoderHeaderLayout;
        }
    }
    ArrayList<ViewPagerBean> viewList;
    StickHeaderViewPagerManager manager;
    ViewPager mViewPager;
    private ImageView imageView;// 动画图片
    private int offset;//imageView的与起始位置的偏移
    private int bmpW;//imageview的宽度
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager)findViewById(R.id.v_scroll);
        StickHeaderLayout shl_root = (StickHeaderLayout)findViewById(R.id.shl_root);
        mViewPager.setOffscreenPageLimit(1);//Set the number of pages that should be retained to either side of current page
        manager = new StickHeaderViewPagerManager(shl_root,mViewPager);
        viewList = new ArrayList<ViewPagerBean>();
        //add gain view
        SimpleRecyclerView gainRecyclerView = new SimpleRecyclerView(this);
        viewList.add(new ViewPagerBean(gainRecyclerView, gainRecyclerView.getPlaceHoderHeaderLayout()));
        //add pain view
        SimpleRecyclerView painRecyclerView = new SimpleRecyclerView(this);
        viewList.add(new ViewPagerBean(painRecyclerView, painRecyclerView.getPlaceHoderHeaderLayout()));

        mViewPager.setAdapter(pagerAdapter);

        initTabBar();
        initImageView();
    }
    public void initImageView(){
        imageView= (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = (int)(dm.widthPixels-getResources().getDimension(R.dimen.activity_horizontal_margin)*2);// 获取分辨率宽度
        offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }
    public void initTabBar(){
        final List<TextView> tvList = new ArrayList<>();

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ly_tab);
        for(int i = 0 ; i < linearLayout.getChildCount() ; i++){
            TextView tv = (TextView)linearLayout.getChildAt(i);
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mViewPager != null && mViewPager.getAdapter().getCount() > finalI){
                        mViewPager.setCurrentItem(finalI);
                    }
                }
            });
            tvList.add(tv);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
//                for(TextView textView : tvList){
//                    textView.setBackgroundColor(Color.parseColor("#521242"));
//                    textView.setTextColor(Color.parseColor("#FFFFFF"));
//                }
//                tvList.get(position).setBackgroundColor(Color.parseColor("#124020"));
//                tvList.get(position).setTextColor(Color.parseColor("#000000"));

                int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
                Animation animation = new TranslateAnimation(one*currentIndex, one*position, 0, 0);
                currentIndex = position;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                imageView.startAnimation(animation);

            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position).root);
            manager.addPlaceHoderHeaderLayout(position, viewList.get(position).placeHoderHeaderLayout);
            return viewList.get(position).root;
        }
    };



}
