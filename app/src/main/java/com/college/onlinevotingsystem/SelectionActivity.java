package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity {

    List<String> C_Name, C_Eno;
    ProgressBar progressBar;
    LinearLayout parent;
    ListView listView;
    FirebaseFirestore db;
    int c_count = 0;
    SharedPreferences prefs;
    String Branch,Year;
    int pos1=-1,pos2=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        progressBar=findViewById(R.id.progressBar2);
        C_Name = new ArrayList<String>();
        C_Eno = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        parent = findViewById(R.id.ll_selection);
        listView = findViewById(R.id.list);

        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        Year = prefs.getString("Year", "0");
        Branch = prefs.getString("Branch", "0");

        read();

    }

    void read() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("Voter").document(Branch).collection(Year).whereEqualTo("Candidate", true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

               for (int i = 0; i < task.getResult().size(); i++) {
                            C_Name.add(task.getResult().getDocuments().get(i).getString("Name"));
                            C_Eno.add(task.getResult().getDocuments().get(i).getId());
                        }
                            C_Name.add("None");
                            C_Eno.add("None");
                        Adapter adapter = new adapter(SelectionActivity.this, C_Name, C_Eno);
                        listView.setAdapter((ListAdapter) adapter);
                        progressBar.setVisibility(View.GONE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SelectionActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please don't press back button!", Toast.LENGTH_SHORT).show();
        //super.onBackPressed();
    }

    /*public class adapter extends BaseAdapter {

        SelectionActivity selectionActivity;
        List<String> c_name;
        List<String> c_eno;

        public adapter(SelectionActivity selectionActivity, List<String> c_name, List<String> c_eno) {
            this.selectionActivity = selectionActivity;
            this.c_eno = c_eno;
            this.c_name = c_name;
        }

        @Override
        public int getCount() {
            return c_name.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) selectionActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, viewGroup, false);

            final CheckBox checkBox;
            TextView t1, t2;
            t1 = view.findViewById(R.id.candidate_name);
            t2 = view.findViewById(R.id.candidate_enrolmentno);
            checkBox = view.findViewById(R.id.checkBox);
            t1.setText(c_name.get(i));
            t2.setText(c_eno.get(i));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    alertbox("please confirm Selected Candidate","You are Select:");
                }

//                if(b)
//                {
//                    c_count++;
//
//                    if(pos1==-1)
//                    {
//                        pos1=i;
//                    }
//                    else if(pos2==-1)
//                    {
//                        pos2=i;
//                    }
//                    if(c_count==2) {
//                        alertbox("please confirm Selected Candidate","You are Select:");
//                    }else if(c_count>2) {
//                        Toast.makeText(selectionActivity, "Plz Select Only 2 candidate", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else if(!b) {
//                    if (pos2 == i)
//                    {
//                        pos2=-1;
//                    }
//                    else if(pos1==i)
//                    {
//                        pos1=-1;
//                    }
//                    c_count--;
//                }
            }
        });
            return view;
        }
    }*/
}