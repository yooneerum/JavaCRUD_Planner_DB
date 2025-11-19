package org.plannerDB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class PlanCRUD implements ICRUD{

    final String selectAll = "select * from Planner";
    final String PLAN_INSERT = "insert into Planner (level, category, finish, created_date, contents) values(?,?,?,?,?)";

    ArrayList<Plan> plans;
    Scanner s;
    final String fname = "Planner.txt";
    Connection conn;

    PlanCRUD(Scanner s) {
        plans = new ArrayList<>();
        this.s = s;
        conn = DBConnection.getConnection();
    }

    public void loadDBData() {
        plans.clear();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectAll);
            while (rs.next()) {
                int id = rs.getInt("id");
                int level = rs.getInt("level");
                int category = rs.getInt("category");
                int finish = rs.getInt("finish");
                String created_date = rs.getString("created_date");
                String contents = rs.getString("contents");
                plans.add(new Plan(id, level, category, finish, created_date, contents));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return f.format(now);
    }

    @Override
    public int add(Plan one) throws SQLException {
        int result = 0;
        PreparedStatement pstmt;
        pstmt = conn.prepareStatement(PLAN_INSERT);
        pstmt.setInt(1 ,one.getLevel());
        pstmt.setInt(2 ,one.getCategory());
        pstmt.setInt(3 ,0);
        pstmt.setString(4 ,getCurrentDate());
        pstmt.setString(5 ,one.getContents());
        result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    public void addPlan() throws SQLException {
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

        Plan one = new Plan(0, level, category, 0, created_date, contents);
        int retval = add(one);
        if (retval > 0) {
            System.out.println("새 일정이 리스트에 추가되었습니다.");
        } else{
            System.out.println("!! 일정 추가 중 오류 발생 !!");
        }
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
        loadDBData();
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
