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


package com.minecraft.moonlake.simpleshieldchat.bungee.listeners;

import com.minecraft.moonlake.simpleshieldchat.bungee.SimpleShieldChatPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {

    private final SimpleShieldChatPlugin main;

    public PlayerChatListener(SimpleShieldChatPlugin main) {
        this.main = main;
    }

    public SimpleShieldChatPlugin getMain() {
        return main;
    }

    @EventHandler(priority = Byte.MAX_VALUE)
    public void onChat(ChatEvent event) {
        Connection connection = event.getSender();

        if(!(connection instanceof ProxiedPlayer))
            return;
        // 处理 BC 玩家聊天事件
        ProxiedPlayer player = (ProxiedPlayer) connection;
        String message = event.getMessage();

        if(player.hasPermission("moonlake.simpleshieldchat.ignore"))
            return;
        // 没有权限则检测
        boolean result = false;

        for(final String value : getMain().getConfiguration().getChatList()) {
            // 循环遍历关键字列表
            result = message.contains(value);

            if(result)
                break;
        }
        if(result) {
            // 包含关键字则阻止
            event.setCancelled(true);
            // 给玩家发送消息
            player.sendMessage(new TextComponent(
                    ChatColor.translateAlternateColorCodes('&',
                            getMain().getConfiguration().getPrefix() +
                            getMain().getConfiguration().getContainsKeyword())
            ));
        }
    }
}
