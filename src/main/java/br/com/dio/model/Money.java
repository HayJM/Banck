package br.com.dio.model;

import java.util.ArrayList;
import java.util.List;

// Removendo Lombok temporariamente até configurar a dependência
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Getter;

@EqualsAndHashCode
@ToString
@Getter
public class Money {
    private final List<MoneyAudit> history = new ArrayList<>();

    public Money(final MoneyAudit history) {
        this.history.add(history);
        
    }

    public void addHistory(final MoneyAudit history){
        this.history.add(history);
    }

    // Getters manuais (substitutos temporários do Lombok)
    public List<MoneyAudit> getHistory() {
        return history;
    }

    // Métodos equals e hashCode manuais (substitutos do @EqualsAndHashCode)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return history.equals(money.history);
    }

    @Override
    public int hashCode() {
        return history.hashCode();
    }

    // Método toString manual (substituto do @ToString)
    @Override
    public String toString() {
        return "Money{history=" + history + "}";
    }

}
