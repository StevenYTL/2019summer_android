package android.example.com.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private OkHttpClient client;
    private String webString; // 這是一個json型態的string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview_main);
        client = new OkHttpClient();
    }

    // button_onclick ConnectWeb
    public void checkInternet(View view) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            //Create background thread to connect and get data
            textView.setText("Connected!");
            new DownloadWebpageTask().execute();
        }
        else {
            textView.setText("No network connection available.");
        }
    }

    class DownloadWebpageTask extends AsyncTask<Void,Void,String>{
        String WebResult;

        @Override
        protected String doInBackground(Void... voids) {

            Request request = new Request.Builder()
                    .url("http://140.116.96.202:8000/getNotify/")
                    .build();

            try{
                Response response = client.newCall(request).execute();
                WebResult = response.body().string();
            }catch (IOException e){
                e.printStackTrace();
            }
            return WebResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
            webString = s;
        }
    }

    // button_onclick AnalysisJson
    public void AnalysisJson(View view) {

    }
}
