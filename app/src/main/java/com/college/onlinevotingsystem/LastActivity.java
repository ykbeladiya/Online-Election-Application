package com.college.onlinevotingsystem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class LastActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button homebtn;
    GifImageView gifImageView;
    TextView textView;
    FirebaseFirestore db;
    long total;
    String Eno1, Year, Branch, EnrolmentNo;
    SharedPreferences prefs;
    int flage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.LastProg);
        homebtn = findViewById(R.id.Button_last);
        gifImageView = findViewById(R.id.ThanksForVotinggif_last);
        textView = findViewById(R.id.textView_last);


        Eno1 = getIntent().getExtras().getString("Eno1");

        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        Year = prefs.getString("Year", "0");
        Branch = prefs.getString("Branch", "0");
        EnrolmentNo = prefs.getString("EnrolmentNo", "0");

        gettotal();

         /*if(Eno1.equals("None")||Eno2.equals("None"))
         {
             flage++;
         }

         Branch="Co-e";
         Year="1";
         setVote();

     }

     void setVote()
     {
//         if(!Year.equals("0") && !Branch.equals("0") && !EnrolmentNo.equals("0") )
         {
            if(!Eno1.equals("None")) {
                db.collection("Voter").document(Branch).collection(Year).document(Eno1).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Long no=documentSnapshot.getLong("Total");
                                Eno1Inc(no);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flage++;
                            }
                        });
            }

             if(!Eno2.equals("None"))
            {
                db.collection("Voter").document(Branch).collection(Year).document(Eno2).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Long no=documentSnapshot.getLong("Total");
                                Eno2Inc(no);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flage++;
                            }
                        });
            }

         }

     }

     void Eno1Inc(Long no)
     {
         no++;
             Map<String, Object> status = new HashMap<>();
             status.put("Total", no);

          db.collection("Voter").document(Branch).collection(Year).document(Eno1).update(status)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          statusChange();
                      }
                  })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });
     }

    void Eno2Inc(Long no)
    {
        no++;
        Map<String, Object> status = new HashMap<>();
        status.put("Total", no);

        db.collection("Voter").document(Branch).collection(Year).document(Eno2).update(status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        statusChange();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    void statusChange()
    {
        if(flage==2)
        {
            Map<String, Object> status = new HashMap<>();
            status.put("Status", true);

            db.collection("Voter").document(Branch).collection(Year).document(EnrolmentNo).update(status)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LastActivity.this, "Something Want to wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }*/
    }

    private void gettotal() {

        db.collection("Voter").document(Branch).collection(Year).document(Eno1).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        total=documentSnapshot.getLong("Total");
                        total++;
                        setstatus(total);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Somrthing Want To \n Wrong !");
                    }
                });
}

    void setstatus(long Tvote)
    {

        Map<String, Object> Totalstatus = new HashMap<>();
        Totalstatus.put("Total", Tvote);
        db.collection("Voter").document(Branch).collection(Year).document(Eno1).update(Totalstatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        UserUpdate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Somrthing Want To \n Wrong !");
                    }
                });

    }


    void UserUpdate()
    {
        Map<String, Object> status = new HashMap<>();
        status.put("Status", true);
        status.put("Vote1", Eno1);
        db.collection("Voter").document(Branch).collection(Year).document(EnrolmentNo).update(status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        gifImageView.setVisibility(View.VISIBLE);
                        homebtn.setVisibility(View.VISIBLE);
                        textView.setText("Thank You For \n Voting");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Somrthing Want To \n Wrong !");
                    }
                });
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "Don't Press back Button !", Toast.LENGTH_SHORT).show();
//         super.onBackPressed();
    }

    public void HomebtnClick(View view) {
        Intent intent = new Intent(LastActivity.this, Main2Activity.class);
        startActivity(intent);
    }
}


//             Map<String, Object> status = new HashMap<>();
//             status.put("Status", true);
//             status.put("Vote1",Eno1);
//             status.put("Vote2",Eno2);
//
//          db.collection("Voter").document(Branch).collection(Year).document(EnrolmentNo).set(status)
//                  .addOnSuccessListener(new OnSuccessListener<Void>() {
//                      @Override
//                      public void onSuccess(Void aVoid) {
//
//                      }
//                  })
//                  .addOnFailureListener(new OnFailureListener() {
//                      @Override
//                      public void onFailure(@NonNull Exception e) {
//
//                      }
//                  });ss