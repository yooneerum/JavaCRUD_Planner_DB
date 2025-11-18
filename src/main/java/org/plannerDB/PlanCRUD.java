package org.plannerDB;

import java.util.ArrayList;
import java.util.Scanner;

public class PlanCRUD implements ICRUD{
    ArrayList<Plan> plans;
    Scanner s;

    PlanCRUD(Scanner s) {
        plans = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        System.out.println("=> 중요도(1,2,3) : ");
        int level = s.nextInt();
        s.nextLine();
        System.out.println("=> 카테고리(1_학업 2_약속 3_개인) : ");
        int category = s.nextInt();
        s.nextLine();
        System.out.println("=> 작성 날짜(YYYY-MM-DD) : ");
        String created_date = s.nextLine();
        System.out.println("=> 일정 내용 : ");
        String contents = s.nextLine();
        return new Plan(0, level, category, 0, created_date, contents) ;
    }

    public void addPlan() {
        Plan one = (Plan) add();
        plans.add(one);
        System.out.println("새 일정이 리스트에 추가되었습니다.");
    }

    @Override
    public int updatePlan(Object obj) {
        return 0;
    }

    @Override
    public int deletePlan(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(int id) {

    }

    public void listAll() {
        System.out.println("---------------------------");
        for (int i = 0; i < plans.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(plans.get(i).toString());
        }
        System.out.println("---------------------------");
    }
}
