package com.changfa.frame.model.event;

public interface DomainEventSubscriber<T extends DomainEvent> {

    void handleEvent(T aDomainEvent);

    Class<T> subscribedToEventType();

}
