package org.plannerDB;

public interface ICRUD {
    public Object add();
    public int updatePlan(Object obj);
    public int deletePlan(Object obj);
    public void selectOne(int id);
}