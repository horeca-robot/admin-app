package edu.fontys.horecarobot.adminappbackend.services;
import org.springframework.stereotype.Service;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import org.apache.commons.io.*;

@Service
public class ImageService {

    private String BasePath;

    public ImageService()
    {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();

        String directoryName = "temp_images_folder_can_be_deleted";
        BasePath = fileSystemView.getHomeDirectory().getAbsolutePath() + File.separator + directoryName;

        File dir = new File(BasePath);

        try {
            if(!dir.exists())
                dir.mkdir();

            BasePath += File.separator;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public String getBase64FromDisk(String imageName){
        String base64="";
        try{
            InputStream inputStream = new FileInputStream(String.format("%s%s", BasePath, imageName));

            byte[] imageBytes = IOUtils.toByteArray(inputStream);

            base64 = Base64.getEncoder().encodeToString(imageBytes);

            inputStream.close();

            return "data:image/png;base64,"+base64;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String saveBase64ToDisk(String base64) {

        String imageExtension = getExtension(base64.split(",")[0]);

        String imageName = String.format("%s%s", UUID.randomUUID(),imageExtension);

        String imageData = base64.substring(base64.indexOf(',') + 1);

        InputStream imageInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(imageData.getBytes(StandardCharsets.UTF_8)));

        OutputStream imageOutputStream = null;
        try {
            imageOutputStream = new BufferedOutputStream(new FileOutputStream(String.format("%s%s", BasePath, imageName)));
            imageOutputStream.write(imageInputStream.readAllBytes());

            imageInputStream.close();
            imageOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return imageName;
    }

    public void deleteFromDisk(String imageName) {
        String imagePath = String.format("%s%s",BasePath, imageName);

        try{
            Files.delete(Paths.get(imagePath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String getExtension(String base64Header) {
        switch (base64Header) {
            case "data:image/jpeg;base64":
                return ".jpeg";
            case "data:image/png;base64":
                return ".png";
            default:
                return ".unknown";
        }
    }

}
