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


package com.minecraft.moonlake.simpleshieldchat.bungee.config;

import com.minecraft.moonlake.simpleshieldchat.bungee.SimpleShieldChatPlugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SimpleShieldChatConfig {

    private final SimpleShieldChatPlugin main;
    private final List<String> chatList;
    private String prefix;
    private String simpleShieldChatReload;
    private String containsKeyword;

    public SimpleShieldChatConfig(SimpleShieldChatPlugin main) {
        this.main = main;
        this.chatList = new ArrayList<>();
    }

    public SimpleShieldChatPlugin getMain() {
        return main;
    }

    public void reload() {
        if (!getMain().getDataFolder().exists())
            getMain().getDataFolder().mkdirs();
        File config = new File(getMain().getDataFolder(), "config.yml");
        if(!config.exists()) {
            try {
                InputStream is = getMain().getClass().getClassLoader().getResourceAsStream("config.yml");
                FileOutputStream fos = new FileOutputStream(config);
                byte[] buff = new byte[1024];
                int length;

                while ((length = is.read(buff)) != -1)
                    fos.write(buff, 0, length);
                fos.flush();
                fos.close();
                is.close();
            } catch (Exception e) {
                getMain().getLogger().log(Level.SEVERE, "写出配置文件数据流时错误, 异常信息:");
                e.printStackTrace();
            }
        }
        // 清空缓存
        chatList.clear();

        try {
            Configuration configuration = YamlConfiguration.getProvider(YamlConfiguration.class).load(config);
            chatList.addAll(configuration.getStringList("List"));
            prefix = configuration.getString("Prefix", "&f[&c屏蔽聊天&f] ");
            simpleShieldChatReload = configuration.getString("Messages.SimpleShieldChatReload", "&a配置文件已经重新载入...");
            containsKeyword = configuration.getString("Messages.ContainsKeyword", "&d你的聊天内容包含关键字, 已被系统屏蔽.");
        } catch (Exception e) {
            getMain().getLogger().log(Level.SEVERE, "读取配置文件数据流时错误, 异常信息:");
            e.printStackTrace();
        }
    }

    public List<String> getChatList() {
        return chatList;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSimpleShieldChatReload() {
        return simpleShieldChatReload;
    }

    public String getContainsKeyword() {
        return containsKeyword;
    }
}
