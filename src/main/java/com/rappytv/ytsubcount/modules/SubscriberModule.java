package com.rappytv.ytsubcount.modules;

import com.rappytv.ytsubcount.main.Main;
import com.rappytv.ytsubcount.util.Util;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.minecraft.util.ResourceLocation;

public class SubscriberModule extends SimpleModule {

    public static int ticks = -1;

    @Override
    public String getDisplayName() {
        return "Subs";
    }

    @Override
    public String getDisplayValue() {
        return Util.getSubs(Main.channelId, Main.apiKey);
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
        return "subs";
    }

    @Override
    public String getControlName() {
        return "Subscriber Count";
    }

    @Override
    public String getDescription() {
        return "Displays the current sub count of the channel-id provided in the addon settings.";
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
