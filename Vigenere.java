//vigenere cipher encryption and object creation
//decryption will be it's own thing since it is more complicated
//imports
public class Vigenere{
  //class variables
  private String plaintext;
  private String ciphertext;
  private int[] key;

  public Vigenere(String plaintext, int[] key){
    this.key = key;
    this.plaintext = plaintext;
    this.ciphertext = encipher(plaintext,key);
  }
  
//consider plaintext and ciphertext -> key

  //add the thign here to make this workkkkkk good
  public Vigenere(String ciphertext){
    this.ciphertext = ciphertext;
    //use the decipher thing lol
    decipher();
  }

  public String encipher(String plain, int[] key){
    String cipher = "";
    char[] charsPlain = plain.toCharArray();
    int index = 0;
    for(int i: charsPlain){
      cipher+=(char)((i-((int)'a')+key[index])%26+(int)'a');
      index++;
    }
    return cipher;
  }

  public static int[] wordToKey(String word){
    int[] theKey = new int[word.length()];
    char[] theWordChar =word.toCharArray();
    for(int i=0; i<word.length(); i++){
      theKey[i]=(int)theWordChar[i]-(int)'a';
    }
    return theKey;
  }

  public double indexCoinc(int length){
    String testString = "";
    double numSum=0;
    double ICTotal=0;
    for(int i = 0; i<length; i++){
      for(int j = i; j<ciphertext.length(); j+=length){
        testString+=ciphertext.substring(j,j+1);
      }

      int[] frequencies = new int[26];
      for(int index = 0; index<testString.length(); index++){
        if(Character.isLetter(testString.charAt(index))){
          frequencies[(int)testString.charAt(index)-(int)'a']++;
        }
      }
      for(int lett = 0; lett<26; lett++){
        numSum+=frequencies[lett]*(frequencies[lett]-1);
      }
      ICTotal+=(numSum/(testString.length()*(testString.length()-1)));
      numSum=0;
      testString = "";
    }
    return ICTotal/length;
  }
  public int[] topChoices(int amount){
    double[] top = new double[5];
    int[] keylength = new int[5];
    for(int i = 0; i<amount; i++){
      for(int j = 0; j<5; j++)
      if(indexCoinc(i)>top[j]){
        for(int k = j; k<4; k++){
          keylength[k+1]=keylength[k];
          top[k+1]=top[k];
        }
        keylength[j]=i;
        top[j]=indexCoinc(i);
      }
    }
    return keylength;
  }

  public void decipher(){
    int length = topChoices(13)[0];
    String testString="";
    String testString1="";
    double minChi =10000;
    int bestGuess=0;
    int[] key = new int[length];
    for(int i = 0; i<length; i++){
      for(int j = i; j<ciphertext.length(); j+=length){
        testString+=ciphertext.substring(j,j+1);
      }
      for(int k = 0; k<26; k++){
        for(int j = 0; j<testString.length(); j++){
          testString1+=(char)(((int)(testString.charAt(j))-(int)'a'+k)%26+(int)'a');
        }
        if(ChiSquared.calculate(testString1)<minChi){
          bestGuess = k;
          minChi = ChiSquared.calculate(testString1);
        }
        testString1="";
      }
      key[i]=(26-bestGuess)%26;
      minChi = 10000;
      testString="";
      testString1="";
    }
    for(int i = 0; i<length; i++){
      this.key=key;
    }
    decipherFromKey();
  }
  public void decipherFromKey(){
    String plain="";
    for(int i = 0; i<ciphertext.length(); i++){
      plain+=(char)((((int)ciphertext.charAt(i)-(int)'a')+((26-key[i%key.length])%26))%26+(int)'a');
    }
    System.out.println(plain);
    keyToEnglish();
  }
  public void printPlaintext(){
    System.out.println(plaintext);
  }
  public void keyToEnglish(){
    for(int i = 0; i<key.length; i++){
      System.out.print((char)(key[i]+(int)'a')+" ");
    }
  }
}