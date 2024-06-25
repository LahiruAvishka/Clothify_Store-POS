package org.example.dao.crud;

import javafx.collections.ObservableList;
import org.example.dao.CrudDao;
import org.example.entitiy.SupplierEntity;

public interface SupplierDao extends CrudDao<SupplierEntity, String> {

    String getLatestId();

    ObservableList<String> getAllIds();
}