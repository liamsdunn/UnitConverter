package com.example.liam.unitconverter.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.HashMap;

import static android.R.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String len = "Length", mass = "Mass", vol = "Volume";
    private HashMap<String,Double> lengthTable, massTable, volumeTable;
    private String fromUnit, toUnit, state;
    private EditText amount;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton, massButton;
    private String[] current_menu, lengthMenu, massMenu;
    private ArrayAdapter<String> toAdapter, fromAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setMaps();
        amount = findViewById(R.id.Display);

//        FromAdapter Section

        fromSpinner = findViewById(R.id.FromSpinner);
        fromAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, current_menu);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        fromSpinner.setOnItemSelectedListener(this);

//        To Spinner Section
        toSpinner = findViewById(R.id.ToSpinner);
        toAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, current_menu);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);
        toSpinner.setOnItemSelectedListener(this);

//        If Button is clicked here, this stuff fires
        massButton = findViewById(R.id.massButton);
        massButton.setOnClickListener(new View.OnClickListener() {

//           THis is where we convert the value in he edit text with spinner.
            @Override
            public void onClick(View arg0){
                String[] test = massTable.keySet().toArray(new String[lengthTable.size()]);
                for (int i = 0; i < current_menu.length; i++){
                    current_menu[i] = test[i];
                }

                state = mass;
                toAdapter.notifyDataSetChanged();
                fromAdapter.notifyDataSetChanged();
                toSpinner.setAdapter(toAdapter);
                fromSpinner.setAdapter(fromAdapter);
            }
        });

        convertButton = findViewById(R.id.ConvertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {

            //           THis is where we convert the value in he edit text with spinner.
            @Override
            public void onClick(View arg0){
                double converted_amount = Double.valueOf(amount.getText().toString());

                switch(state){
                    case "Length":
                        converted_amount *= lengthTable.get(fromUnit)/lengthTable.get(toUnit);
                        break;

                    case "Mass":
                        converted_amount *= massTable.get(fromUnit)/massTable.get(toUnit);
                        break;

                    default:
                        break;
                }

                amount.setText(String.valueOf(converted_amount));

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
//        Might cause issues
        fromUnit = "Kilometer";
        toUnit = "Kilometer";
    }

    public void setMenu(){

        switch(state) {
            case "Length": current_menu = current_menu;
            break;

            case "Mass": current_menu = current_menu;
            break;

            case "Volume": current_menu = current_menu;
            break;

            default: current_menu = current_menu;
            break;
        }

    }

    private void setMaps(){

        lengthTable = new HashMap<String,Double>();
        lengthTable.put("Kilometer", 1000.0);
        lengthTable.put("Meter", 1.0);
        lengthTable.put("Centimeter", 0.01);
        lengthTable.put("Millimeter", 0.001);
        lengthTable.put("Inches", 0.0254);
        lengthTable.put("Feet", 0.3048);
        lengthTable.put("Yards", 0.9144);
        lengthTable.put("Miles", 1609.34);

        massTable = new HashMap<String,Double>();
        massTable.put("Kilogram", 1000.0);
        massTable.put("Hectogram", 100.0);
        massTable.put("Gram", 1.0);
        massTable.put("Centigram", 0.01);
        massTable.put("Milligram", 0.001);
        massTable.put("Ounce", 28.3495);
        massTable.put("Pound", 453.592);
        massTable.put("Ton", 907185.0);

        current_menu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        lengthMenu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        massMenu = massTable.keySet().toArray(new String[massTable.size()]);
        state = len;

    }




//    public void onActivityCreated(Bundle savedInstanceState) {
//
//
//        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);
//        fromSpinner.setAdapter(adapter);
//    }



}
