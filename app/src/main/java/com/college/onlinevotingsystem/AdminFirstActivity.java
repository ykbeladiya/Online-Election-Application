package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminFirstActivity extends AppCompatActivity {

    EditText A_id,A_pass;
    LinearLayout Auth,A_login;
    FirebaseFirestore db;
    ProgressBar progressBar;
    boolean result,election;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_first);

        db = FirebaseFirestore.getInstance();
        A_id=findViewById(R.id.A_id);
        A_pass=findViewById(R.id.A_psw);
        Auth=findViewById(R.id.AdminAuth);
        A_login=findViewById(R.id.login2);
        Auth.setVisibility(View.VISIBLE);
        progressBar=findViewById(R.id.progressBar_admin);



    }

    public void AdminAuthClick(final View view) {

        progressBar.setVisibility(View.VISIBLE);
        db.collection("Admin").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getString("Email").equals(A_id.getText().toString()) && document.getString("Password").equals(A_pass.getText().toString())) {
//                                    Auth.setVisibility(View.GONE);
//                                    A_login.setVisibility(View.VISIBLE);
                                    resultClick(view);
                                }
                                else
                                {
                                    Toast.makeText(AdminFirstActivity.this, "Id or Password Not Found !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(AdminFirstActivity.this, "Some thing Want to wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void resultClick(View view) {

        progressBar.setVisibility(View.VISIBLE);
        db.collection("Result").document("Election").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           result= task.getResult().getBoolean("Status");
                           flag++;
                           next();
                    }
                });

        db.collection("Schedual").document("Election").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       election=task.getResult().getBoolean("Status");
                       flag++;
                       next();
                    }
                });


    }

    void next() {
        progressBar.setVisibility(View.GONE);
        if(flag==2)
        {
            flag=0;
            Intent intent=new Intent(AdminFirstActivity.this,Admin2Activity.class);
            intent.putExtra("result",result);
            intent.putExtra("election",election);
            startActivity(intent);
        }
    }
}
