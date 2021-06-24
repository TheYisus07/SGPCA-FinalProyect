package domain;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Approved {
    private int idMeeting;
    private String memberApprove;

    public Approved(int idMeeting, String memberApprove) {
        this.idMeeting = idMeeting;
        this.memberApprove = memberApprove;
    }
    
    public Approved() {}

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public String getMemberApprove() {
        return memberApprove;
    }

    public void setMemberApprove(String memberApprove) {
        this.memberApprove = memberApprove;
    }
}
