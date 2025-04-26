package com.college.onlinevotingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingureprinthandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private String Eno1,Eno2;

    public fingureprinthandler(Context context,String Eno1,String Eno2) {

        this.context = context;
        this.Eno1=Eno1;
        this.Eno2=Eno2;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal,0,this,null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);
        Intent intent=new Intent(context,SelectionActivity.class);
        Toast.makeText(context, ""+errString, Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now access the app.", true);
        Intent intent=new Intent(context,LastActivity.class);
        intent.putExtra("Eno1", Eno1);
        intent.putExtra("Eno2", Eno2);
        context.startActivity(intent);

    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity) context).findViewById(R.id.Fingurprint_text);
        ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.Fingurprint_img);

        paraLabel.setText(s);

        if (b == false) {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        } else {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.ic_launcher_fingurprint_done);

        }

    }
}
