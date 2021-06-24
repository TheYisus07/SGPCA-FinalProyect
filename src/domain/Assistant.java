package domain;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Assistant {
    private String rol;
    private String fullName;
    private int idMeeting;

    public Assistant(String rol, String fullName, int idMeeting) {
        this.rol = rol;
        this.fullName = fullName;
        this.idMeeting = idMeeting;
    }
    
    public Assistant(String rol, String fullName) {
        this.rol = rol;
        this.fullName = fullName;
    }

    public Assistant() {}

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    @Override
    public String toString() {
        return "Assistant{" + "rol=" + rol + ", fullName=" + fullName + ", idMeeting=" + idMeeting + '}';
    }
}
