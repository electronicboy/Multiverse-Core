/******************************************************************************
 * Multiverse 2 Copyright (c) the Multiverse Team 2020.                       *
 * Multiverse 2 is licensed under the BSD License.                            *
 * For more information please check the README.md file included              *
 * with this project.                                                         *
 ******************************************************************************/

package com.onarandombox.MultiverseCore.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.commandtools.flag.FlagGroup;
import com.onarandombox.MultiverseCore.commandtools.flag.FlagResult;
import com.onarandombox.MultiverseCore.commandtools.flag.CoreFlags;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.bukkit.World.*;

@CommandAlias("mv")
public class ImportCommand extends MultiverseCoreCommand {

    public ImportCommand(MultiverseCore plugin) {
        super(plugin);
        this.setFlagGroup(FlagGroup.of(CoreFlags.GENERATOR, CoreFlags.SPAWN_ADJUST));
    }

    @Subcommand("import")
    @CommandPermission("multiverse.core.import")
    @Syntax("<name> <env> -g [generator[:id]] [-n]")
    @CommandCompletion("@potentialWorlds @environments @flags")
    @Description("Imports a new world into multiverse.")
    public void onImportCommand(@NotNull CommandSender sender,

                                @Syntax("<name>")
                                @Description("Folder name of the world.")
                                @NotNull @co.aikar.commands.annotation.Flags("trim") @Conditions("importableWorldName") String worldName,

                                @NotNull
                                @Syntax("<env>")
                                @Description("The world's environment. See: /mv env")
                                Environment environment,

                                @Nullable
                                @Syntax("-g [generator[:id]] [-n]")
                                @Description("Other world settings. See: http://gg.gg/nn8c2")
                                String[] flagsArray) {

        FlagResult flags = this.getFlagGroup().calculateResult(flagsArray);

        Command.broadcastCommandMessage(sender, String.format("Starting import of world '%s'...", worldName));
        Command.broadcastCommandMessage(sender, (this.plugin.getMVWorldManager().addWorld(worldName,
                environment,
                null,
                null,
                null,
                flags.getValue(CoreFlags.GENERATOR),
                flags.getValue(CoreFlags.SPAWN_ADJUST))
        )
                ? String.format("%sComplete!", ChatColor.GREEN)
                : String.format("%sFailed! See console for more details.", ChatColor.RED));
    }
}
