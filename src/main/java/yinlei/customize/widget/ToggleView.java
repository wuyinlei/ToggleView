package yinlei.customize.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import yinlei.customize.R;

/**
 * Created by wuyin on 2016/5/16.
 * <p/>
 * Android 界面的绘制流程
 * <p/>
 * measure（测量）   -->   layout（摆放）   -->   draw（绘制）
 * <p/>
 * onMeasure       -->    onLayout       -->    onDraw    需要重写的方法
 * <p/>
 * View中
 * <p/>
 * onMeasure(在这个方法中指定自己的宽高)onSizeChanged（这个方法也是可以的）
 * <p/>
 * onLayout（摆放自己的位置）
 * <p/>
 * onDraw(绘制)
 */
public class ToggleView extends View {

    //选择图片
    private Bitmap slideButton;

    //背景
    private Bitmap switchBackground;

    //画笔
    private Paint mPaint;

    //开关状态  默认关闭
    private boolean mSwitchState = false;

    private float currentX;

    /**
     * 用于代码创建控件
     *
     * @param context
     */
    public ToggleView(Context context) {
        this(context, null);
    }


    /**
     * 用于在xml中使用，可以指定自定义属性
     *
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 用于在xml中,如果指定了样式，就走此构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            String namespace = "http://schemas.android.com/apk/res-auto";
            int switchBackgroundResource = attrs.getAttributeResourceValue(namespace,"switch_background",-1);
            int slideButtonResource = attrs.getAttributeResourceValue(namespace,"slide_button",-1);
            mSwitchState = attrs.getAttributeBooleanValue(namespace,"switch_state",false);

            setSwitchBackground(switchBackgroundResource);
            setSlideButtonResource(slideButtonResource);
        }

        init();
    }

    private void init() {
        mPaint = new Paint();

    }


    /**
     * 设置背景图片
     *
     * @param switch_background
     */
    public void setSwitchBackground(int switch_background) {
        switchBackground = BitmapFactory.decodeResource(getResources(), switch_background);
    }

    /**
     * 设置滑块的图片资源
     *
     * @param slide_button
     */
    public void setSlideButtonResource(int slide_button) {
        slideButton = BitmapFactory.decodeResource(getResources(), slide_button);
    }

    /**
     * 设置开关状态
     *
     * @param state
     */
    public void setSwitchState(boolean state) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackground.getWidth(), switchBackground.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //1、绘制背景
        canvas.drawBitmap(switchBackground, 0, 0, mPaint);

        //根据当前用户的触摸位置来画
        if (isTouchState) {
            //让滑块向左绘制自身大小的一半
            float newLeft = (float) (currentX - slideButton.getWidth() / 2.0);
            int maxRight = switchBackground.getWidth() - slideButton.getWidth();
            //限定滑块的范围
            if (newLeft < 0) {
                newLeft = 0;  //最左边范围
            }
            if (newLeft > maxRight) {
                newLeft = maxRight;   //最右边范围
            }
            canvas.drawBitmap(slideButton, newLeft, 0, mPaint);

        } else if (mSwitchState) {

            //根据开关状态直接设置图片的位置
            int newLeft = switchBackground.getWidth() - slideButton.getWidth();
            canvas.drawBitmap(slideButton, newLeft, 0, mPaint);
        } else {
            //2、绘制滑块
            canvas.drawBitmap(slideButton, 0, 0, mPaint);
        }

    }

    boolean isTouchState = false;

    /**
     * 重写触摸事件，响应用户的手势
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                currentX = event.getX();
                isTouchState = true;

                break;
            case MotionEvent.ACTION_MOVE:
                isTouchState = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchState = false;
                currentX = event.getX();
                float center = switchBackground.getWidth() / 2;

                boolean state = currentX > center;

                //如果开关状态变化了，通知界面
                if (state != mSwitchState && mListener != null) {
                    //把最先的状态传递进去
                    mListener.onStateUpdate(state);
                }

                mSwitchState = state;


                break;
            default:

                break;
        }

        //重绘界面
        invalidate();  //会引发onDraw被调用

        return true;  //处理触摸事件，不传递
    }

    private OnSwitchStateUpdateClickListener mListener;


    public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateClickListener stateUpdateListener) {
        mListener = stateUpdateListener;
    }

    //设置点击监听回调

    public interface OnSwitchStateUpdateClickListener {
        //状态的传出
        void onStateUpdate(boolean state);
    }
}
