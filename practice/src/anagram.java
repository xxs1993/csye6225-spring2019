import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class anagram {

    public static void main(String[] args) throws IOException {
        System.out.println("Please input First String");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine().toLowerCase();
        System.out.println("Please input Second String");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String s2 = br2.readLine().toLowerCase();
        if (s1.length() != s2.length())
            return;
        char c1[]= s1.toCharArray();
        char c2[]=s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        if (Arrays.equals(c1, c2))
            System.out.println("it's anagram");
        return;
    }
}
