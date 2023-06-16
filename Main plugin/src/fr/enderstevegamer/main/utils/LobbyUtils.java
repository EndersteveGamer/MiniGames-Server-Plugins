package fr.enderstevegamer.main.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyUtils {
    public static ArrayList<ItemStack> createItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(getItem(ChatColor.GOLD + "Spleef",
                Material.SNOWBALL,
                new String[] {
                        ChatColor.GRAY + "A gamemode where you have to",
                        ChatColor.GRAY + "throw snowballs at your opponents",
                        ChatColor.GRAY + "to break the blocks under them",
                        ChatColor.GRAY + "and make them fall in the void."
                },
                "Spleef-1"
            ));
        items.add(getItem(ChatColor.GOLD + "Arrow Wars",
                Material.ARROW,
                new String[] {
                        ChatColor.GRAY + "A team gamemode where each team",
                        ChatColor.GRAY + "will have to shoot their arrow",
                        ChatColor.GRAY + "at the ennemy team to kill them"
                },
                "ArrowWars-1"
        ));
        items.add(getItem(ChatColor.GOLD + "Creative",
                Material.GRASS_BLOCK,
                new String[] {
                        ChatColor.GRAY + "A creative server"
                },
                "Creative-1"
        ));
        return items;
    }
    public static Inventory getSelectorInventory() {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GOLD + "Gamemode selector");
        ArrayList<ItemStack> items = createItems();
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
        return inventory;
    }

    public static ItemStack getItem(String name, Material material, String[] lore, String serverName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setLocalizedName(serverName);
        item.setItemMeta(meta);
        return item;
    }

    public static void sendPlayerToServer(Player player, String server) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);

        player.sendPluginMessage(Main.INSTANCE, "BungeeCord", out.toByteArray());
    }
}
