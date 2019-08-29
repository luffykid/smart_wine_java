package com.changfa.frame.model.event.order;

import com.changfa.frame.model.event.DomainEvent;

import java.util.Date;

public class OrderCreatedEvent implements DomainEvent {

    private Date occurredOn;
    private Long mbrWineCustomOrderId;

    public OrderCreatedEvent(Long mbrWineCustomOrderId) {

        this.occurredOn = new Date();
        this.setMbrWineCustomOrderId(mbrWineCustomOrderId);

    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public void setMbrWineCustomOrderId(Long mbrWineCustomOrderId) {

        if(mbrWineCustomOrderId == null)
            throw new NullPointerException("mbrWineCustomOrderId must not be null!");

        this.mbrWineCustomOrderId = mbrWineCustomOrderId;

    }

    public Long getMbrWineCustomOrderId() {
        return mbrWineCustomOrderId;
    }
}
