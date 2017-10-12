package com.example.abhinavchinta.top10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Abhinav Chinta on 9/13/2017.
 */

public class ParseApplication {
    private static final String TAG = "ParseApplication";
    private ArrayList<FeedEntry> applications;
    private String src=null;
    public ImageView imageview;
    public int count=1;
    public int newcount;

    public ParseApplication() {
        this.applications=new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData){
        boolean status= true;
        FeedEntry currentRecord=null;
        boolean inentry=false;
        String textvalue="";

        try{
            XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType=xpp.getEventType();
            while (eventType!= XmlPullParser.END_DOCUMENT){
                String tagName =xpp.getName();
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: starting tag for"+tagName);
                        if ("entry".equalsIgnoreCase(tagName)){
                            inentry=true; newcount=0;
                            currentRecord= new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textvalue=xpp.getText();
                        break;
                    case  XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: ending tag for" + tagName);
                        if (inentry){
                        if ("entry".equalsIgnoreCase(tagName)){
                            applications.add(currentRecord);count++;
                            inentry=false;
                        }
                        else if ("collection".equalsIgnoreCase(tagName)){
                            currentRecord.setTitle(textvalue);
                        }

                        else if("artist".equalsIgnoreCase(tagName)){
                            //count++;
                            currentRecord.setArtist(textvalue);
                            //newcount=0;
                        }
                        else if("releaseDate".equalsIgnoreCase(tagName)){
                            currentRecord.setReleaseDate(textvalue);
                        }
                        else if("summary".equalsIgnoreCase(tagName)){
                            currentRecord.setSummary(textvalue);
                        }
                        else if("image".equalsIgnoreCase(tagName)){
                            currentRecord.setImageURL(textvalue);
                            Download newdownload = new Download();
                            //newdownload.execute("http://is3.mzstatic.com/image/thumb/Purple118/v4/51/42/5d/51425d9a-8150-93ce-c6b5-5c1de26ef3e7/mzl.elloeqsq.png/75x75bb-85.png");
                            //newdownload.execute(textvalue);
                            src=textvalue;
                            currentRecord.setBitmap(newdownload.execute(textvalue).get());


                        }
                        else if (newcount==0){
                            if("name".equalsIgnoreCase(tagName)){

                                //count++;
                                newcount++;

                                currentRecord.setName(count+": "+textvalue);

                            }}
                        }
                        break;
                    default:
                        //nothing here
                }
                eventType=xpp.next();

            }



        }catch (Exception e){
            status=false;
            e.printStackTrace();
        }
        count++;
        return status;

    }
    private class Download extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            try{
                //FeedEntry r = new FeedEntry();
                //String src =r.getImageURL();
                //String src = "http://is3.mzstatic.com/image/thumb/Purple118/v4/51/42/5d/51425d9a-8150-93ce-c6b5-5c1de26ef3e7/mzl.elloeqsq.png/75x75bb-85.png";
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                return bitmap;
            }catch (IOException e){

                return null;
            }
            //return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //final ImageView imageview = (ImageView)View.findViewById(R.id.imageView);
            //imageview.setImageBitmap(bitmap);
            //final ImageView imageview = (ImageView)view.findViewById(R.id.imageView);
            //Download newdownload = new Download();
            //newdownload.execute("http://is3.mzstatic.com/image/thumb/Purple118/v4/51/42/5d/51425d9a-8150-93ce-c6b5-5c1de26ef3e7/mzl.elloeqsq.png/75x75bb-85.png");
            //imageview.setImageBitmap(bitmap);





        }
}}
