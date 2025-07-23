package skrobman.dev.springstart.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmailRateLimiterService {
    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    public boolean canSendEmail(String userEmail) {
        Bucket bucket = bucketCache.computeIfAbsent(userEmail, this::newBucket);
        return bucket.tryConsume(1);
    }

    private Bucket newBucket(String userEmail) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofHours(1))))
                .addLimit(Bandwidth.classic(1, Refill.greedy(1, Duration.ofMinutes(1))))
                .build();
    }
}
