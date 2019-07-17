package com.demo.cafeorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CreateOrderActivity extends AppCompatActivity {

    String name;
    String password;
    String drink;

    private TextView textViewHello;
    private TextView textViewAdditions;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;

    private StringBuilder builderAdditions;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        Intent intent = getIntent();

        if (intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }

        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxsSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        builderAdditions = new StringBuilder();

        String hello = String.format(getString(R.string.hello_user), name);
        textViewHello = findViewById(R.id.TextViewHello);
        textViewHello.setText(hello);

        textViewAdditions = findViewById(R.id.TextViewAdditions);
        drink = getString(R.string.tea);
        String additions = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additions);

    }

    public void onClickChangeDrink(View view) {

        RadioButton radioButton = (RadioButton) view;
        int id = radioButton.getId();
        if (id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        } else if (id == R.id.radioButtonCoffee) {
            drink = getString(R.string.coffee);
            spinnerCoffee.setVisibility(View.VISIBLE);
            spinnerTea.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }

        String additional = String.format(getString(R.string.additions), drink);
        textViewAdditions.setText(additional);


    }

    public void onClickSendOrder(View view) {

        builderAdditions.setLength(0);

        if (checkBoxMilk.isChecked()){
            builderAdditions.append(getString(R.string.milk)).append(" ");
        }

        if (checkBoxSugar.isChecked()){
            builderAdditions.append(getString(R.string.sugar)).append(" ");
        }

        if (checkBoxLemon.isChecked() && drink == getString(R.string.tea)){
            builderAdditions.append(getString(R.string.lemon)).append(" ");
        }

        String optionOfDrink = "";
        if (drink.equals(getString(R.string.tea))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        } else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }

        String order = String.format(getString(R.string.order), name, password, drink, optionOfDrink);
        String additionals = "";
        if (builderAdditions.length()>0){
            additionals = "\n" + getString(R.string.need_additions) + builderAdditions.toString();
        }

        String fullOrder = order + additionals;

        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}
