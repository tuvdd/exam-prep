package com.project.exam_prep.dto;

import com.project.exam_prep.entity.QuestionImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionImageDto {
    private Integer id;
    private String name;
    private String imageUrl;

    public QuestionImageDto(QuestionImage questionImage) {
        if (questionImage != null) {
            this.id = questionImage.getId();
            this.name = questionImage.getName();
            this.imageUrl = questionImage.getUrl();
        }
    }
}
