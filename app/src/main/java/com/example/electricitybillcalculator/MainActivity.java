package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner monthSpinner;
    private EditText unitsEditText;
    private RadioGroup rebateRadioGroup;
    private TextView totalChargesTextView, finalCostTextView;
    private Button saveButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        monthSpinner = findViewById(R.id.monthSpinner);
        unitsEditText = findViewById(R.id.unitsEditText);
        rebateRadioGroup = findViewById(R.id.rebateRadioGroup);
        totalChargesTextView = findViewById(R.id.totalChargesTextView);
        finalCostTextView = findViewById(R.id.finalCostTextView);
        saveButton = findViewById(R.id.saveButton);

        db = AppDatabase.getInstance(this);

        // Set up month spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.months_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        // Set up action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Bill Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        // Calculate button click
        findViewById(R.id.calculateButton).setOnClickListener(v -> calculateBill());

        // Save button click
        saveButton.setOnClickListener(v -> saveBill());

        // Navigation buttons
        findViewById(R.id.viewHistoryButton).setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        findViewById(R.id.aboutButton).setOnClickListener(v -> {
            startActivity(new Intent(this, AboutActivity.class));
        });
    }

    private void calculateBill() {
        try {
            String month = monthSpinner.getSelectedItem().toString();
            String unitsStr = unitsEditText.getText().toString();

            if (month.isEmpty() || unitsStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double units = Double.parseDouble(unitsStr);
            if (units <= 0) {
                Toast.makeText(this, "Units must be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = rebateRadioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select rebate percentage", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton radioButton = findViewById(selectedId);
            String radioText = radioButton.getText().toString();
            double rebate = Double.parseDouble(radioText.replace("%", ""));

            double totalCharges = calculateTotalCharges(units);
            double finalCost = totalCharges - (totalCharges * rebate / 100);

            totalChargesTextView.setText(String.format("Total Charges: RM %.2f", totalCharges));
            finalCostTextView.setText(String.format("Final Cost: RM %.2f", finalCost));

            // Show save button
            saveButton.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveBill() {
        try {
            String month = monthSpinner.getSelectedItem().toString();
            double units = Double.parseDouble(unitsEditText.getText().toString());

            int selectedId = rebateRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            double rebate = Double.parseDouble(radioButton.getText().toString().replace("%", ""));

            String totalText = totalChargesTextView.getText().toString();
            double totalCharges = Double.parseDouble(totalText.replace("Total Charges: RM ", ""));

            String finalText = finalCostTextView.getText().toString();
            double finalCost = Double.parseDouble(finalText.replace("Final Cost: RM ", ""));

            Bill bill = new Bill();
            bill.setMonth(month);
            bill.setUnitsUsed(units);
            bill.setRebatePercentage(rebate);
            bill.setTotalCharges(totalCharges);
            bill.setFinalCost(finalCost);

            db.billDao().insert(bill);
            Toast.makeText(this, "Bill saved successfully", Toast.LENGTH_SHORT).show();

            // Hide save button after saving
            saveButton.setVisibility(View.GONE);

        } catch (Exception e) {
            Toast.makeText(this, "Error saving bill", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateTotalCharges(double units) {
        double charges = 0;

        if (units > 600) {
            charges += (units - 600) * 0.546;
            units = 600;
        }
        if (units > 300) {
            charges += (units - 300) * 0.516;
            units = 300;
        }
        if (units > 200) {
            charges += (units - 200) * 0.334;
            units = 200;
        }
        charges += units * 0.218;

        return charges;
    }
}