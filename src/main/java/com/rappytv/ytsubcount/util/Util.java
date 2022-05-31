package com.rappytv.ytsubcount.util;

import com.rappytv.ytsubcount.main.Main;
import com.rappytv.ytsubcount.modules.NameModule;
import com.rappytv.ytsubcount.modules.SubscriberModule;
import com.rappytv.ytsubcount.modules.VideoModule;
import com.rappytv.ytsubcount.modules.ViewModule;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Util {

    public static void msg(String text) {
        Main.getMain().getApi().displayMessageInChat(text);
    }

    public static void refreshModules() {
        NameModule.ticks = -1;
        SubscriberModule.ticks = -1;
        VideoModule.ticks = -1;
        ViewModule.ticks = -1;
    }

    private static ArrayList<String> getInfos(String channelId, String apiKey) {
        HttpsURLConnection httpConn = null;
        try {
            URL u = new URL("https://www.googleapis.com/youtube/v3/channels?part=snippet,contentDetails,statistics&id=" + channelId + "&key=" + apiKey);
            URLConnection conn = u.openConnection();
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ArrayList<String> lines = new ArrayList<>();

            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();

            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return null;
        } finally {
            if(httpConn != null) {
                try {
                    httpConn.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static int[] getStats(String channelId, String apiKey) {
        try {
            ArrayList<String> lines = getInfos(channelId, apiKey);

            boolean hiddenSubs = Boolean.getBoolean(lines.get(49).substring(8, lines.get(49).length() - 1));
            int subs;
            if(hiddenSubs) subs = -1;
            else subs = Integer.parseInt(lines.get(48).substring(28, lines.get(48).length() - 2));
            int views = Integer.parseInt(lines.get(47).substring(22, lines.get(47).length() - 2));
            int videos = Integer.parseInt(lines.get(50).substring(23, lines.get(50).length() - 1));

            return new int[]{subs, views, videos};
        } catch (NullPointerException e) {
            return null;
        }
    }

    private static String name;
    public static String getChannelname(String channelId, String apiKey) {
        if(((NameModule.ticks / 20) / 60) == Main.minutes || NameModule.ticks == -1) {
            try {
                ArrayList<String> lines = getInfos(channelId, apiKey);
                NameModule.ticks = 0;

                String res;
                if(lines == null) res = "Channel not found!";
                else res = lines.get(13).substring(18, lines.get(13).length() - 2);
                name = res;
                return res;
            } catch (NullPointerException e) {
                return "Channel not found!";
            }
        } else {
            NameModule.ticks++;
            return name;
        }
    }

    private static String subs;
    public static String getSubs(String channelId, String apiKey) {
        if(((SubscriberModule.ticks / 20) / 60) == Main.minutes || SubscriberModule.ticks == -1) {
            int[] stats = getStats(channelId, apiKey);
            SubscriberModule.ticks = 0;

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            String res;
            if(stats == null) res = "Channel not found!";
            else if(stats[0] == -1) res = "This channels hides its subscriber count!";
            else res = formatter.format(stats[0]);
            subs = res;
            return res;
        } else {
            SubscriberModule.ticks++;
            return subs;
        }
    }

    private static String views;
    public static String getViews(String channelId, String apiKey) {
        if(((ViewModule.ticks / 20) / 60) == Main.minutes || ViewModule.ticks == -1) {
            int[] stats = getStats(channelId, apiKey);
            ViewModule.ticks = 0;

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            String res;
            if(stats == null) res = "Channel not found!";
            else res = formatter.format(stats[1]);
            views = res;
            return res;
        } else {
            ViewModule.ticks++;
            return views;
        }
    }

    private static String vids;
    public static String getVideos(String channelId, String apiKey) {
        if(((VideoModule.ticks / 20) / 60) == Main.minutes || VideoModule.ticks == -1) {
            int[] stats = getStats(channelId, apiKey);
            VideoModule.ticks = 0;

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(symbols);

            String res;
            if(stats == null) res = "Channel not found!";
            else res = formatter.format(stats[2]);
            vids = res;
            return res;
        } else {
            VideoModule.ticks++;
            return vids;
        }
    }
}
