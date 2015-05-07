package Classess;
public class Cashier extends Pegawai{

   public void setBillPaymentStatus(Bill b,boolean payStat) {
	   b.paymentStatus=payStat;
   }
   

   public void setBillAmount(Bill b,int amo) {
      b.billAmount=amo;
   }
   

   public void printBill(Bill b) {
	   System.out.println();
   }
}