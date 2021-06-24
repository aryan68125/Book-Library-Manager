package com.aditya.booklibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input_update;
    EditText author_input_update;
    EditText pages_input_update;
    Button update_button;
    Button Delete_button;

    String id_UpdateActivity,title_UpdateActivity,author_UpdateActivity,pages_UpdateActivity;

    //creating an object of MyDatabaseHelper Class
    MyDatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title_input_update = findViewById(R.id.title_input_update);
        author_input_update = findViewById(R.id.author_input_update);
        pages_input_update = findViewById(R.id.pages_input_update);
        update_button = findViewById(R.id.update_button);
        Delete_button=findViewById(R.id.Delete_button);

        //First call this
        getIntentDataFromCustomAdapter();


        //create our Actionbar object so that we can change the title of our activity
        ActionBar ab = getSupportActionBar();
        //now we can set the title for our  updateActivity
        if (title_UpdateActivity!=null)
        {
            ab.setTitle(title_UpdateActivity);
        }

        //set onclikListener for our update button
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Second initialize the class and call update() function from that class
                //initiating MyDatabaseHelper class
                mydb = new MyDatabaseHelper(UpdateActivity.this);
                //get text from editText
                String title_temp_editText = title_input_update.getText().toString();
                String author__temp_editText = author_input_update.getText().toString();
                int pages__temp_editText = Integer.valueOf(pages_input_update.getText().toString());
                mydb.updateData(id_UpdateActivity,title_temp_editText,author__temp_editText,pages__temp_editText);

            }
        });


        //set onclickListener for Delete button
        Delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this function creates a delete dialogue bax with yes to delete and no to abort options
                deleteConfirmDialogBox();
            }
        });
    }

    //get data from the CustomAdapter Layout via intent
    void getIntentDataFromCustomAdapter()
    {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("pages"))
        {
            //get data from CustomAdapter class via Intent
            id_UpdateActivity = getIntent().getStringExtra("id");
            title_UpdateActivity = getIntent().getStringExtra("title");
            author_UpdateActivity = getIntent().getStringExtra("author");
            pages_UpdateActivity = getIntent().getStringExtra("pages");

            //setting data to the editText
            title_input_update.setText(title_UpdateActivity);
            author_input_update.setText(author_UpdateActivity);
            pages_input_update.setText(pages_UpdateActivity);
        }
        else
        {
            Toast.makeText(this,"No data Err A113 000x2273821",Toast.LENGTH_SHORT).show();
        }
    }

    //this functioon will create a delete confirm dialog box
    void deleteConfirmDialogBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title_UpdateActivity + " ?");
        builder.setMessage("Are you sure you want to delete "+ title_UpdateActivity+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //create MyDatabaseHelper Class object so that we can use the function deleteOneRowFromTheTableOfDatabase(String row_id)
                //from MyDatabaseHelper class
                MyDatabaseHelper mydb = new MyDatabaseHelper(UpdateActivity.this);
                mydb.deleteOneRowFromTheTableOfDatabase(id_UpdateActivity);
                //finish(); will destroy our update activity and open the main Activity
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing if no is pressed by the user in the delete dialogue box
            }
        });
        builder.create().show();
    }
}