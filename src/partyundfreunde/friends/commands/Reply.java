package de.SoonMitte.partyandfriends.friends.commands;

import de.SoonMitte.partyandfriends.api.TopCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;

/**
 * Will be executed on /r
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Reply extends Command {
	public Reply(String[] aliases) {
		super(aliases[0], Main.getInstance().getConfig().getString("Permissions.FriendPermission"), aliases);
	}

	@Override
	public void execute(CommandSender pCommandSender, String[] args) {
		if (!TopCommand.isPlayer(pCommandSender))
			return;
		OnlinePAFPlayer pPlayer = getPlayerManager().getPlayer((ProxiedPlayer) pCommandSender);
		if (!Main.getInstance().getFriendsMSGCommand().messageGiven(pPlayer, args, 0))
			return;
		PAFPlayer queryPlayer = pPlayer.getLastPlayerWroteTo();
		if (!queryPlayer.doesExist()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix()
					+ Main.getInstance().getMessagesYml().getString("Friends.Command.MSG.NoOneEverWroteToYou")));
			return;
		}
		Main.getInstance().getFriendsMSGCommand().send(pPlayer, args, queryPlayer, 0);
	}

}
