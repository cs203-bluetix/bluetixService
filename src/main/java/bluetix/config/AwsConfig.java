package bluetix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
//@EnableContextInstanceData
public class AwsConfig {

    @Value("${S3_ACCESS_KEY}")
    private String accessKey;
    @Value("${S3_SECRET_ACCESS_KEY}")
    private String secretAccessKey;
    @Value("${S3_REGION_STATIC}")
    private String region;

    @Bean
    public AmazonS3 s3Client() {
    	final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}