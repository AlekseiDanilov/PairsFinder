package ru.danilov.st.data;

import ru.danilov.st.timeseries.ItemTimeSeries;
import ru.danilov.st.trading.Asset;
import ru.danilov.st.utils.InternetConnection;
import ru.danilov.st.utils.TimeSeriesUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Serializer {

    private List<Asset> quotes;
    private Date startDate;
    private Date endDate;
    public Serializer(String fileName, Date startDate) {
        this.endDate = new Date();
        this.startDate = startDate;

        if (InternetConnection.isConnection()) {
            quotes = new LinkedList<>();
            try (BufferedReader c = new BufferedReader(new FileReader(fileName))) {
                String ticker;
                while ((ticker = c.readLine()) != null) {
                    quotes.add(new Asset(ticker));
                    readQuot(ticker);
                }
            } catch (IOException ex) {

            }
            for (int i = 0; i < quotes.size(); i++) {
                quotes.get(i).reverse();
            }
        }

    }

    private void readQuot(String ticker) throws IOException {
        URL url = new URL(getURL2(ticker));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            reader.readLine();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                quotes.get(quotes.size() - 1).add(new ItemTimeSeries(parseValue(inputLine), parseDate(inputLine)));
            }
        } catch (MalformedURLException ex) {

        }
    }

    private String getURL1(String ticker) {
        String url = "http://ichart.yahoo.com/table.csv?s=" + ticker +
                "&a=" + startDate.getMonth() +
                "&b=" + startDate.getDate() +
                "&c=" + (startDate.getYear() + 1900) +
                "&d=" + endDate.getMonth() +
                "&e=" + endDate.getDate() +
                "&f=" + (endDate.getYear() + 1900) +
                "&g=d&ignore=.csv";
        return url;

    }

    private String getURL2(String ticker) {
        String url = "http://real-chart.finance.yahoo.com/table.csv?s=" + ticker +
                "&a=" + startDate.getMonth() +
                "&b=" + startDate.getDate() +
                "&c=" + (startDate.getYear() + 1900) +
                "&d=" + endDate.getMonth() +
                "&e=" + endDate.getDate() +
                "&f=" + (endDate.getYear() + 1900) +
                "&g=d&ignore=.csv";
        return url;

    }

    private Date parseDate(String inputLine) {
        StringTokenizer st = new StringTokenizer(inputLine, ",\n");

        String lineDate = new String();
        if (st.hasMoreTokens()) {
            lineDate = st.nextToken();
        }
        Date date = new Date();

        st = new StringTokenizer(lineDate, "-");
        date.setYear(Integer.parseInt(st.nextToken()) - 1900);
        date.setMonth(Integer.parseInt(st.nextToken()) - 1);
        date.setDate(Integer.parseInt(st.nextToken()));

        return date;
    }

    private double parseValue(String s) {
        StringTokenizer st = new StringTokenizer(s, ",\n");
        double value = 0;
        while (st.hasMoreTokens()) {
            try {
                value = Double.parseDouble(st.nextToken());
            } catch (NumberFormatException e) {
                value = -1;
            }
        }
        return value;
    }

    public List<Asset> getQuotes() {
        return quotes;
    }
}
