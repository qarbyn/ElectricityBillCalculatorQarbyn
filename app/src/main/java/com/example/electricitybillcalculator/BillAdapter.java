package com.example.electricitybillcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class BillAdapter extends ArrayAdapter<Bill> {
    public BillAdapter(Context context, List<Bill> bills) {
        super(context, 0, bills);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
        }

        Bill bill = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(String.format("%s: RM %.2f", bill.getMonth(), bill.getFinalCost()));

        return convertView;
    }
}