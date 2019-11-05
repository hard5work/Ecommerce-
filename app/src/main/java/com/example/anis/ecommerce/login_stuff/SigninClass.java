package com.example.anis.ecommerce.login_stuff;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SigninClass  extends AsyncTask {

    public Context context;

    public SigninClass(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... arg1) {
        try
        {
            String username = (String) arg1[0];
            String password = (String) arg1[1];
            String link = "http:/192.168.1.174:8080/ecommerce/login.php";

            String data =
                    URLEncoder.encode("username" , "UTF-8") + "=" +
                            URLEncoder.encode(username,"UTF-8");

            data+= "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn  = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            //Read Server Response
            while ((line = reader.readLine()) != null){
                sb.append(line);
                break;
            }
            return sb.toString();

        } catch (UnsupportedEncodingException e) {
            return (String)("Exception: " + e.getMessage());
        } catch (MalformedURLException e) {
            return (String)("Exception: " + e.getMessage());
        } catch (IOException e) {
            return (String)("Exception: " + e.getMessage());
        }

    }


    protected void onPostExecute(String result) {
        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
    }
}
