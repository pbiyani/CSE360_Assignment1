import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Roster
{
    private List<Student> list = new ArrayList<>();
    private HashMap<String, Student> map = new HashMap<>();

    public void readCSVFile(String csvFile)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try
        {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {

                // use comma as separator
                String[] student = line.split(cvsSplitBy);

                Student n = new Student(student[0], student[1], student[2], student[3], student[4], student[5]);

                list.add(n);

                map.put(student[5], n);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public JTable createTable()
    {
        String[][] data = new String[list.size()][6];

        for(int i=0; i<list.size(); i++)
        {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getFirstName();
            data[i][2] = list.get(i).getLastName();
            data[i][3] = list.get(i).getProgramPlan();
            data[i][4] = list.get(i).getAcadmicLevel();
            data[i][5] = list.get(i).getASURITE();

        }

        String[] columnNames = {"ID", "First Name", "Last Name", "Program and Plan", "Academic Level", "ASURITE"};

        return new JTable(data, columnNames);
    }

    public HashMap<String, Student> getHashMap()
    {
        return this.map;
    }

    public List<Student> getList()
    {
        return this.list;
    }
}
