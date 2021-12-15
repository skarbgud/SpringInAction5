package tacos;

import com.querydsl.core.annotations.QueryProjection;

public class DeliveryInfo {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryState;

    @QueryProjection
    public DeliveryInfo(
            String deliveryName,
            String deliveryStreet,
            String deliveryState
    ){
        this.deliveryName = deliveryName;
        this.deliveryState = deliveryState;
        this.deliveryStreet = deliveryStreet;
    }
}