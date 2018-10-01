import java.util.ArrayList;
import java.util.List;

public class StudentWork {
    private Assignment theAssignment;
    private Student student;
    private String statusWork = "assigned";    //assigned, turned in or graded
    private Integer gradePoint = null;
    private List<String> insertLink = new ArrayList<>();
    private Boolean returned = false;
    //TODO add private comments
    //List<Comment> privateComment = new ArrayList<>();


    //-----------------------------------setter method-------------------------------//
    public void setTheAssignment(Assignment theAssignment) {
        this.theAssignment = theAssignment;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStatusWork(String statusWork) {
        this.statusWork = statusWork;
    }

    public void setGradePoint(Integer gradePoint) {
        this.gradePoint = gradePoint;
    }

    public void setInsertLink(List<String> insertLink) {
        this.insertLink = insertLink;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    //--------------------------------------------------------------------------------//

    //----------------------------------getter method---------------------------------//

    public Assignment getTheAssignment() {
        return theAssignment;
    }

    public Student getStudent() {
        return student;
    }

    public String getStatusWork() {
        return statusWork;
    }

    public Integer getGradePoint() {
        return gradePoint;
    }

    public List<String> getInsertLink() {
        return insertLink;
    }

    public Boolean getReturned() {
        return returned;
    }

    //-------------------------------------------------------------------------------------//






}
