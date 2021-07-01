package domain;

/**
 * 
 * @author daniCV
 */

public class Objective {
    private int ObjectiveId;
    private String title;
    private String description;
    private String status;
    private String workplanKeyCode;

    public Objective(int ObjectiveId, String title, String description, String status, String workplanKeyCode) {
        this.ObjectiveId = ObjectiveId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.workplanKeyCode = workplanKeyCode;
    }

    public Objective(String title, String description, String status, String workplanKeyCode) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.workplanKeyCode = workplanKeyCode;
    }

    public Objective() {}

    public int getObjectiveId() {
        return ObjectiveId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getWorkplanKeyCode() {
        return workplanKeyCode;
    }

    public void setObjectiveId(int ObjectiveId) {
        this.ObjectiveId = ObjectiveId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkplanKeyCode(String workplanKeyCode) {
        this.workplanKeyCode = workplanKeyCode;
    }
}