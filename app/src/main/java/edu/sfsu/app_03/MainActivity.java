package edu.sfsu.app_03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import edu.sfsu.app_03.models.DataModel;

public class MainActivity extends AppCompatActivity {
    ArrayList<DataModel> dataModel = null;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private static final String api = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DataDownload().execute(api);
    }

    public class DataDownload extends AsyncTask<String, Void, String> {
        Context context;

        public DataDownload() {
             this.context = MainActivity.this;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context = MainActivity.this;
        }

        @Override
        protected String doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                InputStream in = url.openStream();
                Log.v("LOG", "InputStream...");

                FileOutputStream out = openFileOutput("data.json", Context.MODE_PRIVATE);

                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);

                while(bytesRead != -1) {
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }

                out.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Feed Downloaded";
        }

        @Override
        protected void onPostExecute(String result) {
            Context context = MainActivity.this;
        }
    }
}