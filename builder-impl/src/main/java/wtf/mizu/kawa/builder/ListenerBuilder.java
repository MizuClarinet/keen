package wtf.mizu.kawa.builder;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Listener;
import wtf.mizu.kawa.api.Subscription;

import java.util.*;

public class ListenerBuilder {

    private final Map<Class<?>, Collection<Subscription<?>>> listeners = new HashMap<>();

    public ListenerBuilder add(Subscription<?> subscription) {
        Collection<Subscription<?>> subscriptions = listeners.get(subscription.topic());
        if(subscriptions == null) {
            subscriptions = new ArrayList<>();
            subscriptions.add(subscription);
            listeners.put(subscription.topic(), subscriptions);
        } else {
            subscriptions.add(subscription);
        }
        return this;
    }

    public Listener build() {
        return new ImmutableListener(Collections.unmodifiableMap(listeners));
    }

    private static final class ImmutableListener implements Listener {
        private final Map<Class<?>, Collection<Subscription<?>>> listeners;

        private ImmutableListener(Map<Class<?>, Collection<Subscription<?>>> listeners) {
            this.listeners = listeners;
        }

        @Override
        public @NotNull Map<Class<?>, Collection<Subscription<?>>> subscriptions() {
            return listeners;
        }
    }

}
