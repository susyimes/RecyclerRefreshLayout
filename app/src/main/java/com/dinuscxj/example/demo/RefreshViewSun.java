package com.dinuscxj.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.dinuscxj.example.R;
import com.dinuscxj.refresh.IRefreshStatus;

/**
 * the default implementation class of the interface IRefreshStatus, and the class should always be rewritten
 */
public class RefreshViewSun extends android.support.v7.widget.AppCompatImageView implements IRefreshStatus {
    private static final int ANIMATION_DURATION = 150;
    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();

    private Animation mRotateAnimation;
    private Animation mResetRotateAnimation;

    public RefreshViewSun(Context context) {
        this(context, null);
    }

    public RefreshViewSun(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAnimation();
    }

    private void initView() {
        this.setScaleType(ScaleType.CENTER);
        this.setImageResource(R.drawable.loading_n);
    }

    private void initAnimation() {
        mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ANIMATION_DURATION);
        mRotateAnimation.setFillAfter(true);

        mResetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mResetRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mResetRotateAnimation.setDuration(ANIMATION_DURATION);
        mResetRotateAnimation.setFillAfter(true);
    }

    @Override
    public void reset() {
        clearAnimation();

        //this.setImageResource(R.drawable.loading_n);
    }

    @Override
    public void refreshing() {
        clearAnimation();
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.sun);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        this.setAnimation(operatingAnim);
        this.startAnimation(operatingAnim);

    }

    @Override
    public void refreshComplete() {
        clearAnimation();
        this.setVisibility(GONE);
    }

    @Override
    public void pullToRefresh() {
        clearAnimation();
        this.setVisibility(VISIBLE);
        if (getAnimation() == null || getAnimation() == mResetRotateAnimation) {
            startAnimation(mRotateAnimation);
        }
    }

    @Override
    public void releaseToRefresh() {
        clearAnimation();

        if (getAnimation() == null || getAnimation() == mRotateAnimation) {
            startAnimation(mResetRotateAnimation);
        }
    }

    @Override
    public void pullProgress(float pullDistance, float pullProgress) {

    }
}
