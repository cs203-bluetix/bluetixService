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

    @Value("AKIATZYGZYDHQ3KY6MB3")
    private String accessKey;
    @Value("atSecS1hdHSSx2lU1b5cdi/CxRiFiGarvuc2mtVJ")
    private String secretAccessKey;
    @Value("ap-southeast-2")
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