package iba.proton.therapy.sensors.model.signal;


import iba.proton.therapy.sensors.model.keyword.KeywordEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "sensormonitoring", name = "SIGNALS")
@Data
public class SignalEntity {

    @Id
    @Column(name = "node_id", nullable = false)
    private String nodeId;
    @Column(name = "sampling_interval")
    private Integer samplingInterval;
    @Enumerated(EnumType.STRING)
    @Column(name = "deadband_type")
    private DeadbandType deadbandType;
    @Column(name = "deadband_value")
    private Integer deadbandValue;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SIGNAL_KEYWORDS",
            joinColumns = @JoinColumn(name = "signal_node_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private Set<KeywordEntity> keywords = new HashSet<>();
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;
    @UpdateTimestamp
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

}
