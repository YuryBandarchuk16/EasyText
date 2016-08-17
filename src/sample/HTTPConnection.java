package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HTTPConnection {

    private String stringUrl;
    private URL url;
    private HttpURLConnection mainHttpConnection = null;

    public HTTPConnection(String stringUrl) throws Exception {
        this.stringUrl = stringUrl;
        url = new URL(stringUrl);
    }

    public void setStringURL(String stringUrl) throws Exception {
        this.stringUrl = stringUrl;
        url = new URL(stringUrl);
    }

    ArrayList<String> makeRequest() throws Exception {
        mainHttpConnection = (HttpURLConnection)url.openConnection();
        ArrayList<String> result = new ArrayList<>();
        InputStream inputStream;
        if (mainHttpConnection.getResponseCode() < 400) {
            inputStream = mainHttpConnection.getInputStream();
        } else {
            inputStream = mainHttpConnection.getErrorStream();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine = "";

        while ((nextLine = bufferedReader.readLine()) != null) {
            result.add(nextLine);
        }

        return result;
    }

}