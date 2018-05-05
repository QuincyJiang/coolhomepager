package com.jiangxq.coolmenuLib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("all")
@SuppressWarnings("all")
class TranslateLayout extends FrameLayout implements View.OnClickListener {

    private OnMenuClickListener mOnMenuClickListener;

    private ImageView mMenu;

    private TextView mTitle;

    private float mTitleTrans;
    private View view;
    private String TAG = "TranslateLayout";
    private boolean isOpen= false;

    public TranslateLayout(Context context) {
        super(context);
        init();
    }

    public TranslateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TranslateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setWillNotDraw(true);
            inflate(getContext(), R.layout.layout_title, this);
            view = getChildAt(0);
            view.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            mTitleTrans = getResources().getDimension(R.dimen.cl_title_trans);
            mMenu = (ImageView) view.findViewById(R.id.cl_menu);
            mTitle = (TextView) view.findViewById(R.id.cl_title);
            mMenu.setOnClickListener(this);
            setMenuAlpha(0);
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return  ! ((CoolMenuFrameLayout) getParent()).isOpening();
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ((CoolMenuFrameLayout) getParent()).isOpening();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (indexOfChild(view) != getChildCount() - 1) {
            bringChildToFront(view);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public float getXFraction() {
        int width = Utils.getScreenWidth(getContext());
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public void setXFraction(float xFraction) {
        int width = Utils.getScreenWidth(getContext());
        setX((width > 0) ? (xFraction * width) : 0);
    }

    public float getYFraction() {
        int height = Utils.getScreenHeight(getContext());
        return (height == 0) ? 0 : getY() / (float) height;
    }

    public void setYFraction(float yFraction) {
        int height = Utils.getScreenHeight(getContext());
        setY((height > 0) ? (yFraction * height) : 0);
    }

    public void setOnMenuClickListener(OnMenuClickListener mOnMenuClickListener) {
        this.mOnMenuClickListener = mOnMenuClickListener;
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setMenuIcon(Drawable drawable) {
        mMenu.setImageDrawable(drawable);
    }

    public void setMenuIcon(int resId) {
        mMenu.setImageResource(resId);
    }

    public void setMenuTitleSize(float size) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setMenuTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setMenuAlpha(float fraction) {
//        mMenu.setAlpha(fraction);
        mMenu.setScaleX(fraction);
        mMenu.setScaleY(fraction);
        mTitle.setTranslationX((1 - fraction) * -mTitleTrans);
    }

    @Override
    public void onClick(View v) {
        if (R.id.cl_menu == v.getId()) {
            if (mOnMenuClickListener != null) {
                mOnMenuClickListener.onMenuClick();
            }
        }
    }

    interface OnMenuClickListener {
        void onMenuClick();
        void onLayoutClick(View view);
    }
    float startX = 0;
    float startY = 0;
    float endX= 0;
    float endY = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.e("ACTION_DOWN","startX"+"---->"+startX+"startY"+"------>"+startY);
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                Log.e("ACTION_MOVE","endX"+"---->"+endX+"endY"+"------>"+endY);
                break;
            case MotionEvent.ACTION_UP:
                float dx = 0;
                float dy  = 0;
                dx = endX-startX;
                dy = endY-startY;
                if(Math.abs(dx)<40&&Math.abs(dy)<40){
                    startX = 0;
                    startY = 0;
                    if(mOnMenuClickListener!=null){
                        mOnMenuClickListener.onLayoutClick(this);
                        isOpen = ((CoolMenuFrameLayout) getParent()).isOpening();
                    }
                    return true;
                }
                Log.e("ACTION_UP","dx"+"---->"+dx+"dy"+"------>"+dy);
                if(Math.abs(dx)>Math.abs(dy)){
                    if(dx>300){
                        Log.e("ACTION_UP","---------->"+dx+"  dx > 300");
                        if(!isOpen){
                            if (mOnMenuClickListener != null) {
                                mOnMenuClickListener.onMenuClick();
                                isOpen =((CoolMenuFrameLayout) getParent()).isOpening();
                            }
                        }

                    }else if(dx<-200) {
                        Log.e("ACTION_UP","---------->"+dx+"  dx < -300");
                        if(isOpen){
                            if (mOnMenuClickListener != null) {
                                mOnMenuClickListener.onMenuClick();
                                isOpen = ((CoolMenuFrameLayout) getParent()).isOpening();
                            }

                        }

                    }
                }
                startX = 0;
                startY = 0;
                break;


        }
        return true;
    }
}
