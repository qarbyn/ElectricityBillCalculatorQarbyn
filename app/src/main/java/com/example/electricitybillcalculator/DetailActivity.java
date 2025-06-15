package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bill Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int billId = getIntent().getIntExtra("BILL_ID", -1);
        if (billId == -1) {
            finish();
            return;
        }

        Bill bill = AppDatabase.getInstance(this).billDao().getBillById(billId);

        TextView monthTextView = findViewById(R.id.monthTextView);
        TextView unitsTextView = findViewById(R.id.unitsTextView);
        TextView totalChargesTextView = findViewById(R.id.totalChargesTextView);
        TextView rebateTextView = findViewById(R.id.rebateTextView);
        TextView finalCostTextView = findViewById(R.id.finalCostTextView);

        monthTextView.setText(String.format("Month: %s", bill.getMonth()));
        unitsTextView.setText(String.format("Units Used: %.2f kWh", bill.getUnitsUsed()));
        totalChargesTextView.setText(String.format("Total Charges: RM %.2f", bill.getTotalCharges()));
        rebateTextView.setText(String.format("Rebate: %.2f%%", bill.getRebatePercentage()));
        finalCostTextView.setText(String.format("Final Cost: RM %.2f", bill.getFinalCost()));

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