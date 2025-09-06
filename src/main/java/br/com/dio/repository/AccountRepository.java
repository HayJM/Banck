package br.com.dio.repository;
import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NotFundsEnoughException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.model.AccountWallet;
import java.util.List;
import java.util.ArrayList;


public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFounds){
        if(!accounts.isEmpty()){
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            
            for(var p : pix){
                if (pixInUse.contains(p)){
                    throw new PixInUseException("A chave pix '"+ p +"' já está em uso!");
                }
            }
        }
        var newAccount = new AccountWallet(initialFounds, pix);
        accounts.add(newAccount);
        return newAccount;

    }

    public void deposit(final String pix, final long foundsAmount){
        var target = findByPix(pix);
        target.addMoney(foundsAmount,"Depósito");
    }

    public long withdraw(final String pix, final long amount){
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount){
        var source = findByPix(sourcePix);
        var target = findByPix(targetPix);
        checkFundsForTransaction(source, amount);
        var message = "Transferência de '"+sourcePix+"' para '"+targetPix+"'";
        target.addMoney(amount, message);
        source.reduceMoney(amount);
    }
     
    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
        .filter(a -> a.getPix().contains(pix))
        .findFirst()
        .orElseThrow(()-> new AccountNotFoundException("A conta com a chave pix "+pix+" não encontrada!"));

    }

    public List<AccountWallet> list(){
        return accounts;
    }

    private void checkFundsForTransaction(AccountWallet account, long amount) {
        if (account.getFunds() < amount) {
            throw new NotFundsEnoughException("Saldo insuficiente para realizar a operação!");
        }
    }

}
