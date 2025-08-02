package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import doctorhoai.learn.inventoryservice.model.enums.ETypeHistory;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "history_import_or_export")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryImportOrExport extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ETypeHistory type;

    private String note;

    @Column(name = "batch_code")
    private String bath_code;

    @JoinColumn(name = "source_id")
    @ManyToOne
    private Source sourceId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private List<HistoryIngredients> historyIngredients;

}
