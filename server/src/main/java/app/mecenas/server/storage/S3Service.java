
package app.mecenas.server.storage;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials; import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region; import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner; import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import java.net.URL; import java.time.Duration;
@Service public class S3Service {
  private final String bucket; private final S3Presigner p;
  public S3Service(@Value("${aws.s3.bucket}") String b, @Value("${aws.s3.region}") String r, @Value("${aws.s3.accessKey}") String ak, @Value("${aws.s3.secretKey}") String sk){
    bucket=b; p=S3Presigner.builder().region(Region.of(r)).credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ak,sk))).build();
  }
  public URL presignPut(String key, String contentType){ var put=PutObjectRequest.builder().bucket(bucket).key(key).contentType(contentType).build(); var req=PutObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(5)).putObjectRequest(put).build(); return p.presignPutObject(req).url(); }
}
