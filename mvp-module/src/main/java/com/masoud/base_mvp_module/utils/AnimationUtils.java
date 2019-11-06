package com.masoud.base_mvp_module.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.masoud.base_mvp_module.R;

/**
 * Created by Masoud pc on 6/14/2018.
 */
public class AnimationUtils {

    private static AnimationUtils instance;

    private static Animation abc_slide_in_bottom, abc_slide_out_bottom;
    private int enterAnimation;
    private int exitAnimation;
    private boolean isCircular = true;

    private AnimationUtils() {

    }

    public static AnimationUtils getInstance() {

        if (instance == null)
            instance = new AnimationUtils();
        return instance;
    }


    public static AnimationUtils createInstance(int enterAnimation, int exitAnimation, boolean isCircular) {

        return new AnimationUtils().setEnterAnimation(enterAnimation).setExitAnimation(exitAnimation).setCircular(isCircular);
    }

    public static AnimationUtils fragmentTransication() {

        return new AnimationUtils()
                .setEnterAnimation(android.R.anim.slide_in_left)
                .setExitAnimation(android.R.anim.slide_out_right);

    }

    public static Animation createButtonAnimation(Context context, boolean in) {

        if (in) {

            if (abc_slide_in_bottom == null)
                return abc_slide_in_bottom = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);

            return abc_slide_in_bottom;

        } else {

            if (abc_slide_out_bottom == null)
                return abc_slide_out_bottom = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.abc_slide_out_bottom);
            else
                return abc_slide_out_bottom;
        }

    }

    public int getEnterAnimation() {
        return enterAnimation;
    }

    public AnimationUtils setEnterAnimation(int enterAnimation) {
        this.enterAnimation = enterAnimation;
        return this;
    }

    public int getExitAnimation() {
        return exitAnimation;
    }

    public AnimationUtils setExitAnimation(int exitAnimation) {
        this.exitAnimation = exitAnimation;
        return this;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public AnimationUtils setCircular(boolean circular) {
        isCircular = circular;
        return this;
    }

    public void slideIn(final View view, final boolean rToL) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.f);

        if (view.getWidth() > 0) {
            slideInNow(view, rToL);
        } else {
            // wait till height is measured
            view.post(new Runnable() {
                @Override
                public void run() {
                    slideInNow(view, rToL);
                }
            });
        }
    }

    private void slideInNow(final View view, boolean rToL) {

        if (rToL) {

            view.setTranslationX(-view.getWidth());
            view.animate()
                    .translationX(0)
                    .alpha(1.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.VISIBLE);
                            view.setAlpha(1.f);
                        }
                    });

        } else {

            view.setTranslationX(2 * view.getWidth());
            view.animate()
                    .translationX(0)
                    .alpha(1.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.VISIBLE);
                            view.setAlpha(1.f);
                        }
                    });

        }
    }

    public void slideOut(final View view, boolean rToL) {

        if (rToL) {
            view.animate()
                    .translationX(-view.getWidth())
                    .alpha(0.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // superfluous restoration
                            view.setVisibility(View.GONE);
                            view.setAlpha(1.f);
                            view.setTranslationY(0.f);
                        }
                    });
        } else {

            view.animate()
                    .translationX(view.getWidth())
                    .alpha(0.f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // superfluous restoration
                            view.setVisibility(View.GONE);
                            view.setAlpha(1.f);
                            view.setTranslationY(0.f);
                        }
                    });
        }


    }

    public void visibleToDown(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void hideToUp(final View view, Animation.AnimationListener listener) {


        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                -view.getHeight()-100); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(listener);
        view.startAnimation(animate);
    }

    public void rotation(View view , int degree){

        view.animate().rotation(degree).start();

    }

}
