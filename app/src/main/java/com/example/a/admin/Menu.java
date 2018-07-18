package com.example.a.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Menu extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView listss;
    ImageButton iv;
    BufferedReader reader;
    String  error=null;
    String view[]={"","","","","","","","","","","",""};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        iv = (ImageButton)findViewById(R.id.imageButton);
        listss = (ListView)findViewById(R.id.lis);



    }
    public void a(View v){
        ReadData t1 = new ReadData();
        t1.execute(new String[]{"http://192.168.0.16/readjson2.php"});
    }
    private class ReadData extends AsyncTask<String, Void, String> {
        String text="";
        private InputStream is1;
        ArrayList<String> list1;
        String line="";


        ProgressDialog dia = new ProgressDialog(Menu.this);
        protected  void onPreExecute(){
            dia.setMessage("Reading Data");
            dia.show();
        }
        protected String doInBackground(String... params) {
            for(String url1 : params) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    HttpResponse response = client.execute(post);
                    is1 = response.getEntity().getContent();
                } catch (ClientProtocolException e) {
                    Toast.makeText(Menu.this, e.toString(), Toast.LENGTH_LONG).show();
                    error = "ClientProtocolException:" + e.getMessage();
                }  catch (IOException e) {
                    Toast.makeText(Menu.this, e.toString(), Toast.LENGTH_LONG).show();
                    error = "ClientProtocolException:" + e.getMessage();
                }

                try {
                    reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"),8);

                    while ((line = reader.readLine()) != null) {
                        text = line + "\n";
                    }
                    is1.close();

                } catch (UnsupportedEncodingException e) {
                    error = "Unsuppor Encoding:" + e.getMessage();
                } catch (IOException e) {
                    error = "Error IO:" + e.getMessage();
                }


                list1  = new ArrayList<String>();
                try {
                    JSONArray ja = new JSONArray(text);
                    for(int i=0;i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        list1.add("테이블 번호"+jo.getString("tabelnum"));
                        view[i]=("김치찌개"+jo.getString("kim")+"\n"+"부대찌개"+jo.getString("bu")
                                +"\n"+"순두부찌개"+jo.getString("sun")
                                +"\n"+"된장찌개"+jo.getString("den"));
                    }
                } catch (JSONException e) {
                    error ="Error Convert to JSON or Error JSON Format." + e.getMessage();
                }
            }
            return error;
        }

        protected void onPostExecute(String result) {
            if(dia.isShowing()){
                dia.dismiss();
            }
            if(result != null){
                Toast.makeText(Menu.this , error, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(Menu.this , text, Toast.LENGTH_LONG).show();
                ArrayAdapter<String> adap1 = new ArrayAdapter(
                        Menu.this,
                        android.R.layout.simple_list_item_1,
                        list1);
                listss.setAdapter(adap1);
                listss.setOnItemClickListener(Menu.this);
            }
        }


    }
    public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id){
        Toast.makeText(Menu.this,"asdfasdf",Toast.LENGTH_LONG).show();
        AlertDialog.Builder as = new AlertDialog.Builder(this);
        as.setTitle("내용");
        as.setMessage(view[position]);
        as.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        as.show();
    }
}
