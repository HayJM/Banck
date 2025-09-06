import java.util.stream.Collectors;
import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NotFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import java.util.List;
import static java.time.temporal.ChronoUnit.SECONDS;
import java.util.Scanner;

public class Main {
    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        var ok = true;
        
        System.out.println("Ola seja bem vindo ao banco DIO");
        do{
            System.out.println("Selecione a opção desejada:");
            System.out.println("1 - Criar Conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Fazer um investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferir entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Resgatar investimento");
            System.out.println("9 - Listar Contas");
            System.out.println("10 - Listar Investimentos");
            System.out.println("11 - Listar carteiras de Investimentos");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Historico da conta");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            var option = scanner.nextInt();
            switch(option){
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> creatWalletinvest();
                case 4 -> depositInAccount();    
                case 5 -> withdrawFromAccount();
                case 6 -> transferBetweenAccounts();
                case 7 -> creatWalletinvest() ;
                case 8 -> redeemInvestment();
                case 9 -> accountRepository.list().forEach(System.out::println);       
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12->{
                    investmentRepository.updateAmount(option);
                    System.out.println("Investimentos atualizados com sucesso!");
                }
                case 13 ->checkHistory();
                case 0->{
                    System.out.println("Obrigado por usar o banco DIO, até logo!");
                    System.exit(0);
                    ok = false;
                }
                default->{
                    System.out.println("Opção inválida, tente novamente!");
                }
            } 
        }while(ok);
        scanner.close();
                              
        }   

    static void createAccount(){
        System.out.println("Informe as chaves pix (separadas por ';')");
        var pixInput = List.of(scanner.next().split(";"));
        System.out.println("Informe o valor inicial de abertura da conta!");
        var initialFounds = scanner.nextLong();
        var wallet = accountRepository.create(pixInput, initialFounds);
        System.out.println("Conta criada com sucesso!\n" + wallet); 
        scanner.nextLine();

    }   
    
    static void createInvestment(){
        System.out.println("Informe a taxa de rendimento do investimento (em %)");
        var tax = scanner.nextInt();
        System.out.println("Informe o valor inicial do investimento!");
        var initialFounds = scanner.nextLong();
        var investment = investmentRepository.create(tax, initialFounds);        
        System.out.println("Investimento criado com sucesso!\n" + investment);
        scanner.nextLine();
    }   
    
    static void creatWalletinvest(){
        System.out.println("Informe a chave pix da conta :");
        var pix = scanner.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o id do investimento:");
        var investmentId = scanner.nextInt();
        var investmentWallet =  investmentRepository.initInvestment(account, investmentId);
        System.out.println("Carteira de investimento criada com sucesso!\n" + investmentWallet);
        scanner.nextLine();
        
    }

    
    static void depositInAccount(){
        System.out.println("Informe a chave pix da conta que receberá o depósito:");
        var pix = scanner.next();
        System.out.println("Informe o valor do depósito:");
        var amount = scanner.nextLong();
        try{
            accountRepository.deposit(pix, amount);
        }catch(AccountNotFoundException ex){
            System.out.println(ex.getMessage());
            
        }       
        System.out.println("Depósito realizado com sucesso!");
        scanner.nextLine();
    }
    
    static void withdrawFromAccount(){
        System.out.println("Informe a chave pix da conta que realizará o saque:");
        var pix = scanner.next();
        System.out.println("Informe o valor do saque:");
        var amount = scanner.nextLong();
        try{
            accountRepository.withdraw(pix, amount);
        }catch(AccountNotFoundException | NotFundsEnoughException ex){
            System.out.println(ex.getMessage());            
        }
        System.out.println("Saque realizado com sucesso!");
        scanner.nextLine();
    }
    
    static void transferBetweenAccounts(){
        System.out.println("Informe a chave pix da conta que realizará a transferência:");
        var sourcePix = scanner.next(); 
        System.out.println("Informe a chave pix da conta que receberá a transferência:");
        var targetPix = scanner.next();
        System.out.println("Informe o valor da transferência:");
        var amount = scanner.nextLong();
        try{
            accountRepository.transferMoney(sourcePix, targetPix, amount);
        }catch(AccountNotFoundException | NotFundsEnoughException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("Transferência realizada com sucesso!");
        scanner.nextLine();
        
    }
    static void initInvestment(){
        System.out.println("Informe a chave pix da conta para o investimento:");
        var pix = scanner.next();
        System.out.println("Informe o o valor que será investido:");
        var amount= scanner.nextLong();
        try{
            investmentRepository.deposit(pix, amount);
        }catch(AccountNotFoundException ex){
            System.out.println(ex.getMessage());
            
        }       
        System.out.println("Depósito realizado com sucesso!");
        scanner.nextLine();
    }
    
    static void redeemInvestment(){
        System.out.println("Informe a chave pix da conta para resgatar o investimento:");
        var pix = scanner.next();
        System.out.println("Informe o o valor que será resgatado:");
        var amount= scanner.nextLong();
        try{
            investmentRepository.withdraw(pix, amount);
        }catch(AccountNotFoundException | NotFundsEnoughException ex){
            System.out.println(ex.getMessage());
            
        }       
        System.out.println("Resgate realizado com sucesso!");
        scanner.nextLine();
    }

    static void checkHistory(){
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        var pix = scanner.next();
        AccountWallet wallet;
        try{
            wallet = accountRepository.findByPix(pix);
            var audit = wallet.getFinancialTransactions();
            var group = audit.stream()
                .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
            
        }catch(AccountNotFoundException ex){
            System.out.println(ex.getMessage());    
            scanner.nextLine();
        }
    }

}



