package com.example.abhinavchinta.top10;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Abhinav Chinta on 9/15/2017.
 */

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutinflater;
    private List<FeedEntry> application;
    public ImageView imageview;
    public String src=null;
    public int count=1;


    public FeedAdapter(@NonNull Context context, @LayoutRes int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutinflater = LayoutInflater.from(context);
        this.application = applications;
    }

    @Override
    public int getCount() {
        return application.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewholder;
        if (convertView==null){
            convertView=layoutinflater.inflate(layoutResource,parent,false);

            viewholder=new Viewholder(convertView);
            convertView.setTag(viewholder);
        }
        else {
            viewholder = (Viewholder)convertView.getTag();
        }
        //View view = layoutinflater.inflate(layoutResource,parent,false);
        TextView title = (TextView)convertView.findViewById(R.id.TVname);
        TextView artist = (TextView)convertView.findViewById(R.id.artist);
        TextView summary = (TextView)convertView.findViewById(R.id.summary);
        TextView album = (TextView)convertView.findViewById(R.id.album);

        imageview = (ImageView)convertView.findViewById(R.id.imageView);

        FeedEntry currentapp = application.get(position);
        //viewholder.tvname.setText(currentapp.getTitle());
        //viewholder.tvname.setText(count+": ");count++;
        viewholder.tvname.setText(currentapp.getName());

        viewholder.artist.setText(currentapp.getArtist());
        viewholder.album.setText(currentapp.getTitle());

        //viewholder.summary.setText(currentapp.getTitle());
        viewholder.summary.setText(currentapp.getSummary());
        viewholder.imageview.setImageBitmap(currentapp.getBitmap());

        //summary.setText(currentapp.getImageURL());
        //Download download = new Download();
        //download.doInBackground("http://is3.mzstatic.com/image/thumb/Purple118/v4/51/42/5d/51425d9a-8150-93ce-c6b5-5c1de26ef3e7/mzl.elloeqsq.png/75x75bb-85.png");
        //imageview.setImageBitmap(bitmap);
        //final ImageView imageview = (ImageView)findViewById(R.id.imageView);
        //loadbitmap bitmap = new
        //imageview.setImageBitmap(loadbitmap("tps://cdn.applypixels.com/app/uploads/2016/04/icon_large.png"));

        //mageview.setImageResource(currentapp.getImageURL());
        /*try{String url ="https://cdn.applypixels.com/app/uploads/2016/04/icon_large.png";
            ImageView imageview = (ImageView)view.findViewById(R.id.imageView);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            imageview.setImageBitmap(bitmap);
        }catch (MalformedURLException e){
            Log.e(TAG, "getView: " + e.getMessage() );
        }catch (IOException e){
            Log.e(TAG, "getView: "+ e.getMessage() );
        }*/




        return convertView;
    }

    private class Viewholder{
        final TextView tvname;
        final TextView artist;
        final TextView summary;
        final ImageView imageview;
        final TextView album;
        Viewholder(View v){
            this.tvname=(TextView)v.findViewById(R.id.TVname);
            this.artist=(TextView)v.findViewById(R.id.artist);
            this.summary=(TextView)v.findViewById(R.id.summary);
            this.imageview=(ImageView)v.findViewById(R.id.imageView);
            //this.summary=(TextView)v.findViewById(R.id.su)
            this.album=(TextView)v.findViewById(R.id.album);

        }
    }
}
