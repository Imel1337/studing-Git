package ru.lab.ritm;

import android.content.*;
import java.time.*;
import java.util.*;
import org.json.*;

public class HabitRepository {
  private final SharedPreferences prefs;
  public final List<Habit> habits = new ArrayList<>();

  public HabitRepository(Context c) {
    prefs = c.getSharedPreferences("ritm", 0);
    try {
      JSONArray a = new JSONArray(prefs.getString("habits", "[]"));
      for (int i = 0; i < a.length(); i++) habits.add(Habit.from(a.getJSONObject(i)));
    } catch (JSONException e) {
      throw new IllegalStateException("Cannot read saved habits", e);
    }
  }

  public void save() {
    try {
      JSONArray a = new JSONArray();
      for (Habit h : habits) a.put(h.json());
      prefs.edit().putString("habits", a.toString()).apply();
    } catch (JSONException e) {
      throw new IllegalStateException(e);
    }
  }

  public Habit find(String id) {
    for (Habit h : habits) if (h.id.equals(id)) return h;
    return null;
  }

  public List<Habit> active() {
    List<Habit> a = new ArrayList<>();
    for (Habit h : habits) if (!h.archived) a.add(h);
    return a;
  }

  public void toggle(Habit h) {
    String d = LocalDate.now().toString();
    if (!h.days.remove(d)) h.days.add(d);
    save();
  }

  public int count(LocalDate d) {
    int n = 0;
    for (Habit h : habits) if (h.days.contains(d.toString())) n++;
    return n;
  }

  public int total() {
    int n = 0;
    for (Habit h : habits) n += h.days.size();
    return n;
  }

  public int streak(Habit h) {
    LocalDate d = LocalDate.now();
    if (!h.days.contains(d.toString())) d = d.minusDays(1);
    int n = 0;
    while (h.days.contains(d.toString())) {
      n++;
      d = d.minusDays(1);
    }
    return n;
  }

  public String pref(String key, String fallback) {
    return prefs.getString(key, fallback);
  }

  public void setPref(String key, String value) {
    prefs.edit().putString(key, value).apply();
  }

  public void seed() {
    if (!pref("started", "no").equals("yes")) {
      habits.add(
          new Habit("Читать 20 минут", "Развитие", "Одна глава — маленький шаг к большой идее."));
      habits.add(new Habit("Прогуляться", "Здоровье", "Выйти на свежий воздух на 20 минут."));
      habits.add(new Habit("Выпить стакан воды", "Здоровье", "Начать утро с заботы о себе."));
      save();
      setPref("started", "yes");
    }
  }
}
