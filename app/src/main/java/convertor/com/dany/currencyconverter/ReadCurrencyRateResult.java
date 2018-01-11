package convertor.com.dany.currencyconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ReadCurrencyRateResult extends AsyncTask<String , Void ,String> {
    String server_response;
    String resultResponse;

    @Override
    protected String doInBackground(String... strings){

        URL url;
        HttpURLConnection urlConnection = null;
        String[] responseCodes=new String[]{};
        try {
            url = new URL(strings[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                server_response = readStream(urlConnection.getInputStream());
                server_response=server_response.replace("{","").replace("}","");
                 responseCodes=server_response.split(":");
                 if(responseCodes.length > 1){
                     resultResponse=responseCodes[1];
                 }


            }

        } catch (MalformedURLException e) {
            resultResponse="ERR101 : Check Internet Connection";
        } catch (IOException e) {
            resultResponse="ERR101 : Check Internet Connection";
        }finally {
            urlConnection.disconnect();
        }

        return resultResponse;
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }



    }

