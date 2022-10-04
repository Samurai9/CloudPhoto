package ru.itis.nasibullin.cloudphoto.results;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public class ListImagesResult implements Result<List<S3ObjectSummary>> {
    private final List<S3ObjectSummary> images;

    public ListImagesResult(List<S3ObjectSummary> images) {
        this.images = images;
    }

    @Override
    public List<S3ObjectSummary> getResult() {
        return images;
    }
}
