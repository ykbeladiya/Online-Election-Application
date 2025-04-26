package com.college.onlinevotingsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    ResultActivity resultActivity;
    List<String> c_name;
    List<String> c_eno;
    List<Long> c_Total;
    public ResultAdapter(ResultActivity resultActivity, List<String> c_name, List<String> c_eno, List<Long> c_Total) {
        this.resultActivity=resultActivity;
        this.c_eno=c_eno;
        this.c_name=c_name;
        this.c_Total=c_Total;
    }

    @Override
    public int getCount() {
        return c_eno.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) resultActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.result_item, viewGroup, false);

        TextView t1, t2,t3;
        t1 = view.findViewById(R.id.candidate_name);
        t2 = view.findViewById(R.id.candidate_enrolmentno);
        t3 = view.findViewById(R.id.Totaltxt);
        t1.setText(c_name.get(i));
        t2.setText(c_eno.get(i));
        t3.setText(c_Total.get(i).toString());


        return view;
    }
}
