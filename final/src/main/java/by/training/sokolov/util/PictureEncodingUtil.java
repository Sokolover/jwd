package by.training.sokolov.util;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static by.training.sokolov.core.constants.CommonAppConstants.TMP_DIR;
import static java.util.Objects.isNull;

public final class PictureEncodingUtil {

    public static String getPictureEncoded(Part picture) throws IOException {

        String fileName = getFileName(picture);
        if (isNull(fileName) || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }

        String filePath = TMP_DIR + File.separator + fileName;
        picture.write(filePath);

        File file = new File(filePath);

//        String mimetype= new MimetypesFileTypeMap().getContentType(file);
//        String type = mimetype.split("/")[0];
//        if(type.equals("image"))
//            System.out.println("It's an image");
//        else
//            System.out.println("It's NOT an image");


        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private static String getFileName(Part part) {

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
