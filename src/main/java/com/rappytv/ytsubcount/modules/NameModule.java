package com.rappytv.ytsubcount.modules;

import com.rappytv.ytsubcount.main.Main;
import com.rappytv.ytsubcount.util.Util;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.minecraft.util.ResourceLocation;

public class NameModule extends SimpleModule {

    public static int ticks = -1;

    @Override
    public String getDisplayName() {
        return "Name";
    }

    @Override
    public String getDisplayValue() {
        return Util.getChannelname(Main.channelId, Main.apiKey);
    }

    @Override
    public String getDefaultValue() {
        return String.valueOf(0);
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(new ResourceLocation("ytsubcount/textures/youtube.png"));
    }

    @Override
    public void loadSettings() {}

    @Override
    public String getSettingName() {
        return "name";
    }

    @Override
    public String getControlName() {
        return "Channel Name";
    }

    @Override
    public String getDescription() {
        return "Displays the channel name of the channel-id provided in the addon settings.";
    }


    @Override
    public int getSortingId() {
        return 1;
    }

    @Override
    public ModuleCategory getCategory() {
        return ModuleCategoryRegistry.CATEGORY_EXTERNAL_SERVICES;
    }
}
