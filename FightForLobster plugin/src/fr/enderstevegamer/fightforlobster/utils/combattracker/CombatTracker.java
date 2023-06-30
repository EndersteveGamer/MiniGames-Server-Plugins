package fr.enderstevegamer.fightforlobster.utils.combattracker;

import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatDamage;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatDamageCause;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatEvent;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatEventType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CombatTracker {
    private final UUID trackerOwner;
    private final ArrayList<CombatLog> combatLogs = new ArrayList<>();
    private CombatLog currentCombat;

    public CombatTracker(UUID trackerOwner) {
        this.trackerOwner = trackerOwner;
        this.currentCombat = null;
    }

    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (currentCombat == null) startCombat();
        CombatDamage damage = null;
        switch (event.getCause()) {
            case WITHER -> damage = new CombatDamage(CombatDamageCause.WITHER, (int) event.getFinalDamage());
            case FIRE -> damage = new CombatDamage(CombatDamageCause.FIRE, (int) event.getFinalDamage());
            case LAVA -> damage = new CombatDamage(CombatDamageCause.LAVA, (int) event.getFinalDamage());
            case DROWNING -> damage = new CombatDamage(CombatDamageCause.DROWN, (int) event.getFinalDamage());
        }
        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            damage = new CombatDamage(CombatDamageCause.ENTITY, (int) event.getFinalDamage(),
                    entityEvent.getDamager());
        }
        if (damage == null) damage = new CombatDamage(CombatDamageCause.UNKNOWN, (int) event.getFinalDamage());
        currentCombat.addDamage(damage);
    }

    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getEntity().getUniqueId().equals(trackerOwner)) return;
        endCombat();
    }

    private void endCombat() {
        currentCombat.addEvent(new CombatEvent(CombatEventType.COMBAT_END));
        combatLogs.add(currentCombat);
        currentCombat = null;
    }

    private void startCombat() {
        currentCombat = new CombatLog(trackerOwner);
    }

    private ItemStack getEmptyLogBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta == null) return book;
        meta.setTitle("Combat Tracker");
        meta.setAuthor("Plugin");
        meta.setPages("You haven't started any fight yet!");
        book.setItemMeta(meta);
        return book;
    }

    private ItemStack getLogBook(CombatLog log) {
        if (log == null) return getEmptyLogBook();
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta == null) return book;
        meta.setTitle("Combat Tracker");
        meta.setAuthor("Plugin");
        meta.setPages(stringsToPages(log.getLogString()));
        book.setItemMeta(meta);
        return book;
    }

    private static List<String> stringsToPages(List<String> stringList) {
        final int STRINGS_PER_PAGES = 7;
        ArrayList<String> pages = new ArrayList<>();
        int strings = 0;
        StringBuilder page = new StringBuilder();
        for (String string : stringList) {
            page.append(string).append("\n");
            strings++;
            if (strings >= STRINGS_PER_PAGES) {
                pages.add(page.toString());
                strings = 0;
                page = new StringBuilder();
            }
        }
        if (strings != 0) pages.add(page.toString());
        return pages;
    }

    public void openTrackerLog(Player player) {
        player.openBook(getLogBook(currentCombat));
    }
}
