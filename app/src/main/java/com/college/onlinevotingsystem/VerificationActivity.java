package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VerificationActivity extends AppCompatActivity {

    LinearLayout layout;
    Button otpbtn;
    RelativeLayout TVoting;
    Spinner branch, year;
    ArrayAdapter adapter1;
    EditText Eno;
    String EnrolmentNo, Branch, Year,MobileNo;
    Boolean Status;
    FirebaseFirestore db;
    TextView Nametxt, MobileNotxt;
    ProgressBar progressBar;
    SharedPreferences.Editor editor;

    @SuppressLint({"ResourceType", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        layout=findViewById(R.id.bg);
        otpbtn=findViewById(R.id.Otpbtn);
        TVoting=findViewById(R.id.ThanksForVoting);
        db = FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.VprogressBar);
        Nametxt=findViewById(R.id.Name);
        MobileNotxt=findViewById(R.id.MobileNo);
        branch = findViewById(R.id.Branch);
        year = findViewById(R.id.Year);

        editor = getSharedPreferences("DATA", MODE_PRIVATE).edit();



        adapter1 = ArrayAdapter.createFromResource(this, R.array.Branch, android.R.layout.simple_list_item_single_choice);
        branch.setAdapter(adapter1);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_list_item_single_choice);
        year.setAdapter(adapter1);

        Eno = findViewById(R.id.E_no);

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Branch = getResources().getStringArray(R.array.Branch)[i];
                } else {
                    Branch = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Year = getResources().getStringArray(R.array.Year)[i];
                } else {
                    Year = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void getData(View view) {

        editor.putString("EnrolmentNo", null);
        editor.putString("Year", null);
        editor.putString("Branch",null);
        editor.apply();

        layout.setBackgroundColor(Color.parseColor("#ffffff"));
        Nametxt.setText("");
        MobileNotxt.setText("");
        progressBar.setVisibility(View.VISIBLE);
        otpbtn.setVisibility(View.GONE);
        TVoting.setVisibility(View.GONE);
        EnrolmentNo = Eno.getText().toString();
        if (EnrolmentNo.length()==0)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Plz Enter Enrolment Number Correctly", Toast.LENGTH_SHORT).show();
        }
        else if(Branch.equals(""))
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Plz Select Branch", Toast.LENGTH_SHORT).show();
        }
        else if(Year.equals(""))
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Plz Select Year", Toast.LENGTH_SHORT).show();
        }
        else {

            db.collection("Voter").document(Branch).collection(Year).document(EnrolmentNo).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            if (documentSnapshot.getString("Name") == null) {
                                Toast.makeText(VerificationActivity.this, "No Data Found Plz Contact to Admin", Toast.LENGTH_SHORT).show();
                            } else {
                                Nametxt.setText(documentSnapshot.getString("Name"));
                                MobileNotxt.setText("XXXXX"+documentSnapshot.getString("MobileNo").substring(5));
                                MobileNo=documentSnapshot.getString("MobileNo");
//                                Toast.makeText(VerificationActivity.this, ""+MobileNo, Toast.LENGTH_SHORT).show();
                                Status=documentSnapshot.getBoolean("Status");
                                if(Status)
                                {
                                    layout.setBackgroundColor(Color.parseColor("#6A4CE10D"));
                                    TVoting.setVisibility(View.VISIBLE);
                                }
                                else {
                                    layout.setBackgroundColor(Color.parseColor("#6A4CE10D"));
                                    otpbtn.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            }
    }

    public void getOTP(View view) {

        editor.putString("EnrolmentNo", EnrolmentNo);
        editor.putString("Year", Year);
        editor.putString("Branch",Branch);
        editor.apply();

        Intent intent=new Intent(this,OtpActivity.class);
        intent.putExtra("MobileNo",MobileNo);
        startActivity(intent);
    }

//    public void getData(View view) {
//        Name_list.clear();
//        Mobile_list.clear();
//        Year.clear();
//        vote.clear();
//        name.setText("");
//        mobiletxt.setText("");
//        if(eno.getText().length()!=0) {
//            progressBar.setVisibility(View.VISIBLE);
//            RequestQueue queue = Volley.newRequestQueue(this);
//            final String url = "https://beladiya.000webhostapp.com/OVS_DB/Student_Data.php?branch=" + Branch + "&e_no=" + eno.getText() + "";
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @SuppressLint("SetTextI18n")
//                        @Override
//                        public void onResponse(String response) {
//
////                            Toast.makeText(VerificationActivity.this, ""+response, Toast.LENGTH_SHORT).show();
//                            try {
//                                jar = new JSONArray(response);
//                                if (jar.length() == 1) {
//                                    job = jar.getJSONObject(0);
//                                    Name_list.add(job.getString("Name"));
//                                    Mobile_list.add(job.getString("Mobile"));
//                                    Year.add(job.getString("Year"));
//                                    vote.add(job.getString("Votting"));
//                                    name.setText("Name: " + Name_list.get(0).toString());
//                                    Mstr=Mobile_list.get(0).toString().substring(6,10);
//                                    mobiletxt.setText("Mobile No: XXXXXX"+Mstr);
////                                    Toast.makeText(VerificationActivity.this, "Vote="+vote.get(0).toString(), Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    Toast.makeText(VerificationActivity.this,jar.length()+" data found in our database", Toast.LENGTH_SHORT).show();
//                                }
//                                progressBar.setVisibility(View.GONE);
//
//                            } catch (JSONException e) {
////                                Toast.makeText(VerificationActivity.this, ""+e, Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(VerificationActivity.this, "something want to wrong!!", Toast.LENGTH_SHORT).show();
//                }
//            });
//            queue.add(stringRequest);
//        }
//        else
//        {
//            progressBar.setVisibility(View.GONE);
//            Toast.makeText(this, "please Enter Enrollment No ", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    public void getOTP(View view) {
//
//        if(jar.length()==1)
//        {
////            Toast.makeText(this, "Vote="+vote.get(0).toString(), Toast.LENGTH_SHORT).show();
//            if(vote.get(0).toString().equals("0"))
//            {
//                Intent intent = new Intent(VerificationActivity.this, OtpActivity.class);
//                intent.putExtra("Mobile", Mobile_list.get(0).toString());
//                intent.putExtra("Branch", Branch);
//                intent.putExtra("E_No", eno.getText().toString());
//                intent.putExtra("Year", Year.get(0).toString());
//                startActivity(intent);
//            }
//            else if(vote.get(0).toString().equals("1"))
//            {
//             //   Toast.makeText(this, "Already Votting done!!!", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(VerificationActivity.this,LastActivity.class);
//                intent.putExtra("key",1);
//                startActivity(intent);
//            }
//        }
//        else
//        {
//            Toast.makeText(this, "please enter valid Enrollment No", Toast.LENGTH_SHORT).show();
//        }
//    }
}