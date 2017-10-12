package com.example.abhinavchinta.top10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView ListApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListApps = (ListView) findViewById(R.id.xmlListView);

        downloadurl("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

       // Log.d(TAG, "onCreate: starting asynctask");
       // DownloadData downloadata= new DownloadData();
       // downloadata.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
       // Log.d(TAG, "onCreate: done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.feedsmenu,menu);
        //MenuInflater inflater= getMenuInflater();
        //inflater.inflate(R.menu.feedsmenu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String feedurl;
        switch (id){
            case R.id.menu1:
                feedurl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
                break;
            case R.id.menu2:
                feedurl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml";
                break;
            case R.id.menu3:
               feedurl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
                break;
            default: return super.onOptionsItemSelected(item);
        }
       downloadurl(feedurl);
        return true;
    }
    private void downloadurl(String feedurl){
        Log.d(TAG, "downloadurl: starting asynctask");
        DownloadData downloadata= new DownloadData();
        downloadata.execute(feedurl);
        Log.d(TAG, "downloadurl: done");

    }

    private class DownloadData extends AsyncTask<String, String , String>{
        private static final String TAG = "DownloadData";
        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressBar);

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            //Log.d(TAG, "onPostExecute: parameter is: "+s);
            ParseApplication parseapplication = new ParseApplication();
            parseapplication.parse(s);

           // ArrayAdapter<FeedEntry> arrayadapter =  new ArrayAdapter<FeedEntry>(
           //         MainActivity.this, R.layout.list_item, parseapplication.getApplications());
           // ListApps.setAdapter(arrayadapter);
            FeedAdapter feedadapter = new FeedAdapter(MainActivity.this, R.layout.titlenames,
                    parseapplication.getApplications());
            ListApps.setAdapter(feedadapter);
            progressbar.setVisibility(View.GONE);



        }

        @Override
        protected String doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: starts with" + strings[0]);
            String rssfeed= downloadxml(strings[0]);
            if (rssfeed==null){
                Log.e(TAG, "doInBackground: ERROR DOWNLOADING" );
            }
            //final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressBar);
            //progressbar.setProgress(0);
            return rssfeed;
            //return "doInBackground completed";
        }

        @Override
        protected void onProgressUpdate(String... progress) {



            progressbar.setProgress(0);

                progressbar.setProgress(Integer.parseInt(progress[0]));


            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        private String downloadxml(String urlPath){
            StringBuilder xmlResult = new StringBuilder();
            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response =connection.getResponseCode();
                Log.d(TAG, "downloadxml: the response code was:  "+response);
              //  InputStream inputstream = connection.getInputStream();
              //  InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
              //  BufferedReader reader= new BufferedReader(inputstreamreader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                int charsread;
                char[] inputBuffer = new char [500];
                while (true){
                    charsread =reader.read(inputBuffer);
                    if (charsread<0){
                        break;
                    }
                    if (charsread>0){
                        xmlResult.append(String.copyValueOf(inputBuffer,0,charsread));
                    }
                }

            reader.close();

                return xmlResult.toString();

            }catch (MalformedURLException e){
                Log.e(TAG, "downloadxml: invalid URL" + e.getMessage());
            }
            catch (IOException e){
                Log.e(TAG, "downloadxml: IO exeption " + e.getMessage() );
            }
            catch (SecurityException e){
                Log.e(TAG, "downloadxml: needs sec exeption" + e.getMessage() );
            }
            return null;

        }
    }
}
