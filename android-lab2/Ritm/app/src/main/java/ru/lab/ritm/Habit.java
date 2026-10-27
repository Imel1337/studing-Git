package ru.lab.ritm;

import java.util.*;
import org.json.*;

public class Habit {
  public String id, title, category, note;
  public boolean archived;
  public Set<String> days = new TreeSet<>();

  public Habit(String title, String category, String note) {
    this.id = UUID.randomUUID().toString();
    this.title = title;
    this.category = category;
    this.note = note;
  }

  public JSONObject json() throws JSONException {
    JSONObject o = new JSONObject();
    o.put("id", id);
    o.put("title", title);
    o.put("category", category);
    o.put("note", note);
    o.put("archived", archived);
    o.put("days", new JSONArray(days));
    return o;
  }

  public static Habit from(JSONObject o) throws JSONException {
    Habit h = new Habit(o.getString("title"), o.getString("category"), o.optString("note"));
    h.id = o.getString("id");
    h.archived = o.optBoolean("archived");
    JSONArray a = o.optJSONArray("days");
    if (a != null) for (int i = 0; i < a.length(); i++) h.days.add(a.getString(i));
    return h;
  }
}
