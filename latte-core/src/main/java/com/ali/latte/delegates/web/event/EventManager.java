package com.ali.latte.delegates.web.event;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.HashMap;

/**
 * Created by 澄鱼 on 2018/5/15.
 */

public class EventManager {

    private static final HashMap<String, Event> EVENT = new HashMap<>();

    private EventManager() {}

    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    public EventManager addEvent(@NotNull String name, @NotNull Event event) {
        EVENT.put(name, event);
        return this;
    }

    public Event createEvent(@NotNull String action) {
        final Event event = EVENT.get(action);
        if (event == null) {
            return new UndefineEvent();
        }

        return event;
    }

}
