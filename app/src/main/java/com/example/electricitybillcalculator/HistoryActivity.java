package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ListView billListView;
    private AppDatabase db;
    private BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bill History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        billListView = findViewById(R.id.billListView);
        db = AppDatabase.getInstance(this);

        db.billDao().getAllBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                adapter = new BillAdapter(HistoryActivity.this, bills);
                billListView.setAdapter(adapter);
            }
        });

        billListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill = adapter.getItem(position);
                Intent intent = new Intent(HistoryActivity.this, DetailActivity.class);
                intent.putExtra("BILL_ID", bill.getId());
                startActivity(intent);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}