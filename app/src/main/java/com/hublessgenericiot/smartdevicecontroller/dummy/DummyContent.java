package com.hublessgenericiot.smartdevicecontroller.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;
    private static Random rnd = new Random();

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        String[] rooms = {"Living Room", "Kitchen", "Bedroom", "Den", "Kyle's Room"};
        int rand = Math.abs(rnd.nextInt() % 5);
        return new DummyItem(String.valueOf(position), "Device #" + position, rooms[rand], getRandomBoolean(), false);
    }

    private static boolean getRandomBoolean() {
        return rnd.nextBoolean();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String name;
        public final String room;
        public boolean state;
        public boolean newDevice;

        public DummyItem(String id, String name, String room, boolean state, boolean newDevice) {
            this.id = id;
            this.name = name;
            this.room = room;
            this.state = state;
            this.newDevice = newDevice;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
