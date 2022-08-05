package com.mtn.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post{

    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private String title;
    private String body;

    @Override
    public String toString() {
        return "Post{" +
                "  userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                "}";
    }
}
