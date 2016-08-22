package com.poplitou.orderpager.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.poplitou.orderpager.R;
import com.poplitou.orderpager.fragments.LoginFragment;
import com.poplitou.orderpager.utils.AuthUtils;

/**
 * This activity is shown upon first use of the app or when the user logs out.
 */
public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);

        setupSlides();

    }

    private void setupSlides() {

        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_welcome_slide_title)
                .description(R.string.intro_welcome_slide_description)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .build());

        if(!AuthUtils.isSignedIn()) {
            addSlide(new FragmentSlide.Builder()
                    .background(R.color.colorPrompt)
                    .backgroundDark(R.color.colorPromptDark)
                    .fragment(new LoginFragment())
                    .build());
        }

        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_complete_slide_title)
                .description(R.string.intro_complete_slide_description)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .canGoBackward(false)
                .build());
    }
}
