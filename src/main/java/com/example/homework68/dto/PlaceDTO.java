package com.example.homework66.dto;

import com.example.homework66.entity.Place;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceDTO {
    private Integer id;
    private String name;
    private String imagePath;

    public static PlaceDTO from(Place place) {
        return builder()
                .id(place.getId())
                .name(place.getName())
                .imagePath(calcStoreImagePath(place))
                .build();
    }

    private static String calcStoreImagePath(Place place) {
        if (!place.getImage().isBlank()) {
            return place.getImage();
        }
        return String.format("/images/store%d.png", Math.abs(place.getName().hashCode() % 4));
    }
}
