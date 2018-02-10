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


package com.minecraft.moonlake.simpleshieldchat.commands;

import com.minecraft.moonlake.simpleshieldchat.SimpleShieldChatPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSimpleShieldChat implements CommandExecutor {

    private final SimpleShieldChatPlugin main;

    public CommandSimpleShieldChat(SimpleShieldChatPlugin main) {
        this.main = main;
    }

    public SimpleShieldChatPlugin getMain() {
        return main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
            printHelp(sender);
        } else if(args.length == 1) {
            if(!sender.hasPermission("moonlake.simpleshieldchat.reload")) {
                sender.sendMessage(getMain().getMessage("NoPermission"));
                return true;
            }
            getMain().reloadConfig();
            getMain().reloadPrefix();
            getMain().getSimpleShieldChatManager().reload();
            sender.sendMessage(getMain().getMessage("SimpleShieldChatReload"));
        } else {
            sender.sendMessage(getMain().getMessage("ErrorCommandArgs", "/ssc help 查看命令帮助."));
        }
        return true;
    }

    private void printHelp(CommandSender sender) {
        sender.sendMessage(new String[] {
                getMain().toColor("&b&l&m          &d SimpleShieldChat &7By &6Month_Light &b&l&m          "),
                getMain().toColor("&6/ssc help &7- 查看插件命令帮助."),
                getMain().toColor("&6/ssc reload &7- 重新载入插件配置文件."),
        });
    }
}
