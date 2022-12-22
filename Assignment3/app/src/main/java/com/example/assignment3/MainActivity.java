package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listfood ;
    ArrayList<String> arrayListFood ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listfood = (ListView) findViewById(R.id.list_menu_food);

        arrayListFood = new ArrayList<>();

        arrayListFood.add("Recipes");
        arrayListFood.add("Cooking");
        arrayListFood.add("Shopping");
        arrayListFood.add("Culture");
        arrayListFood.add("Restaurants");
        arrayListFood.add("Healthyish");
        arrayListFood.add("Basically");
        arrayListFood.add("The Latest");
        arrayListFood.add("Articles and Venues");
        arrayListFood.add("Galleries");



        ArrayAdapter foodAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayListFood);
        listfood.setAdapter(foodAdapter);

        listfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,Menu.class);
                intent.putExtra("vt",position);
                MainActivity.this.startActivity(intent);
            }
        });
    }

}