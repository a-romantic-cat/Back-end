package aromanticcat.umcproject.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Table(name = "letter_paper")
public class LetterPaper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @OneToMany(mappedBy = "letterPaper")
    private List<Letter> letters;
}