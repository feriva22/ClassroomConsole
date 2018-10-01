import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assignment {

    private String title;
    private String instructions;
    private int pointGrade;
    private Teacher createdBy;
    private Date createDate;
    private Date dueDate;
    private List<Student> lookedFor = new ArrayList<>();
    private ClassRoom classes;
    private Topic topic;
    private Date editedDate;
    private List<String> insertLink = new ArrayList<>();
    private Boolean isDrafted;
    private Date dateScheduled;//null if not scheduled
    private List<StudentWork> listStudentWork = new ArrayList<>();


    //-----------------------------constructor method-----------------------------//
    public Assignment(){
        setCreateDate(new Date());
    }



    //----------------------------------------------------------------------------//

    //-----------------------------additional method------------------------------//
    public int getTotalTurnedIn(){
        int turnedIn = 0;
        for (StudentWork studentWork : getListStudentWork()){
            if (studentWork.getStatusWork().equals("turned in")){
                turnedIn++;
            }
        }
        return turnedIn;
    }

    public int getTotalGraded(){
        int graded = 0;
        for (StudentWork studentWork : getListStudentWork()){
            if (studentWork.getStatusWork().equals("graded")){
                graded++;
            }
        }
        return graded;
    }

    public StudentWork getWorkByStudent(Student student){
        for (StudentWork studentWork : listStudentWork){
            if (studentWork.getStudent() == student){
                return studentWork;
            }
        }
        return null;
    }


    //----------------------------------------------------------------------------//


    //-----------------------------getter method----------------------------------//
    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getPointGrade() {
        return pointGrade;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<Student> getLookedFor() {
        return lookedFor;
    }

    public ClassRoom getClasses() {
        return classes;
    }

    public Topic getTopic() {
        return topic;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public List<String> getInsertLink() {
        return insertLink;
    }

    public Boolean getDrafted() {
        return isDrafted;
    }

    public Date getDateScheduled() {
        return dateScheduled;
    }

    public List<StudentWork> getListStudentWork() {
        return listStudentWork;
    }


    public Teacher getCreatedBy() {
        return createdBy;
    }


    //----------------------------------------------------------------------------//



    //-----------------------------setter method-----------------------------------//

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setPointGrade(int pointGrade) {
        this.pointGrade = pointGrade;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setLookedFor(List<Student> lookedFor) {
        this.lookedFor = lookedFor;
    }

    public void setClasses(ClassRoom classes) {
        this.classes = classes;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public void setInsertLink(String insertLink) {
        this.insertLink.add(insertLink);
    }

    public void setDrafted(Boolean drafted) {
        isDrafted = drafted;
    }

    public void setDateScheduled(Date dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public void setListStudentWork(StudentWork theStudentWork) {
        this.listStudentWork.add(theStudentWork);
    }

    public void setCreatedBy(Teacher createdBy) {
        this.createdBy = createdBy;
    }


    //----------------------------------------------------------------------------//
}
