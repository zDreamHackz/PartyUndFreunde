package de.SoonMitte.partyandfriends.friends.subcommands;

import de.SoonMitte.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;

import static de.SoonMitte.partyandfriends.main.Main.getInstance;

/**
 * Will be executed on /friend msg
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Message extends FriendSubCommand {
	public Message(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		getInstance().getFriendsMSGCommand().send(pPlayer, args, 0);
	}
}
