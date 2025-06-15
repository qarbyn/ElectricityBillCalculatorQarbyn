package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set up action bar with back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set developer info
        TextView studentInfo = findViewById(R.id.studentInfoTextView);
        studentInfo.setText("Name: Muhammad Qarbyn Musyrif Bin Mohd Karim\nStudent ID: 2024916877");

        // Set course info
        TextView courseInfo = findViewById(R.id.courseInfoTextView);
        courseInfo.setText("Course Code: ICT602\nCourse Name: Mobile Technology and Development");

        // Load your picture
        ImageView developerImage = findViewById(R.id.developerImageView);
        // If you want to load from URL instead:
        // Glide.with(this).load("YOUR_IMAGE_URL").into(developerImage);
    }

    // Handle GitHub link click
    public void openGitHubLink(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/qarbyn/ElectricityBillCalculatorQarbyn"));
        startActivity(browserIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}