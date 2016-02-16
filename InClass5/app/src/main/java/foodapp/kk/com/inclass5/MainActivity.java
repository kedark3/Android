package foodapp.kk.com.inclass5;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/*
        * Name:Kedar Vijay Kulkarni
        * Filename:MainActivity.java
        * Assignment: In Class 05
        */

public class MainActivity extends AppCompatActivity {
    EditText editTextSearch;
    //ImageView imageViewNext, imageViewPrev;
    static ImageView imageViewResult;
    static HashMap<String,ArrayList<String>> imageIds;
    ArrayList<String> urlList=null;
    static ProgressDialog pDialog,pDiaglog2;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.imageViewNext).setClickable(false);
        findViewById(R.id.imageViewPrevious).setClickable(false);
        imageIds=new  HashMap<String,ArrayList<String>> ();
        editTextSearch=(EditText)findViewById(R.id.editTextSearchString);
        //imageViewNext=(ImageView) findViewById(R.id.imageViewNext);
        //imageViewPrev=(ImageView) findViewById(R.id.imageViewPrevious);
        pDialog=new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading Dictionary");
        pDiaglog2=new ProgressDialog(MainActivity.this);
        pDiaglog2.setMessage("Loading next image");
        imageViewResult= (ImageView) findViewById(R.id.imageViewResult);

        if(checkConnection()){
            Toast.makeText(MainActivity.this,"Network Available!",Toast.LENGTH_LONG).show();
            RequestParams params= new RequestParams("GET", "http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt");
            new getImageIdData().execute(params);
        }
        else{
            Toast.makeText(MainActivity.this,"Network NOT Available!",Toast.LENGTH_LONG).show();

        }


        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkConnection()==false) {
                    Toast.makeText(MainActivity.this,"Network NOT Available!",Toast.LENGTH_LONG).show();
                    return;
                }
                index=0;
                if (editTextSearch.getText().length() > 0) {
                    //Log.d("Demo", imageIds.get(editTextSearch.getText().toString()).get(0).toString());
                    if (imageIds.get(editTextSearch.getText().toString()) != null) {
                        urlList = imageIds.get(editTextSearch.getText().toString());
                        if(index==urlList.size())index=0;
                        RequestParams params = new RequestParams("GET", urlList.get(index));

                        new getImage().execute(params);
                        if(urlList.size()>1){
                            findViewById(R.id.imageViewNext).setClickable(true);
                            findViewById(R.id.imageViewPrevious).setClickable(true);
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "No Images found", Toast.LENGTH_LONG).show();
                        findViewById(R.id.imageViewNext).setClickable(false);
                        findViewById(R.id.imageViewPrevious).setClickable(false);
                    }

                }
            }
        });

        findViewById(R.id.imageViewNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlList==null)return;
                if(checkConnection()==false) {
                    Toast.makeText(MainActivity.this,"Network NOT Available!",Toast.LENGTH_LONG).show();
                    return;
                }
                index++;
                if(index<urlList.size()){
                    urlList = imageIds.get(editTextSearch.getText().toString());
                    RequestParams params = new RequestParams("GET", urlList.get(index));
                    new getImage().execute(params);
                }
                else{
                    index=0;
                    urlList = imageIds.get(editTextSearch.getText().toString());
                    RequestParams params = new RequestParams("GET", urlList.get(index));
                    index++;
                    new getImage().execute(params);
                }
            }
        });

        findViewById(R.id.imageViewPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlList==null)return;
                if(checkConnection()==false) {
                    Toast.makeText(MainActivity.this,"Network NOT Available!",Toast.LENGTH_LONG).show();
                    return;
                }
                index--;
                if(index>-1){

                    urlList = imageIds.get(editTextSearch.getText().toString());
                    RequestParams params = new RequestParams("GET", urlList.get(index));

                    new getImage().execute(params);
                }
                else{
                    index=urlList.size()-1;
                    urlList = imageIds.get(editTextSearch.getText().toString());
                    RequestParams params = new RequestParams("GET", urlList.get(index));
                    index--;
                    new getImage().execute(params);
                }
            }
        });
    }


    public boolean checkConnection(){

        ConnectivityManager cm= (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;

    }



}
