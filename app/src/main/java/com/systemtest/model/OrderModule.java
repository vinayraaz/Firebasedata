package com.systemtest.model;

public class OrderModule {
    private String orderId;
    private String orderName,orderDetails,orderAdd;
    private Integer orderStatus;

    public OrderModule() {

    }

    public OrderModule(String orderId, String orderName, String orderDetails, String orderAdd, Integer orderStatus) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDetails = orderDetails;
        this.orderAdd = orderAdd;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderAdd() {
        return orderAdd;
    }

    public void setOrderAdd(String orderAdd) {
        this.orderAdd = orderAdd;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
