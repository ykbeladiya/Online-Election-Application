package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ConfermationActivity extends AppCompatActivity {

    ProgressBar progressBar;
    String Mobile,Mstr,Year,Branch,E_No;
    private FirebaseAuth mAuth;
    Pinview pinview;
    String mVerificationId;
    TextView mobilestr;
    ArrayList Name_list,vote;
    JSONArray jar;
    JSONObject job;
    Button bn;
    int len=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confermation);
        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.progressBar4);
        vote=new ArrayList();
        E_No=getIntent().getExtras().getString("E_No");
        Name_list=getIntent().getExtras().getStringArrayList("Name_list");
//        Toast.makeText(this, E_No, Toast.LENGTH_SHORT).show();
        mobilestr=findViewById(R.id.MobileStr2);
        pinview=findViewById(R.id.pinview2);
        bn=findViewById(R.id.verifyButton2);
        Mobile=getIntent().getExtras().getString("Mobile");
        Year=getIntent().getExtras().getString("Year");
        Branch=getIntent().getExtras().getString("Branch");
        Mstr=Mobile.substring(6,10);
        mobilestr.setText("OTP send on mobile number\n XXXXXX"+Mstr);

        datarequest();
//        sendvarificationcode();

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
                @Override
                public void onDataEntered(Pinview pinview, boolean fromUser) {
//                        Toast.makeText(OtpActivity.this, "Pin="+pinview.getValue(), Toast.LENGTH_SHORT).show();
                }
            });
            Verifycode(pinview.getValue());
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
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            //Log.d(TAG, "onVerificationCompleted:" + credential);

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

                            datarequest();
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
    void datarequest() {
           progressBar.setVisibility(View.VISIBLE);
           vote.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://beladiya.000webhostapp.com/OVS_DB/AddCount.php?year="+Year+"&branch="+Branch+"&name1="+Name_list.get(0).toString()+"&name2="+Name_list.get(1).toString()+"&e_no="+E_No+"";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

//                        Toast.makeText(ConfermationActivity.this, "Responce"+response, Toast.LENGTH_SHORT).show();
                        try {

                            jar = new JSONArray(response);

                            for (int i = 0; i < jar.length(); i++) {
                                job = jar.getJSONObject(i);
                                vote.add(job.getString("Votting"));
                            }
//                            Toast.makeText(ConfermationActivity.this, vote.get(0).toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            if (vote.get(0).toString()=="1") {
                                Intent intent = new Intent(ConfermationActivity.this, LastActivity.class);
                                intent.putExtra("Year", Year);
                                intent.putExtra("Branch", Branch);
                                intent.putExtra("Mobile", Mobile);
                                intent.putExtra("E_No", E_No);
                                intent.putExtra("Name_list", Name_list);
                                // startActivity(intent);
                            }
                            else
                            {
                                alertbox("something want to wrong");
                            }
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            alertbox(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                alertbox(error.toString());
                     }
        });
        queue.add(stringRequest);
    }

    void alertbox(String msg) {
        new AlertDialog.Builder(ConfermationActivity.this)
                .setTitle("Internet error")
                .setMessage(msg)
                .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        datarequest();
                    }
                }).show();
    }

}
