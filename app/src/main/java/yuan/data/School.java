package yuan.data;

public class School {
    public School(String name) {
        where=name;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    private String where;
}
