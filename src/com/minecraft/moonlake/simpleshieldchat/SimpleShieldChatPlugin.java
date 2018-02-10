/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.minecraft.moonlake.simpleshieldchat;

import com.minecraft.moonlake.simpleshieldchat.commands.CommandSimpleShieldChat;
import com.minecraft.moonlake.simpleshieldchat.listeners.PlayerListener;
import com.minecraft.moonlake.simpleshieldchat.manager.SimpleShieldChatManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SimpleShieldChatPlugin extends JavaPlugin {

    private String prefix;
    private SimpleShieldChatManager simpleShieldChatManager;

    public SimpleShieldChatPlugin() {
    }

    @Override
    public void onEnable() {
        this.initFolder();
        this.simpleShieldChatManager = new SimpleShieldChatManager(this);
        this.simpleShieldChatManager.reload();

        this.getCommand("ssc").setExecutor(new CommandSimpleShieldChat(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getLogger().info("简单屏蔽聊天 SimpleShieldChat 插件 v" + getDescription().getVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {
    }

    private void initFolder() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists())
            saveDefaultConfig();
        reloadPrefix();
    }

    public void reloadPrefix() {
        this.prefix = toColor(getConfig().getString("Prefix", "&f[&c屏蔽聊天&f] "));
    }

    public String getMessage(String key, Object... args) {
        return toColor(prefix + String.format(getConfig().getString("Messages." + key, ""), args));
    }

    public String toColor(String src) {
        return ChatColor.translateAlternateColorCodes('&', src);
    }

    public SimpleShieldChatManager getSimpleShieldChatManager() {
        return simpleShieldChatManager;
    }
}
