package com.kaleidoscope.backportedcookery.event;

import com.kaleidoscope.backportedcookery.BackportedConfig;
import com.kaleidoscope.backportedcookery.block.StrawBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Keeps track of who is currently sleeping in a straw bed.
 *
 * <p>26.3 straw beds are single-use and must disappear when the player wakes up (also
 * when clicking "Get up"), and they must not change the spawn point. On 1.21.1 those
 * two things are implemented with {@code PlayerWakeUpEvent} +
 * {@code PlayerSetSpawnEvent}. The server-tick fallback also covers edge cases such as
 * death, dimension change or disconnect.</p>
 *
 * <p>All access happens on the logical server thread, so no synchronisation is needed.</p>
 */
public final class StrawBedSleepTracker {
    private static final Map<UUID, Ticket> TICKETS = new HashMap<>();

    private StrawBedSleepTracker() {
    }

    public static void track(Player player, BlockPos head, BlockPos foot) {
        TICKETS.put(player.getUUID(), new Ticket(
                player,
                player.level().dimension(),
                head.immutable(),
                foot.immutable()));
    }

    /** Called when startSleepInBed fails. */
    public static void cancel(Player player) {
        TICKETS.remove(player.getUUID());
    }

    public static boolean isSleeping(Player player) {
        return TICKETS.containsKey(player.getUUID());
    }

    /** Called on wake-up / logout; destroys (or releases) the bed once. */
    public static void consume(Player player) {
        Ticket ticket = TICKETS.remove(player.getUUID());
        if (ticket != null) {
            finish(ticket);
        }
    }

    /** Safety net for cases where no wake-up event is fired. */
    public static void tick() {
        if (TICKETS.isEmpty()) {
            return;
        }
        List<UUID> finished = new ArrayList<>();
        for (Map.Entry<UUID, Ticket> entry : TICKETS.entrySet()) {
            Ticket ticket = entry.getValue();
            Player player = ticket.player();
            if (!player.isAlive() || !player.isSleeping()
                    || !player.level().dimension().equals(ticket.dimension())) {
                finished.add(entry.getKey());
            }
        }
        for (UUID uuid : finished) {
            Ticket ticket = TICKETS.remove(uuid);
            if (ticket != null) {
                finish(ticket);
            }
        }
    }

    private static void finish(Ticket ticket) {
        Level level = ticket.player().level();
        if (!level.dimension().equals(ticket.dimension())) {
            return;
        }
        if (BackportedConfig.safe(BackportedConfig.STRAW_BED_SINGLE_USE)) {
            StrawBedBlock.removeWithoutDrops(level, ticket.head());
            StrawBedBlock.removeWithoutDrops(level, ticket.foot());
        } else {
            StrawBedBlock.setUnoccupied(level, ticket.head());
            StrawBedBlock.setUnoccupied(level, ticket.foot());
        }
    }

    private record Ticket(Player player, ResourceKey<Level> dimension,
                          BlockPos head, BlockPos foot) {
    }
}
