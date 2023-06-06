package com.example.al_quranapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.al_quranapp.Qurandata;
import com.example.al_quranapp.ParaSurah;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;



public class SurahDetail extends AppCompatActivity {

    private static final String SELECTED_SURAH_INDEX_KEY = "selectedSurahIndex";
    private static final String AYAT_CONTENT_KEY = "ayatContent";
    private static final String CURRENT_AYAT_NUMBER_KEY = "currentAyatNumber";
    TextView ayatTextView;

    ParaSurah ps;
    Qurandata qd;

    EditText editText1;
    Button  btn;
    String[] ayatContent;
    int selectedSurahIndex;
    int startingIndex;
    int ayatCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);
        ps = new ParaSurah();
        qd = new Qurandata();
        ayatTextView = findViewById(R.id.textView1);
        editText1= findViewById(R.id.editText);
        btn = findViewById(R.id.Searchbtn);


        TextView Surah=findViewById(R.id.textView);
        Intent intent = getIntent();
        if (intent != null) {
            selectedSurahIndex = intent.getIntExtra("selectedSurahIndex", 0);
            startingIndex = ps.getSurahStart(selectedSurahIndex);
            ayatCount = ps.getSurahVerses(selectedSurahIndex);

            if (savedInstanceState != null) {
                // Restore the saved state
                ayatContent = savedInstanceState.getStringArray(AYAT_CONTENT_KEY);

            } else {
                // Load the ayat content for the selected surah
                ayatContent = qd.GetData(startingIndex - 1, startingIndex + ayatCount);

            }

            String surahUrdu = ps.urduSurahNames[selectedSurahIndex];
           Surah.setText(surahUrdu);

            StringBuilder ayatContentBuilder = new StringBuilder();
            for (String ayat : ayatContent) {
                ayatContentBuilder.append(ayat).append(" ");
            }

            // Display the ayat content
            ayatTextView.setText(ayatContentBuilder.toString());

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ayatNumberStr =  editText1.getText().toString();
                    if (!TextUtils.isEmpty(ayatNumberStr)) {
                        int ayatNumber = Integer.parseInt(ayatNumberStr);
                        selectedSurahIndex = intent.getIntExtra("selectedSurahIndex", 0);


                        startingIndex = ps.getSurahStart(selectedSurahIndex);


                        ayatCount = ps.getSurahVerses(selectedSurahIndex);


                        int targetIndex = startingIndex + (ayatNumber - 1);


                        if (targetIndex >= startingIndex && targetIndex < (startingIndex + ayatCount)) {
                            String[] ayatContent = qd.GetData(targetIndex, targetIndex+1);


                            String ayatContentString = ayatContent[0];

                            // Create an intent to start AyatDetailActivity
                            Intent detailIntent = new Intent(SurahDetail.this, AyatDetail.class);

                            detailIntent.putExtra("surahName", surahUrdu);
                            detailIntent.putExtra("ayatNumber", ayatNumber);
                            detailIntent.putExtra("ayatContent", ayatContentString);

                            startActivity(detailIntent);


                        } else {

                            Toast.makeText(SurahDetail.this, "Invalid ayat number", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(SurahDetail.this, "Invalid ayat number", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the necessary data in the bundle
        outState.putInt(SELECTED_SURAH_INDEX_KEY, selectedSurahIndex);
        outState.putStringArray(AYAT_CONTENT_KEY, ayatContent);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the necessary data from the bundle
        selectedSurahIndex = savedInstanceState.getInt(SELECTED_SURAH_INDEX_KEY);
        startingIndex = ps.getSurahStart(selectedSurahIndex);
        ayatCount = ps.getSurahVerses(selectedSurahIndex);
        ayatContent = savedInstanceState.getStringArray(AYAT_CONTENT_KEY);

    }

}