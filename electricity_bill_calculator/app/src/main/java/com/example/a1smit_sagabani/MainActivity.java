package com.example.a1smit_sagabani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.a1smit_sagabani.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // binding variable - programatically access ui elements
    private ActivityMainBinding activityMainBinding;

    // Rate constants as per
    // 3.>4. Electricity rates must be modeled as constants in the MainActivity file
    private final double mor_rate = 0.132;
    private final double aft_rate = 0.065;
    private final double eve_rate = 0.094;
    private final double tax_rate = 0.13;
    private final double er_rate = 0.08;

    // variables needed for calculations
    private double uti_mor;
    private double uti_aft;
    private double uti_eve;

    private double chg_mor;
    private double chg_aft;
    private double chg_eve;

    private boolean ren_energy;

    private double chg_total;
    private double sales_tax;

    private double env_rebate;
    private double reg_chg;
    private double total_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing binding variable
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        // assocaiting with java file with xml file
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.sRenewable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 2.>3. The switch is used to indicate if the person was using a renewable energy source.
                if(isChecked == true) {
                    ren_energy = true;
                } else if (isChecked != true) {
                    ren_energy = false;
                }
            }
        });

        // attaching click handler to the button
        // 3.>3.Click handlers must be attached programmatically, using the button’s .setOnClickListener
        // function.
        // 3.>2 UI elements must be programmatically accessed using view bindings. Data bindings or usage of
        // findViewById is not permitted.
        activityMainBinding.calculate.setOnClickListener(new View.OnClickListener() {
            // 2.>4. When the user taps the Calculate button, the application should calculate and display the
            //breakdown of their bill in the UI. The user interface must clearly show all parts of the calculation.
            @Override
            public void onClick(View v) {
                // thought this should be in a try catch block as to catch exeptions. have not implemented the catch part as not requried.
                try {
                    uti_mor =  Double.parseDouble(activityMainBinding.iMor.getText().toString());
                    uti_aft =  Double.parseDouble(activityMainBinding.iAft.getText().toString());
                    uti_eve =  Double.parseDouble(activityMainBinding.iEve.getText().toString());
                } catch (Throwable e) {

                }

                chg_mor = uti_mor * mor_rate;
                chg_aft = uti_aft * aft_rate;
                chg_eve = uti_eve * eve_rate;

                chg_total = chg_mor + chg_aft + chg_eve;

                activityMainBinding.outMorChg.setText("Morning usage charges: $" + Math.round(chg_mor*100)/100d);
                activityMainBinding.outAftChg.setText("Afternoon usage charges: $" + Math.round(chg_aft*100)/100d);
                activityMainBinding.outEveChg.setText("Evening usage charges: $" + Math.round(chg_eve*100)/100d);
                activityMainBinding.outTotChg.setText("Total usage charges: $" + Math.round(chg_total*100)/100d);

                sales_tax = chg_total * tax_rate;
                activityMainBinding.outSales.setText("Sales tax: $" + Math.round(sales_tax*100)/100d);

                env_rebate = (ren_energy ? 1d : 0d) * chg_total * er_rate;
                activityMainBinding.outEnvReb.setText("Environment rebate: -$" + Math.round(env_rebate*100)/100d);

                reg_chg = sales_tax - env_rebate;
                activityMainBinding.outTotReb.setText("Total regulatory charge: $" + Math.round(reg_chg*100)/100d);

                total_bill = chg_total + reg_chg;
                activityMainBinding.outTotBill.setText("Total Bill amount: $" + Math.round(total_bill*100)/100d);

                activityMainBinding.iMor.setText(null);
                activityMainBinding.iAft.setText(null);
                activityMainBinding.iEve.setText(null);
            }
        });

        activityMainBinding.reset.setOnClickListener(new View.OnClickListener() {
            // 2.>5. When the user taps the Reset button, the application must clear all text boxes and any displayed
            //calculations.
            @Override
            public void onClick(View v) {
                activityMainBinding.iMor.setText(null);
                activityMainBinding.iAft.setText(null);
                activityMainBinding.iEve.setText(null);
                activityMainBinding.outMorChg.setText("Morning usage charges: $0");
                activityMainBinding.outAftChg.setText("Afternoon usage charges: $0");
                activityMainBinding.outEveChg.setText("Evening usage charges: $0");
                activityMainBinding.outTotChg.setText("Total usage charges: $0");
                activityMainBinding.outSales.setText("Sales tax: $0");
                activityMainBinding.outEnvReb.setText("Environment rebate: -$0");
                activityMainBinding.outTotReb.setText("Total regulatory charge: $0");
                activityMainBinding.outTotBill.setText("Total Bill amount: $0");

            }
        });
    }
}