import java.util.ArrayList;
import java.util.List;

public class Student {

    private User student;
    private List<ClassRoom> studyOn = new ArrayList<>();
    private List<String> statusOnClass = new ArrayList<>();  //option is confirmed or unconfirmed


    //----------------------------additional method-------------------------//
    public ClassRoom getClassStudy(String classCode){
        for (ClassRoom myClass : studyOn){
            if (myClass.getClassCode().equals(classCode)){
                return myClass;
            }
        }

        return null;
    }

    //----------------------------------------------------------------------//


    //----------------------------getter method-----------------------------//

    public User getStudent() {
        return student;
    }

    public List<ClassRoom> getStudyOn() {
        return studyOn;
    }

    public List<String> getStatusOnClass() {
        return statusOnClass;
    }

    //----------------------------------------------------------------------//

    //----------------------------setter method-----------------------------//
    public void setStudent(User student) {
        this.student = student;
    }


    public void setStudyOn(ClassRoom studyOn) {
        this.studyOn.add(studyOn);
    }

    public void setStatusOnClass(String statusOnClass) {
        this.statusOnClass.add(statusOnClass);
    }

    //----------------------------------------------------------------------//
}
