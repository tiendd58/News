package com.example.hacks_000.newspaper.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hacks_000.newspaper.R;
import com.example.hacks_000.newspaper.fragment.ScreenFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;

public class ReadNewsActivity extends AppCompatActivity {
    private String link;
    private String description;
    private String title;
    private String img;
    private  TextView tvTitle;
    private TextView tvDescription;
    private TextView tv1;
    private TextView tv2;
    private ImageView iv;
    private Toolbar toolbar;
    private MediaPlayer media = new MediaPlayer();
    private String[] toSpeak1;
    private String[] toSpeak2;
    private String content1 ="";
    private String content2 = "";
    private boolean finish = false;
    private boolean stop=false;
    private boolean pause=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        tvTitle = (TextView) findViewById(R.id.title);
        tvDescription = (TextView) findViewById(R.id.description);
        tv1 = (TextView) findViewById(R.id.content1);
        tv2 = (TextView) findViewById(R.id.content2);
        iv = (ImageView) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        /*ScreenFragment screenFragment = (ScreenFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_screen);
        screenFragment.setUp(R.id.fragment_screen, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);*/
        new _JSOUP().execute(null,null,null);

    }
    public class _JSOUP extends AsyncTask<Void , Void, Void> {
        ProgressDialog dialog;
        Bitmap mIcon11;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ReadNewsActivity.this,ProgressDialog.THEME_HOLO_DARK);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.setMessage("loading...");
            dialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                Bundle extras = getIntent().getExtras();
                Bundle bundle = extras.getBundle("bundleLink");
                link = bundle.getString("link");
                description = bundle.getString("description");
                title = bundle.getString("title");
                img = bundle.getString("image");
                Document document = Jsoup.connect(link).get();
                Elements elements = document.select("p");
                content1 = ""+ elements.get(5).text()+"\n";
                for (int i = 6; i < elements.size()-5; i++) {
                    content2 += "\n"+ elements.get(i).text();
                }
                mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(img).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e ){

            }
            return  null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            tvTitle.setText(title);
            tvDescription.setText(description);
            iv.setImageBitmap(mIcon11);
            tv1.setText(content1);
            tv2.setText(content2);
        }

    }

    public class SPEAK extends AsyncTask<Void , Void, Void> {
        ProgressDialog dialog;
        Bitmap mIcon11;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ReadNewsActivity.this,ProgressDialog.THEME_HOLO_DARK);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.setMessage("loading...");
            dialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {

            try {
                Bundle extras = getIntent().getExtras();
                Bundle bundle = extras.getBundle("bundleLink");
                link = bundle.getString("link");
                description = bundle.getString("description");
                title = bundle.getString("title");
                img = bundle.getString("image");
                Document document = Jsoup.connect(link).get();
                Elements elements = document.select("p");
                content1 = ""+ elements.get(5).text()+"\n";
                for (int i = 6; i < elements.size()-5; i++) {
                    content2 += "\n"+ elements.get(i).text();
                }
                mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(img).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e ){

            }
            return  null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            tvTitle.setText(title);
            tvDescription.setText(description);
            iv.setImageBitmap(mIcon11);
            tv1.setText(content1);
            tv2.setText(content2);
        }

    }
    public void play(View view) {
        ImageButton listen=(ImageButton) findViewById(R.id.imageButton);
        listen.setImageResource(R.drawable.listen);
        new Thread(new Runnable() {
            @Override
            public void run() {
                subContent();
                finish=true;
            }
        }).start();
        while(!finish);
        new Thread(new Runnable() {
            @Override
            public void run() {
                stop=false;
                speak(title);
                for(String i:toSpeak2){
                    speak(i);
                    if(stop){
                        break;
                    }
                    while (pause);
                }
            }
        }).start();


    }
    //function speak from text
    protected void speak(final String text) {
        if(media.isPlaying())
            return;

        try {
            media.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
            String urlEncoded = Uri.encode(text, ALLOWED_URI_CHARS);
            String urlMedia = "http://118.69.135.22/synthesis/file?voiceType=female&text=" + urlEncoded;
            media.setDataSource(urlMedia);
            media.prepare();
            media.start();
            while (media.isPlaying()) ;
            media.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void subContent(){
        toSpeak1=content1.split(",");
        toSpeak2=content2.split(",");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        media.stop();
        stop=true;
    }
    public void pause(View view){
        ImageButton ib=(ImageButton) findViewById(R.id.imageButton2);
        if(!pause){

            ib.setImageResource(R.drawable.play);
            media.pause();
            pause=true;
        }else{

            ib.setImageResource(R.drawable.pause);
            media.start();
            pause=false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
