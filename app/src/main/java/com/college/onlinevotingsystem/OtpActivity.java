package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    String Mobile,Mstr;
    private FirebaseAuth mAuth;
    Pinview pinview;
    String mVerificationId;
    TextView mobilestr;
    Button bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();

        mobilestr=findViewById(R.id.MobileStr);
        pinview=findViewById(R.id.pinview);
        bn=findViewById(R.id.verifyButton);
        Mobile=getIntent().getExtras().getString("MobileNo");
        Mstr=Mobile.substring(6,10);
        mobilestr.setText("OTP send on mobile number\n XXXXXX"+Mstr);

        sendvarificationcode();

//        direct();

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
                    @Override
                    public void onDataEntered(Pinview pinview, boolean fromUser) {
//                        Toast.makeText(OtpActivity.this, "Pin="+pinview.getValue(), Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    Verifycode(pinview.getValue());
                }
                catch (Exception e)
                {

                }
            }
        });
    }

    private void Verifycode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);

    }

    private void sendvarificationcode() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+Mobile,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {


            String code=credential.getSmsCode();
            if(code!=null)
            {
                pinview.setValue(code);
                Verifycode(code);
            }
            //   signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //Log.w(TAG, "onVerificationFailed", e);
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }
            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            //Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            // ...
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //  Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent=new Intent(OtpActivity.this,SelectionActivity.class);
                            startActivity(intent);

                        } else {
                            // Sign in failed, display a message and update the UI
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    void direct()
    {
        Intent intent=new Intent(OtpActivity.this,SelectionActivity.class);
        startActivity(intent);
    }


}
