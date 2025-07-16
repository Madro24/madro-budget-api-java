package madro.finances.budget.entities;

import jakarta.persistence.*;

@Entity
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String netEffect; // "expense" or "income"

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetEffect() {
        return netEffect;
    }

    public void setNetEffect(String netEffect) {
        this.netEffect = netEffect;
    }
}