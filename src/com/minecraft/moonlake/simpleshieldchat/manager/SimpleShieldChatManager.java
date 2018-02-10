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


package com.minecraft.moonlake.simpleshieldchat.manager;

import com.minecraft.moonlake.simpleshieldchat.SimpleShieldChatPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class SimpleShieldChatManager {

    private final SimpleShieldChatPlugin main;
    private final List<String> chatList;

    public SimpleShieldChatManager(SimpleShieldChatPlugin main) {
        this.main = main;
        this.chatList = new ArrayList<>();
    }

    public SimpleShieldChatPlugin getMain() {
        return main;
    }

    public void reload() {
        // 重新加载配置文件数据

        // 先清空缓存
        chatList.clear();

        // 再进行读取
        FileConfiguration fileConfiguration = getMain().getConfig();

        chatList.addAll(fileConfiguration.getStringList("List"));
    }

    public boolean contains(String message) {
        // 判断消息是否包含关键字
        boolean result = false;

        for(final String value : chatList) {
            // 循环遍历关键字列表
            result = message.contains(value);

            if(result)
                break;
        }
        return result;
    }

    public void handlerPlayerChat(Player player, String message, AsyncPlayerChatEvent event) {
        // 处理玩家聊天事件
        if(player.isOp() || player.hasPermission("moonlake.simpleshieldchat.ignore"))
            return;
        // 判断消息是否包含集合的关键字
        if(contains(message)) {
            // 包含关键字则阻止消息
            event.setCancelled(true);
            // 给玩家发送消息
            player.sendMessage(getMain().getMessage("ContainsKeyword"));
        }
    }
}
