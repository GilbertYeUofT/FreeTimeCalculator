import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;



/** When User is successfully logged in, MainMenu window pops with 4 options to continue*/
public class MainMenu implements ActionListener {
    private JFrame frame;
    private JButton addRecurButton;
    private JButton addOneOffButton;
    private JButton groupsButton;
    private JButton viewCalendarButton;
    private JButton returnButton;
    private LogIn loginController;
    private GroupController groupController;
    private String studentUsername;
    private CalendarController calendarController;
    private StudentController studentController;
    private JButton upload;


    /**
     * constructor StartMenu with 4 parameters
     *
     * @param loginController
     * @param groupController
     * @param calendarController
     * @param studentController
     * @param studentUsername
     */

    public MainMenu(LogIn loginController, GroupController groupController, CalendarController calendarController,
                    StudentController studentController, String studentUsername) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
        this.studentUsername = studentUsername;
        this.upload = new JButton("Upload iCal File");
        this.upload.setBounds(150, 0, 150,40);
        this.upload.addActionListener(this);


        this.frame = new JFrame();
        this.addRecurButton = new JButton("Create Recurring Events");
        this.addOneOffButton = new JButton("Create OneOff Events");
        this.groupsButton = new JButton("Groups");
        this.viewCalendarButton = new JButton("View Your Calendar");
        this.returnButton = new JButton("Logout");

        this.addRecurButton.setBounds(50, 150, 170, 40);
        this.addOneOffButton.setBounds(250, 150, 170, 40);
        this.groupsButton.setBounds(50, 300, 170, 40);
        this.viewCalendarButton.setBounds(250, 300, 170, 40);
        this.returnButton.setBounds(0, 0, 100, 20);

        this.addRecurButton.addActionListener(this);
        this.addOneOffButton.addActionListener(this);
        this.groupsButton.addActionListener(this);
        this.viewCalendarButton.addActionListener(this);
        this.returnButton.addActionListener(this);
        this.frame.setLayout(null);

        this.frame.add(addRecurButton);
        this.frame.add(addOneOffButton);
        this.frame.add(groupsButton);
        this.frame.add(viewCalendarButton);
        this.frame.add(returnButton);
        this.frame.add(upload);

        this.frame.setVisible(true);
        this.frame.setSize(500, 500);


    }

    /**
     * Makes button to perform based on a choice of a user.
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addRecurButton) {
            this.frame.dispose();
            RecurringMenu recurringMenu = new RecurringMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        } else if (e.getSource() == this.addOneOffButton) {
            this.frame.dispose();
            OneOffMenu oneOffMenu = new OneOffMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);

        } else if (e.getSource() == this.groupsButton) {
            this.frame.dispose();
            GroupMenu groupMenu = new GroupMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);

        } else if (e.getSource() == this.viewCalendarButton) {
            GregorianCalendar cal = new GregorianCalendar();
            int realMonth = cal.get(GregorianCalendar.MONTH);
            int realYear = cal.get(GregorianCalendar.YEAR);
            CalendarFrame calendarFrame = new CalendarFrame(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername, realMonth, realYear);

        } else if (e.getSource() == this.returnButton) {
            this.frame.dispose();
            StartMenu startMenu = new StartMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController);

        } else if (e.getSource() == this.upload) {

            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache");
            if (response== JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
                try {
                    this.useFile(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParserException ex) {
                    ex.printStackTrace();
                }


            }





        }


    }


    public void useFile(File file) throws IOException, ParserException {

        FileInputStream fin = new FileInputStream(file);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        ComponentList<CalendarComponent> list = calendar.getComponents();


        for (CalendarComponent i : list) {

            if (i instanceof VEvent) {

                String name = i.getName();
                String startDate = ((VEvent) i).getStartDate().getValue();
                System.out.println("this is it:");
                System.out.println(name);
                System.out.println(startDate);

                Date endDate = ((VEvent) i).getEndDate().getDate();




            }


        }










    }





}