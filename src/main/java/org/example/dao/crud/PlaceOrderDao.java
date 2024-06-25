package org.example.dao.crud;

import org.example.dao.CrudDao;
import org.example.entitiy.OrderEntity;

public interface PlaceOrderDao extends CrudDao<OrderEntity, String> {

    String getLatestOrderId();
}