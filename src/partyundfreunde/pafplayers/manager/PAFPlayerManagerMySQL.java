package de.SoonMitte.partyandfriends.pafplayers.manager;

import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.SoonMitte.partyandfriends.communication.sql.MySQL;
import de.SoonMitte.partyandfriends.communication.sql.MySQLData;
import de.SoonMitte.partyandfriends.pafplayers.mysql.OnlinePAFPlayerMySQL;
import de.SoonMitte.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import de.SoonMitte.partyandfriends.utilities.disable.Deactivated;
import de.SoonMitte.partyandfriends.utilities.disable.Disabler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class PAFPlayerManagerMySQL extends PAFPlayerManager implements Deactivated {
	private static MySQL connection;

	public PAFPlayerManagerMySQL(MySQLData pMySQLData) {
		connection = new MySQL(pMySQLData);
		Disabler.getInstance().registerDeactivated(this);
	}

	public PAFPlayer getPlayer(String pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player == null)
			return new PAFPlayerMySQL(getConnection().getPlayerID(pPlayer));
		else
			return getPlayer(player);
	}

	public OnlinePAFPlayer getPlayer(ProxiedPlayer pPlayer) {
		return new OnlinePAFPlayerMySQL(getConnection().getPlayerID(pPlayer), pPlayer);
	}

	@Override
	public PAFPlayer getPlayer(UUID pPlayer) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(pPlayer);
		if (player != null)
			return getPlayer(player);
		return getPlayer(getConnection().getPlayerID(pPlayer));
	}

	public PAFPlayer getPlayer(int pPlayerID) {
		return getPlayer(getConnection().getName(pPlayerID));
	}

	public static MySQL getConnection() {
		return connection;
	}

	@Override
	public void onDisable() {
		connection.closeConnection();
	}
}
