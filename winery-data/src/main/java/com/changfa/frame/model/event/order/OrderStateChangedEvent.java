package com.changfa.frame.model.event.order;

import com.changfa.frame.model.event.DomainEvent;

import java.util.Date;

public class OrderStateChangedEvent implements DomainEvent {

    private Date occurredOn;
    private Long mbrWineCustomOrderId;
    private Integer updatedOrderState;

    public OrderStateChangedEvent(Long mbrWineCustomOrderId,
                                  Integer updatedOrderState) {

        this.occurredOn = new Date();
        this.setMbrWineCustomOrderId(mbrWineCustomOrderId);
        this.setUpdatedOrderState(updatedOrderState);

    }

    private void setMbrWineCustomOrderId(Long mbrWineCustomOrderId) {

        if (mbrWineCustomOrderId == null)
            throw new NullPointerException("mbrWineCustomOrderId must not be null!");

        this.mbrWineCustomOrderId = mbrWineCustomOrderId;

    }

    private void setUpdatedOrderState(Integer updatedOrderState) {

        if(updatedOrderState == null)
            throw new NullPointerException("updatedOrderState must not be null!");

        this.updatedOrderState = updatedOrderState;

    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }

    public Long getMbrWineCustomOrderId() {
        return mbrWineCustomOrderId;
    }

    public Integer getUpdatedOrderState() {
        return updatedOrderState;
    }
}
