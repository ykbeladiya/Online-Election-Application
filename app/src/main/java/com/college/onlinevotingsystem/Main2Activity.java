package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Main2Activity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce;
    ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progressBar=findViewById(R.id.main2progressBar);

    }

    public void resultbtn(View view) {
        progressBar.setVisibility(View.VISIBLE);

        db.collection("Result").document("Election").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getBoolean("Status"))
                        {
                            progressBar.setVisibility(View.GONE);
                            Intent intent=new Intent(Main2Activity.this,ResultActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Main2Activity.this, "RESULT IS NOT DISPLAY !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void voterbtn(View view) {
        progressBar.setVisibility(View.VISIBLE);

        db.collection("Schedual").document("Election").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getBoolean("Status"))
                        {
                            progressBar.setVisibility(View.GONE);
                            Intent intent=new Intent(Main2Activity.this,VerificationActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Main2Activity.this, "ELECTION IS NOT STARTED !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
  }

    public void OnClickAdmin(View view) {
        Intent intent = new Intent(Main2Activity.this, AdminFirstActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
