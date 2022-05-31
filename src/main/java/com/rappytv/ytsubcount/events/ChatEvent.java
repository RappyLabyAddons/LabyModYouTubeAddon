package com.rappytv.ytsubcount.events;

import com.rappytv.ytsubcount.main.Main;
import com.rappytv.ytsubcount.util.Util;
import net.labymod.api.events.MessageSendEvent;

import java.util.Date;

public class ChatEvent implements MessageSendEvent {

    public static int refreshCooldown = 30;
    private Date lastRefresh = null;

    @Override
    public boolean onSend(String s) {
        String[] args = s.split(" ");
        if(args[0].equalsIgnoreCase("/yt")) {
            if(args.length < 2) {
                Util.msg(Main.prefix + "\u00A7cPlease provide a valid argument! Args: subs, views, videos, refresh");
            } else if(args[1].equalsIgnoreCase("subs")) {
                Util.msg(Main.prefix + "\u00A7fYou currently have \u00A7e" + Util.getSubs(Main.channelId, Main.apiKey) + " \u00A7fSubscribers on YouTube.");
            } else if(args[1].equalsIgnoreCase("views")) {
                Util.msg(Main.prefix + "\u00A7fYou currently have a total of \u00A7e" + Util.getViews(Main.channelId, Main.apiKey) + " \u00A7fViews on YouTube.");
            } else if(args[1].equalsIgnoreCase("videos")) {
                Util.msg(Main.prefix + "\u00A7fYou currently have \u00A7e" + Util.getVideos(Main.channelId, Main.apiKey) + " \u00A7fVideos uploaded on YouTube.");
            } else if(args[1].equalsIgnoreCase("refresh")) {
                if(lastRefresh != null) {
                    if((new Date().getTime() - lastRefresh.getTime()) < (refreshCooldown * 1000L)) {
                        Util.msg(Main.prefix + "\u00A7cYou can only use this command every \u00A7e" + refreshCooldown + "s\u00A7c!\n" + Main.prefix + "\u00A7cPlease wait \u00A7e" + ((refreshCooldown * 1000L) - (new Date().getTime() - lastRefresh.getTime())) / 1000 + "s\u00A7c!");
                        return true;
                    }
                }
                Util.refreshModules();
                Util.msg(Main.prefix + "\u00A7aModules successfully refreshed.");
                lastRefresh = new Date();
            } else {
                Util.msg(Main.prefix + "\u00A7cPlease provide a valid argument! Args: subs, views, videos, refresh");
            }
            return true;
        }
        return false;
    }
}
