package ru.lab.ritm;

import android.app.*;
import android.content.*;
import android.os.*;
import java.time.*;
import java.util.*;

/** Run only in debug: adb shell am instrument -w ru.lab.ritm/.SmokeTests */
public class SmokeTests extends Instrumentation {
  private int assertions = 0;

  private void check(boolean result, String message) {
    if (!result) throw new AssertionError(message);
    assertions++;
  }

  @Override
  public void onCreate(Bundle arguments) {
    super.onCreate(arguments);
    start();
  }

  @Override
  public void onStart() {
    Bundle result = new Bundle();
    Context ctx = getTargetContext();
    android.content.SharedPreferences prefs = ctx.getSharedPreferences("ritm", 0);
    Map<String, ?> backup = prefs.getAll();
    try {
      prefs.edit().clear().commit();
      HabitRepository r = new HabitRepository(ctx);
      check(r.habits.isEmpty(), "empty repository");
      r.seed();
      r.seed();
      check(r.habits.size() == 3, "seed idempotence");
      Habit h = new Habit("Тестовая привычка", "Развитие", "Тестовая заметка");
      r.habits.add(h);
      r.save();
      r = new HabitRepository(ctx);
      h = r.find(h.id);
      check(h != null && h.note.equals("Тестовая заметка"), "create survives reload");
      r.toggle(h);
      check(r.count(LocalDate.now()) == 1, "today count");
      check(r.streak(h) == 1, "streak today");
      r = new HabitRepository(ctx);
      h = r.find(h.id);
      check(h.days.contains(LocalDate.now().toString()), "completion persists");
      r.toggle(h);
      check(r.count(LocalDate.now()) == 0, "undo");
      h.days.add(LocalDate.now().minusDays(1).toString());
      h.days.add(LocalDate.now().minusDays(2).toString());
      check(r.streak(h) == 2, "yesterday streak");
      h.days.add(LocalDate.now().toString());
      check(r.streak(h) == 3, "consecutive days");
      h.title = "Изменённая привычка";
      h.archived = true;
      r.save();
      r = new HabitRepository(ctx);
      h = r.find(h.id);
      check(h.archived && h.title.equals("Изменённая привычка"), "edit and archive persist");
      check(r.active().size() == 3, "archived excluded");
      check(r.total() == 3, "archive retains history");
      h.archived = false;
      r.save();
      check(r.active().size() == 4, "restore");
      r.setPref("name", "Тестер");
      r.setPref("goal", "Проверить сохранение");
      r = new HabitRepository(ctx);
      check(r.pref("name", "").equals("Тестер"), "profile persists");
      Class<?>[] screens = {
        Screens.Today.class,
        Screens.Habits.class,
        Screens.Editor.class,
        Screens.Detail.class,
        Screens.Stats.class,
        Screens.History.class,
        Screens.Catalog.class,
        Screens.Profile.class,
        Screens.ProfileEdit.class,
        Screens.Settings.class,
        Screens.About.class,
        Screens.Archive.class
      };
      for (Class<?> screen : screens) {
        Intent intent =
            new Intent(ctx, screen).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("id", h.id);
        Activity a = startActivitySync(intent);
        waitForIdleSync();
        check(a != null, "activity " + screen.getSimpleName());
        runOnMainSync(a::finish);
        waitForIdleSync();
      }
      result.putString(
          "stream",
          "PASS: "
              + assertions
              + " assertions; persistence, streaks, archive and 12 Activity launches.\n");
    } catch (Throwable e) {
      result.putString("stream", "FAIL: " + android.util.Log.getStackTraceString(e));
      result.putString("failure", e.toString());
    } finally {
      android.content.SharedPreferences.Editor ed = prefs.edit().clear();
      for (Map.Entry<String, ?> e : backup.entrySet())
        ed.putString(e.getKey(), (String) e.getValue());
      ed.commit();
    }
    finish(result.containsKey("failure") ? Activity.RESULT_CANCELED : Activity.RESULT_OK, result);
  }
}
