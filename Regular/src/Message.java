import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.regex.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import java.util.*;

public class Message {
	private String message;
    private Pattern expression;
    private String pattern = "[0-2][0-9]{3}-[0-3][0-9]-[01][0-9][\\s+][0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
    private String patternForDate = "yyyy-MM-dd HH:mm:ss";
    private Date date;
    private String messageSource;
    private String description;
    
    private HashMap<String, MessageImportance> symbols = new HashMap<String, MessageImportance>();
    private String messageCharacters;
    public MessageImportance Importance;

    public void Initialize()
    {
        this.symbols.put(".", MessageImportance.Low);
        this.symbols.put("!", MessageImportance.Medium);
        this.symbols.put("!!!", MessageImportance.High);
        this.symbols.put("!!!!!", MessageImportance.VeryHigh);
        this.getDate();
        this.getCharacters();
        this.getSource();
        this.getMessage();
    }

    public Message(String inputMessage)
    {
        if (inputMessage!=null)
            this.message = inputMessage;
        else
            throw new NullPointerException("Message is Empty! Check message");
        this.expression = Pattern.compile(pattern);
        this.Initialize();
    }

    public String getDate()
    {
    	Date date = null;
    	SimpleDateFormat df = null;
        if (expression!=null){
            Matcher match = expression.matcher(message);
            if (match.find()){
	            	String firstMatch = match.group();	
	                if (firstMatch!=null){
	                	df = new SimpleDateFormat(patternForDate);

	                    try {
	                        date = df.parse(firstMatch);
	                        this.date = date;
	                    } catch (ParseException e) {
	                        throw new RuntimeException("Failed to parse date: ", e);
	                    }
	                }
            }
            else
                throw new RuntimeException("Date in Message do not satisfy yyyy-MM-dd HH:mm:ss pattern!");
        }
        return df.format(this.date);
    }

    public String getCharacters()
    {
        String importance = message.substring(patternForDate.length() + 1).split("\\s")[0].trim();
        if (importance!=null && symbols.containsKey(importance))
        {
            this.Importance = symbols.get(importance);
            this.messageCharacters = importance;
        }
        else throw new RuntimeException("Message doesn't contains priority level");
        return messageCharacters;
    }

    public String getSource(){
    	String from = message.substring(patternForDate.length() + messageCharacters.length() + 2).split("\\s")[0].trim();
        if (from!=null && !from.isEmpty())
            this.messageSource = from;
        else throw new RuntimeException("Message doesn't contains valid source or has incorrect format");
       return messageSource;
    }

    public String getMessage(){
        String messageBody = message.substring(patternForDate.length() + messageCharacters.length() + messageSource.length() + 3);
        if (messageBody!=null)
            this.description = messageBody;
        return description;
    }

    public enum MessageImportance{ 
        Low,
        Medium,
        High,
        VeryHigh
    }
}
