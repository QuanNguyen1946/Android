package com.example.assignment3;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Menu extends AppCompatActivity {
    ListView listfood;
    Button back;

    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_food_layout);

        listfood = findViewById(R.id.list_menu_food1);
        Intent intent = getIntent();
        int position = intent.getIntExtra("vt", 0);
        createListFood(position);

        back = findViewById(R.id.bt_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createListFood(int position) {
        ArrayAdapter arrayAdapter;
        switch (position) {
            case 0: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/recipes-rss-feed/rss",null,"0");
                return;
            }
            case 1: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/cooking-rss-feed/rss?_ga=2.138966448.2072719289.1668786044-1201296406.1668506192",null,"0");
                return;
            }
            case 2: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/shopping-rss-feed/rss",null,"0");
                return;
            }
            case 3: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/culture-rss-feed/rss",null,"0");
                return;
            }
            case 4: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/restaurants-rss-feed/rss",null,"0");
                return;
            }
            case 5: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/healthyish-rss-feed/rss",null,"0");
                return;
            }
            case 6: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/basically-rss-feed/rss",null,"0");
                return;
            }
            case 7: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/rss",null,"0");
                return;
            }
            case 8: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/stories/rss",null,"0");
                return;
            }
            case 9: {
                new ReadRSS().execute("https://www.bonappetit.com/feed/galleries/rss",null,"0");
                return;
            }
        }
    }

    public void DialogInput(String title, String describe, String creator, String publisher, String linkWeb) {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);


        TextView titleTv = dialog.findViewById(R.id.tv_title);
        TextView descriptionTv = dialog.findViewById(R.id.tv_description);
        TextView creatorTv = dialog.findViewById(R.id.tv_creator);
        TextView publisherTv = dialog.findViewById(R.id.tv_publisher);
        ImageView imgFood = findViewById(R.id.image_food);
        Button moreBt = dialog.findViewById(R.id.bt_more);
        Button closeBt = dialog.findViewById(R.id.bt_close);

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        moreBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Web.class);
                intent.putExtra("linkweb",linkWeb);
                Menu.this.startActivity(intent);
            }
        });

        titleTv.setText(title);
        descriptionTv.setText(describe);
        creatorTv.setText(creator);
        publisherTv.setText(publisher);
        dialog.show();
    }

    private class ReadRSS extends AsyncTask<String, Void, String> implements com.example.assignment3.ReadRSS {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);

            NodeList titleNode = document.getElementsByTagName("item");



            for (int i = 0; i < 10; i++) {
                Element element = (Element) titleNode.item(i);
                arrayList.add(i + "/ "  + parser.getValue(element, "title"));
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(Menu.this, android.R.layout.simple_list_item_1, arrayList);
            listfood.setAdapter(arrayAdapter);

            listfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i <= position; i++) {
                        Element element = (Element) titleNode.item(i);
                        if ( i == position ) {
                            DialogInput(parser.getValue(element, "title"),parser.getValue(element, "description"),parser.getValue(element, "dc:creator"),parser.getValue(element, "dc:publisher"),parser.getValue(element,"link"));
                            System.out.println("hihi " + parser.getValue(element,""));
                        }
                    }
                }
            });
        }
    }
}

