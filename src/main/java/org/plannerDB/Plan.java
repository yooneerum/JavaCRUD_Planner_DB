package org.plannerDB;

public class Plan {
    private int id;
    private int level;
    private int category;
    private int finish;
    private String created_date;
    private String contents;

    Plan(){}
    Plan(int id, int level, int category, int finish, String created_date, String contents) {
        this.id = id;
        this.level = level;
        this.category = category;
        this.finish = finish;
        this.created_date = created_date;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    @Override
    public String toString() {
        String slevel = "";
        String scategory = "";
        String sfinish = "";
        for(int i=0;i<level;i++) slevel += "*";
        if(category==1) {
            scategory = "학업";
        } else if (category==2) {
            scategory = "약속";
        } else if (category==3) {
            scategory = "개인";
        }
        if (finish==1) {
            sfinish = "O";
        } else if (finish==2) {
            sfinish = "X";
        } else {
            sfinish = "--";
        }

            String str = String.format("%-4s", slevel) + String.format("%-6s", scategory) + String.format("%-6s", sfinish) + String.format("%10s", created_date) + "  |  " + contents;
        return str;
    }
}
