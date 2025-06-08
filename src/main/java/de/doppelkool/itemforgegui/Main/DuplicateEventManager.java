package de.doppelkool.itemforgegui.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.UUID;

/**
 * Manager class to extend from to prevent multiple executions of one event at once
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class DuplicateEventManager<E extends Event> {
	private final HashMap<UUID, Boolean> pendingPlayerUUIDs = new HashMap<>();
	protected String cancelString;

	/**
	 * Adds player to the List and removes it after a server tick.
	 * Same extending listener and its fired event will be blocked
	 *
	 * @param playerUUID The uuid of the player to check against same events fired multiple times.
	 * @param event      The cancellable (extended by an event) that is used to cancel the event
	 */
	protected void duplicateExecutionSafeProcess(UUID playerUUID, E event) {
		Boolean isCancelled = pendingPlayerUUIDs.get(playerUUID);
		if (isCancelled != null) {
			if (isCancelled) {
				if (event instanceof Cancellable c) {
					c.setCancelled(true);
				} else {
					customCancelLogic(event);
				}
			}
			return;
		}

		if (pendingPlayerUUIDs.isEmpty()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), pendingPlayerUUIDs::clear);
		}

		boolean wasCancelled = eventLogic(event);
		if (wasCancelled) {
			if (event instanceof Cancellable cancellableEvent) {
				cancellableEvent.setCancelled(true);
			} else {
				customCancelLogic(event);
			}

			Player player = Bukkit.getPlayer(playerUUID);
			if (player != null && cancelString != null && !cancelString.isEmpty())
				player.sendMessage(cancelString);
		}

		pendingPlayerUUIDs.put(playerUUID, wasCancelled);
	}

	/**
	 * @return if the event should be cancelled
	 */
	protected abstract boolean eventLogic(E event);

	/**
	 * @implNote Needs to be overridden by events that don't implement {@link Cancellable} to specify cancel behaviour
	 */
	protected void customCancelLogic(E event) {
	}
}
