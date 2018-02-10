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


package com.minecraft.moonlake.simpleshieldchat.bungee;

import com.minecraft.moonlake.simpleshieldchat.bungee.commands.CommandSimpleShieldChat;
import com.minecraft.moonlake.simpleshieldchat.bungee.config.SimpleShieldChatConfig;
import com.minecraft.moonlake.simpleshieldchat.bungee.listeners.PlayerChatListener;
import net.md_5.bungee.api.plugin.Plugin;

public class SimpleShieldChatPlugin extends Plugin {

    private SimpleShieldChatConfig configuration;

    public SimpleShieldChatPlugin() {
    }

    @Override
    public void onEnable() {
        this.configuration = new SimpleShieldChatConfig(this);
        this.configuration.reload();

        getProxy().getPluginManager().registerListener(this, new PlayerChatListener(this));
        getProxy().getPluginManager().registerCommand(this, new CommandSimpleShieldChat(this));
        getLogger().info("简单屏蔽聊天 SimpleShieldChat 插件 v" + getDescription().getVersion() + " 成功加载.");
    }

    @Override
    public void onDisable() {
    }

    public SimpleShieldChatConfig getConfiguration() {
        return configuration;
    }
}
