package ru.lab.ritm;

import android.app.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.widget.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/** Each nested public Activity is a separate functional screen registered in the manifest. */
public final class Screens {
  public static class Welcome extends BaseActivity {
    protected void render() {
      if (repo.pref("started", "no").equals("yes")) {
        go(Today.class);
        finish();
        return;
      }
      page(
          "Ритм / маленькие шаги",
          "Большие перемены\nначинаются с малого",
          "Твой спокойный помощник в формировании полезных привычек.",
          false);
      LinearLayout c = card(body);
      text(c, "↗", 72, GREEN, true);
      text(c, "Не идеально. Регулярно.", 24, INK, true);
      text(
          c,
          "Выбери привычку, сделай небольшой шаг и наблюдай за своим прогрессом.",
          16,
          MUTED,
          false);
      text(body, "01   Создавай свои привычки", 16, INK, true);
      text(body, "02   Отмечай каждый день", 16, INK, true);
      text(body, "03   Замечай свой рост", 16, INK, true);
      space(body, 20);
      button(
          body,
          "Начать свой ритм  →",
          () -> {
            repo.seed();
            go(Today.class);
            finish();
          },
          true);
      text(body, "Без регистрации. Все данные на устройстве.", 12, MUTED, false);
    }
  }

  public static class Today extends BaseActivity {
    protected void render() {
      String date =
          LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, d MMMM", new Locale("ru")));
      page(
          date,
          "Привет, " + repo.pref("name", "друг") + "!",
          repo.pref("focus", "Сегодня — ещё один маленький шаг вперёд."),
          true);
      List<Habit> active = repo.active();
      int done = 0;
      for (Habit h : active) if (h.days.contains(LocalDate.now().toString())) done++;
      LinearLayout c = card(body);
      c.setBackground(bg(LIGHT, 22));
      text(c, "ТВОЙ РИТМ СЕГОДНЯ", 11, GREEN, true);
      text(c, done + " / " + active.size(), 42, GREEN, true);
      text(
          c,
          active.isEmpty()
              ? "Добавь первую привычку"
              : done == active.size()
                  ? "Все привычки выполнены. Отличный день!"
                  : "Каждая отметка имеет значение.",
          14,
          GREEN,
          false);
      ProgressBar bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
      bar.setMax(Math.max(1, active.size()));
      bar.setProgress(done);
      c.addView(bar);
      space(body, 8);
      text(body, "План на сегодня", 22, INK, true);
      if (active.isEmpty())
        empty("Начни с малого", "Выбери идею из каталога или создай свою привычку.");
      for (Habit h : active) habitCard(body, h, true);
      button(body, "＋  Новая привычка", () -> go(Editor.class), true);
      button(body, "Найти вдохновение", () -> go(Catalog.class), false);
    }
  }

