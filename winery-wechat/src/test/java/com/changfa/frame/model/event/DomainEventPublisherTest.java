package com.changfa.frame.model.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DomainEventPublisherTest {

    private DomainEventSubscriber<DomainEvent> subscriber;
    private DomainEventPublisher publisher;

    @Before
    public void setUp() {

        this.subscriber = Mockito.mock(DomainEventSubscriber.class);

        Mockito.when(subscriber.subscribedToEventType())
                .thenReturn(DomainEvent.class);

        publisher = DomainEventPublisher.newInstance();

        publisher.subcribe(subscriber);

    }

    @Test
    public void testNewInstanceSuccess() {

        DomainEventPublisher publisher = DomainEventPublisher.newInstance();

        Assert.assertNotNull(publisher);

    }

    @Test
    public void testPublish() {

        DomainEvent mockDomainEvent = Mockito.mock(DomainEvent.class);

        publisher.publish(mockDomainEvent);

        Mockito.verify(subscriber).handleEvent(mockDomainEvent);

    }
}