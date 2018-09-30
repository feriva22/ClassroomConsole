public class User {

    //list attribut
    private String email;
    private String name;
    private String password;
    private Student studentStatus;
    private Teacher teacherStatus;



    //TODO created in future for below attribute
    /*
    String profilPicture;
    boolean notifEmail;
    boolean commentOnyourPost;
    boolean commentThatmentionYou;
    */


    //---------------------------constructor method-------------------------//
    public User(){
        //set default value for student status
        studentStatus = new Student();
        studentStatus.setStudent(this);

        //set default value for teacher status
        teacherStatus = new Teacher();
        teacherStatus.setTeacher(this);

    }

    //----------------------------------------------------------------------//

    //---------------------------additional method--------------------------//



    public void addTeachingOn(ClassRoom myclass,boolean statusConfirm){
        teacherStatus.setTeachOn(myclass);
        //if statusConfirm is true, so status is confirmed, if not status not confirmed
        if (statusConfirm){
            teacherStatus.setStatusOnClass("confirmed");
        }
        else {
            teacherStatus.setStatusOnClass("unconfirmed");
        }

        myclass.setListTeacher(this.getTeacherStatus());
    }

    public void addStudyOn(ClassRoom myclass,boolean statusConfirm){
        studentStatus.setStudyOn(myclass);
        //if statusConfirm is true, so status is confirmed, if not status not confirmed
        if (statusConfirm){
            studentStatus.setStatusOnClass("confirmed");
        }
        else {
            studentStatus.setStatusOnClass("unconfirmed");
        }


        myclass.setListStudent(this.getStudentStatus());
    }

    //----------------------------------------------------------------------//


    //----------------------------getter method-----------------------------//
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Student getStudentStatus() {
        return studentStatus;
    }

    public Teacher getTeacherStatus() {
        return teacherStatus;
    }


    //--------------------------------------------------------------------//


    //----------------------------setter method---------------------------//
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //--------------------------------------------------------------------//



}
