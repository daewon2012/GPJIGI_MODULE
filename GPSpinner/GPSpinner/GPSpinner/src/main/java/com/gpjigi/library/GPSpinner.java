package com.gpjigi.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gpjigi.mylibrary.R;

import java.util.ArrayList;
import java.util.List;


class Item {

    String mTime;
    String mUnit;

    Item(String time, String unit) {
        mTime = time;
        mUnit = unit;
    }
}

public class GPSpinner extends LinearLayout {

    private String TAG = GPSpinner.class.getSimpleName();

    class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

        private String TAG = SwipeGestureListener.class.getSimpleName();
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                    return true;
                }
            }
            return false;
        }

        private void onSwipeRight() {
            Log.d(TAG, "onSwipeRight()");
        }

        private void onSwipeLeft() {
            Log.d(TAG, "onSwipeLeft()");
        }

        private void onSwipeUp() {
            Log.d(TAG, "onSwipeUp()");

            mIndex++;
            Log.d(TAG, "mIndex: " + mIndex);
            if (mIndex >= mValue.length - 4) {
                mIndex = mValue.length - 5;
            }
            Log.d(TAG, "mIndex: " + mIndex);
            for (int i = 0; i < mCount; i++) {
                mTextViews[i].setText(mValue[i + mIndex][0]);
            }
            mTextViewTime.setText(mValue[mIndex + 2][1]);

        }

        private void onSwipeDown() {
            Log.d(TAG, "onSwipeDown()");

            mIndex--;
            Log.d(TAG, "mIndex: " + mIndex);
            if (mIndex < 0) {
                mIndex = 0;
            }
            for (int i = 0; i < mCount; i++) {
                mTextViews[i].setText(mValue[i + mIndex][0]);
            }
            mTextViewTime.setText(mValue[mIndex + 2][1]);

        }
    }

    private int mCount = 5;
    private TextView[] mTextViews = new TextView[mCount];
    private TextView mTextViewTime;
    private int mIndex = 0;
    private GPSpinnerOnClickListener mGpSpinnerOnClickListener;
    private int mDisplayCount = 0;
    private GestureDetector mGestureDetector;

    private String mValue[][] = {
        {"", ""},
        {"", ""},
        {"20", "Unit"},
        {"30", "Unit"},
        {"60", "Unit"},
        {"90", "Unit"},
        {"100", "Unit"},
        {"200", "Unit"},
        {"300", "Unit"},
        {"", ""},
        {"", ""},
    };

    public GPSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public GPSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GPSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }


    private void init(Context context, AttributeSet attrs) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.number_gpspinner, this, true);

        LinearLayout linearLayout = view.findViewById(R.id.np_linearLayout_list);
        linearLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(TAG, "NumberPicker2 > keyCode: " + keyCode);
                if (keyCode == 19) { //go up
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        mIndex--;
                        Log.d(TAG, "mIndex: " + mIndex);
                        if (mIndex < 0) {
                            mIndex = 0;
                        }
                        for (int i = 0; i < mCount; i++) {
                            mTextViews[i].setText(mValue[i + mIndex][0]);
                        }
                        mTextViewTime.setText(mValue[mIndex + 2][1]);
                        return true;
                    }
                    return true;
                } else if (keyCode == 20) { //go down
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        mIndex++;
                        Log.d(TAG, "mIndex: " + mIndex);
                        if (mIndex >= mValue.length - 4) {
                            mIndex = mValue.length - 5;
                        }
                        Log.d(TAG, "mIndex: " + mIndex);
                        for (int i = 0; i < mCount; i++) {
                            mTextViews[i].setText(mValue[i + mIndex][0]);
                        }
                        mTextViewTime.setText(mValue[mIndex + 2][1]);
                        return true;
                    }
                } else if (keyCode == 23) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (mGpSpinnerOnClickListener != null) {
                            mGpSpinnerOnClickListener.onClick(mIndex);
                        }
                    }
                }

                return false;
            }
        });

        mGestureDetector = new GestureDetector(this.getContext(), new SwipeGestureListener());
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });




        mTextViews[0] = linearLayout.findViewById(R.id.ng_textView);
        mTextViews[1] = linearLayout.findViewById(R.id.ng_textView2);
        mTextViews[2] = linearLayout.findViewById(R.id.ng_textView3);
        mTextViews[3] = linearLayout.findViewById(R.id.ng_textView4);
        mTextViews[4] = linearLayout.findViewById(R.id.ng_textView5);
        mTextViewTime = linearLayout.findViewById(R.id.ng_textView3_time);

        for (int i = 0; i < mCount; i++) {
            mTextViews[i].setText(mValue[i][0]);
        }
        mTextViewTime.setText(mValue[mIndex + 2][1]);
    }

    public void setOnGPClickListenr(GPSpinnerOnClickListener listener) {
        mGpSpinnerOnClickListener = listener;
    }

}
