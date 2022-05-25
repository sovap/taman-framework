package helpers;

import org.testng.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileUpdater {   
    
	public static void updateTransactionFile(String templateFilePath, String newFilePath, ArrayList<ArrayList<String>> dataForUpdate) {
        File templateFile = new File(templateFilePath);
        File newFile = new File(newFilePath);
        
        // check if files exists
        if (!templateFile.exists()) {
            Assert.fail("[ERROR] Template file not found: " + templateFilePath);
		}
        
        if (!newFile.exists()) {
            Assert.fail("[ERROR] Transaction file not found: " + newFilePath);
		}

        StringBuilder oldContent = new StringBuilder();
        String newContent;
        BufferedReader reader;
        FileWriter writer;
        
        try {
            reader = new BufferedReader(new FileReader(templateFile));
             
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
             
            while (line != null) 
            {
                oldContent.append(line).append(System.lineSeparator());
                 
                line = reader.readLine();
            }
                                    
            //Replacing values
            newContent = oldContent.toString();
            for(ArrayList<String> list: dataForUpdate) {
            	newContent = newContent.replaceAll(list.get(0), list.get(1));
            }

            //Rewriting the input text file with newContent
            writer = new FileWriter(newFile);          
            writer.write(newContent);
            
            // close the resources
            reader.close();
            writer.close();
        } catch (Exception e) {
            Assert.fail("[ERROR] Error occurred while trying to update the transaction file: " + templateFilePath + "/n" + e);
        }
    }
}