package br.com.dio.model;
import lombok.Getter;
import lombok.ToString;
import static br.com.dio.model.BanckService.INVESTMENT;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;


@ToString
@Getter
public class InvestmentWallet extends Wallet{
    private final Investment investment;
    private final AccountWallet account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount),getService(),"Valor investido");
    }

    public void upDateAmount(final long porcent){
        var amount = (getFunds() * porcent) / 100;
        var history =  new MoneyAudit(UUID.randomUUID(), getService(),"Rendimentos",OffsetDateTime.now());
        var money = Stream.generate(()-> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
    public Investment getInvestment() {
        return investment;
    }
    
    

}
