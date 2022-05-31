package com.rappytv.ytsubcount.main;

import com.rappytv.ytsubcount.events.ChatEvent;
import com.rappytv.ytsubcount.modules.NameModule;
import com.rappytv.ytsubcount.modules.SubscriberModule;
import com.rappytv.ytsubcount.modules.VideoModule;
import com.rappytv.ytsubcount.modules.ViewModule;
import com.rappytv.ytsubcount.util.Util;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.SliderElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class Main extends LabyModAddon {

    public static String defaultApiKey = "AIzaSyAG9L9QNN4nN0n2AC6p4D78WmMGQ5GNVQM";
    public static String apiKey = "AIzaSyAG9L9QNN4nN0n2AC6p4D78WmMGQ5GNVQM";
    public static String channelId = "";
    public static int minutes = 5;
    public static Main instance;
    public static String prefix = "\u00A74\u00A7lSub Count \u00A78\u00A7l> \u00A7r";

    public static Main getMain() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        getApi().registerModule(new NameModule());
        getApi().registerModule(new SubscriberModule());
        getApi().registerModule(new VideoModule());
        getApi().registerModule(new ViewModule());
        getApi().getEventManager().register(new ChatEvent());
    }

    @Override
    public void loadConfig() {
        minutes = getConfig().has("refreshMinutes") ? getConfig().get("refreshMinutes").getAsInt() : minutes;
        apiKey = getConfig().has("apiKey") ? getConfig().get("apiKey").getAsString() : apiKey;
        channelId = getConfig().has("channelId") ? getConfig().get("channelId").getAsString() : channelId;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

        SliderElement refreshRate = new SliderElement("Refresh Rate (in minutes)", new ControlElement.IconData(Material.WATCH), minutes);

        // Setting the slider's min & max values
        refreshRate.setRange(10, 120);

        // Setting slider steps
        refreshRate.setSteps(5);

        // Adding change listener
        refreshRate.addCallback(new Consumer<Integer>() {

            @Override
            public void accept(Integer accepted) {
                minutes = accepted;

                getConfig().addProperty("refreshMinutes", minutes);
                saveConfig();
                Util.refreshModules();
            }
        });

        StringElement chURL = new StringElement("Channel URL", new ControlElement.IconData(new ResourceLocation("ytsubcount/textures/youtube.png")), channelId, new Consumer<String>() {

            @Override
            public void accept(String accepted) {
                channelId = accepted;

                getConfig().addProperty("channelId", channelId);
                saveConfig();
                Util.refreshModules();
            }
        });

        StringElement apiKey = new StringElement("Custom API Key (optional)", new ControlElement.IconData(new ResourceLocation("ytsubcount/textures/google.png")), Main.apiKey == defaultApiKey ? "" : Main.apiKey, new Consumer<String>() {

            @Override
            public void accept(String accepted) {
                if(accepted.equals("")) {
                    Main.apiKey = defaultApiKey;
                } else Main.apiKey = accepted;

                getConfig().addProperty("apiKey", Main.apiKey);
                saveConfig();
                Util.refreshModules();
            }
        });

        list.add(refreshRate);
        list.add(chURL);
        list.add(apiKey);
    }
}
