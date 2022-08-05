package com.mtn.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment{

    @Id @GeneratedValue
    private Long id;
    private Long postId;
    private String name;
    private String email;
    @Column(columnDefinition = "CHARACTER VARYING(500)")
    private String body;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ",postId=" + postId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                "}";
    }
}
