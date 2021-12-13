package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

@Entity
@NamedQuery(name = "Deck.deleteAllRows", query = "DELETE from Deck")
@Table(name = "deck")
public class Deck implements Serializable {

    @Id
    @Column(name = "deck_id", nullable = false, length = 12)
    private String deck_id;

    @Column(name = "created")
    private Long created;

    @Column(name = "completed")
    private Long completed;

    @Column(name = "finished")
    private boolean finished;

    public Deck() {
    }

    public Deck(String deck_id) {
        this.deck_id = deck_id;
        Date date = new Date();
        this.created = new Timestamp(date.getTime()).getTime();
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
