package ru.itis.nasibullin.cloudphoto.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Arguments {
    private String path;
    private String album;
    private String photo;
}
