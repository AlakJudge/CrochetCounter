package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int s = 0;
    static int rNumber = 1;
    public TextView rowNumber;
    String[] stitchOptions = {"Single Crochet (Sc)", "Chain Stitch (Ch)",
            "Half Double Crochet (Hdc)", "Double Crochet (Dc)", "Half Treble Crochet (Htr)",
            "Treble Crochet (Tr)", "Double Treble Crochet (Dtr)", "Slip Stitch (Sl St)"};
    String selectedStitch;
    boolean multipleStitches = false;
    int first = 0;
    TextView[] multiStitchRow = new TextView[99];
    int multiCounter = 0;
    String rowData;
    int patternRowCounter = 0;
    int patternCounter = 0;
    ArrayList<String>[] patternRowData = new ArrayList[99];
    ArrayList<String>[]patternData = new ArrayList[50];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner stitchTypes = findViewById(R.id.stitchTypes);
        TextView rowNumberText = findViewById(R.id.rowNumberText);

        patternRowData[patternRowCounter] = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, stitchOptions);

        stitchTypes.setAdapter(adapter);
        selectedStitch = stitchTypes.getSelectedItem().toString();
        rowNumberText.setText("Row Nº");

        stitchTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (s >= 1) {
                    multipleStitches = true;

                    if (first == 0) {
                        firstStitch();
                    }
                    else {
                        otherStitches();
                    }

                    selectedStitch = stitchTypes.getSelectedItem().toString();
                    TextView stitches = findViewById(R.id.numberStitches);
                    s = 0;
                    stitches.setText(String.valueOf(s));
                    first++;
                }
                else
                    selectedStitch = stitchTypes.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        rowNumber = findViewById(R.id.rowNumber);
        rowNumber.setText(String.valueOf(rNumber));

        Button button = findViewById(R.id.patternButton);
        button.setOnClickListener(this::showPatternMenu);
    }

    public void addStitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        s++;
        stitches.setText(String.valueOf(s));
    }

    public void add5Stitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        s += 5;
        stitches.setText(String.valueOf(s));
    }

    public void add10Stitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        s += 10;
        stitches.setText(String.valueOf(s));
    }

    public void subStitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        if (s > 0) {
            s--;
            stitches.setText(String.valueOf(s));
        }
    }

    public void sub5Stitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        if (s > 0) {
            s -= 5;
            stitches.setText(String.valueOf(s));
        }
    }

    public void sub10Stitches (View v) {
        TextView stitches = findViewById(R.id.numberStitches);
        if (s > 0) {
            s -= 10;
            stitches.setText(String.valueOf(s));
        }
    }

    private void updateRowNumber() {
        rowNumber = findViewById(R.id.rowNumber);
        rowNumber.setText(String.valueOf(rNumber));
    }

    public void newRow (View v) {

        if (!multipleStitches) {
            stitchInitials();
            rowData = s + selectedStitch;
            LinearLayout rowList = findViewById(R.id.rowList);
            TextView row = new TextView(this);
            row.setText(rowData);
            row.setTextSize(20);
            row.setTypeface(row.getTypeface(), Typeface.NORMAL);
            rowList.addView(row, 0);

            LinearLayout rowListNumber = findViewById(R.id.rowListNumber);
            TextView rowNumber = new TextView(this);
            rowNumber.setText(String.valueOf(rNumber));
            rowNumber.setTextSize(20);
            rowNumber.setTypeface(rowNumber.getTypeface(), Typeface.BOLD);
            rowNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            rowListNumber.addView(rowNumber, 0);
        }

        else {
            stitchInitials();
            String currentText = multiStitchRow[multiCounter].getText().toString();
            rowData = currentText + ", " + s + selectedStitch;
            multiStitchRow[multiCounter].setText(rowData);
            multipleStitches = false;
            multiCounter++;
            first = 0;
        }

        TextView stitches = findViewById(R.id.numberStitches);
        s = 0;
        stitches.setText(String.valueOf(s));
        rNumber++;

        updateRowNumber();
        patternRowData();
    }

    public void firstStitch() {
        stitchInitials();
        LinearLayout rowList = findViewById(R.id.rowList);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);
        multiStitchRow[multiCounter] = new TextView(this);
        multiStitchRow[multiCounter].setText(MessageFormat.format("{0}{1}", s, selectedStitch));
        multiStitchRow[multiCounter].setTextSize(20);
        multiStitchRow[multiCounter].setTypeface(multiStitchRow[multiCounter].getTypeface(), Typeface.NORMAL);
        rowList.addView(multiStitchRow[multiCounter], 0);

        LinearLayout rowListNumber = findViewById(R.id.rowListNumber);
        TextView rowNumber = new TextView(this);
        rowNumber.setText(String.valueOf(rNumber));
        rowNumber.setTextSize(20);
        rowNumber.setTypeface(rowNumber.getTypeface(), Typeface.BOLD);
        rowNumber.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rowListNumber.addView(rowNumber, 0);
    }

    public void otherStitches() {
        stitchInitials();
        String currentText = multiStitchRow[multiCounter].getText().toString();
        multiStitchRow[multiCounter].setText(MessageFormat.format("{0}, {1}{2}", currentText, s, selectedStitch));
    }

    public void newPattern (View v) {
        LinearLayout rowListNumber = findViewById(R.id.rowListNumber);
        LinearLayout rowList = findViewById(R.id.rowList);
        TextView stitches = findViewById(R.id.numberStitches);
        s = 0;
        rNumber = 1;
        first = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, stitchOptions);
        Spinner stitchTypes = findViewById(R.id.stitchTypes);
        stitchTypes.setAdapter(adapter);
        selectedStitch = stitchTypes.getSelectedItem().toString();
        rowNumber = findViewById(R.id.rowNumber);
        rowNumber.setText(String.valueOf(rNumber));
        stitches.setText(String.valueOf(s));
        rowListNumber.removeAllViews();
        rowList.removeAllViews();
    }

    public void stitchInitials () {

        switch (selectedStitch) {
            case "Chain Stitch (Ch)":
                selectedStitch = " Ch";
                break;
            case "Single Crochet (Sc)":
                selectedStitch = " Sc";
                break;
            case "Half Double Crochet (Hdc)":
                selectedStitch = " Hdc";
                break;
            case "Double Crochet (Dc)":
                selectedStitch = " Dc";
                break;
            case "Half Treble Crochet (Htr)":
                selectedStitch = " Htr";
                break;
            case "Treble Crochet (Tr)":
                selectedStitch = " Tr";
                break;
            case "Double Treble Crochet (Dtr)":
                selectedStitch = " Dtr";
                break;
            case "Slip Stitch (Sl St)":
                selectedStitch = " Sl St";
                break;
        }
    }

    public void patternRowData () {

        patternRowData[patternRowCounter].add(rowData);
    }

    private void savePattern(ArrayList patternData) {
        SharedPreferences sharedPreferences = getSharedPreferences("PatternData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pattern"+patternCounter+1, (String) patternData.get(patternCounter));
        editor.apply();
    }

    public void loadPattern () {

        newPattern(getCurrentFocus());
        SharedPreferences sharedPreferences = getSharedPreferences("PatternData", Context.MODE_PRIVATE);
        String loadedPattern = sharedPreferences.getString("pattern", "EmptyPattern");

        if (!"EmptyPattern".equals(loadedPattern)) {
            Log.d("MyTag", "Loaded Pattern: " + loadedPattern);
        } else {
            // The pattern was not found in shared preferences, or it was the default value.
            Log.w("MyTag", "Pattern not found or default pattern loaded.");
        }

        LinearLayout rowList = findViewById(R.id.rowList);
        TextView row = new TextView(this);
        row.setText(rowData);
        row.setTextSize(20);
        row.setTypeface(row.getTypeface(), Typeface.BOLD);
        rowList.addView(row, 0);
    }
    @SuppressLint("NonConstantResourceId")
    private void showPatternMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.pattern_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.savePattern:
                    patternData[patternCounter] = new ArrayList<String>();
                    patternData[patternCounter].addAll(patternRowData[patternRowCounter]);
                    patternRowCounter++;
                    patternRowData[patternRowCounter] = new ArrayList<>();
                    savePattern(patternData[patternCounter]);
                    patternCounter++;
                    //Log.d("outputz", ""+patternData[0].get(1));
                    Toast.makeText(MainActivity.this, "Pattern saved", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.loadPattern:
                    loadPattern();
                    break;
            }
            return true;
        });
        popupMenu.show();
    }
}