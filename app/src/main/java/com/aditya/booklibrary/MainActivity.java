package com.aditya.booklibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //custom Adapter object
    CustomAdapter customAdapter;

    //initialize MyDatabaseHelper class
    MyDatabaseHelper myDB;

    //now create an array list to hold the list of id,titleOfBooks,Author_Names,NumberOf_Pages from our Database
    ArrayList<String> book_id,book_title,book_author,book_page;

    FloatingActionButton Add_Button ;
    RecyclerView recylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recylerView = findViewById(R.id.recylerView);

        Add_Button = (FloatingActionButton) findViewById(R.id.Add_Button);

        //now setting up an onclick listener for our floationg button
        Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we will open a new activity add activity
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);

        book_id =  new ArrayList<>();
        book_title=  new ArrayList<>();
        book_author=  new ArrayList<>();
        book_page=  new ArrayList<>();

        //calling StoreDataInArrays() function
        StoreDataInArrays();

        //initialize our custom Adapter object
        /*
        customAdapter = new CustomAdapter(activity: MainActivity.this,context: this,book_id,book_title,book_author,book_page);
        the above line will refresh the recylerView in our main Activity by updating the incoming data from CustomAdapter class
         */
        customAdapter = new CustomAdapter(MainActivity.this,this,book_id,book_title,book_author,book_page);
        recylerView.setAdapter(customAdapter);
        recylerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }


    //refreshing the RecylerView in our main activity with new data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {   //no finally this is the method that refreshes our main activity
            recreate();
        }
    }

    void StoreDataInArrays()
    {
        //calling ReadAllData() method or function from MyDatabaseHelper class
        Cursor cursor = myDB.ReadAllData();
        if(cursor.getCount() == 0) //that means there is no data
        {
            Toast.makeText(this,"No Data Present in the Memeory!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //move the cursor to the next position in the table
            //adding data from our database to our arrays
            while(cursor.moveToNext())
            {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_page.add(cursor.getString(3));

            }
        }
    }// DisplayData function closed
}//mainActivity class closed