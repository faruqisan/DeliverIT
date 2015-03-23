package Classess;
public class Cashier extends Pegawai implements Operation {

   public void setBillPaymentStatus(Bill b,boolean payStat) {
	   b.paymentStatus=payStat;
   }
   

   public void setBillAmount(Bill b,int amo) {
      b.billAmount=amo;
   }
   

   public void printBill(Bill b) {
	   System.out.println();
   }


@Override
public void login() {
	// TODO Auto-generated method stub
	
}


@Override
public void logout() {
	// TODO Auto-generated method stub
	
}


@Override
public void changePassword() {
	// TODO Auto-generated method stub
	
}

}