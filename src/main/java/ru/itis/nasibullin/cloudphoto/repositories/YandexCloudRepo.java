package ru.itis.nasibullin.cloudphoto.repositories;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.SneakyThrows;
import ru.itis.nasibullin.cloudphoto.entities.Arguments;
import ru.itis.nasibullin.cloudphoto.entities.Config;
import ru.itis.nasibullin.cloudphoto.exceptions.NoImagesException;
import ru.itis.nasibullin.cloudphoto.utils.ConfigUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YandexCloudRepo {
    private final Config config;
    private final AmazonS3 storage;

    public YandexCloudRepo(final Config config) {
        this.config = config;
        ConfigUtils.validateConfig(config);
        storage = AmazonS3ClientBuilder.standard().withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey())
                        ))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net", config.getRegion()
                        )
                ).build();
    }

    public void create(String name, File file, boolean isPublic) {
        PutObjectRequest request = new PutObjectRequest(config.getBucket(), name, file);
        if (isPublic) {
//            AccessControlList acl = storage.getBucketAcl(config.getBucket());
//            SetBucketAclRequest setBucketAclRequest = ;
//            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//            request.setAccessControlList(acl);
            storage.setBucketAcl(new SetBucketAclRequest(config.getBucket(), CannedAccessControlList.PublicRead));
//            System.out.println(storage.getBucketAcl(config.getBucket()));
        }
        storage.putObject(request);
    }

    public List<S3ObjectSummary> getAllImages(Arguments args) {
        ListObjectsRequest request = new ListObjectsRequest();
        request.setBucketName(config.getBucket());
        request.setPrefix(args.getAlbum());
        List<S3ObjectSummary> images = storage.listObjects(request).getObjectSummaries();
        images.removeIf(image -> !image.getKey().contains(".jpg") && !image.getKey().contains(".jpeg"));
        return images;
    }

    public List<String> getAllAlbums() {
        ListObjectsRequest request = new ListObjectsRequest();
        request.setBucketName(config.getBucket());
        List<S3ObjectSummary> images = storage.listObjects(request).getObjectSummaries();
        images.removeIf(image -> !image.getKey().contains(".jpg") && !image.getKey().contains(".jpeg"));
        Set<String> albums = new HashSet<>();
        for (S3ObjectSummary image: images) {
            if (image.getKey().split("/").length > 1) {
                albums.add(image.getKey().split("/")[0]);
            }
        }
        return new ArrayList<>(albums);
    }
    public List<String> getDownloadImagesURL(Arguments arguments) {
        List<String> albums = getAllAlbums();
        List<String> urls = new ArrayList<>();
        for (String album: albums) {
            List<S3ObjectSummary> images = getAllImages(arguments);
            for (S3ObjectSummary image: images) {
                urls.add(storage.getUrl(config.getBucket(), image.getKey()).toString());
            }
        }
        return urls;
    }

    public String getDownloadURL(String name) {
        return storage.getUrl(config.getBucket(), name).toString();
    }

    @SneakyThrows
    public void downloadAllImage(Arguments arguments) {
        if (!Files.exists(Paths.get(arguments.getPath()))) {
            throw new FileNotFoundException("Cannot find folder " + arguments.getPath());
        }
        if (!Files.isDirectory(Paths.get(arguments.getPath()))) {
            throw new IllegalArgumentException("Not directory " + arguments.getPath());
        }
        for (S3ObjectSummary image: getAllImages(arguments)) {
            File saveFile = new File(arguments.getPath() + File.separator + image.getKey().split("/")[1]);

            GetObjectRequest request = new GetObjectRequest(config.getBucket(), image.getKey());
            S3Object response = storage.getObject(request);
            InputStream inputStream = response.getObjectContent();
            if (inputStream.available() == 0) {
                throw new NoImagesException("No image in " + arguments.getAlbum());
            }
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            try {
                int a;
                while ((a = inputStream.read()) != -1) {
                    fileOutputStream.write(a);
                }
                fileOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void delete(Arguments arguments) {
        DeleteObjectsRequest request = new DeleteObjectsRequest(config.getBucket());
        List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<>();
        if (arguments.getPhoto() == null) {
            for (S3ObjectSummary image: getAllImages(arguments)) {
                keys.add(new DeleteObjectsRequest.KeyVersion(image.getKey()));
            }
        } else {
            keys.add(new DeleteObjectsRequest.KeyVersion(arguments.getAlbum() + "/" + arguments.getPhoto()));
        }
        request.setKeys(keys);
        storage.deleteObjects(request);
    }
}
