import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyApplications extends JFrame implements ActionListener
{
    private static JFrame frame;

    private static JMenu file, about;

    private static JMenuBar mb;

    private static JMenuItem item1, item2, item3, item4;

    private static JTable rosterTable, attendanceTable;

    private static JPanel rosterPanel, attendancePanel;

    private static Roster r;

    private static List<String> dates = new ArrayList<>();

    public static void main(String[] args)
    {
        MyApplications app = new MyApplications();

        frame = new JFrame("CSE360 Final Project");
        mb = new JMenuBar();

        file = new JMenu("File");
        about = new JMenu("About");

        item1 = new JMenuItem("Load a Roster");
        item2 = new JMenuItem("Add Attendance");
        item3 = new JMenuItem("Save");
        item4 = new JMenuItem("Plot Data");

        item1.addActionListener(app);
        item2.addActionListener(app);
        item3.addActionListener(app);
        item4.addActionListener(app);

        MenuListener listener = new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e)
            {
                JDialog dialog = new JDialog(frame, "Team Information");

                String info = "<html>Arnav Kasturia - 1215344494<br>Lakshit Singhal - 1215279416<br>Purvesh Biyani - 1215598839</html>";

                JLabel l1 = new JLabel(info);

                dialog.add(l1);

                dialog.setSize(250, 250);

                dialog.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        };

        about.addMenuListener(listener);

        file.add(item1);
        file.add(item2);
        file.add(item3);
        file.add(item4);

        mb.add(file);
        mb.add(about);

        frame.setJMenuBar(mb);

        frame.setSize(750, 750);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if(s.equals("Load a Roster"))
        {
            if(attendancePanel != null)
                frame.remove(attendancePanel);

            if(rosterPanel != null)
                frame.remove(rosterPanel);

            String filePath = askForFilePath();

            if(filePath.equals(""))
                return;

            r = new Roster();

            r.readCSVFile(filePath);

            rosterPanel = new JPanel();

            rosterTable = r.createTable();

            JScrollPane scroll =  new JScrollPane(rosterTable);

            //scroll.setPreferredSize(new Dimension(700, 100));

            rosterPanel.add(scroll);

            frame.add(rosterPanel);

            frame.setVisible(true);
        }
        else if(s.equals("Add Attendance"))
        {
            if(rosterPanel != null)
                frame.remove(rosterPanel);
            else
                return;

            if(attendancePanel != null)
                frame.remove(attendancePanel);

            JPanel p = new JPanel();

            JTextField te = new JTextField("Enter a date",16);

            JButton button = new JButton("submit");

            p.add(te);
            p.add(button);

            frame.add(p);

            frame.setVisible(true);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String currDate = te.getText();
                    String filePath = askForFilePath();
                    if(filePath.equals(""))
                        return;

                    frame.remove(p);

                    dates.add(currDate);

                    Attendance a = new Attendance(r, currDate);

                    a.readCSVFile(filePath);

                    attendancePanel = new JPanel();

                    attendanceTable = a.addToTable(dates);

                    JScrollPane attendanceScroll =  new JScrollPane(attendanceTable);

                    attendanceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    attendancePanel.add(attendanceScroll);

                    frame.add(attendancePanel);

                    frame.setVisible(true);

                    JDialog dialog = new JDialog(frame, "Data Loaded");

                    String text = "<html>Data loaded for "+r.getHashMap().size()+" users in the roster.";

                    Map<String, Double> map= a.getMap();

                    if(map.isEmpty())
                        return;

                    text += "<br><br>" + map.size()+" additional attendee(s) were found:<br>";
                    JLabel lbl2 = new JLabel();

                    dialog.add(lbl2);

                    String notAdded = "";
                    for(String temp: map.keySet())
                    {
                        notAdded += "<br>" + temp + " connected for "+(map.get(temp)/60)+" minute(s)</html>";
                    }

                    text += "\n" + notAdded;

                    JLabel lbl = new JLabel(text);

                    dialog.add(lbl);

                    dialog.setSize(300, 300);

                    dialog.setVisible(true);
                }
            });
        }
        else if(s.equals("Save"))
        {
            JTable tableToExport = attendanceTable;
            String pathToExportTo = askForFilePath();
            try
            {

                TableModel model = tableToExport.getModel();
                FileWriter csv = new FileWriter(new File(pathToExportTo));

                for (int i = 0; i < model.getColumnCount(); i++)
                {
                    csv.write(model.getColumnName(i) + ",");
                }

                csv.write("\n");

                for (int i = 0; i < model.getRowCount(); i++)
                {
                    for (int j = 0; j < model.getColumnCount(); j++)
                    {
                        csv.write(model.getValueAt(i, j).toString() + ",");
                    }
                    csv.write("\n");
                }

                csv.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else if(s.equals("Plot Data"))
        {
            SwingUtilities.invokeLater(() -> {
                plotGraph graph = new plotGraph("Student Attendance", attendanceTable, dates);
                graph.setSize(800, 400);
                graph.setLocationRelativeTo(null);
                graph.setVisible(true);
            });
        }
    }

    public String askForFilePath()
    {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView());

        int r = j.showSaveDialog(null);

        if(r == JFileChooser.APPROVE_OPTION)
        {
            return j.getSelectedFile().getAbsolutePath();
        }

        return "";
    }
}
