package com.highest.paying.commonutils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageInflate extends RelativeLayout {
    private ImageView ivImage;
    private LottieAnimationView ltLottie;
    private ProgressBar progressBar;

    //Default Values
    private boolean lottieAutoPlay = true;
    private boolean lottieLoop = true;
    private boolean adjustViewBound = true;
    private int progressBarColor = Color.BLACK;
    private int progressBarSize = 48;

    public ImageInflate(Context context) {
        super(context);
        inflateLayout(context);
    }

    public ImageInflate(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout(context, attrs);
    }

    public ImageInflate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout(context, attrs);
    }

    private void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.relative_image_inflate, this, false);

        progressBar = findViewById(R.id.progressBar);
        ivImage = findViewById(R.id.ivImage);
        ltLottie = findViewById(R.id.ltLottie);
    }

    private void inflateLayout(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.relative_image_inflate, this, true);

        progressBar = findViewById(R.id.progressBar);
        ivImage = findViewById(R.id.ivImage);
        ltLottie = findViewById(R.id.ltLottie);

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CommonImage);

            TypedArray typedArray1 = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CommonImage, 0, 0);

            boolean adjustViewBound = typedArray.getBoolean(R.styleable.CommonImage_IC_AdjustViewBound, false);
            ivImage.setAdjustViewBounds(adjustViewBound);

            int scaleType = typedArray.getInt(R.styleable.CommonImage_IC_ImageScaleType, -1);
            if (scaleType >= 0) {
                ImageView.ScaleType[] scaleTypes = ImageView.ScaleType.values();
                ivImage.setScaleType(scaleTypes[scaleType]);
            }

            lottieAutoPlay = typedArray.getBoolean(R.styleable.CommonImage_IC_LottieAutoPlay, true);
            lottieLoop = typedArray.getBoolean(R.styleable.CommonImage_IC_LottieLoop, true);

            try {
                progressBarColor = typedArray1.getColor(R.styleable.CommonImage_IC_ProgressBarColor, Color.BLACK);
                progressBarSize = typedArray1.getDimensionPixelSize(R.styleable.CommonImage_IC_ProgressBarSize, dpToPx(48));
            } finally {
                typedArray1.recycle();
            }
            typedArray.recycle();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.getIndeterminateDrawable().setColorFilter(progressBarColor, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(progressBarColor);
            progressBar.setIndeterminateTintList(colorStateList);
        }

        if (lottieLoop) {
            ltLottie.setRepeatCount(LottieDrawable.INFINITE);  // Loop the animation
        } else {
            ltLottie.setRepeatCount(0);  // Play once
        }

        if (lottieAutoPlay) {
            ltLottie.playAnimation();  // Autoplay the animation
        }

        setProgressBarSize(progressBarSize);
    }

    private void setProgressBarSize(int size) {
        LayoutParams params = (LayoutParams) progressBar.getLayoutParams();
        params.width = size;
        params.height = size;
        progressBar.setLayoutParams(params);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public void IconInflate(Context context, String icon) {
        if (icon.contains(".json")) {
            ivImage.setVisibility(View.GONE);
            ltLottie.setVisibility(View.VISIBLE);
            setLottieAnimation(ltLottie, icon);
            ltLottie.setRepeatCount(LottieDrawable.INFINITE);
            ltLottie.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(@NonNull Animator animation) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationCancel(@NonNull Animator animation) {

                }

                @Override
                public void onAnimationRepeat(@NonNull Animator animation) {

                }
            });
        } else {
            ivImage.setVisibility(View.VISIBLE);
            ltLottie.setVisibility(View.GONE);
            Glide.with(context)
                    .load(icon)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivImage);
        }
    }

    public void setLottieAnimation(LottieAnimationView ivLottie, String image) {
        try {
            ivLottie.setFailureListener(new LottieListener<Throwable>() {
                @Override
                public void onResult(Throwable result) {

                }
            });
            ivLottie.setAnimationFromUrl(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
