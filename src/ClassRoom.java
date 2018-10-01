import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassRoom {

    private String className;
    private String section;
    private String subject;
    private String room;
    private Teacher owner;
    private List<Teacher> listTeacher = new ArrayList<>();
    private List<Student> listStudent = new ArrayList<>();
    private String classCode;
    private int privacyPost; //(0-2)studentcan post & comment or student only comment , or only teacher post and comment
    List<Post> studentPost = new ArrayList<>();
    List<Announcement> teacherPost = new ArrayList<>();
    List<Topic> listTopic = new ArrayList<>();




    //TODO create other attribut on the future
    /*
    String theme
    String colorClass
     */

    //--------------------------------------constructor method------------------------------------//

    public ClassRoom(){
        //set class code automatically
        this.setClassCode(getRandomCode());

        //set default privacyPost
        this.setPrivacyPost(0);
    }

    //--------------------------------------------------------------------------------------------//

    //--------------------------------------additional method-------------------------------------//

    public String getRandomCode(){

        String hasil="";
        //create
        Random rand = new Random();
        for (int i=0;i<3;i++){
            int hasilnomor = (int) (Math.random() * ((9 - 0) +1));
            char hasilchar = (char) ((int) (Math.random() * ((90 - 65)+1)) + 65);
            hasil= hasil+hasilnomor+hasilchar;
        }
        return hasil;
    }

    public int getStatusOnClass(User user){
        //return true if teacher, false if student
        //looping for check in teacher
        for (Teacher teacher : getListTeacher()){
            if (teacher.getTeacher() == user){
                return 0;
            }
        }

        for (Student student : getListStudent()){
            if (student.getStudent() == user){
                return 1;
            }
        }

        return -1;
    }

    //--------------------------------------setter method-----------------------------------------//

    public void setClassName(String className) {
        this.className = className;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setOwner(Teacher owner) {
        this.owner = owner;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setPrivacyPost(int privacyPost) {
        this.privacyPost = privacyPost;
    }

    public void setListTeacher(Teacher teacher){
        this.listTeacher.add(teacher);
    }

    public void setListStudent(Student student) {
        this.listStudent.add(student);
    }
    //----------------------------------------------------------------------------------//


    //------------------------------------getter method---------------------------------//
    public String getRoom() {
        return room;
    }



    public String getClassName() {
        return className;
    }



    public String getSection() {
        return section;
    }



    public String getSubject() {
        return subject;
    }



    public Teacher getOwner() {
        return owner;
    }

    public String getClassCode() {
        return classCode;
    }

    public List<Teacher> getListTeacher() {
        return listTeacher;
    }

    public int getPrivacyPost() {
        return privacyPost;
    }

    public List<Student> getListStudent() {
        return listStudent;
    }




    //------------------------------------------------------------------------------//

}
