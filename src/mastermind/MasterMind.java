package mastermind;
/* @author sumeyral

 */
import java.util.Random;
import java.util.Scanner;

public class MasterMind {
    public static void main(String [] args)
    {
        int chosen = Game.player1() ; // İlk oyuncu 1000-9999 arasında bir sayı seçer
        Scanner kb = new Scanner(System.in);
        System.out.println("Tahmin edilen sayı içerisinde belirlenen sayı içerisindeki rakamlardan biri var " +
                "fakat basamak değeri tutumuyorsa - işaretiyle basamak değeri tutuyorsa + işaretiyle gösterilir");

        for (int i = 0; i < 10; ++i) {
            System.out.print("Tahmin edilen sayı:");
            int guess=Integer.parseInt(kb.nextLine());
            int result = Game.player2(chosen,guess);
            if(result == 0)
                continue;
            else if(result == 1){
                System.out.printf("Belirlenen sayı:%d%n",chosen);
                break;}
            else
                continue;
        }
        System.out.printf("Belirlenen sayı:%d idi%n",chosen);
    }
}
class Game {
    public static int player1() {
        Random rand = new Random();
        int chosen;

        for(;;) {
            chosen = rand.nextInt(9000) + 1000;
            if (NumberUtil.isUniqDigits(chosen)) // Seçilen sayının rakamları farklı olmalıdır
                break;
        }
        return chosen;
    }
    public static int player2(int number, int guess) {
        int[] digits = NumberUtil.getDigits(number);
        if (NumberUtil.getDigitsCount(guess) != 4) {
            System.out.println("Hatalı giriş!4 basamaklı bir sayı giriniz:");
            return 0;
        }
        int[] gdigits = NumberUtil.getDigits(guess);
        boolean[] check = new boolean[4];
        int count = 0, i;

        for (i = 0; i < digits.length; ++i) {
            for (int k = 0; k < gdigits.length; ++k)
                if (gdigits[i] == digits[k])
                    count++;
        }
        int plus = 0;
        if (count != 0) {

            for (i = 0; i < digits.length; ++i)
                if (gdigits[i] == digits[i])
                    check[i] = true;
            for (i = 0; i < check.length; ++i)
                if (check[i])
                    plus++;
        }
        if (plus == 4) {
            System.out.println("Correct guess");
            return 1;
        } else {
            display(count, plus);
            return -1;
        }
    }
    private static void display(int count, int plus) {
        if (count == 0 && plus == 0)
            return;
        if (count == 1 && plus == 0)
            System.out.println("Verilecek bilgi:-");
        else if (count == 1 && plus == 1)
            System.out.println("Verilecek bilgi:+");
        else if (count == plus)
            System.out.printf("Verilecek bilgi:+%d%n", plus);
        else
            System.out.printf("Verilecek bilgi:+%d-%d%n", plus, count - plus);
    }

}
class NumberUtil{
    public static int [] getDigits(int val)
    {
        int n = getDigitsCount(val);
        val = Math.abs(val);
        int [] digits = new int[n];

        int index = n - 1;

        while (val != 0) {
            digits[index--] = val % 10;
            val /= 10;
        }

        return digits;
    }
    public static int getDigitsCount(int val)
    {

        if (val == 0)
            return 1;

        val = Math.abs(val);
        int count = 0;

        return (int)Math.log10(val) + 1;
    }
    public static boolean isUniqDigits(int val)
    {
        int [] digits = getDigits(val);

        for(int i=0; i< digits.length; ++i) {
            for (int j = 0; j < digits.length; ++j) {
                if (i == j)
                    continue;
                else if(digits[i] == digits[j])
                    return false;
            }
        }
        return true;
    }
}
