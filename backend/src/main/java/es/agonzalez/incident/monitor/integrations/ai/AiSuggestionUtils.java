package es.agonzalez.incident.monitor.integrations.ai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import es.agonzalez.incident.monitor.integrations.ai.dtos.SuggestResponse;


public class AiSuggestionUtils {

     // Parseo naive: intenta extraer ["..."] y reasoning "..."
  private static final Pattern SUGGESTIONS_ARRAY =
      Pattern.compile("\"suggestions\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
  private static final Pattern REASONING =
      Pattern.compile("\"reasoning\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);

  public static SuggestResponse tryParseJson(String text){
    try{
      var sugMatcher = SUGGESTIONS_ARRAY.matcher(text);
      if(!sugMatcher.find()) return null;

      var arr = sugMatcher.group(1);
      var items = new ArrayList<String>();
      // divide por comas fuera de comillas: simplificado suponiendo elementos simples
      var token = new StringBuilder();
      boolean inQuotes = false;
      for (int i=0;i<arr.length();i++){
        char ch = arr.charAt(i);
        if (ch=='"') inQuotes = !inQuotes;
        if (ch==',' && !inQuotes){
          items.add(clean(token.toString()));
          token.setLength(0);
        } else {
          token.append(ch);
        }
      }
      if (token.length()>0) items.add(clean(token.toString()));
      items.removeIf(s -> s.isBlank());

      String reasoning = null;
      Matcher r = REASONING.matcher(text);
      if (r.find()) reasoning = unescape(r.group(1));

      if (items.isEmpty()) return null;
      return new SuggestResponse(items, reasoning);
    }catch(Exception ignore){
      return null;
    }
  }

  private static String clean(String s){
    s = s.trim();
    if (s.startsWith("\"")) s = s.substring(1);
    if (s.endsWith("\"")) s = s.substring(0, s.length()-1);
    return unescape(s);
  }
  private static String unescape(String s){
    return s.replace("\\n"," ").replace("\\\"", "\"").trim();
  }
}