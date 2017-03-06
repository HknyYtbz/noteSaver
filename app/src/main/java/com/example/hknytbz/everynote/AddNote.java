package com.example.hknytbz.everynote;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class AddNote extends AppCompatActivity implements TextWatcher {
    EditText newNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        android.app.ActionBar backButton =getActionBar();
        if(backButton != null)
            backButton.setDisplayHomeAsUpEnabled(true);
        newNote = (EditText) findViewById(R.id.editText);
        Intent i = getIntent();
        int pos = i.getIntExtra("pos",-1);
        if(pos != -1 && pos != MainActivity.notes.size())
        {
            newNote.setText(MainActivity.notes.get(pos));
        }
        newNote.addTextChangedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {

            case android.R.id.home:
                Intent temp = getIntent();
                int t =  temp.getIntExtra("pos",-1);
                Log.i("Interesting",t+"");
                if(t == 0 && MainActivity.notes.size() != 0)
                {
                    MainActivity.notes.set(t,newNote.getText().toString());
                }
                else if (t == 0 && MainActivity.notes.size() == 0)
                {
                    MainActivity.notes.add(t,newNote.getText().toString());
                }
                else
                {
                    MainActivity.notes.add(t,newNote.getText().toString());
                }


                if(MainActivity.mySet == null)
                {
                    MainActivity.mySet = new HashSet<>();
                }
                else
                {
                    MainActivity.mySet.clear();
                }

                    MainActivity.mySet.addAll(MainActivity.notes);
                    MainActivity.myPref.edit().remove("notes").apply();
                    MainActivity.myPref.edit().putStringSet("notes", MainActivity.mySet).apply();
                    MainActivity.adapter.notifyDataSetChanged();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            MainActivity.adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
