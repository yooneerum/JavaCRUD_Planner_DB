package org.plannerDB;

import java.sql.SQLException;

public interface ICRUD {
    int add(Plan one) throws SQLException;
    int update(Plan one) throws SQLException;
    public void deletePlan();
    public void selectOne(int id);
}