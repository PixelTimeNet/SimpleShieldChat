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


package com.minecraft.moonlake.simpleshieldchat.bungee.commands;

import com.minecraft.moonlake.simpleshieldchat.bungee.SimpleShieldChatPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandSimpleShieldChat extends Command {

    private final SimpleShieldChatPlugin main;

    public CommandSimpleShieldChat(SimpleShieldChatPlugin main) {
        super("ssc", "moonlake.simpleshieldchat", new String[0]);

        this.main = main;
    }

    public SimpleShieldChatPlugin getMain() {
        return main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            getMain().getConfiguration().reload();
            sender.sendMessage(new TextComponent(
                    ChatColor.translateAlternateColorCodes('&',
                            getMain().getConfiguration().getPrefix() +
                                    getMain().getConfiguration().getSimpleShieldChatReload())
            ));
        }
    }
}