  public static class Habits extends BaseActivity {
    protected void render() {
      page("Моя коллекция", "Привычки", "То, чему ты выбираешь уделять время.", true);
      button(body, "＋  Добавить привычку", () -> go(Editor.class), true);
      EditText search = input("Поиск по названию или категории", "", false);
      LinearLayout list = new LinearLayout(this);
      list.setOrientation(LinearLayout.VERTICAL);
      body.addView(list);
      Runnable show =
          () -> {
            list.removeAllViews();
            String q = search.getText().toString().trim().toLowerCase(new Locale("ru"));
            int n = 0;
            for (Habit h : repo.active())
              if ((h.title + " " + h.category).toLowerCase(new Locale("ru")).contains(q)) {
                habitCard(list, h, false);
                n++;
              }
            if (n == 0)
              text(list, "Ничего не найдено. Измени запрос или добавь привычку.", 16, MUTED, false);
          };
      search.addTextChangedListener(
          new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}

            public void onTextChanged(CharSequence s, int st, int b, int c) {
              show.run();
            }

            public void afterTextChanged(Editable e) {}
          });
      show.run();
      button(body, "Каталог идей  →", () -> go(Catalog.class), false);
      button(body, "Архив привычек  →", () -> go(Archive.class), false);
    }
  }

  public static class Editor extends BaseActivity {
    protected void render() {
      Habit existing = repo.find(getIntent().getStringExtra("id"));
      page(
          "Небольшое обещание себе",
          existing == null ? "Новая привычка" : "Редактирование",
          "Сделай цель простой и выполнимой каждый день.",
          false);
      EditText title =
          input(
              "Название *",
              existing == null
                  ? getIntent().getStringExtra("title") == null
                      ? ""
                      : getIntent().getStringExtra("title")
                  : existing.title,
              false);
      title.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)});
      text(body, "Категория", 13, INK, true);
      Spinner category = new Spinner(this);
      category.setId(R.id.habit_category);
      String[] cats = {"Здоровье", "Развитие", "Спокойствие", "Порядок"};
      category.setAdapter(
          new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cats));
      body.addView(category, new LinearLayout.LayoutParams(-1, dp(52)));
      String cat = existing == null ? getIntent().getStringExtra("category") : existing.category;
      for (int i = 0; i < cats.length; i++) if (cats[i].equals(cat)) category.setSelection(i);
      EditText note = input("Заметка: как выполнить", existing == null ? "" : existing.note, true);
      note.setFilters(new InputFilter[] {new InputFilter.LengthFilter(500)});
      text(body, "Повторение: каждый день. Одна отметка в сутки.", 13, MUTED, false);
      button(
          body,
          "Сохранить привычку",
          () -> {
            String t = title.getText().toString().trim();
            if (t.length() < 2) {
              title.setError("Введи минимум 2 символа");
              title.requestFocus();
              return;
            }
            if (existing == null) {
              repo.habits.add(
                  new Habit(
                      t, category.getSelectedItem().toString(), note.getText().toString().trim()));
            } else {
              existing.title = t;
              existing.category = category.getSelectedItem().toString();
              existing.note = note.getText().toString().trim();
            }
            repo.save();
            toast("Привычка сохранена");
            finish();
          },
          true);
    }
  }

  public static class Detail extends BaseActivity {
    protected void render() {
      Habit h = repo.find(getIntent().getStringExtra("id"));
      if (h == null) {
        finish();
        return;
      }
      page(
          h.category,
          h.title,
          h.archived ? "Эта привычка в архиве" : "По одному маленькому шагу каждый день.",
          false);
      LinearLayout c = card(body);
      text(c, repo.streak(h) + " дней подряд", 30, GREEN, true);
      text(c, h.days.size() + " отметок за всё время", 15, MUTED, false);
      text(body, "Как выполнить", 20, INK, true);
      text(body, h.note.isEmpty() ? "Заметка пока не добавлена." : h.note, 16, MUTED, false);
      if (!h.archived)
        button(
            body,
            h.days.contains(LocalDate.now().toString())
                ? "✓ Выполнено сегодня · отменить"
                : "Отметить выполнение",
            () -> {
              repo.toggle(h);
              render();
            },
            true);
      button(body, "Редактировать", () -> edit(h), false);
      text(body, "Последние отметки", 20, INK, true);
      List<String> dates = new ArrayList<>(h.days);
      Collections.reverse(dates);
      if (dates.isEmpty()) text(body, "Здесь появится история твоих шагов.", 14, MUTED, false);
      for (int i = 0; i < Math.min(7, dates.size()); i++)
        text(
            body,
            "✓  " + LocalDate.parse(dates.get(i)).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            15,
            GREEN,
            false);
      button(
          body,
          h.archived ? "Восстановить из архива" : "Убрать в архив",
          () ->
              new AlertDialog.Builder(this)
                  .setTitle(h.archived ? "Восстановить привычку?" : "Убрать привычку в архив?")
                  .setMessage("Все отметки и история сохранятся.")
                  .setNegativeButton("Отмена", null)
                  .setPositiveButton(
                      "Продолжить",
                      (d, w) -> {
                        h.archived = !h.archived;
                        repo.save();
                        render();
                      })
                  .show(),
          false);
    }
  }

  public static class Stats extends BaseActivity {
    protected void render() {
      page("Твои маленькие победы", "Прогресс", "Сравнивай себя только с собой вчерашним.", true);
      LinearLayout c = card(body);
      text(c, String.valueOf(repo.total()), 48, GREEN, true);
      text(c, "выполнений за всё время", 15, MUTED, false);
      int best = 0;
      for (Habit h : repo.habits) best = Math.max(best, repo.streak(h));
      text(c, "Текущая лучшая серия: " + best + " дн.", 15, INK, true);
      text(body, "Последние 7 дней", 22, INK, true);
      for (int i = 6; i >= 0; i--) {
        LocalDate d = LocalDate.now().minusDays(i);
        LinearLayout row = card(body);
        int n = repo.count(d);
        text(
            row,
            d.format(DateTimeFormatter.ofPattern("EEE, dd.MM", new Locale("ru")))
                + "  ·  "
                + n
                + " выполнено",
            14,
            INK,
            true);
        ProgressBar b = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        b.setMax(Math.max(1, repo.habits.size()));
        b.setProgress(n);
        row.addView(b);
      }
      button(body, "Полная история  →", () -> go(History.class), true);
    }
  }

  public static class History extends BaseActivity {
    protected void render() {
      page("Дневник достижений", "История", "Все отметки, включая привычки из архива.", false);
      TreeSet<String> dates = new TreeSet<>(Collections.reverseOrder());
      for (Habit h : repo.habits) dates.addAll(h.days);
      if (dates.isEmpty()) empty("Первая победа впереди", "Отметь привычку на экране «Сегодня».");
      for (String d : dates) {
        text(
            body,
            LocalDate.parse(d).format(DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"))),
            18,
            INK,
            true);
        for (Habit h : repo.habits)
          if (h.days.contains(d)) {
            LinearLayout c = card(body);
            text(c, "✓  " + h.title, 16, GREEN, true);
            text(c, h.category + (h.archived ? " · в архиве" : ""), 12, MUTED, false);
            c.setOnClickListener(v -> detail(h));
          }
      }
    }
  }

  public static class Catalog extends BaseActivity {
    protected void render() {
      page("Начни с идеи", "Вдохновение", "Готовые идеи. Настрой любую под себя.", false);
      String[][] data = {
        {"Читать 20 минут", "Развитие", "Погрузись в новую историю или идею."},
        {"Прогуляться", "Здоровье", "Немного движения и свежего воздуха."},
        {"Записать 3 благодарности", "Спокойствие", "Заметь хорошее в обычном дне."},
        {"Убрать рабочий стол", "Порядок", "Освободи пространство для нового."},
        {"Выучить 5 слов", "Развитие", "Новый язык по несколько минут в день."},
        {"Сделать растяжку", "Здоровье", "Мягкая пауза для твоего тела."}
      };
      for (String[] item : data) {
        LinearLayout c = card(body);
        text(c, item[1].toUpperCase(java.util.Locale.getDefault()), 11, GREEN, true);
        text(c, item[0], 22, INK, true);
        text(c, item[2], 14, MUTED, false);
        button(
            c,
            "Выбрать привычку",
            () ->
                startActivity(
                    new Intent(this, Editor.class)
                        .putExtra("title", item[0])
                        .putExtra("category", item[1])),
            false);
      }
    }
  }

  public static class Profile extends BaseActivity {
    protected void render() {
      page("Личное пространство", "Мой профиль", "Твой ритм. Твои правила.", true);
      LinearLayout c = card(body);
      text(c, repo.pref("name", "Друг"), 30, GREEN, true);
      text(c, repo.pref("goal", "Стать немного лучше каждый день"), 16, MUTED, false);
      text(
          c,
          repo.active().size() + " активных привычек  ·  " + repo.total() + " выполнений",
          13,
          INK,
          true);
      button(body, "Изменить профиль", () -> go(ProfileEdit.class), true);
      button(body, "История достижений", () -> go(History.class), false);
      button(body, "Архив привычек", () -> go(Archive.class), false);
      button(body, "Настройки", () -> go(Settings.class), false);
      button(body, "О приложении", () -> go(About.class), false);
    }
  }

  public static class ProfileEdit extends BaseActivity {
    protected void render() {
      page("Знакомство", "Твой профиль", "Как к тебе обращаться и что тебя вдохновляет?", false);
      EditText name = input("Имя *", repo.pref("name", "Друг"), false);
      name.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
      EditText goal =
          input("Моя мотивация", repo.pref("goal", "Стать немного лучше каждый день"), true);
      goal.setFilters(new InputFilter[] {new InputFilter.LengthFilter(200)});
      button(
          body,
          "Сохранить профиль",
          () -> {
            String n = name.getText().toString().trim();
            if (n.isEmpty()) {
              name.setError("Введи имя");
              name.requestFocus();
              return;
            }
            repo.setPref("name", n);
            repo.setPref("goal", goal.getText().toString().trim());
            toast("Профиль сохранён");
            finish();
          },
          true);
    }
  }

  public static class Settings extends BaseActivity {
    protected void render() {
      page("Под себя", "Настройки", "Управляй данными и своим ежедневным фокусом.", false);
      EditText focus =
          input(
              "Фраза для ежедневной мотивации",
              repo.pref("focus", "Маленькие шаги тоже считаются"),
              true);
      focus.setFilters(new InputFilter[] {new InputFilter.LengthFilter(160)});
      button(
          body,
          "Сохранить фразу",
          () -> {
            String s = focus.getText().toString().trim();
            if (s.isEmpty()) {
              focus.setError("Напиши мотивирующую фразу");
              return;
            }
            repo.setPref("focus", s);
            toast("Фраза сохранена");
          },
          true);
      LinearLayout c = card(body);
      text(c, "Хранение данных", 20, INK, true);
      text(
          c,
          "Привычки, профиль и отметки сохраняются на этом устройстве. Интернет и регистрация не"
              + " нужны. Удаление приложения удалит его данные.",
          15,
          MUTED,
          false);
      button(body, "Посмотреть архив", () -> go(Archive.class), false);
      button(body, "О приложении", () -> go(About.class), false);
    }
  }

  public static class About extends BaseActivity {
    protected void render() {
      page(
          "Ритм / версия 1.0",
          "Маленькие шаги.\nБольшой смысл.",
          "Учебный проект · лабораторная работа №2",
          false);
      LinearLayout c = card(body);
      text(c, "Зачем нужен Ритм", 22, INK, true);
      text(
          c,
          "Приложение помогает формировать ежедневные привычки без лишнего давления. Создавай"
              + " простые цели, отмечай выполнение и наблюдай за прогрессом.",
          16,
          MUTED,
          false);
      text(body, "Как пользоваться", 22, INK, true);
      text(
          body,
          "1. Добавь привычку или выбери идею.\n\n"
              + "2. Выполни её и поставь отметку на главной.\n\n"
              + "3. Посмотри статистику и историю.\n\n"
              + "4. Ненужные привычки перенеси в архив — история останется.",
          16,
          MUTED,
          false);
      text(
          body,
          "Серия — последовательные дни выполнения, заканчивающиеся сегодня или вчера. Для каждой"
              + " привычки доступна одна отметка за день.",
          14,
          MUTED,
          false);
    }
  }

  public static class Archive extends BaseActivity {
    protected void render() {
      page("Можно вернуться позже", "Архив", "Отдых от привычки — тоже часть пути.", false);
      int n = 0;
      for (Habit h : repo.habits)
        if (h.archived) {
          habitCard(body, h, false);
          n++;
        }
      if (n == 0)
        empty(
            "В архиве пока пусто",
            "На странице привычки можно убрать её в архив, сохранив историю.");
    }
  }
}
