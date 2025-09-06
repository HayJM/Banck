package br.com.dio.repository;

import br.com.dio.exception.AccountWithInvestmentException;
import br.com.dio.exception.NotFundsEnoughException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.exception.InvestimentNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;
//import static br.com.dio.model.BanckService.INVESTMENT;
import java.util.List;
import java.util.ArrayList;

public final class InvestmentRepository {

    private long nextId = 0;
    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();

    public Investment create(final long tax, final long initialFounds){
        this.nextId++;        
        var investment = new Investment(this.nextId, tax, initialFounds);
        investments.add(investment);
        return investment;
        
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id){
        if(!wallets.isEmpty()){
            var accountInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
            if (accountInUse.contains(account)){
                throw new AccountWithInvestmentException("A conta com pix '"+ account.getPix() +"' já possui um investimento ativo!");
            }    
        }
        var investment = findById(id);        
        checkFundsForTransaction(account, investment.initialFounds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFounds());
        wallets.add(wallet);
        return wallet;
    }

    public InvestmentWallet deposit( final String pix, final long founds){
        var wallet = findWalletByAccountPix(pix); 
        wallet.addMoney(wallet.getAccount().reduceMoney(founds),wallet.getService(), "Aplicação em investimento");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds){
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet.getAccount(), funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds),wallet.getService(), "Resgate de investimento");
        if(wallet.getFunds() == 0){
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount(final long percent){
        wallets.forEach( w -> w.upDateAmount(percent));
    }

    public Investment findById(final long id) {
        return this.investments.stream()
        .filter(i -> i.id() == id)
        .findFirst()
        .orElseThrow(()-> new InvestimentNotFoundException("Investimento com id "+id+" não encontrado!"));
    }

    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return this.wallets.stream()
        .filter(w -> w.getAccount().getPix().contains(pix))
        .findFirst()
        .orElseThrow(()-> new WalletNotFoundException("Carteira de investimento não encontrada para a conta com pix "+pix+"!"));
    }
    
    public List<InvestmentWallet> listWallets(){
        return this.wallets;
    }

    public List<Investment> list(){
        return this.investments;
    }
    
    public static void checkFundsForTransaction(final AccountWallet source, final long amount) {
        if(source.getFunds() < amount) {
            throw new NotFundsEnoughException("Sua conta não possui saldo suficiente para realizar essa transação!");   
        }
    }
   
}
