package bluetix.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/nft")
public class NFTController {
	
	@Value("${nft.metadata.path}")
	private String metadataPath;
	
	@GetMapping("/download/{eventName}/{fileName}") //download folder directory specified file
	public ResponseEntity<Resource> serveFile(@PathVariable String eventName, @PathVariable String fileName) {
	    try {
	    	String filePath = metadataPath + File.separator + eventName + File.separator + fileName;

	        if (Files.exists(Path.of(filePath))) {
	            System.out.println("File path exists: " + filePath);
	            Resource resource = new FileSystemResource(filePath);
	            
	            if (resource.exists()) {
	                return ResponseEntity
	                    .ok()
	                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                    .body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } else {
	            System.out.println("File path does not exist: " + filePath);
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Handle the exception
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PostMapping("/upload/{eventName}") //upload file with file.json and eventname
	public ResponseEntity<String> uploadMetadata(
	    @RequestParam("file") MultipartFile file, @PathVariable String eventName) {
	    try {
	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body("Please upload a file.");
            }
	        
	        String uploadDir = metadataPath + File.separator + eventName;

	        File directory = new File(uploadDir);
	        if (!directory.exists()) {
	        	System.out.println("Making dir " + uploadDir + "....");
	            directory.mkdirs();
	        }
	        
	        File destFile = new File(uploadDir + File.separator + File.separator + file.getOriginalFilename());
	        
            if (destFile.exists()) {
                file.transferTo(destFile);
                return ResponseEntity.ok("File overwritten successfully.");
            }

            file.transferTo(destFile);
            return ResponseEntity.ok("File uploaded successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
	    }
	}
	
	@GetMapping("/events/{eventName}") //lists all tickets in the event
    public ResponseEntity<List<String>> listFiles(@PathVariable String eventName) {
        try {
            String directoryPath = metadataPath + File.separator + eventName + File.separator;

            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("Directory does not exist: " + directoryPath);
                return ResponseEntity.notFound().build();
            }

            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                List<String> fileNames = new ArrayList<>();
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
                return ResponseEntity.ok(fileNames);
            } else {
                System.out.println("No files found in the directory: " + directoryPath);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
    @GetMapping("/{eventName}/{fileName}") //view content of JSON
    public ResponseEntity<String> displayJsonFileContents(@PathVariable String eventName, @PathVariable String fileName) {
        try {
            String filePath = metadataPath + File.separator + eventName+ File.separator + fileName;

            if (Files.exists(Paths.get(filePath))) {
                String fileContents = new String(Files.readAllBytes(Paths.get(filePath)));
                return ResponseEntity.ok(fileContents);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
