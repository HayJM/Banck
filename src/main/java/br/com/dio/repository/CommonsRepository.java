package br.com.dio.repository;
import br.com.dio.exception.NotFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import static br.com.dio.model.BanckService.ACCOUNT;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {
    
    public static void checkFoundsTransaction(final AccountWallet source,final long amount) {
        if(source.getFunds() < amount) {
            throw new NotFundsEnoughException("Sua conta não possui saldo suficiente para realizar essa transação!");
        } 
        
    }

    public static List<Money> generateMoney(final UUID transactinId, final long founds, final String descripion){
        var history = new MoneyAudit(transactinId, ACCOUNT, descripion,OffsetDateTime.now());
        return Stream.generate(()-> new Money(history)).limit(founds).toList();   
    
    }
}
