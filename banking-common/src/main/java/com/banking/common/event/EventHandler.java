package com.banking.common.event;

/**
 * Interface for handling domain events.
 * Implementations should be idempotent as events may be delivered more than once.
 *
 * @param <E> the specific domain event type
 */
public interface EventHandler<E extends DomainEvent> {

    /**
     * Handle a domain event.
     * Implementations must be idempotent.
     *
     * @param event the domain event to handle
     */
    void handle(E event);

    /**
     * Get the event type this handler is responsible for.
     *
     * @return the event class
     */
    Class<E> getEventType();

    /**
     * Whether this handler should process events asynchronously.
     * Default: true
     */
    default boolean isAsync() {
        return true;
    }

    /**
     * The order of this handler when multiple handlers exist for the same event.
     * Lower values have higher priority. Default: 0.
     */
    default int getOrder() {
        return 0;
    }
}
