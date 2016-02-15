package foodapp.kk.com.inclass5;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageViewNext, imageViewPrev;
    ArrayList<String> imageIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageIds=new ArrayList<String>();
        textView=(TextView)findViewById(R.id.textView);
        imageViewNext=(ImageView) findViewById(R.id.imageViewNext);
        imageViewPrev=(ImageView) findViewById(R.id.imageViewPrevious);
        
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading next Image");

        if(checkConnection()){
            Toast.makeText(MainActivity.this,"Network Available!",Toast.LENGTH_LONG).show();
            RequestParams params= new RequestParams("GET", "http://dev.theappsdr.com/lectures/inclass_photos/index.php");
            new getImageIdData().execute(params);
        }
        else{
            Toast.makeText(MainActivity.this,"Network NOT Available!",Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public boolean checkConnection(){

        ConnectivityManager cm= (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;

    }


    public class getImageIdData extends AsyncTask<RequestParams,Void,String>{


        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader reader=null;

            try{
                HttpURLConnection con= params[0].setupConnection();
                reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line="";
                while ((line=reader.readLine()) != null){
                    sb.append(line +"\n");
                    imageIds.add(line);
                }
                return sb.toString();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            textView.setText(imageIds.get(0));
            RequestParams params= new RequestParams("GET", "http://dev.theappsdr.com/lectures/inclass_photos/index.php");
            params.addParams("pid",imageIds.get(0));
            new getImage().execute(params);
        }
    }

    public class getImage extends AsyncTask<RequestParams,Void,Bitmap>{


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView= (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(RequestParams... params) {
            try {
                HttpURLConnection con= params[0].setupConnection();
                Bitmap image= BitmapFactory.decodeStream(con.getInputStream());

                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
