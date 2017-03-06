package com.example.hknytbz.everynote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> notes = new ArrayList<>();

    public static int index = 0;
    public static ArrayAdapter<String> adapter;
    public static Set<String> mySet;
    public static SharedPreferences myPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myPref = this.getSharedPreferences("com.example.hknytbz.everynote", Context.MODE_PRIVATE);
        mySet = myPref.getStringSet("notes",null);
        notes.clear();
        if(notes == null)
            notes.add("Hello! Click + to add notes");
        if(mySet != null)
        {

            notes.addAll(mySet);
        }
        else
        {
            Log.i("Set","NULL");

            mySet  = new HashSet< >();
            mySet.addAll(notes);
            Log.i("Add","Done");
            myPref.edit().remove("notes").apply();
            myPref.edit().putStringSet("notes",mySet).apply();
        }

        adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
        ListView printNotes = (ListView) findViewById(R.id.notes);
        printNotes.setAdapter(adapter);
        printNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),AddNote.class);
                i.putExtra("pos",position);
                startActivity(i);
            }
        });
        printNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                if(mySet == null)
                                {
                                    mySet = new HashSet<String>();
                                }
                                else
                                {
                                    mySet.clear();
                                }
                                mySet.addAll(notes);
                                myPref.edit().remove("notes").apply();
                                myPref.edit().putStringSet("notes",mySet).apply();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .show();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addNote) {
            Intent i = new Intent(getApplicationContext(),AddNote.class);
            i.putExtra("pos",notes.size());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
