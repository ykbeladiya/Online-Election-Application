package com.college.onlinevotingsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ProgressBar progressBar;
    JSONArray jar;
    JSONObject job;
    final List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.Branch);
        progressBar=findViewById(R.id.progressBar);
        spinner.setEnabled(false);

        list.add("- -Select Branch- -");

        datarequest();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             //   Toast.makeText(MainActivity.this, "pos="+id, Toast.LENGTH_SHORT).show();

                if(position>0) {
                    String str = list.get(position);
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("Branch",str);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void datarequest()
    {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://beladiya.000webhostapp.com/OVS_DB/Select_Branch.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jar=new JSONArray(response);

                            for(int i=0;i<jar.length();i++)
                            {
                                job=jar.getJSONObject(i);
                                list.add(job.getString("Branch_Name"));
                            }
                            progressBar.setVisibility(View.GONE);
                            spinner.setEnabled(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Alertmessege();
            }
        });
        queue.add(stringRequest);
    }
    void Alertmessege()
    {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("No internet")
                .setMessage("Check internet connection and retry")
                .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        list.isEmpty();
                        datarequest();
                    }
                })
                .show();
    }
}
