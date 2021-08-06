import java.util.HashMap;
import java.util.Map;

public class Student
{
    private String id;
    private String firstName;
    private String LastName;
    private String programPlan;
    private String acadmic_Level;
    private String ASURITE;

    private Map<String, Integer> map;

    public Student(String id, String firstName,String LastName,String programPlan,String acadmic_Level, String ASURITE)
    {
        this.id = id;
        this.firstName = firstName;
        this.LastName = LastName;
        this.programPlan = programPlan;
        this.acadmic_Level = acadmic_Level;
        this.ASURITE = ASURITE;
        map = new HashMap<>();
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public String getProgramPlan()
    {
        return programPlan;
    }

    public String getAcadmicLevel()
    {
        return acadmic_Level;
    }

    public String getASURITE()
    {
        return ASURITE;
    }

    public Map<String, Integer> getMap()
    {
        return map;
    }


    public void setAttendance(String date, int a)
    {
        map.put(date, map.getOrDefault(date,0)+a);
    }
}
