package doctorhoai.learn.inventoryservice.model;

import doctorhoai.learn.inventoryservice.model.audit.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "source")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Source extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String link;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_by")
    private Integer createBy;

    // list
    @OneToMany(mappedBy = "sourceId")
    private List<HistoryImportOrExport> history;
}
