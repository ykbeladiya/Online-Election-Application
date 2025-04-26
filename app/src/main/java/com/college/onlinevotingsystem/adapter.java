package com.college.onlinevotingsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class adapter extends BaseAdapter {

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

//        final CheckBox checkBox;
        RadioButton radioButton;
        TextView t1, t2;
        t1 = view.findViewById(R.id.candidate_name);
        t2 = view.findViewById(R.id.candidate_enrolmentno);
        radioButton=view.findViewById(R.id.radioButton);
//        checkBox = view.findViewById(R.id.checkBox);
        t1.setText(c_name.get(i));
        t2.setText(c_eno.get(i));

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    alertbox(i);
                }
            }
        });

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                if(b)
//                {
//                    alertbox(i);
//                }

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
//            }
//        });
        return view;
    }

    void alertbox(final int pos) {
        new AlertDialog.Builder(selectionActivity)
                .setTitle("please confirm Selected Candidate")
                .setMessage("You are Select:"+"\n"+c_name.get(pos)+"\t"+c_eno.get(pos)+"\n")
                .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(selectionActivity,SelectionActivity.class);
                        selectionActivity.startActivity(intent);
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(selectionActivity,LastActivity.class);
                        intent.putExtra("Eno1",c_eno.get(pos));
                        selectionActivity.startActivity(intent);
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        alertbox(pos);
                    }
                })
                .show();
    }
}