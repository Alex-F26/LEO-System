# LEO-System
Satellite analysis program. Uses pre-defined lists of satellite coordinates, launch date, conjunction count, orbit type, and other details,  to make calculations such as collision risks, long-term impact, satellite density, etc.

# Installation & How-to-Use

Download all of the folders and java files and add them to a single project folder where you will be running your simulation

Open VSCode or the IDE of your choice. 
In the IDE terminal, navigate to your project folder (cd project folder  <OR>  create a workspace)
Compile the java code (javac *.java)
Run the RunSimulation.java file inside the IDE terminal

If it all compiled correctly, a default starting menu may show up, unless the User-List.csv is already populated. 
Default Login:
Username: ADMIN 
Password: ADMIN

If you downloaded the provided User-List.csv file, it will have data containing some usernames and passwords that you may use to navigate through the simulation.

When choosing a file to scan:
- This project uses javax.swing.JFileChooser & javax.swing.JFrame
- You will need to make sure that the 'CSV' folder provided is inside of the same folder that you are running your simulation in (e.g if you downloaded the java classes to a "javaProject" folder, the CSV folder should be inside of that folder as well)

# Resources

- .csv files containing satellite details (organized and 'jumbled')
- .csv file containing usernames and password
- Systemlog.txt will be generated and updated every time the simulation is ran
- new & updated .csv and .txt files will be generated depending on which assessment is chosen
- JUnit tests are provided for further class tests
