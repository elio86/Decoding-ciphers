//designed to return a normal value distribution for the english language
public class NormalEnglish{
  public static double frequencyOf(int letter, int length){
    double[] freqTable = {0.08167,0.01492,0.02782,0.04253,0.12702,0.02288,0.02015,0.06094,0.06966,0.00153,0.00772,0.04025,0.02406,0.06749,0.07507,0.01929,0.00095,0.05987,0.06327,0.09056,0.02758,0.00978,0.0236,0.0015,0.01974,0.00074};
    return freqTable[letter]*length;
  }
}