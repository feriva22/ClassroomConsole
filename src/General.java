import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class General {

    public static User loggedin;
    public static List<User> listRegisteredUser = new ArrayList<>();
    public static List<ClassRoom> listClassRoom = new ArrayList<>();
    public static List<ClassRoom> tmpListClass = new ArrayList<>();


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


    //--------------------------------------------------------------------------//


    //--------------------------------process controller------------------------//
    public static void init(){
        //first visit
        loggedin = null;

        //create demo user
        User edi = new User();
        edi.setEmail("edi@gmail.com");
        edi.setName("edi");
        edi.setPassword("123456");
        listRegisteredUser.add(edi);

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
        newClass.setListTeacher(loggedin.getTeacherStatus());   //add teacher to list teacher

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



    //--------------------------------------------------------------------------//


    //---------------------------------view controller -------------------------//

    public static void loginView () throws InterruptedException {
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

    public static void regisView() throws InterruptedException {
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

    public static void dashboardView() throws InterruptedException {
        clsScreen();
        System.out.println("Login as "+loggedin.getName());

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
        System.out.println("Action : a.join class b.Create class c.Logout ");
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
            //logout
            logoutProcess();
            System.out.println("Logout success");
            System.out.println("Back to choice,........ ");
            Thread.sleep(1000);

        }
    }


    //------------------------------------------------------------------------------//

    public static void main(String[] args) throws InterruptedException {
        init();

        while(true){
            clsScreen();
            System.out.println("Pilih login/regis");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
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
