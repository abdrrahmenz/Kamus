package com.expert.andro.kamus.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.expert.andro.kamus.MainActivity;
import com.expert.andro.kamus.R;
import com.expert.andro.kamus.db.KamusHelper;
import com.expert.andro.kamus.model.KamusModel;
import com.expert.andro.kamus.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreLoadActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        AppPreference appPreference = new AppPreference(this);

        Boolean firtsRun = appPreference.getFirstRun();

        if (firtsRun){
            new LoadData().execute();
        } else {
            startActivity(new Intent(PreLoadActivity.this, MainActivity.class));
            finish();
        }
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            kamusHelper = new KamusHelper(getApplicationContext());
            appPreference = new AppPreference(PreLoadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            Log.d("FIRST RUN", " "+firstRun);
            if (firstRun){
                ArrayList<KamusModel> listKamusIdEng = preLoadRawIdEng();
                ArrayList<KamusModel> listKamusEngId = preLoadRawEngId();
                Log.d("Size", " "+listKamusIdEng.size());
                progress = 30;

                publishProgress((int) progress);

                kamusHelper.open();

                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / listKamusIdEng.size();

                kamusHelper.insertTransactionIdEng(listKamusIdEng);
                kamusHelper.insertTransactionEngId(listKamusEngId);

                kamusHelper.close();
                appPreference.setFirstRun(false);

                publishProgress((int) maxProgress);
            }else {
                try {
                    synchronized (this){
                        this.wait();

                        publishProgress(50);
                        this.wait();
                        publishProgress((int)maxProgress);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(PreLoadActivity.this, MainActivity.class));
            finish();
        }
    }

    private ArrayList<KamusModel> preLoadRawIdEng() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res =getResources();
            InputStream raw_data = res.openRawResource(R.raw.indonesia_english);
            reader = new BufferedReader(new InputStreamReader(raw_data));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                KamusModel kamusModel;
                kamusModel = new KamusModel(splitstr[0],splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line!=null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return kamusModels;
    }

    private ArrayList<KamusModel> preLoadRawEngId() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;

        try {
            Resources res =getResources();
            InputStream raw_data = res.openRawResource(R.raw.english_indonesia);
            reader = new BufferedReader(new InputStreamReader(raw_data));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                KamusModel kamusModel;
                kamusModel = new KamusModel(splitstr[0],splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line!=null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return kamusModels;
    }
}
