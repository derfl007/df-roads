package derfl007.roads.common.commands;

import derfl007.roads.common.items.ItemTrafficRemote;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.permission.PermissionAPI;

public abstract class CommandTrafficLightsBase extends CommandBase {

	/**
	 * Check if the given ICommandSender has permission to execute this command
	 */
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		EntityPlayer player;
		try {
			player = getCommandSenderAsPlayer(sender);
			return PermissionAPI.hasPermission(player, "dfroads.command.trafficlights")
					&& player.capabilities.allowEdit;
		} catch (PlayerNotFoundException ex) {
			return true;
		}
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args != null && args.length == 1 && "help".equals(args[0])) {
			sender.sendMessage(new TextComponentTranslation(getHelp()));
			return;
		}
		EntityPlayer player;
		try {
			player = getCommandSenderAsPlayer(sender);
		} catch (PlayerNotFoundException ex) {
			sender.sendMessage(new TextComponentTranslation("command.trafficlights.invalidsender"));
			return;
		}
		if (!(player.getHeldItemMainhand().getItem() instanceof ItemTrafficRemote)) {
			sender.sendMessage(new TextComponentTranslation("command.trafficlights.invalidhelditem"));
			return;
		}
		if (args == null && expectedArgumentsCount() > 0) {
			unexpectedArguments(sender);
			return;
		}
		if (args != null && expectedArgumentsCount() != args.length) {
			if (args.length < expectedArgumentsCount() || !hasOptionalArguments()) {
				unexpectedArguments(sender);
				return;
			}
		}

		execute(server, player, args);
	}

	protected boolean hasOptionalArguments() {
		return false;
	}

	protected abstract int expectedArgumentsCount();

	public abstract void execute(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException;

	public abstract String getHelp();

	public void unexpectedArguments(ICommandSender sender) throws CommandException {
		throw new WrongUsageException(getUsage(sender));

	}
}
