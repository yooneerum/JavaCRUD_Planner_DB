package org.plannerDB;

public interface ICRUD {
    public Object add();
    public void updatePlan();
    public int deletePlan(Object obj);
    public void selectOne(int id);
}