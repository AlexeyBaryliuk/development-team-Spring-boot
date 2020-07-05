package com.epam.brest.courses.service.xml;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@SuppressFBWarnings(value = "OS_OPEN_STREAM_EXCEPTION_PATH")
public class XmlArchiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlFileExportServiceImpl.class);

    public void zip(String source_dir, String zip_file) throws Exception
    {

        LOGGER.debug("zip({}, {})", source_dir, zip_file);

        FileOutputStream fout = new FileOutputStream(zip_file);
        ZipOutputStream zout = new ZipOutputStream(fout);

        File fileSource = new File(source_dir);

        addDirectory(zout, fileSource);

        // Close ZipOutputStream
        zout.close();

        System.out.println("Zip file has created!");
    }

    private void addDirectory(ZipOutputStream zout, File fileSource) throws Exception
    {

        File[] files = fileSource.listFiles();
        for(int i = 0; i < files.length; i++) {
            // If file is a directory, then call recursively
            // addDirectory method
            if(files[i].isDirectory()) {
                addDirectory(zout, files[i]);
                continue;
            }
            System.out.println("File has added <" + files[i].getName() + ">");

            FileInputStream fis = new FileInputStream(files[i]);

            zout.putNextEntry(new ZipEntry(files[i].getPath()));

            byte[] buffer = new byte[4048];
            int length;

            while((length = fis.read(buffer)) > 0)
                zout.write(buffer, 0, length);
            // Close ZipOutputStream and InputStream
            zout.closeEntry();
            fis.close();
        }
    }
}
