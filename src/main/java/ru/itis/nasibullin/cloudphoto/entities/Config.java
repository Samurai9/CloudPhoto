package ru.itis.nasibullin.cloudphoto.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config {
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String region;
    private String endpoint;
}
