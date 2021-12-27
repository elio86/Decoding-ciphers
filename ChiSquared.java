public class ChiSquared{
  public static double calculate(String plaintext){
    int[] frequencies = new int[26];
    for(int index = 0; index<plaintext.length(); index++){
      if(Character.isLetter(plaintext.charAt(index))){
        frequencies[(int)plaintext.charAt(index)-(int)'a']++;
      }
    }
    double chi=0;
    for(int letterTest = 0; letterTest<26; letterTest++){
      chi+=Math.pow((frequencies[letterTest]-NormalEnglish.frequencyOf(letterTest, plaintext.length())),2)/(NormalEnglish.frequencyOf(letterTest, plaintext.length()));
    }
    return chi;
  }
}