package bluetix.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {
	
	@Value("bluetixbucket")
	private String bucketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
    public String uploadFile(MultipartFile file, String fileName, String filePath) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileExt = getFileExtension(file.getOriginalFilename());
        if(fileName.equals("")) {
        	fileName = file.getOriginalFilename();
        } else {
            fileName += ("."+ fileExt);
        }
        
        if(fileExt.equals("jpg")) {
            fileObj = compressImage(fileObj, 0.85f, fileName);
        }
        
        String finalFileName = filePath + "/"+ fileName; //Without back slash
        s3Client.putObject(new PutObjectRequest(bucketName, finalFileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + finalFileName;
    }
    
    public File compressImage(File inputImageFile, float compressionQuality, String outputFileName) {
        try {
    	BufferedImage image = ImageIO.read(inputImageFile);

        File compressedImageFile = new File(outputFileName);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compressionQuality);
        }

        writer.write(null, new IIOImage(image, null, null), param);
        ios.close();
        writer.dispose();

        return compressedImageFile; // Return the compressed image file
        } catch (Exception e) {
        	System.out.println("Compressed image file not found");
        	return inputImageFile;
        }
    }

    
    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }
	
	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)){
			fos.write(file.getBytes());
		} catch (IOException e) {
			log.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
	}

}
