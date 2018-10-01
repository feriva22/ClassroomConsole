import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class General {

    public static User loggedin;
    public static List<User> listRegisteredUser = new ArrayList<>();
    public static List<ClassRoom> listClassRoom = new ArrayList<>();
    public static List<ClassRoom> tmpListClass = new ArrayList<>();

    public static List<Assignment> tmpListAssignment = new ArrayList<>();



    //-------------------------------properties controller ----------------------//

    public static String getInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void clsScreen() {
        try {
            String os = System.getProperty("os.name");
            String[] osnya = os.split(" ");
            if (osnya[0].equals("Linux")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } else if (osnya[0].equals("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            System.out.println("sorry there was an error in removing the screen");
        }
    }

    public static ClassRoom getClassByName(String name){
        for (ClassRoom classRoom : listClassRoom){
            if (classRoom.getClassName().equalsIgnoreCase(name)){
                return classRoom;
            }
        }
        return null;
    }


    //--------------------------------------------------------------------------//


    //--------------------------------process controller------------------------//
    public static void init() throws ParseException {
        //first visit
        loggedin = null;

        //create demo user
        regisProcess("edi@gmail.com","edi","123456");
        regisProcess("dia@gmail.com","dia","12345");
        regisProcess("tedi@gmail.com","tedi","123123");
        regisProcess("dinda@gmail.com","dinda","123123");

        //create demo class for the user edi
        loggedin = searchUser("edi@gmail.com");
        createClassProcess("Internet of things","electro","electro","class A");
        createClassProcess("Data Science","science","science","class B");

        //create demo class for the user dia
        loggedin = searchUser("dia@gmail.com");
        createClassProcess("Operating System","computer","computer","class A");

        //create demo class for the user tedi joined all class has created above
        loggedin = searchUser("tedi@gmail.com");
        for (ClassRoom myClass :listClassRoom){
            joinClassProcess(myClass.getClassCode());
        }

        //create demo class for the user dinda joined all clasas has created above
        loggedin = searchUser("dinda@gmail.com");
        for (ClassRoom myClass : listClassRoom){
            joinClassProcess(myClass.getClassCode());
        }


        //auto create topic and assignment
        loggedin = searchUser("edi@gmail.com");
        createTopicProcess(getClassByName("Internet of things"),"Tugas dasar");
        createAssignment(getClassByName("Internet of things"),"tugas 1","kumpulkan tugas baru anda",
                "100",new SimpleDateFormat("dd-MM-yyy,HH-mm-ss").parse("10-10-2018,10-10-0"),"coba.com",
                false,null,"1",
                "x");

        createAssignment(getClassByName("Internet of things"),"tugas 2","kumpulkan tugas kedua anda",
                "100",null,"coba.com",
                false,null,"1",
                "x");

        //back to first visit
        loggedin = null;



    }

    public static User searchUser(String email){
        for (User user : listRegisteredUser){
            if (user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    public static boolean regisProcess(String email,String name, String pass){
        for (User user : listRegisteredUser){
            if (user.getEmail().equalsIgnoreCase(email) || user.getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(pass);
        listRegisteredUser.add(newUser);
        loggedin = newUser;
        return true;
    }

    public static boolean loginProcess(String email,String pass){
        for (User user : listRegisteredUser){
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(pass)){
                loggedin = user;
                return true;
            }
        }
        return false;
    }

    public static void logoutProcess(){
        loggedin = null;
    }

    public static boolean createClassProcess(String className,String section,String subject, String room){

        for (ClassRoom theClass: listClassRoom) {
            if (theClass.getClassName().equals(className)){
                return false;
            }
        }

        //create object Classroom
        ClassRoom newClass = new ClassRoom();
        newClass.setClassName(className);
        newClass.setSection(section);
        newClass.setSubject(subject);
        newClass.setRoom(room);
        newClass.setOwner(loggedin.getTeacherStatus());

        //add newClass to list class teach in Teacher
        loggedin.addTeachingOn(newClass,true);
        //add newClass to listClassroom
        listClassRoom.add(newClass);
        return true;
    }

    public static boolean joinClassProcess(String codeClass){
        if (loggedin.getStudentStatus().getClassStudy(codeClass) != null){
            return false;   //if user has join in the class
        }

        if (loggedin.getTeacherStatus().getClassTeach(codeClass) != null){
            return false;   //if user has join in the class
        }

        for (ClassRoom theClass :listClassRoom){
            if (theClass.getClassCode().equals(codeClass)){
                loggedin.addStudyOn(theClass,true);
                return true;
            }
        }
        return false;
    }

    public static void leaveClassProcess(ClassRoom theClassroom){
        theClassroom.getListStudent().removeIf(t -> t.getStudent() == loggedin);
        loggedin.getStudentStatus().getStudyOn().removeIf(t -> t.getClassName().equals(theClassroom.getClassName()));
    }

    public static boolean createTopicProcess(ClassRoom theClass,String nameTopic){
        if (theClass.getTopicByName(nameTopic) == null){
            Topic newTopic = new Topic();
            newTopic.setName(nameTopic);
            newTopic.setClassLocation(theClass);

            theClass.setListTopic(newTopic);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean createAssignment(ClassRoom theClassroom,String title,String instructions, String pointGrade, Date dueDate,
                                           String insertLink,Boolean isDrafted, Date scheduledDate, String topic, String lookedFor){

        Assignment newAssignment = new Assignment();
        newAssignment.setTitle(title);
        newAssignment.setClasses(theClassroom);
        newAssignment.setInsertLink(insertLink);
        newAssignment.setInstructions(instructions);
        newAssignment.setPointGrade(Integer.parseInt(pointGrade));
        newAssignment.setCreatedBy(loggedin.getTeacherStatus());
        newAssignment.setDueDate(dueDate);
        newAssignment.setDrafted(isDrafted);
        newAssignment.setDateScheduled(scheduledDate);
        //check if topic is x is no topic or other
        if (topic.equalsIgnoreCase("x")){
            newAssignment.setTopic(null);
        }
        else {
            newAssignment.setTopic(theClassroom.getListTopic().get(Integer.parseInt(topic)-1));
            theClassroom.getListTopic().get(Integer.parseInt(topic)-1).setListAssignment(newAssignment);
        }

        //add student will be added to StudentWork and lookedFor
        //TODO next future need protection input
        if (lookedFor.equalsIgnoreCase("x")){
            for (Student student : theClassroom.getListStudent()){
                StudentWork studentWork = new StudentWork();
                studentWork.setStudent(student);
                studentWork.setTheAssignment(newAssignment);

                newAssignment.setListStudentWork(studentWork);
            }
        }
        else {
            //if not all student so pick student with spesific number choice
            String[] choiceStudentNumber = lookedFor.split(",");
            for (String choice : choiceStudentNumber) {
                StudentWork studentWork = new StudentWork();
                studentWork.setStudent(theClassroom.getListStudent().get(Integer.parseInt(choice) - 1));
                studentWork.setTheAssignment(newAssignment);

                newAssignment.setListStudentWork(studentWork);
            }
        }

        return true;
    }


    //--------------------------------------------------------------------------//


    //---------------------------------view controller -------------------------//

    public static void loginView () throws InterruptedException, ParseException {
        clsScreen();
        System.out.println("LOGIN FORM");
        System.out.print("Email :");
        String email = getInput();
        System.out.print("Password :");
        String pass = getInput();

        if (loginProcess(email,pass)){
            System.out.println("Login success");
            System.out.println("Redirected to dashboard,..............");
            Thread.sleep(1000);
            dashboardView();

        } else {
            System.out.println("Failed login");
            System.out.println("back to choice view,....................");
            Thread.sleep(1000);
        }

    }

    public static void regisView() throws InterruptedException, ParseException {
        clsScreen();
        System.out.println("REGIS FORM");
        System.out.print("Email : ");
        String email = getInput();
        System.out.print("Username : ");
        String name = getInput();
        System.out.print("Password : ");
        String pass = getInput();

        if (regisProcess(email,name,pass)){
            System.out.println("Registration success");
            System.out.println("Login as "+name+",...............");
            System.out.println("Redirected to dashboard,.........");
            Thread.sleep(1000);
            dashboardView();
        }
        else {
            System.out.println("Sorry email or name has been used");
            System.out.println("Please input again,..............");
            Thread.sleep(1000);
        }


        //TODO add other attribut form in future
    }

    public static void dashboardView() throws InterruptedException, ParseException {
        clsScreen();
        System.out.println("List Classes");
        System.out.println("Hello "+loggedin.getName());

        //clear list classroom first
        tmpListClass.clear();
        int i = 1;
        //show the class that user teachOn
        System.out.println("Teach in :");
        for (ClassRoom theClassTeach : loggedin.getTeacherStatus().getTeachOn()){
            tmpListClass.add(theClassTeach);
            System.out.println(i+". "+theClassTeach.getClassName() + "("+theClassTeach.getClassCode()+")");

            i++;
        }
        System.out.println("==================================");
        System.out.println("Study in :");
        for (ClassRoom theClassStudy : loggedin.getStudentStatus().getStudyOn() ){
            tmpListClass.add(theClassStudy);
            System.out.println(i+". "+theClassStudy.getClassName()  + "("+theClassStudy.getClassCode()+")");
            i++;
        }

        //TODO create case if select join class or create class
        System.out.println("Action : a.join class b.Create class c.Leave class d.Logout");
        System.out.print("input : ");
        String inputan = getInput();
        if (inputan.equalsIgnoreCase("a")){
            //join class by code class
            clsScreen();
            System.out.print("Input the class code :");
            String classCode = getInput();
            if (joinClassProcess(classCode)){
                System.out.println("Success join class ");
                System.out.println("refresh page ,..................");
                Thread.sleep(1000);
                dashboardView();
            }
            else {
                System.out.println("Failed join class, wrong class code/you has join the class");
                System.out.println("refresh page ,..................");
                Thread.sleep(1000);
                dashboardView();
            }

        }
        else if(inputan.equalsIgnoreCase("b")){
            //create class
            clsScreen();
            System.out.println("Create class form");
            System.out.print("input class name : ");
            String className = getInput();
            System.out.print("input section : ");
            String section = getInput();
            System.out.print("input subject : ");
            String subject = getInput();
            System.out.print("input room : ");
            String room = getInput();

            if (createClassProcess(className,section,subject,room)){
                System.out.println("Success create class "+className);
                System.out.println("refresh page ,..................");
                Thread.sleep(1000);
                dashboardView();
            }
            else {
                System.out.println("Failed create class because class has been created");
                System.out.println("refresh page ,..................");
                Thread.sleep(1000);
                dashboardView();
            }
        }
        else if(inputan.equalsIgnoreCase("c")){
            System.out.print("Please choice the number class will be leaved : ");
            String choice = getInput();
            if (Integer.parseInt(choice) <= 0 || Integer.parseInt(choice) > tmpListClass.size()){
                System.out.println("Your choice is invalid");
                System.out.println("refresh page ,..................");
                Thread.sleep(1000);
                dashboardView();
            }
            else {
                System.out.print("Are you sure want to leave the class (y/n) : ");
                String choiceCheck = getInput();
                if (choiceCheck.equalsIgnoreCase("y")){
                    leaveClassProcess(tmpListClass.get(Integer.parseInt(choice)-1));
                    System.out.println("Success leave class");
                    System.out.println("refresh page ,..................");
                    Thread.sleep(1000);
                    dashboardView();
                }
                else {
                    System.out.println("refresh page ,..................");
                    Thread.sleep(1000);
                    dashboardView();
                }
            }
        }
        else if(inputan.matches("[0-9]+")){
            if (Integer.parseInt(inputan) <= tmpListClass.size() && Integer.parseInt(inputan) > 0) {
                viewClassRoom(tmpListClass.get(Integer.parseInt(inputan)-1));
            }
            else {
                System.out.println("The number list of class nothing");
                System.out.println("refresh page ,....................");
                Thread.sleep(1000);
                dashboardView();
            }
        }
        else if(inputan.equalsIgnoreCase("d")){
            //logout
            logoutProcess();
            System.out.println("Logout success");
            System.out.println("Back to choice,........ ");
            Thread.sleep(1000);
        }

    }

    public static void viewClassRoom(ClassRoom theClass) throws InterruptedException, ParseException {
        clsScreen();
        System.out.println("Class Detail :");
        System.out.println(theClass.getClassName()+" ("+theClass.getSection()+")");
        System.out.println("Class code : "+theClass.getClassCode());
        if (theClass.getStatusOnClass(loggedin) == 0){
            System.out.println("Hello teacher "+loggedin.getName());
        }
        else if (theClass.getStatusOnClass(loggedin) == 1){
            System.out.println("Hello student "+loggedin.getName());
        }
        else {
            System.out.println("Something wrong in query status user on class");
        }


        System.out.println("=============================================================");
        System.out.println("a. Stream view");
        System.out.println("b. Classwork view");
        System.out.println("c. People view");
        System.out.println("d. Back to Dashboard");
        if (theClass.getStatusOnClass(loggedin) ==0) {System.out.println("e. Setting");}
        System.out.print("input : ");
        String choice = getInput();
        if (choice.equalsIgnoreCase("a")){

        }
        else if (choice.equalsIgnoreCase("b")){
            viewClasswork(theClass);
        }
        else if (choice.equalsIgnoreCase("c")){
            clsScreen();
            viewPeople(theClass);
        }
        else if (choice.equalsIgnoreCase("d")){
            System.out.println("Back to Dashboard,..........");
            Thread.sleep(1000);
            dashboardView();
        }
        else if(choice.equalsIgnoreCase("e")){
            viewSetting(theClass);
        }
        else {
            System.out.println("refresh page ,....................");
            Thread.sleep(1000);
            viewClassRoom(theClass);
        }
    }

    public static void viewClasswork(ClassRoom theClass) throws InterruptedException, ParseException {

        tmpListAssignment.clear();
        int i =1;
        int j =1;
        int statusUser = theClass.getStatusOnClass(loggedin);
        clsScreen();
        System.out.println("ClassWork View");

        for (Topic topic : theClass.getListTopic()){
            System.out.println("* "+topic.getName());
            System.out.println("  -> Assignment");
            for (Assignment assignment : topic.getListAssignment()){
                //add the assignment to tmp
                tmpListAssignment.add(assignment);
                System.out.println("   "+i+". "+assignment.getTitle());
                System.out.println("        "+assignment.getTotalTurnedIn()+" Turned In "
                        +assignment.getTotalGraded()+" Graded "+
                        (assignment.getListStudentWork().size()-assignment.getTotalTurnedIn()-assignment.getTotalGraded())+" Assigned");
                i++;
            }
            System.out.println("  -> Material");
            for (Material material : topic.getListMaterial()){
                System.out.println("   "+j+". "+material.getTitle());
                j++;
            }
        }
        System.out.println();
        if (statusUser == 0) {
            System.out.println("Actions : a.Add b.Pick Assignment c.Pick Material x.Back to Class Detail");
        }
        else {
            System.out.println("Actions : a.Pick Assignment b.pick Material x.Back to Class Detail");
        }
        System.out.print("input : ");
        String choice = getInput();
        if (choice.equalsIgnoreCase("a")){
            if (statusUser==0){
                //create class
                System.out.println("Add : a.Add Assignment b.Add Questions c.Add Material d.Add Topic e.Back to Class Work");
                System.out.print("input : ");
                String choiceAdd = getInput();
                if (choiceAdd.equalsIgnoreCase("a")){
                    //add assignment
                    clsScreen();
                    System.out.println("Add assignment Form for class "+theClass.getClassName());
                    System.out.print("Input Title : ");
                    String title = getInput();
                    System.out.print("Input Instructions : ");
                    String instruction = getInput();
                    System.out.print("Input max point Given : ");
                    String point = getInput();
                    System.out.print("Input insert link : ");
                    String insertLink = getInput();

                    System.out.print("Have due date ? if yes input day-month-year,hour-minute-seconds , if no input none :");
                    String dueDate = getInput();
                    Date dueDateAssignment = null;
                    if (dueDate.equalsIgnoreCase("none")){
                        dueDateAssignment = null;
                    }
                    else {
                        dueDateAssignment = new SimpleDateFormat("dd-MM-yyyy,HH-mm-ss").parse(dueDate);
                    }

                    System.out.println("Choice student will look the Assignment : ");
                    int s=1;
                    System.out.println("x. All student");
                    for(Student student : theClass.getListStudent()){
                        System.out.println(s+". "+student.getStudent().getName());
                        s++;
                    }
                    System.out.print("input (if multiple, input number with separated ',' ) : ");
                    String lookedFor = getInput();

                    System.out.println("Choice topic will be assigned to the assignment : ");
                    int t =1;
                    System.out.println("x. no Topic");
                    for (Topic topic : theClass.getListTopic()){
                        System.out.println(t+". "+topic.getName());
                        t++;
                    }

                    System.out.print("input choice : ");
                    String choicesTopic = getInput();

                    System.out.println("Actions : a.Post b.Scheduled c.Save Draft");
                    System.out.print("input : ");
                    String action = getInput();
                    if (action.equalsIgnoreCase("a")){
                        //post without scheduled and save draft
                        createAssignment(theClass,title,instruction,point,dueDateAssignment,insertLink,false,
                                null,choicesTopic,lookedFor);
                        System.out.println("Success create Assignment ");
                        System.out.println("Refresh page,..............");
                        Thread.sleep(1000);
                        viewClasswork(theClass);

                    }
                    else if (action.equalsIgnoreCase("b")){
                        //post with scheduled and without save draft
                        System.out.print("Schedule Date format (day-month-year,hour-minute-seconds) :");
                        String scheduleDate = getInput();
                        Date scheduleDateAssignment = new SimpleDateFormat("dd-MM-yyyy,HH-mm-ss").parse(scheduleDate);
                        createAssignment(theClass,title,instruction,point,dueDateAssignment,insertLink,false,
                                scheduleDateAssignment,choicesTopic,lookedFor);

                    }
                    else if (action.equalsIgnoreCase("c")){
                        //post with scheduled
                        createAssignment(theClass,title,instruction,point,dueDateAssignment,insertLink,false,
                                null,choicesTopic,lookedFor);
                    }

                }
                else if (choiceAdd.equalsIgnoreCase("b")){
                    //add question
                }
                else if (choiceAdd.equalsIgnoreCase("c")){
                    //add material
                }
                else if (choiceAdd.equalsIgnoreCase("d")){
                    clsScreen();
                    System.out.print("Input the name Topic : ");
                    String name = getInput();
                    if (createTopicProcess(theClass,name)){
                        System.out.println("Success create topic");
                        System.out.println("refresh page ,....................");
                        Thread.sleep(1000);
                        viewClasswork(theClass);
                    }
                    else {
                        System.out.println("Failed create topic ");
                        System.out.println("refresh page ,....................");
                        Thread.sleep(1000);
                        viewClasswork(theClass);
                    }
                }
                else if (choiceAdd.equalsIgnoreCase("e")){
                    System.out.println("refresh page ,....................");
                    Thread.sleep(1000);
                    viewClasswork(theClass);
                }
            }
            else {
                //pick assignment if student
                System.out.print("Choice the number Assignment : ");
                String choiceAssign = getInput();
                viewDetailAssignment(tmpListAssignment.get(Integer.parseInt(choiceAssign)-1));

            }
        }
        else if(choice.equalsIgnoreCase("b")){
            //pick material if student
            //pick assignment if teacher
        }
        else if(choice.equalsIgnoreCase("c")){
            //pick material for teacher
        }
        else if(choice.equalsIgnoreCase("x")){
            System.out.println("Back to Class Detail");
            Thread.sleep(1000);
            viewClassRoom(theClass);
        }

    }

    public static void viewDetailAssignment(Assignment assignment) throws InterruptedException, ParseException {
        clsScreen();
        int statusPrivacy = assignment.getClasses().getStatusOnClass(loggedin);
        if (statusPrivacy == 1) {
            System.out.println("Assignment detail");
            System.out.println(assignment.getTitle() + " by " + assignment.getCreatedBy().getTeacher().getName());
            System.out.println("posted " + new SimpleDateFormat("MMM dd, HH:mm").format(assignment.getCreateDate()));
            if (assignment.getDueDate() != null) {
                System.out.println("Due " + new SimpleDateFormat("MMM dd, HH:mm").format(assignment.getDueDate()));
            } else {
                System.out.println("No due date");
            }

            System.out.println(assignment.getInstructions());
            String status = assignment.getWorkByStudent(loggedin.getStudentStatus()).getStatusWork();
            System.out.println("Status : " + status);


            System.out.println("Actions : a." + ((status.equalsIgnoreCase("assigned")) ? "Submit work" : "Resubmit") + " b.Back to classwork view");
            System.out.print("input : ");
            String choice = getInput();
            if (choice.equalsIgnoreCase("a")) {
                if (status.equalsIgnoreCase("assigned") || status.equalsIgnoreCase("turned in")) {
                    System.out.print("Input the link for turned work : ");
                    String link = getInput();
                    assignment.setInsertLink(link);
                    assignment.getWorkByStudent(loggedin.getStudentStatus()).setStatusWork("turned in");
                    System.out.println("Success turned in");
                    System.out.println("Refresh page,...............");
                    Thread.sleep(1000);
                    viewDetailAssignment(assignment);
                }

            }
            else  if (choice.equalsIgnoreCase("b")){
                System.out.println("Back to classwork view ");
                Thread.sleep(1000);
                viewClasswork(assignment.getClasses());
            }

        }

    }

    public static void viewPeople(ClassRoom theClass) throws InterruptedException, ParseException {
        // show list of teacher
        clsScreen();

        System.out.println("====Teachers====");
        int i = 1;
        for (Teacher teacher : theClass.getListTeacher()) {
            System.out.println(i + ". " + teacher.getTeacher().getName());
            i++;
        }
        System.out.println();
        // show list of Student
        if (theClass.getStatusOnClass(loggedin) == 0) {
            System.out.println("====Students====");
        } else if (theClass.getStatusOnClass(loggedin) == 1) {
            System.out.println("====Classmates====");
        } else {
            System.out.println("Something wrong.....");
        }
        int j = 1;
        for (Student student : theClass.getListStudent()) {
            System.out.println(j + ". " + student.getStudent().getName());
            j++;
        }

        System.out.println("=============================================================");
        if (theClass.getStatusOnClass(loggedin) == 0) {
            System.out.println("a. Back to Class Detail");
            System.out.print("input : ");
            String choice = getInput();
            if (choice.equalsIgnoreCase("a")) {
                System.out.println("Back to class Detail,..........");
                Thread.sleep(1000);
                viewClassRoom(theClass);
            }
        } else if (theClass.getStatusOnClass(loggedin) == 1) {
            System.out.println("action :" + " a. Pick Teacher " + "b. Pick Student " + "c. add Teacher "
                    + "d. add Student " + "e. Back to Class Detail ");
            System.out.print("input : ");
            String choice = getInput();
            if (choice.equalsIgnoreCase("a")) {

            } else if (choice.equalsIgnoreCase("b")) {

            } else if (choice.equalsIgnoreCase("c")) {

            } else if (choice.equalsIgnoreCase("d")) {

            } else if (choice.equalsIgnoreCase("e")) {
                System.out.println("Back to class Detail,..........");
                Thread.sleep(1000);
                viewClassRoom(theClass);
            }
        }
    }

    public static void viewSetting(ClassRoom theClass) throws InterruptedException, ParseException {
        System.out.println("Classroom of : " + theClass.getClassName());
        System.out.println("Section : " + theClass.getSection());
        System.out.println("Room : " + theClass.getRoom());
        System.out.println("Subject : " + theClass.getSubject());
        System.out.println("Kode kelas : " + theClass.getClassCode());

        System.out.println("action :" + " a.change name class " + "b.change section " + "c.change room "
                + "d.change subject " + "e.disabled class " + "f.enabled class" + "g.Back to Classroom");
        System.out.print("input : ");
        String choice = getInput();
        if (choice.equalsIgnoreCase("a")) {
            System.out.println("Input class name : ");
            String classname = getInput();
            theClass.setClassName(classname);
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        } else if (choice.equalsIgnoreCase("b")) {
            System.out.println("Input section : ");
            String sectionname = getInput();
            theClass.setSection(sectionname);
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        } else if (choice.equalsIgnoreCase("c")) {
            System.out.println("Input room : ");
            String room = getInput();
            theClass.setRoom(room);
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        } else if (choice.equalsIgnoreCase("d")) {
            System.out.println("Input subject : ");
            String subject = getInput();
            theClass.setSubject(subject);;
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        } else if (choice.equalsIgnoreCase("e")) {
            theClass.setClassCode(null);
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        }else if (choice.equalsIgnoreCase("f")) {
            theClass.setClassCode(theClass.getRandomCode());
            System.out.println("Back to viewSetting,.......");
            Thread.sleep(1000);
            viewSetting(theClass);
        }else if (choice.equalsIgnoreCase("g")) {
            System.out.println("Back to class Detail,..........");
            Thread.sleep(1000);
            viewClassRoom(theClass);
        }

    }




    //------------------------------------------------------------------------------//

    public static void main(String[] args) throws InterruptedException, ParseException {
        init();

        while(true){
            clsScreen();
            System.out.println("Choice Login or Registration");
            System.out.println("1. Login");
            System.out.println("2. Registration");
            System.out.print("input : ");

            String input = getInput();

            if (input.equalsIgnoreCase("1")) {
                loginView();
            }
            else if(input.equalsIgnoreCase("2")){
                regisView();
            }
            else {
                System.out.println("input wrong");
            }
        }
    }
}
