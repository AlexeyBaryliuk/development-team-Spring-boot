package com.epam.brest.courses.service.xml;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@PropertySource("classpath:xml.properties")
@SuppressFBWarnings(value = "OS_OPEN_STREAM_EXCEPTION_PATH")
public class ArchiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveService.class);

    private static final String SLASH_BACK      = "/";

    @Value("${path_to_unzipFolder}")
    private String pathToProjectsUnzipFolder;

    @Autowired
    private CheckFolder checkFolder;


    public String unzip(MultipartFile multipartFile) throws Exception
    {
        File file = convertMultiPartToFile(multipartFile);

        LOGGER.debug("++++++++++++++++++++++++ Bytes = {}", file.length());
        String pathToFile = "";

        ZipFile zipFile = new ZipFile(file);
        LOGGER.debug("++++++++++++++++++++++++ Zip = {}", zipFile.getEntries());
        Enumeration<?> entries = zipFile.getEntries();
        LOGGER.debug("++++++++++++++++ = {}", entries.hasMoreElements());

        while (entries.hasMoreElements()) {
            org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry) entries.nextElement();
            LOGGER.debug("********************* = {}" , entry.getSize());
            String entryName = entry.getName();

            if (entryName.endsWith(SLASH_BACK)) {
                LOGGER.debug("Create the directory <{}>", entryName );
                checkFolder.createFolder (entryName);
                continue;
            } else
                checkFolder.checkFolder(entryName, pathToProjectsUnzipFolder);
            LOGGER.debug("Reading the file < {} >" , entryName );

            InputStream fis = zipFile.getInputStream(entry);
            LOGGER.debug("/////////////////fis.available() = {} ", fis.available());

            pathToFile = pathToProjectsUnzipFolder + entryName;
            FileOutputStream fos = new FileOutputStream(pathToFile);

            copyData(fis,fos);

            fis.close();
            fos.close();
        }
        zipFile.close() ;
        LOGGER.debug("Zip file has unzipped!");

        return pathToFile;
    }

    private static File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = File.createTempFile("projects_zip", ".zip");

        convFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        convFile.deleteOnExit();
        LOGGER.debug("++++++++++++++++++++++++ Bytes = {}", convFile.length());
        return convFile;
    }

    private static void copyData(InputStream in, OutputStream out) throws Exception {
        int b;
        while ((b = in.read()) > 0) {

            out.write(b);

        }
    }

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
