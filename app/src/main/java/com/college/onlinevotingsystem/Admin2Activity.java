package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Admin2Activity extends AppCompatActivity {

    Switch result,election;
    FirebaseFirestore db;
    boolean resultstatus,electionstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        db = FirebaseFirestore.getInstance();

        result=findViewById(R.id.resultSwitch);
        election=findViewById(R.id.electionSwitch);

        resultstatus=getIntent().getExtras().getBoolean("result");
        electionstatus=getIntent().getExtras().getBoolean("election");

        result.setChecked(resultstatus);
        election.setChecked(electionstatus);

        result.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

                Map<String, Object> status = new HashMap<>();
                status.put("Status", b);
                db.collection("Result").document("Election").update(status)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(b)
                                {
                                    Toast.makeText(Admin2Activity.this, "Result Display", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Admin2Activity.this, "Result Not Display", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        election.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton,final boolean b) {

                Map<String, Object> status = new HashMap<>();
                status.put("Status", b);
                db.collection("Schedual").document("Election").update(status)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(b)
                                {
                                    Toast.makeText(Admin2Activity.this, "Election Start", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Admin2Activity.this, "Election Stop", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    public void HomebtnClick(View view) {
        Intent intent=new Intent(Admin2Activity.this,Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Admin2Activity.this,Main2Activity.class);
        startActivity(intent);
//        super.onBackPressed();
    }
}