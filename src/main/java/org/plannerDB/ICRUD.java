package org.plannerDB;

import java.sql.SQLException;

public interface ICRUD {
    public int add(Plan one) throws SQLException;
    public void updatePlan();
    public void deletePlan();
    public void selectOne(int id);
}