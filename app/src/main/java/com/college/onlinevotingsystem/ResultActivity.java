package com.college.onlinevotingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

//    ProgressBar progressBar;
//    Spinner spinner;
//    JSONArray jar;
//    String Branch, Year;
//    JSONObject job;
//    ListView listView;
//    List list;
//    TextView t1;
//    ArrayList Name_list, Count;

    Spinner branch,year;
    ProgressBar progressBar;
    ListView listView;
    List<Long> C_Total;
    ArrayAdapter adapter1;
    List<String> C_Name, C_Eno;
    String Branch,Year;
    FirebaseFirestore db;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        db = FirebaseFirestore.getInstance();

        progressBar=findViewById(R.id.progressBar5);
        listView=findViewById(R.id.result_list);
        C_Name = new ArrayList<String>();
        C_Total=new ArrayList<Long>();
        C_Eno = new ArrayList<String>();

        branch = findViewById(R.id.Branch);
        year = findViewById(R.id.Year);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.Branch, android.R.layout.simple_list_item_single_choice);
        branch.setAdapter(adapter1);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_list_item_single_choice);
        year.setAdapter(adapter1);

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Branch = getResources().getStringArray(R.array.Branch)[i];
                    if(Year!="")
                    {
                        getdata();
                    }
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
                    if(Branch!="")
                    {
                        getdata();
                    }
                } else {
                    Year = "";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void getdata()
    {
        progressBar.setVisibility(View.VISIBLE);
        C_Name.clear();
        C_Total.clear();
        C_Eno.clear();
        db.collection("Voter").document(Branch).collection(Year).whereEqualTo("Candidate", true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (int i = 0; i < task.getResult().size(); i++) {
                            C_Name.add(task.getResult().getDocuments().get(i).getString("Name"));
                            C_Total.add(task.getResult().getDocuments().get(i).getLong("Total"));
                            C_Eno.add(task.getResult().getDocuments().get(i).getId());
                        }
                        Adapter adapter = new ResultAdapter(ResultActivity.this, C_Name, C_Eno,C_Total);
                        listView.setAdapter((ListAdapter) adapter);
                        progressBar.setVisibility(View.GONE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ResultActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
    /*{
{
    t1=findViewById(R.id.resulttxt);
    t1.setVisibility(View.GONE);
    spinner=findViewById(R.id.ResultNo);
    jar=new JSONArray();
    listView=findViewById(R.id.result_list);
    Branch=getIntent().getExtras().getString("Branch").toLowerCase();
    progressBar=findViewById(R.id.progressBar5);
    jar=new JSONArray();
    Name_list=new ArrayList();
    Count=new ArrayList();

    listView.setVisibility(View.GONE);

    list=new ArrayList();
    list.add("-Select Year-");
    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4");

    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(dataAdapter);

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if(i>0)
            {
                Year=list.get(i).toString();
                datarequest();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    });
}

//    public void resultclick(View view) {
//        Name_list.clear();
//        Count.clear();
//        Toast.makeText(this, Year+" "+Branch, Toast.LENGTH_SHORT).show();
//        datarequest();
//    }

        z

        void alertbox(String msg) {
            new AlertDialog.Builder(ResultActivity.this)
                    .setTitle("Internet Error")
                    .setMessage(msg)
                    .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            datarequest();
                        }
                    }).show();
        }
    }*/