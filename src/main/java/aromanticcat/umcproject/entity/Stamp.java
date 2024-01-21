package aromanticcat.umcproject.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Table(name = "stamp")
public class Stamp extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stamp_id;

    @NotNull
    private String image_url;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "stamp")
    private List<Letter> letters;
}