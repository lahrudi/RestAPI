package com.mtn.assessment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo{

    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private String title;
    private Boolean completed;

    @Override
    public String toString() {
        return "Todo{" +
                "  userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                "}";
    }
}
