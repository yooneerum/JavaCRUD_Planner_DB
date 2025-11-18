package org.plannerDB;

public interface ICRUD {
    public Object add();
    public void updatePlan();
    public void deletePlan();
    public void selectOne(int id);
}