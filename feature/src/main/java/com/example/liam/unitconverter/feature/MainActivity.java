package com.example.liam.unitconverter.feature;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.HashMap;

import static android.R.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private HashMap<String,Double> valueTable;
    private String fromUnit;
    private String toUnit;
    private EditText amount;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button convertButton;
    private ArrayAdapter<CharSequence> toAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setMap();

        amount = findViewById(R.id.Display);

//        FromAdapter Section
        fromSpinner = findViewById(R.id.FromSpinner);
        final ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(
                this, R.array.app_categories, layout.simple_spinner_item
        );

        fromAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        fromSpinner.setOnItemSelectedListener(this);

//        ToAdapter Section
        toSpinner = findViewById(R.id.ToSpinner);
        toAdapter= ArrayAdapter.createFromResource(
                this, R.array.app_categories, layout.simple_spinner_item
        );
        toAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);
        toSpinner.setOnItemSelectedListener(this);



//        If Button is clicked here, this stuff fires
        convertButton = findViewById(R.id.ConvertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {

//           THis is where we convert the value in he edit text with spinner.
            @Override
            public void onClick(View arg0){
                double input = Double.valueOf(amount.getText().toString());

                double toMeter = input * valueTable.get(fromUnit);
                toMeter = toMeter / valueTable.get(toUnit);
                amount.setText(String.valueOf(toMeter));
                
//
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.FromSpinner) {
            fromUnit = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.ToSpinner) {
            toUnit = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        fromUnit = "Kilometer";
        toUnit = "Kilometer";
    }

    private void setMap(){
        valueTable = new HashMap<String,Double>();
        valueTable.put("Kilometer", 1000.0);
        valueTable.put("Meter", 1.0);
        valueTable.put("Centimeter", 0.01);
        valueTable.put("Millimeter", 0.001);
        valueTable.put("Inches", 0.0254);
        valueTable.put("Feet", 0.3048);
        valueTable.put("Yards", 0.9144);
        valueTable.put("Miles", 1609.34);
    }




//    public void onActivityCreated(Bundle savedInstanceState) {
//
//
//        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
//        fromSpinner.setAdapter(adapter);
//    }



}
