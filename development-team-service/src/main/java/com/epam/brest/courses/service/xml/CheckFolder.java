package com.epam.brest.courses.service.xml;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
public class CheckFolder {

    private static final String SLASH_BACK      = "/";
    
    private void createDir(final String dir)
    {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

   public String checkFolder(final String file_path)
    {
        String dir = "";
        if (!file_path.endsWith(SLASH_BACK) && file_path.contains(SLASH_BACK)) {
            dir = file_path.substring(0, file_path.lastIndexOf(SLASH_BACK));
            createDir(dir);
        }
        return dir;
    }


}
