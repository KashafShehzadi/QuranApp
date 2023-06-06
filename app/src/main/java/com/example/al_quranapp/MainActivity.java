package com.example.al_quranapp;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    ListView surahListView;

    private static final String SELECTED_SURAH_INDEX_KEY = "selectedSurahIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surahListView = findViewById(R.id.SurahList);
        ParaSurah ps;
        ps = new ParaSurah();
        Qurandata qd;
        qd = new Qurandata();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ps.urduSurahNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView myText = (TextView) view.findViewById(android.R.id.text1);
                return view;
            }
        };
        surahListView.setAdapter(adapter);

        surahListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, SurahDetail.class);
                intent.putExtra(SELECTED_SURAH_INDEX_KEY, position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the selected surah index in the bundle
        outState.putInt(SELECTED_SURAH_INDEX_KEY, surahListView.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the selected surah index from the bundle
        int selectedSurahIndex = savedInstanceState.getInt(SELECTED_SURAH_INDEX_KEY);
        surahListView.setSelection(selectedSurahIndex);
    }


    }