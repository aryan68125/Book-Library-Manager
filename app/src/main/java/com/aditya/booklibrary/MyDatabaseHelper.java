package com.aditya.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper
{

    private Context context;
    //Constants
    //database name
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    //table name
    public static final String  TABLE_NAME = "my_library";
    //creating columns names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE  ="book_title";
    private static final String COLUMN_AUTHOR ="book_author";
    private static final String COLUMN_PAGES ="book_pages";

    public MyDatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //query is holding the sql statement needed for the operation
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_PAGES + " INTEGER); " ;
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
          //whenever we upgrade our database we need to call our oncreate method as well
          onCreate(db);
    }

    //this method will implement add book functionality to our application
    void addBook(String title,String author,int Pages)
    {
        //create a sql Lite database object
        //this will refer to our SQLiteOpenHelper class
        //getWritableDatabase() will help us to write to our table
        SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues cv will store all our data from our application and will pass this to our database table
        ContentValues cv = new ContentValues();
        //cv.put(key:"column name",value:"Data")
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_AUTHOR,author);
        cv.put(COLUMN_PAGES,Pages);
        //now we will insert the data inside our database using our SQLite object db
        long result = db.insert(TABLE_NAME,null,cv);

        if(result == -1) //our application failed to insert the data
        {
            Toast.makeText(context,"Oops Something Went Wrong Try again Later!",Toast.LENGTH_SHORT).show();
        }
        else //our application sucessfully inserted the data from our app to our database
        {
            Toast.makeText(context,"Added Successfully!",Toast.LENGTH_SHORT).show();
        }
    }

    //this method will return a cursor object
    //this method will be responsible for population our recyclerView
    //this method will be called in our mainActivity
    Cursor ReadAllData()
    {   //selecting all the data from our database table
        String query = "SELECT * FROM "+ TABLE_NAME;
        //creaing an sql database object and get readable database this time
        SQLiteDatabase db = this.getReadableDatabase();
        //creating a cursor object
        Cursor cursor = null;
        if(db!=null)
        {
           cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    //this method will be called inside the UpdateActivity
    void updateData(String row_id, String title_updateData, String author_updateData, int pages_updateData)
    {     //for writing to our database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title_updateData);
        cv.put(COLUMN_AUTHOR,author_updateData);
        cv.put(COLUMN_PAGES,pages_updateData);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});

        if(result==-1)
        {
            Toast.makeText(context,"Update Failed! Err A007 27183xD997",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Update Success!",Toast.LENGTH_SHORT).show();
        }
    }

    /*
    this function will be called in updateActivity class so that when press dlete button we will be able to delete
    the selected row from the table of the database
     */
    void deleteOneRowFromTheTableOfDatabase(String row_id)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if(result ==-1) //that means the delete operation failed
        {
          Toast.makeText(context,"Delete Operation Failed Err Sierra117 00AOSYRUSx$$53321",Toast.LENGTH_SHORT).show();
        }
        else //delete operation sucessfull
        {
            Toast.makeText(context,"Delete Operation Success!",Toast.LENGTH_SHORT).show();
        }
    }
}
