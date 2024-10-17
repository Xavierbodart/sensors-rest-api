package iba.proton.therapy.sensors.model.keyword;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "sensormonitoring", name = "KEYWORDS")
@Data
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

}