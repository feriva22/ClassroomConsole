import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private User teacher;
    private List<ClassRoom> teachOn = new ArrayList<>();
    //for confirm the teacher need status
    private List<String> statusOnClass = new ArrayList<>(); //option is confirmed or unconfirmed



    //----------------------------additional method-------------------------//
    public ClassRoom getClassTeach(String classCode){
        for (ClassRoom myClass : teachOn){
            if (myClass.getClassCode().equals(classCode)){
                return myClass;
            }
        }

        return null;
    }


    //----------------------------------------------------------------------//

    //----------------------------getter method-----------------------------//

    public User getTeacher() {
        return teacher;
    }

    public List<ClassRoom> getTeachOn(){return teachOn;}

    //----------------------------------------------------------------------//

    //----------------------------setter method-----------------------------//
    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public void setTeachOn(ClassRoom myclass) {
        teachOn.add(myclass);
    }

    public void setStatusOnClass(String statusOnClass){
        this.statusOnClass.add(statusOnClass);
    }
    //----------------------------------------------------------------------//


}
