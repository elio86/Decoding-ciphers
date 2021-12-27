//affine decryption known key
import java.util.Scanner;
import java.util.Arrays;

public class ROTN {
  private String plaintext="";
  private int a;
  private int b;
  private int modInvA;
  private String ciphertext="";
  
  public ROTN(Scanner input){
    System.out.println("Input the plaintext");
    plaintext = input.nextLine().toLowerCase();
    System.out.println("Enter the key starting with the a value");
    a = input.nextInt();
    b = input.nextInt();
    this.ciphertext = encypher();
  }

  public ROTN(int a, int b, String plaintext){
    this.plaintext = plaintext;
    this.b = b;
    this.a = a;
    this.ciphertext = this.encypher();
  }

  public ROTN(String ciphertext, int a, int b){
    this.decrypt();
  }

  public ROTN(String ciphertext){

    //oh yeah why not just use fucking chi squared idiot
    int[] coprimesMod26 = {1,3,5,7,9,11,15,17,19,21,23,25};
    int[] inversesMod26 = {1,9,21,15,3,19,7,23,11,5,17,25};
    char letter = 'a';
    //tests all combos of the values (all 312)
    String[] matches = new String[3];
    double[] chiValues = new double[3];
    chiValues[0]=99999999;
    chiValues[1]=99999999;
    chiValues[2]=99999999;
    for(int i = 0; i<12; i++){
      modInvA = inversesMod26[i];
      a = coprimesMod26[i];
      
      for(int j = 0; j<26; j++){
        b = j;
        for(int k = 0; k<ciphertext.length(); k++){
          if(!(78<=(int)ciphertext.charAt(k)&&(int)ciphertext.charAt(k)<=122)){
            letter = ciphertext.charAt(k);
          }else{
            letter = (char)(modInvA*((int)(ciphertext.charAt(k))-b)%26+(int)('a'));
          }
          plaintext+=letter;
        }
        //System.out.println(plaintext+" "+a+" "+ (b+7)%26);  //this is where to add the chi squared and print the top 3 results 
        
        double chi = ChiSquared.calculate(plaintext);
        if(chi<chiValues[0]){
          matches[2]=matches[1];
          chiValues[2]=chiValues[1];
          matches[1]=matches[0];
          chiValues[1]=chiValues[0];
          matches[0]=plaintext;
          chiValues[0]=chi;
        }else if(chi<chiValues[1]){
          matches[2]=matches[1];
          chiValues[2]=chiValues[1];
          matches[1]=plaintext;
          chiValues[1]=chi;
        }else if(chi<chiValues[2]){
          matches[2]=plaintext;
          chiValues[2]=chi;
        }
        plaintext = "";
      }
    }
    System.out.println(matches[0]);
  }

  public String encypher(){
    char letter ='a';
    //plugs into the formula for encryption
    for(int i=0; i<plaintext.length(); i++){
      if(!(78<=(int)plaintext.charAt(i)&&(int)plaintext.charAt(i)<=122)){
        letter = plaintext.charAt(i);
      }else{
        letter = ((char)(((a*((int)plaintext.charAt(i)-(int)('a'))+b)%26)+(int)('a')));
      }
      ciphertext+=letter;
    }
    return ciphertext;
  }
  //functions for changing the object to encrypt or decrypt other text
  public void encryptOther(String plain){
    this.plaintext = plain;
  }
  public void decryptOther(String cipher){
    this.ciphertext = cipher;
  }
  public void changeKey(int a, int b){
    this.a = a;
    this.b = b;
  }

  public void decrypt(){
    char letter = 'a';
    int[] coprimesMod26 = {1,3,5,7,9,11,15,17,19,21,23,25};
    int[] inversesMod26 = {1,9,21,15,3,19,7,23,11,5,17,25};

    //calcs inverse mod 26 of a
    int modInvA = inversesMod26[Arrays.binarySearch(coprimesMod26,a)];
    for(int i = 0; i<ciphertext.length(); i++){
      if(!(78<=(int)ciphertext.charAt(i)&&(int)ciphertext.charAt(i)<=122)){
        letter = ciphertext.charAt(i);
      }else{
        letter = (char)((modInvA*((int)(ciphertext.charAt(i))-8-(int)('a'))+26*25)%26+(int)('a'));
      }
      plaintext+=letter;
    }
  }
  //returns the ciphertext
  public String cipher(){
    return ciphertext;
  }
  //returns primary information about the cipher including key, plain and ciphertext
  public String toString(){
    return "Plaintext: "+plaintext+" Key: "+a+" , "+b+" Ciphertext: "+ ciphertext;
  }
}