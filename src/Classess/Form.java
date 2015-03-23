package Classess;
public class Form {

   private String id;
   private String date;
   private String customerName;
   private String companyName;

   public String getId() {
	return id;
   }

   public void setId(String id) {
		this.id = id;
   }

   public String getDate() {
		return date;
   }
	
   public void setDate(String date) {
		this.date = date;
   }
	
   public void setCusName(String name) {
	      this.customerName = name;
   }
   
   public String getCusName(){
	   return this.customerName;
   }

   public void setCompName(String compName) {
      this.companyName = compName;
   }
   
   public String getCompName(){
	   return this.companyName;
   }

}