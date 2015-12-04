package com.leandro.my_json;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String URL = "http://lnx.villaraffaella.it/app/json_rooms.php";
    private final OkHttpClient client = new OkHttpClient();
    private TextView jsonview, device, os, chipset;
    private TableLayout table;
    private ProgressBar progress;
    private ImageView device_image;
    private String json = null;
    private LinearLayout layout;

    public List<room> getRooms() {
        return rooms;
    }

    public void setRooms(List<room> rooms) {
        this.rooms = rooms;
    }

    private List<room> rooms = new ArrayList<room>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = (TableLayout) findViewById(R.id.table);
        jsonview = (TextView) findViewById(R.id.jsonview);
        layout = (LinearLayout) findViewById(R.id.container);
        device = (TextView) findViewById(R.id.device);
        os = (TextView) findViewById(R.id.os);
        device_image = (ImageView) findViewById(R.id.device_image);
        chipset = (TextView) findViewById(R.id.chipset);
        jsonview.setMovementMethod(new ScrollingMovementMethod());
        progress = (ProgressBar) findViewById(R.id.progressBar);

        BackgroundTask task = new BackgroundTask();
        task.execute();
        parse_json();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void set_json(String s) {
        progress.setVisibility(View.GONE);
        jsonview.setText(s);
        json = s;
    }

    private void parse_json() {
        if (json != null) {
            //JSONObject object=null;
            JSONArray jsonArray = null;
            String ImageUrl = null;



            try {
                jsonArray = new JSONArray(json);

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "JSON OBJECT EXCEPTION" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            try {
                if (jsonArray != null) {
                    //jArray = object.getJSONArray("0");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        room vettore_room_json = new room();
                        vettore_room_json.setId(12);
                        vettore_room_json.setName("test");
                        vettore_room_json.setNote("note_testt");
                        //rooms.add(vettore_room_json);
                        rooms.add(vettore_room_json);

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        device.setText(jsonObject.getString("id"));
                        os.setText("n:" + jsonArray.length());
                        chipset.setText(jsonObject.getString("name"));
                        //chipset.setText("TEST TEST TEST");
                        //ImageUrl = object.getString("image");
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Picasso.with(MainActivity.this).load(ImageUrl).into(device_image);
            layout.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.get_json) {
            BackgroundTask task = new BackgroundTask();
            task.execute();
            return true;
        } else if (id == R.id.parse) {

            parse_json();
            return true;
        } else if (id == R.id.view_json) {
            Intent i = new Intent(MainActivity.this, Display_items.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.leandro.my_json/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.leandro.my_json/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(URL)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            if (data != null) {
                set_json(data);
            }
        }
    }


}

