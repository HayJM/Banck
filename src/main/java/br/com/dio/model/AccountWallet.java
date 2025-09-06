package br.com.dio.model;
//import java.util.Collection;
import java.util.List;
import static br.com.dio.model.BanckService.ACCOUNT;

public class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }
    public AccountWallet( final long amount, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount,"Valor de abertura da conta");
    }

    public void addMoney(final long amount, final String description){
        var money = generateMoney(amount,description);
        this.money.addAll(money);
    }
    public List<String> getPix() {
        return pix;
    } 

}
