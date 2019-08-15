package com.systemtest.model;

public class OrderModel {
    private String orderId,orderName,orderAdd;
    private int orderStatus;



    public OrderModel(String orderId, String orderName, String orderAdd, Integer orderStatus) {
        this.orderId = orderId;
        this.orderName = orderName;
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
