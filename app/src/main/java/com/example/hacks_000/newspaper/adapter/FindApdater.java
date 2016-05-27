package com.example.hacks_000.newspaper.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hacks_000.newspaper.R;
import com.example.hacks_000.newspaper.model.NewsObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TranManhTien on 07/12/2015.
 */
public class FindApdater extends ArrayAdapter<NewsObject> {

    Context mContext;
    ArrayList<NewsObject> mList = new ArrayList<NewsObject>();


    public FindApdater(Context context, int resource, List<NewsObject> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mList = new ArrayList<NewsObject>(objects);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
         ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           rowView = inflater.inflate(R.layout.search_screen, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.textView);
            viewHolder.desciption = (TextView) rowView.findViewById(R.id.textView2);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsObject newsObject = mList.get(position);
        viewHolder.title.setText(newsObject.getTitle());
        viewHolder.desciption.setText(newsObject.getDescription());

        return rowView;
    }

    static  class ViewHolder{
        TextView title;
        TextView desciption;
    }
}
