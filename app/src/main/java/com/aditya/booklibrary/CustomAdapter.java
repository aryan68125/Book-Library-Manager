package com.aditya.booklibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

   private Context context;
   private ArrayList book_id_CustomAdapter, book_title_CustomAdapter, book_author_CustomAdapter,book_pages_CustomAdapter;

   //item position index in our recylerView
    int position;

    //create an activity object so that we can refresh our recyclerView in our application
    Activity activity;

    //custom Adapter constructor should have a context and four different arrayList
    //because when we initialize this class inside our main activity
    //we want to pass all these array list that we have already created in our MainActivity
    CustomAdapter(Activity activity,Context context , ArrayList book_id,ArrayList book_title, ArrayList book_author,ArrayList book_pages)
    {
        //refreshing our Main activity RecyclerView with new data
        this.activity = activity;

        //setting up the parameter variable of this constructor to our global variable of this class
        //so we can access these objects in our full class
        this.context = context;
        this.book_id_CustomAdapter = book_id;
        this.book_title_CustomAdapter = book_title;
        this.book_author_CustomAdapter = book_author;
        this.book_pages_CustomAdapter = book_pages;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we will be infating our row layout for our RecyclerView
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.recyler_view_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        this.position = position;
        //getting data from arrays and setting the data to our textView that is present in row Layout for our RecylerView
        //here we are going to set Text into these textView's
        holder.book_id_row.setText(String.valueOf(book_id_CustomAdapter.get(position)));
        holder.book_title_row.setText(String.valueOf(book_title_CustomAdapter.get(position)));
        holder.book_author_row.setText(String.valueOf(book_author_CustomAdapter.get(position)));
        holder.book_pages_row.setText(String.valueOf(book_pages_CustomAdapter.get(position)));

        //setting up the onclick Listener for our mainlayout
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                //sending data to update activity via intent
                intent.putExtra("id",String.valueOf(book_id_CustomAdapter.get(position)));
                intent.putExtra("title",String.valueOf(book_title_CustomAdapter.get(position)));
                intent.putExtra("author",String.valueOf(book_author_CustomAdapter.get(position)));
                intent.putExtra("pages",String.valueOf(book_pages_CustomAdapter.get(position)));
                //refreshing our Main activity recylerView with updated data
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id_CustomAdapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //creating textView objects from our RecyclerView Row Layout
        TextView book_id_row;
        TextView book_title_row;
        TextView book_author_row;
        TextView book_pages_row;
        // calling our mainlayout from our recycler_view_row.xml file
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //now getting the id's of those textView's
            book_id_row = itemView.findViewById(R.id.book_id_row);
            book_title_row = itemView.findViewById(R.id.book_title_row);
            book_author_row = itemView.findViewById(R.id.book_author_row);
            book_pages_row = itemView.findViewById(R.id.book_pages_row);
            // binding our mainlayout from our recycler_view_row.xml file to CustomAdapter class
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
