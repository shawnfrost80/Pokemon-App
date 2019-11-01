package com.example.pokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class detailActivity extends AppCompatActivity {

    LinearLayout typeLayout;
    LinearLayout weaknessLayout;
    Intent intent;

    public TextView createTextView(int color) {
        TextView textView = new TextView(this);
        textView.setBackgroundResource(color == R.color.colorAccent ? R.drawable.rounded_corner : R.drawable.rounded_corner2);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(30, 20, 30, 20);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(getColor(color));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }

    public void addTypes() {
        ArrayList<String> types = intent.getStringArrayListExtra("types");
        typeLayout = findViewById(R.id.types);
        for (int i = 0; i < (types != null ? types.size() : 0); i++) {
            TextView textView = createTextView(R.color.colorAccent);
            try {
                textView.setText(types.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(textView);
            relativeLayout.setForegroundGravity(Gravity.CENTER);
            relativeLayout.setGravity(Gravity.CENTER);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f));
            typeLayout.addView(relativeLayout);
        }
    }

    public void addWeakness() {
        ArrayList<String> weaknesses = intent.getStringArrayListExtra("weaknesses");
        weaknessLayout = findViewById(R.id.weakness);
        for (int i = 0; i < (weaknesses != null ? weaknesses.size() : 0); i++) {
            TextView textView = createTextView(R.color.colorAccent2);
            try {
                textView.setText(weaknesses.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(textView);
            relativeLayout.setForegroundGravity(Gravity.CENTER);
            relativeLayout.setGravity(Gravity.CENTER);
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f));
            weaknessLayout.addView(relativeLayout);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        intent = getIntent();

        addTypes();
        addWeakness();

        String name = intent.getStringExtra("name");
        String image = intent.getStringExtra("image");
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");

        ImageView pokemonImage = findViewById(R.id.detailImage);
        Picasso.get().load(image).into(pokemonImage);

        TextView nameView = findViewById(R.id.name);
        nameView.setText(name);

        TextView heightView = findViewById(R.id.height);
        heightView.setText(String.format("Height: %s", height));

        TextView weightView = findViewById(R.id.weight);
        weightView.setText(String.format("Weight: %s", weight));

    }
}
