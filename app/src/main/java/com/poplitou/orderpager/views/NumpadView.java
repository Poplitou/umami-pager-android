package com.poplitou.orderpager.views;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.poplitou.orderpager.R;

/**
 * Created by quent on 2016-07-24.
 */
public class NumpadView extends FrameLayout implements View.OnClickListener {

    StringBuilder mBuilder = new StringBuilder();

    private OnNumpadClickListener mOnNumpadClickListener;
    public interface OnNumpadClickListener {
        void onNumpadClickListener(int num);
    }

    public NumpadView(Context context) {
        super(context);
        init();
    }

    public NumpadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumpadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.numpad, this);
        initViews();
    }

    private void initViews() {
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_clear).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
    }

    public void setOnNumpadClickListener(OnNumpadClickListener onNumpadClickListener) {
        mOnNumpadClickListener = onNumpadClickListener;
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            if(mBuilder.length() < 4)
                mBuilder.append(((TextView) v).getText());
        }
        switch (v.getId()) {
            case R.id.t9_key_clear: { // handle clear button
                mBuilder.setLength(0);
            }
            break;
            case R.id.t9_key_backspace: { // handle backspace button
                if(mBuilder.length() != 0)
                    mBuilder.deleteCharAt(mBuilder.length() - 1);
            }
            break;
        }

        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        if(mOnNumpadClickListener != null) {
            if(mBuilder.length() > 0) mOnNumpadClickListener.onNumpadClickListener(Integer.parseInt(mBuilder.toString()));
            else mOnNumpadClickListener.onNumpadClickListener(0);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mOnNumpadClickListener = null;
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
}
