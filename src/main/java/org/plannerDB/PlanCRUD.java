package org.plannerDB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PlanCRUD implements ICRUD{
    ArrayList<Plan> plans;
    Scanner s;
    final String fname = "Planner.txt";

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
    public void updatePlan() {
        System.out.println("=> 수정하고 싶은 일정 내용 검색 : ");
        String keyword = s.next();
        s.nextLine();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.println("=> 수정할 항목 번호 : ");
        int id = s.nextInt();
        s.nextLine();
        System.out.println("=> 중요도(1,2,3) : ");
        int level = s.nextInt();
        s.nextLine();
        System.out.println("=> 카테고리(1_학업 2_약속 3_개인) : ");
        int category = s.nextInt();
        s.nextLine();
        System.out.println("=> 완료여부(1_완료 2_실패) : ");
        int finish = s.nextInt();
        s.nextLine();
        System.out.println("=> 작성 날짜(YYYY-MM-DD) : ");
        String created_date = s.nextLine();
        System.out.println("=> 일정 내용 : ");
        String contents = s.nextLine();
        Plan plan = plans.get(idlist.get(id-1));
        plan.setLevel(level);
        plan.setCategory(category);
        plan.setFinish(finish);
        plan.setCreated_date(created_date);
        plan.setContents(contents);
        System.out.println("!! 수정 완료 !!");
    }

    @Override
    public void deletePlan() {
        System.out.println("=> 삭제하고 싶은 내용 검색 : ");
        String keyword = s.next();
        s.nextLine();
        ArrayList<Integer> idlist = this.listAll(keyword);
        if (!idlist.isEmpty()) {
            System.out.println("=> 삭제할 항목 번호 : ");
            int id = s.nextInt();
            s.nextLine();
            while (id > idlist.size()) {
                System.out.println("존재하지 않는 항목입니다.");
                System.out.println("=> 삭제할 항목 번호 재입력 : ");
                id = s.nextInt();
                s.nextLine();
            }
            System.out.println("=> 정말 삭제하시겠습니까?(Y/n) ");
            String ans = s.next();
            if(ans.equalsIgnoreCase("Y")) {
                plans.remove((int)idlist.get(id-1));
                System.out.println("일정이 삭제되었습니다.");
            } else {
                System.out.println("취소되었습니다.");
            }
        }else {
            System.out.println("검색 결과가 없습니다.");
        }
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
    public ArrayList<Integer> listAll(String keyword) {
        ArrayList<Integer> idlist = new ArrayList<>();
        int j = 0;
        System.out.println("---------------------------");
        for(int i=0; i<plans.size();i++){
            String contents = plans.get(i).getContents();
            if(contents.contains(keyword)){
                System.out.print((j+1) +" ");
                System.out.println(plans.get(i).toString());
                idlist.add(i);
                j++;
            }
        }
        System.out.println("---------------------------");
        return idlist;
    }

    public void listLevelPlan() {
        System.out.println("=> 중요도(1~3) 입력 : ");
        int level = s.nextInt();
        s.nextLine();

        ArrayList<Plan> temp = new ArrayList<>();
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getLevel() == level) {
                temp.add(plans.get(i));
            }
        }
        System.out.println("---------------------------");
        for (int i = 0; i < temp.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(temp.get(i).toString());
        }
        System.out.println("---------------------------");
    }

    public void listCategoryPlan() {
        System.out.println("=> 카테고리(1_학업 2_약속 3_개인) 입력 : ");
        int category = s.nextInt();
        s.nextLine();

        ArrayList<Plan> temp = new ArrayList<>();
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getCategory() == category) {
                temp.add(plans.get(i));
            }
        }
        System.out.println("---------------------------");
        for (int i = 0; i < temp.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(temp.get(i).toString());
        }
        System.out.println("---------------------------");
    }

    public void listSearchPlan() {
        System.out.println("=> 찾고 싶은 내용 : ");
        String keyword = s.next();
        s.nextLine();
        ArrayList<Plan> temp = new ArrayList<>();

        for (int i = 0; i < plans.size(); i++) {
            String contents = plans.get(i).getContents();
            if (contents.contains(keyword)) {
                temp.add(plans.get(i));
            }
        }
        System.out.println("---------------------------");
        for (int i = 0; i < temp.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(temp.get(i).toString());
        }
        System.out.println("---------------------------");
    }

    public void saveFile() {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter(fname));
            for(Plan one : plans){
                pr.write(one.toFileString() + "\n");
            }
            pr.close();
            System.out.println("!! 데이터 저장 완료 !!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
