package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.COMBAT;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.DEFENSE_AND_DURABILITY;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.ENV_AND_SURVIVAL;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.INTERACTION_AND_REACH;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.MOBILITY;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory.PERCEPTION_AND_AWARENESS;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadCombat;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadDefenseAndDurability;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadEnvAndSurvival;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadInteractionAndReach;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadMobility;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistryFiller.loadPerceptionAndAwareness;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;

public class AttributeRegistry {
	public static final AttributeRegistry REGISTRY;

	static {
		REGISTRY = new AttributeRegistry(
			Map.of(
				DEFENSE_AND_DURABILITY, loadDefenseAndDurability(),
				COMBAT, loadCombat(),
				MOBILITY, loadMobility(),
				INTERACTION_AND_REACH, loadInteractionAndReach(),
				PERCEPTION_AND_AWARENESS, loadPerceptionAndAwareness(),
				ENV_AND_SURVIVAL, loadEnvAndSurvival()
			));
	}

	private final Map<NamespacedKey, AttributeRegistryEntry> attributeToEntry = new HashMap<>();

	private static NamespacedKey keyOf(Attribute attr) {
		return attr.getKey();
	}

	public Attribute getByKeyString(String key) {
		return Registry.ATTRIBUTE.getOrThrow(NamespacedKey.minecraft(key));
	}

	public AttributeRegistry(Map<AttributeCategory, List<AttributeItem>> source) {
		source.forEach((category, items) -> {
			for (AttributeItem it : items) {
				NamespacedKey k = keyOf(it.getAttribute());
				AttributeRegistryEntry prev = attributeToEntry.putIfAbsent(k, new AttributeRegistryEntry(it, category));
				if (prev != null) {
					throw new IllegalStateException("Duplicate Attribute: " + k + " in " + category);
				}
			}
		});
	}

	public AttributeItem getItem(Attribute attr) {
		AttributeRegistryEntry e = attributeToEntry.get(keyOf(attr));
		if (e == null) throw new NoSuchElementException("No item for " + attr);
		return e.item();
	}

	public AttributeCategory getCategory(Attribute attr) {
		AttributeRegistryEntry e = attributeToEntry.get(keyOf(attr));
		if (e == null) throw new NoSuchElementException("No category for " + attr);
		return e.category();
	}

	public List<AttributeItem> getItems(AttributeCategory category) {
		List<AttributeItem> out = new ArrayList<>();
		for (AttributeRegistryEntry e : attributeToEntry.values()) if (e.category() == category) out.add(e.item());
		return Collections.unmodifiableList(out);
	}

	public List<AttributeItem> getItems_AlreadyAppliedAreBarriers(AttributeCategory category, Set<Attribute> deactivatedAttributes) {
		List<AttributeItem> out = new ArrayList<>();
		for (AttributeRegistryEntry e : attributeToEntry.values()) {
			if (e.category() != category) {
				continue;
			}
			AttributeItem item = e.item().clone();
			prepareAttributeItem(item, deactivatedAttributes);
			out.add(item);
		}
		return Collections.unmodifiableList(out);
	}

	private void prepareAttributeItem(AttributeItem item, Set<Attribute> deactivatedAttributes) {
		ItemStack attributeItemstack = item.getItem().clone();
		modifyItemFlags(attributeItemstack, ItemFlag.HIDE_ATTRIBUTES);


		if (!deactivatedAttributes.contains(item.getAttribute())) {
			modifyLore(attributeItemstack, ChatColor.YELLOW + "Click to select attribute type");
		} else {
			modifyLore(attributeItemstack,
				ChatColor.GRAY + "There are already attribute modifier set with this type.",
				ChatColor.GRAY + "Click on the corresponding item in the 'Active Attribute Modifiers' menu to edit them");
			attributeItemstack = ItemStackCreateHelper.notAvailable(attributeItemstack);
		}

		item.setItem(attributeItemstack);
	}
}
