package madro.finances.budget.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date start;
    private Date end;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Float planned;
    private Float expenses;
    private Float preBalance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public enum Status {
        ACTIVE,
        UNDER_REVIEW,
        CLOSED
    }

    // Getters and setters methods
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Float getPlanned() {
        return planned;
    }

    public void setPlanned(Float planned) {
        this.planned = planned;
    }

    public Float getExpenses() {
        return expenses;
    }

    public void setExpenses(Float expenses) {
        this.expenses = expenses;
    }

    public Float getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(Float preBalance) {
        this.preBalance = preBalance;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
