package br.com.brainweb.interview.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table("hero")
public class Hero implements Serializable {

	private static final long serialVersionUID = 8658440817578840730L;
	
	@Id
	@With
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @NotEmpty
    @Length(max = 255)
    private String name;

    @NotNull
    private Race race;

    @NotNull
    private UUID powerStatsId;
    
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
