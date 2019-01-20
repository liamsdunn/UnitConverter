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

//      Initializing all our unit conversion maps and setting state.
        this.setMaps();

//      Initializing our number view and setting it to zero
        amount = findViewById(R.id.Display);
        amount.setText(String.valueOf(0));

//      Initializing our from unit spinner with a dynamic spinner
        fromSpinner = findViewById(R.id.FromSpinner);
        fromAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, current_menu);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        fromSpinner.setOnItemSelectedListener(this);

//      Initializing our to unit spinner with a dynamic spinner
        toSpinner = findViewById(R.id.ToSpinner);
        toAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, current_menu);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);
        toSpinner.setOnItemSelectedListener(this);

//      Initializing our various buttons
        massButton = findViewById(R.id.massButton);
        massButton.setOnClickListener(this);

        lengthButton = findViewById(R.id.lengthButton);
        lengthButton.setOnClickListener(this);

        volumeButton = findViewById(R.id.volumeButton);
        volumeButton.setOnClickListener(this);

        convertButton = findViewById(R.id.ConvertButton);
        convertButton.setOnClickListener(this);
    }

    /**
     * This function updates the unit variables with the text of the spinner who called it,
     * allowing for its data to be processed later.
     * @param parent - The only param needed, used to determine caller of function
     * @param view
     * @param position
     * @param id
     */
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

    @Override
    public void onClick(View v) {

        int id = v.getId();

        /*This section handles the action of our convert button. It takes our value, multiplies it
        * by its conversion to the metric standard, then multiplies it by the inverse of the unit
        * it is being converted to. It displays 5 decimal places. */
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

            /*This section handles with the unit conversion buttons, since the spinners are connected
            * to the same array, its values must be changed instead of replacing the array. Along
            * with this, the converters state is updated accordingly.*/
            if (id == R.id.lengthButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = lengthMenu[i];
                }
                state = len;

            } else if (id == R.id.massButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = massMenu[i];
                }
                state = mass;

            } else if (id == R.id.volumeButton) {

                for (int i = 0; i < current_menu.length; i++) {
                    current_menu[i] = volumeMenu[i];
                }
                state = vol;
            }

            /*Finally, the spinners text adapters are told about the text update*/
            toAdapter.notifyDataSetChanged();
            fromAdapter.notifyDataSetChanged();
            toSpinner.setAdapter(toAdapter);
            fromSpinner.setAdapter(fromAdapter);

        }
    }

    /**
     * This function sets all of the unit maps with their corresponding unit of measurement
     * and its conversion to the metric standard unit (meter, liter, gram). Then the current
     * unit state is updated and all arrays of units initialized.
     */
    private void setMaps(){

        lengthTable = new HashMap<>();
        lengthTable.put("Kilometer", 1000.0);
        lengthTable.put("Meter", 1.0);
        lengthTable.put("Centimeter", 0.01);
        lengthTable.put("Millimeter", 0.001);
        lengthTable.put("Inches", 0.0254);
        lengthTable.put("Feet", 0.3048);
        lengthTable.put("Yards", 0.9144);
        lengthTable.put("Miles", 1609.34);

        massTable = new HashMap<>();
        massTable.put("Kilogram", 1000.0);
        massTable.put("Hectogram", 100.0);
        massTable.put("Gram", 1.0);
        massTable.put("Centigram", 0.01);
        massTable.put("Milligram", 0.001);
        massTable.put("Ounce", 28.3495);
        massTable.put("Pound", 453.592);
        massTable.put("Ton", 907185.0);

        volumeTable = new HashMap<>();
        volumeTable.put("Kiloliter", 1000.0);
        volumeTable.put("Liter", 1.0);
        volumeTable.put("Centiliter", 0.01);
        volumeTable.put("Milliliter", 0.001);
        volumeTable.put("Cup", 0.236588);
        volumeTable.put("Pint", 0.473176);
        volumeTable.put("Quart", 0.946353);
        volumeTable.put("Gallon", 3.78541);

//      Current menu is set using its own array rather than setting equal to the length menu.
//      This is done to prevent issues with pointers, as updating values depends on the original array
        current_menu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        lengthMenu = lengthTable.keySet().toArray(new String[lengthTable.size()]);
        massMenu = massTable.keySet().toArray(new String[massTable.size()]);
        volumeMenu = volumeTable.keySet().toArray(new String[volumeTable.size()]);
        state = len;
    }
}
