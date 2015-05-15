package Model;
public class User {
   private String name;
   private String address;
   private String phoneNumber;
   private String eMail;
   private String id;
   private String password;
   private String gender;
   private int notification;
   
   public User() {
	   this.name=null;
	   this.address=null;
	   this.phoneNumber=null;
	   this.eMail=null;
	   this.id=null;
	   this.password=null;
           this.gender=null;
           this.notification=0;
   }

   public String getName() {
	   return name;
   }

   public void setName(String name) {
	   this.name = name;
   }

   public String getAddress() {
	   return address;
   }

   public void setAddress(String address) {
	   this.address = address;
   }

   public String getPhoneNumber() {
	   return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
	   this.phoneNumber = phoneNumber;
   }

   public String geteMail() {
	   return eMail;
   }

   public void seteMail(String eMail) {
	   this.eMail = eMail;
   }

   public String getId() {
	   return id;
   }

   public void setId(String id) {
	   this.id = id;
   }

   public String getPassword() {
	   return password;
   }

    public void setPassword(String password) {
	this.password = password;
    }
    
    public void setGender(String gender){
        this.gender=gender;
    }
    public String getGender(){
        return this.gender;
    }

}