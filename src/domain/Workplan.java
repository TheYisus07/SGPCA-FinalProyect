package domain;

import java.util.Date;

/**
 * 
 * @author daniCV
 */

public class Workplan {
    private String keyCode;
    private Date startDate;
    private Date finishDate;
    private String memberFullName;

    public Workplan(String keyCode, Date startDate, Date finishDate, String memberFullName) {
        this.keyCode = keyCode;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.memberFullName = memberFullName;
    }

    public Workplan() {}

    public String getKeyCode() {
        return keyCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    @Override
    public String toString() {
        return "Workplan{" + "keyCode=" + keyCode + ", startDate=" + startDate + ", finishDate=" + finishDate + ", memberFullName=" + memberFullName + '}';
    }
}