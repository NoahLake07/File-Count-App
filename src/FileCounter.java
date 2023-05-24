import com.virtualconsole.app.VConsole;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileCounter extends VConsole {

    public static final Color CONSOLE_COLOR = new Color(0, 41, 63);
    public static final int UPDATE_RULE = 500;

    private void initialize(){
        println("FILE COUNTER STARTED.\n",CONSOLE_COLOR);
        gatherInputs();
    }

    private void gatherInputs(){
        println("\tPlease enter a directory pathname to scan...\n",CONSOLE_COLOR);

        String input = getInputFromConsole();
        while(!new File(input).exists()){
            println("ERROR: That was not a valid directory pathname; please try again...", Color.RED);
            input = getInputFromConsole();
        }

        println("\tPathname stored. Input \"GO\" to start the scan or \"CANCEL\" to exit the thread.\n",CONSOLE_COLOR);

        String confirmation = getInputFromConsole().toUpperCase();
        while(!(confirmation.equals("CANCEL") || confirmation.equals("GO"))){
            println("ERROR: That wasn't a valid response. Try again...", Color.RED);
            confirmation = getInputFromConsole().toUpperCase();
        }

        if(confirmation.toUpperCase().equals("GO")){
            println("Started scan...",CONSOLE_COLOR);
            println("\t> FOUND A TOTAL OF "+scan(input) + " FILES INSIDE THE GIVEN DIRECTORY.\n\t\tRecurring to main thread...\n",new Color(0, 98, 4));
            gatherInputs();
        } else {
            println("Canceled operation. Recurring to main thread.",Color.ORANGE);
            gatherInputs();
        }
    }

    private int scan(String input){
        File startingDirectory = new File(input);
        if(!startingDirectory.isDirectory()){
            println("You submitted a file, not a directory...\n");
            return 1;
        }

        int filesFound = searchDirectory(startingDirectory);
        return filesFound;
    }

    private int searchDirectory(File dir){
        int filesInsideDirLevel = 0;
        for (int i = 0; i < dir.listFiles().length-1; i++) {
            if(dir.listFiles()[i].isDirectory()){
                filesInsideDirLevel += searchDirectory(dir.listFiles()[i]);
            } else {
                filesInsideDirLevel++;
            }
        }
        return filesInsideDirLevel;
    }

    public String getInputFromConsole(){
        Font defaultFont = UIManager.getFont("Label.font");
        Font italicFont = defaultFont.deriveFont(Font.ITALIC);
        String input = super.getInputFromConsole();
        println(input,new Color(2, 107, 95),italicFont);
        return input;
    }

    public static void main(String[] args) {
        new FileCounter().initialize();
    }
}