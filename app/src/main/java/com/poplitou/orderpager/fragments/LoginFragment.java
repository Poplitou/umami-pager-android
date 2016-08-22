package com.poplitou.orderpager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.poplitou.orderpager.R;

import butterknife.BindView;

/**
 * Fragment to be used during the Intro Slide to allow the user to sign in.
 * TODO: add a password forget function
 */
public class LoginFragment extends SlideFragment {

    FirebaseAuth mAuth;

    EditText mEmailInput;
    EditText mPasswordInput;
    LinearLayout mProgress;

    @Override
    public boolean canGoForward() {
        return false;
    }

    @Override
    public boolean canGoBackward() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_login, container, false);

        mEmailInput = (EditText) view.findViewById(R.id.fragment_slide_login_email);
        mPasswordInput = (EditText) view.findViewById(R.id.fragment_slide_login_password);
        mProgress = (LinearLayout) view.findViewById(R.id.fragment_slide_login_progress);

        return view;
    }

    @Override
    public boolean onNextButtonClick() {
        signIn();
        return false;

    }

    /**
     * Sign in function. Is called when the user press the next button.
     */
    private void signIn() {
        String email = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        boolean error = false;

        //Checking for input mistakes
        if(email.isEmpty()) {
            mEmailInput.setError(getContext().getString(R.string.error_email_empty));
            error = true;
        }
        if(password.isEmpty()) {
            mPasswordInput.setError(getContext().getString(R.string.error_password_empty));
            error = true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailInput.setError(getContext().getString(R.string.error_email_invalid));
            error = true;
        }

        if(error) {
            //Mistake found, shake the next button and return.
            nextSlide();
            return;
        }

        //No mistakes, hide nav button and carry on with the sign in process.
        hideNavButton();

        mProgress.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                            //Successful, move to the next slide
                            nextSlide(true);
                            showNavButton();
                    }
        })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Not successful, try again
                        mProgress.setVisibility(View.GONE);
                        showNavButton();

                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        nextSlide();
                    }
        });
    }
}
