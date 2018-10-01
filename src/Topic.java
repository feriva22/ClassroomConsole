import java.util.ArrayList;
import java.util.List;

public class Topic {
    private String name;
    private ClassRoom classLocation;
    private List<Assignment> listAssignment = new ArrayList<>();
    private List<Material> listMaterial = new ArrayList<>();



    //--------------------------------setter method-------------------------------//
    public void setName(String name) {
        this.name = name;
    }

    public void setListAssignment(Assignment assignment) {
        this.listAssignment.add(assignment);
    }

    public void setListMaterial(List<Material> listMaterial) {
        this.listMaterial = listMaterial;
    }

    public void setClassLocation(ClassRoom classLocation) {
        this.classLocation = classLocation;
    }




    //----------------------------------------------------------------------------//

    //--------------------------------getter method-------------------------------//
    public String getName() {
        return name;
    }

    public List<Assignment> getListAssignment() {
        return listAssignment;
    }

    public List<Material> getListMaterial() {
        return listMaterial;
    }

    public ClassRoom getClassLocation() {
        return classLocation;
    }



    //----------------------------------------------------------------------------//




}

