package com.changfa.frame.website.event;

public class DomainEventSubscriber<T> {

    public void handleEvent(DomainEvent aDomainEvent) { }

    public Class<DomainEvent> subscribedToEventType() {

        return DomainEvent.class;

    }

}
