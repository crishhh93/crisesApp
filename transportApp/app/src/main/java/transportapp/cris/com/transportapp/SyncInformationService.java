package transportapp.cris.com.transportapp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import org.json.JSONArray;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class SyncInformationService extends IntentService {

    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public SyncInformationService() {
        super("SyncDataService");
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        String jsonString = intent.getStringExtra("NewUser");

        HttpURLConnection conn;
        try {
            URL url = new URL("http://192.168.0.103/api.php");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            String urlParameters = "users=" + jsonString;
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                Log.d("transportapp","response code 200");
            }

        }catch (IOException e){
            Log.d("transportapp","Exception");
        }


        Log.d("transportapp", jsonString);
    }
}