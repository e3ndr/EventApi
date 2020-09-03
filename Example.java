package xyz.e3ndr.eventapi;

import xyz.e3ndr.eventapi.events.AbstractCancellableEvent;
import xyz.e3ndr.eventapi.events.AbstractEvent;
import xyz.e3ndr.eventapi.listeners.EventListener;

public class Example {

    public static void main(String[] args) {
        EventHandler<EventType> handler = new EventHandler<>();

        handler.register(new Listener2());

        handler.call(new TestEvent());
    }

    public static class Listener {
        @EventListener
        public void onTest(TestEvent event) {
            System.out.println("onTest");
            event.setCancelled(true);
        }

        @EventListener(ignoreCancelled = true)
        public void onEvent(AbstractEvent<?> event) {
            System.out.println("onEvent");
        }
    }

    public static class Listener2 extends Listener {}

    public static class TestEvent extends AbstractCancellableEvent<EventType> {

        public TestEvent() {
            super(EventType.TEST);
        }

    }

    public static enum EventType {
        TEST;

    }

}
