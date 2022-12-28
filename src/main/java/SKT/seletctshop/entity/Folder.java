package SKT.seletctshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Folder extends  Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
