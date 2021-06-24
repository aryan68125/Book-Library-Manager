package com.aditya.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
EditText title_input;
EditText author_input;
EditText pages_input;
Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        //now set onclickListener to this button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create MyDatabaseHelper class object
                MyDatabaseHelper MyDB = new MyDatabaseHelper(AddActivity.this);

                //getting data from our three editText
                String title_AddActivity = title_input.getText().toString();
                String author_AddActivity = author_input.getText().toString();
                int pages_AddActivity = Integer.valueOf(pages_input.getText().toString());

                //now we can call addBook method from our MyDatabaseHelper class
                MyDB.addBook(title_AddActivity,author_AddActivity,pages_AddActivity);
                title_input.getText().clear();
                author_input.getText().clear();
                pages_input.getText().clear();
            }
        });
    }
}