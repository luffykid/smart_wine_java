package com.changfa.frame.website.event;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher {

    private List<DomainEventSubscriber> subscribers = new ArrayList<>();


    public static DomainEventPublisher newInstance() {

        return new DomainEventPublisher();

    }

    public void subcribe(DomainEventSubscriber<DomainEvent> domainEventDomainEventSubscriber) {

        this.subscribers.add(domainEventDomainEventSubscriber);

    }

    public <T extends DomainEvent> void publish(T aDomainEvent) {

        Class<?> eventType = aDomainEvent.getClass();

        for (DomainEventSubscriber subscriber : subscribers) {

            if ( subscriber.subscribedToEventType().equals(eventType)
                 || subscriber.subscribedToEventType().equals(DomainEvent.class)) {

                subscriber.handleEvent(aDomainEvent);

            }

        }

    }

}
