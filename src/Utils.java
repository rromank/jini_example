import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getIpAddress(String input) {
        Pattern pattern = Pattern.compile("(\\d+.\\d+.\\d+.\\d+:\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}