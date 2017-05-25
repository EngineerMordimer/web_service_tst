package s12103.pjatk.pl.web_service_tst;

import java.util.Date;

/**
 * Created by maciej.chmielinski on 13.05.2017.
 */

public class Lesson {
    private String building;
    private Date beginDate;
    private Date endDate;
    private String code;
    private String name;
    private String classroom;
    private String type;
    private long id;

    public Lesson(String building, Date beginDate, Date endDate, String code, String name, String classroom, String type, long id) {
        this.building = building;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.code = code;
        this.name = name;
        this.classroom = classroom;
        this.type = type;
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "building='" + building + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", classroom='" + classroom + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}
