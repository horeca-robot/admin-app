package edu.fontys.horecarobot.adminappbackend.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    private String BasePath;
    private final String DirectoryName = "temp_images_folder_can_be_deleted";

    public ImageService()
    {
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();

        BasePath = fileSystemView.getHomeDirectory().getAbsolutePath() + File.separator + DirectoryName;

        File dir = new File(BasePath);

        try {
            if(!dir.exists())
                dir.mkdir();

            BasePath += File.separator;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public byte[] getBase64FromDisk(String imageName) {

        String imagePath = String.format("%s%s", BasePath, imageName);

        try {
            File imageFile = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            DataBufferByte imageBytes = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
            return imageBytes.getData();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String saveBase64ToDisk(String base64) {

        String imageExtension = getExtension(base64.split(",")[0]);

        String imageName = String.format("%s.%s", UUID.randomUUID(),imageExtension);

        String imageData = base64.substring(base64.indexOf(',') + 1);

        try{
            InputStream imageInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(imageData.getBytes(StandardCharsets.UTF_8)));

            OutputStream imageOutputStream = new BufferedOutputStream(new FileOutputStream(String.format("%s%s", BasePath, imageName)));

            imageOutputStream.write(imageInputStream.readAllBytes());

            imageInputStream.close();
            imageInputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return imageName;
    }

    public boolean deleteFromDisk(String imageName) {
        try{
            File image = new File(String.format("%s%s",BasePath, imageName));

            return image.delete();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private String getExtension(String base64Header) {
        switch (base64Header) {
            case "data:image/jpeg;base64":
                return ".jpeg";
            case "data:image/png;base64":
                return "png";
            default:
                return ".unknown";
        }
    }

}
