package fi.ptm.networkingexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author PTM
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fetchData(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String url = editText.getText().toString();
        FetchDataTask task = new FetchDataTask();
        task.execute(url);
    }


    class FetchDataTask extends AsyncTask<String, Void, String> {

        protected  void onPreExecute() {
            Button button = (Button) findViewById(R.id.button);
            button.setEnabled(false);
            TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText("Fetching, please wait...");
        }

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(String response) {
            TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText(response);
            Button button = (Button) findViewById(R.id.button);
            button.setEnabled(true);
        }
    }

}
