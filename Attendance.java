import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance
{
    private Roster r;
    private Map<String, Double> extras;
    private String currDate;

    public Attendance(Roster r, String date)
    {
        this.r = r;
        extras = new HashMap<>();
        this.currDate = date;
    }

    public void readCSVFile(String csvFile)
    {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        Map<String, Student> studentMap = r.getHashMap();

        try
        {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {

                // use comma as separator
                String[] student = line.split(cvsSplitBy);

                if(!studentMap.containsKey(student[0]))
                {
                    if(!extras.containsKey(student[0]))
                        extras.put(student[0], Double.parseDouble(student[1]));
                    else
                    {
                        double curr = extras.get(student[0]);
                        double toAdd = curr + Double.parseDouble(student[1]);
                        extras.put(student[0], toAdd);
                    }
                }
                else
                {
                    studentMap.get(student[0]).setAttendance(currDate, Integer.parseInt(student[1]));
                }

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

    public JTable addToTable(List<String> dates)
    {
        List<Student> list = r.getList();

        String[][] data = new String[list.size()][6+dates.size()];

        for(int i=0; i<list.size(); i++)
        {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getFirstName();
            data[i][2] = list.get(i).getLastName();
            data[i][3] = list.get(i).getProgramPlan();
            data[i][4] = list.get(i).getAcadmicLevel();
            data[i][5] = list.get(i).getASURITE();

            for(int j=0; j<dates.size(); j++)
            {
                data[i][5+j+1] = Integer.toString(list.get(i).getMap().get(dates.get(j)));
            }

        }

        String[] columnNames = new String[6+dates.size()];

        columnNames[0] = "ID";
        columnNames[1] = "First Name";
        columnNames[2] = "Last Name";
        columnNames[3] = "Program and Plan";
        columnNames[4] = "Academic Level";
        columnNames[5] = "ASURITE";

        for(int i=0; i<dates.size(); i++)
        {
            columnNames[5+i+1] = dates.get(i);
        }

        return new JTable(data, columnNames);
    }

    public Map<String, Double> getMap()
    {
        return extras;
    }
}
