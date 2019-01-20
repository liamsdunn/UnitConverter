package com.example.liam.unitconverter.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import static android.R.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private final static String len = "Length", mass = "Mass", vol = "Volume";
    private static HashMap<String,Double> lengthTable, massTable, volumeTable;
    private String fromUnit, toUnit, state;
    private EditText amount;
    private Spinner fromSpinner, toSpinner;
    private Button convertButton, massButton, lengthButton, volumeButton;
    private String[] current_menu, lengthMenu, massMenu, volumeMenu;
    private ArrayAdapter<String> toAdapter, fromAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setMaps();
        amount = findViewById(R.id.Display);
        amount.setText(String.valueOf(0));


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
        massButton.setOnClickListener(this);

        lengthButton = findViewById(R.id.lengthButton);
        lengthButton.setOnClickListener(this);

        volumeButton = findViewById(R.id.volumeButton);
        volumeButton.setOnClickListener(this);

        convertButton = findViewById(R.id.ConvertButton);
        convertButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.ConvertButton) {
            double converted_amount = Double.valueOf(amount.getText().toString());
            switch (state) {
                case len:
                    converted_amount *= lengthTable.get(fromUnit) / lengthTable.get(toUnit);
                    break;

                case mass:
                    converted_amount *= massTable.get(fromUnit) / massTable.get(toUnit);
                    break;
                case vol:
                    converted_amount *= volumeTable.get(fromUnit) / volumeTable.get(toUnit);
                    break;

                default:
                    break;
            }
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            amount.setText(String.valueOf(df.format(converted_amount)));

        } else {

            if (id == R.id.lengthButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = lengthMenu[i];
                }
                state = len;
                toAdapter.notifyDataSetChanged();
                fromAdapter.notifyDataSetChanged();
                toSpinner.setAdapter(toAdapter);
                fromSpinner.setAdapter(fromAdapter);

            } else if (id == R.id.massButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = massMenu[i];
                }
                state = mass;
                toAdapter.notifyDataSetChanged();
                fromAdapter.notifyDataSetChanged();
                toSpinner.setAdapter(toAdapter);
                fromSpinner.setAdapter(fromAdapter);

            } else if (id == R.id.volumeButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = volumeMenu[i];
                }
                state = vol;
                toAdapter.notifyDataSetChanged();
                fromAdapter.notifyDataSetChanged();
                toSpinner.setAdapter(toAdapter);
                fromSpinner.setAdapter(fromAdapter);
            }

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

        volumeTable = new HashMap<String,Double>();
        volumeTable.put("Kiloliter", 1000.0);
        volumeTable.put("Liter", 1.0);
        volumeTable.put("Centiliter", 0.01);
        volumeTable.put("Milliliter", 0.001);
        volumeTable.put("Cup", 0.236588);
        volumeTable.put("Pint", 0.473176);
        volumeTable.put("Quart", 0.946353);
        volumeTable.put("Gallon", 3.78541);

        current_menu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        lengthMenu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        massMenu = massTable.keySet().toArray(new String[massTable.size()]);
        volumeMenu = volumeTable.keySet().toArray(new String[volumeTable.size()]);
        state = len;
    }
}
