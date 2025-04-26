package com.college.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class FingurPrintAuthActivity extends AppCompatActivity {

    TextView t1,mParaLabel;
    ImageView imageView;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private String Eno1,Eno2;

    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingur_print_auth);
        t1=findViewById(R.id.Fingurprint_header);
        mParaLabel=findViewById(R.id.Fingurprint_text);
        imageView=findViewById(R.id.Fingurprint_img);




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){

                mParaLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

                mParaLabel.setText("Give a Permission to access FingurPrint"+"\n"+" And Try Again...");

            } else if (!keyguardManager.isKeyguardSecure()){

                mParaLabel.setText("There is No Security !!"+"\n"+" You Need to Add Security on your Device"+"\n"+" And Try Again...");

            } else if (!fingerprintManager.hasEnrolledFingerprints()){

                mParaLabel.setText("Add Atlist One FingerPrint For Votting !!"+"\n"+" And Try Again...");

            } else {

                mParaLabel.setText("Place your Finger on Scanner to Conform Votting");
                generateKey();

                if (cipherInit()){

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    fingureprinthandler fingerprintHandler = new fingureprinthandler(this,getIntent().getExtras().getString("Eno1"),getIntent().getExtras().getString("Eno2"));
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);

                }
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (Exception e) {
            return false;
        }

    }

}