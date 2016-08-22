package com.heinrichreimersoftware.materialintro.app;

import android.support.v4.app.Fragment;

public class SlideFragment extends Fragment {

    public boolean canGoForward() {
        return true;
    }

    public boolean canGoBackward() {
        return true;
    }

    public void updateNavigation() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).lockSwipeIfNeeded();
        }
    }

    protected void nextSlide() {
        nextSlide(false);
    }

    protected void nextSlide(boolean ignoreRestriction) {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).nextSlide(ignoreRestriction);
        }
    }

    protected void previousSlide() {
        previousSlide(false);
    }

    protected void previousSlide(boolean ignoreRestriction) {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).previousSlide(ignoreRestriction);
        }
    }

    public void hideNavButton() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).hideNavButton();
        }
    }

    public void showNavButton() {
        if (getActivity() instanceof IntroActivity) {
            ((IntroActivity) getActivity()).showNavButton();
        }
    }

    public boolean onNextButtonClick() {
        return true;
    }

    public boolean onPreviousButtonClick() {
        return true;
    }

}
