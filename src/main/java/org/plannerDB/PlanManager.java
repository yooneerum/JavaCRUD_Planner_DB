package org.plannerDB;

import java.sql.SQLException;
import java.util.Scanner;

public class PlanManager {
    Scanner s = new Scanner(System.in);
    PlanCRUD planCRUD;
    PlanManager() {
        planCRUD = new PlanCRUD(s);
    }

    public int selectMenu() {
        System.out.print(
                "***** My TodoList *****\n"
                        + "1. 모든 할일\n"
                        + "2. 중요도별 할일\n"
                        + "3. 카테고리별 할일\n"
                        + "4. 할일 검색\n"
                        + "5. 새로운 일정 등록\n"
                        + "6. 일정 수정\n"
                        + "7. 일정 삭제\n"
                        + "8. 파일에 저장\n"
                        + "0. 나가기\n"
                        + "***********************\n"
                        + "=>원하는 메뉴는?");

        return s.nextInt();
    }

    public void start() throws SQLException {
        while (true) {
            int menu = selectMenu();
            if (menu == 0) break;
            else if (menu == 1) {
                planCRUD.listAll();
            } else if (menu == 2) {
                planCRUD.listLevelPlan();
            } else if (menu == 3) {
                planCRUD.listCategoryPlan();
            } else if (menu == 4) {
                planCRUD.listSearchPlan();
            } else if (menu == 5) {
                planCRUD.addPlan();
            } else if (menu == 6) {
                planCRUD.updatePlan();
            } else if (menu == 7) {
                planCRUD.deletePlan();
            } else if (menu == 8) {
                planCRUD.saveFile();
            }
        }
    }
}
