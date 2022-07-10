package wtf.mizu.kawa.builder;

import org.jetbrains.annotations.NotNull;
import wtf.mizu.kawa.api.Subscription;

import java.util.function.Consumer;

import static wtf.mizu.kawa.api.Priority.DEFAULT;

public class SubscriptionBuilder<T> {

    private Class<T> topic;
    private Consumer<T> consumer = (e) -> {};
    private short priority = DEFAULT;

    public SubscriptionBuilder<T> topic(Class<T> topic) {
        this.topic = topic;
        return this;
    }

    public SubscriptionBuilder<T> consumer(Consumer<T> consumer) {
        this.consumer = consumer;
        return this;
    }

    public SubscriptionBuilder<T> priority(short priority) {
        this.priority = priority;
        return this;
    }

    public Subscription<T> build() {
        return new ImmutableSubscription<T>(topic, consumer, priority);
    }

    private static final class ImmutableSubscription<T> implements Subscription<T> {
        private final Class<T> topic;
        private final Consumer<T> consumer;
        private final short priority;

        private ImmutableSubscription(Class<T> topic, Consumer<T> consumer, short priority) {
            this.topic = topic;
            this.consumer = consumer;
            this.priority = priority;
        }


        @Override
        public @NotNull Class<T> topic() {
            return topic;
        }

        @Override
        public Consumer<T> consumer() {
            return consumer;
        }

        @Override
        public short priority() {
            return priority;
        }
    }

}
