package ru.danilov.st.utils;

import java.net.HttpURLConnection;
import java.net.URL;

public final class InternetConnection {

    private InternetConnection() {}

    public static boolean isConnection() {
        boolean result = false;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL("http://www.google.com").openConnection();
            con.setRequestMethod("HEAD");
            result = (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {

        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception e) {

                }
            }
        }
        return result;
    }
}
