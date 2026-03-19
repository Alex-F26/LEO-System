import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;

/**
 * Provides a graphical file chooser to allow users to select a file from the "CSV" directory.
 * 
 * <p>This class opens a file selection dialog, defaulting to a folder named "CSV" within the current
 * working directory. Once a file is selected, its name is returned as a {@code String}.
 * 
 * <p>If the user cancels the file selection, the menu is re-displayed via {@code UserMenu}.
 */
public class OpenFolder {

    /**
     * Default constructor.
     */
    public OpenFolder(){
        
    }

    /**
     * Opens a file chooser dialog set to the "CSV" directory within the current working directory.
     *
     * @return The name of the selected file, or an empty string if no file is selected.
     */
    public String openFolder() {

            JFrame frame = new JFrame();
            String fileToRead = "";
            String currentDirectory = System.getProperty("user.dir");
            String targetFolder = "CSV";
            String filePath = currentDirectory + java.io.File.separator + targetFolder;
            JFileChooser fileChooser = new JFileChooser(filePath);

        frame.setVisible(true);
        BringToFront(frame);
       
            System.out.println("File Path: " + filePath);

            fileChooser.setDialogTitle("Select a File");

        if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)){
                    frame.setVisible(false);
                        File selectedFile = fileChooser.getSelectedFile();
                    fileToRead = selectedFile.getName();
                        System.out.println("Selected File Name: \n" + fileToRead);
        }else {
                    frame.setVisible(false);
                        System.out.println("No File Selected");
        }  
        return fileToRead;
}

    /**
     * Forces the given JFrame to come to the front of the screen.
     *
     * @param frame the JFrame to bring to the front
     */
    private void BringToFront(JFrame frame) {                  
                    frame.setExtendedState(JFrame.ICONIFIED);
                    frame.setExtendedState(JFrame.NORMAL);
    }
}
