package com.example.hacks_000.newspaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hacks_000.newspaper.R;
import com.example.hacks_000.newspaper.activity.HomeActivity;
import com.example.hacks_000.newspaper.activity.ReadNewsActivity;
import com.example.hacks_000.newspaper.model.NewsObject;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by hacks_000 on 11/25/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsObject> listObj;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView title;
        private TextView desciption;
        private int pos;

        public ViewHolder(View itemView) {
            super(itemView);

            CardView cv = (CardView) itemView.findViewById(R.id.cv);
            img = (ImageView) itemView.findViewById(R.id.summary_image);
            title = (TextView) itemView.findViewById(R.id.title_news);
            desciption = (TextView) itemView.findViewById(R.id.description_news);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("link", listObj.get(pos).getLink());
            bundle.putString("description", listObj.get(pos).getDescription());
            bundle.putString("title", listObj.get(pos).getTitle());
            bundle.putString("image", listObj.get(pos).getSummaryImg());

            Intent intent = new Intent(context, ReadNewsActivity.class);
            intent.putExtra("bundleLink", bundle);
            context.startActivity(intent);
        }
    }

    public NewsAdapter(Context context, ArrayList<NewsObject> listObj) {
        this.listObj = listObj;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        new DownloadImageTask(holder.img).execute(listObj.get(position).getSummaryImg());
        holder.title.setText(listObj.get(position).getTitle());
        holder.desciption.setText(listObj.get(position).getDescription());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return listObj.size();
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
}
