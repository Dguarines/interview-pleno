package br.com.brainweb.interview.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

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
@Table("power_stats")
public class PowerStats implements Serializable {
	
	private static final long serialVersionUID = -7242772945931915110L;

	@Id
    @With
    @EqualsAndHashCode.Include
    protected UUID id;

    @NotNull
    @Max(32767)
    private Integer strength;

    @NotNull
    @Max(32767)
    private Integer agility;

    @NotNull
    @Max(32767)
    private Integer dexterity;

    @NotNull
    @Max(32767)
    private Integer intelligence;
    
    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
