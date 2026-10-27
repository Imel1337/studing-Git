package ru.lab.ritm;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.time.*;

public abstract class BaseActivity extends Activity {
  protected final int GREEN = Color.rgb(37, 99, 75),
      INK = Color.rgb(28, 46, 38),
      MUTED = Color.rgb(105, 118, 109),
      BG = Color.rgb(246, 245, 240),
      LIGHT = Color.rgb(226, 237, 224);
  protected HabitRepository repo;
  protected LinearLayout body, root;
  private int fieldIndex = 0;
  private boolean firstResume = true;

  protected abstract void render();

  @Override
  public void onCreate(Bundle b) {
    super.onCreate(b);
    repo = new HabitRepository(this);
    render();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (firstResume) {
      firstResume = false;
      return;
    }
    if (this instanceof Screens.Editor
        || this instanceof Screens.ProfileEdit
        || this instanceof Screens.Settings) return;
    repo = new HabitRepository(this);
    fieldIndex = 0;
    render();
  }

  protected int dp(float n) {
    return (int) (n * getResources().getDisplayMetrics().density + .5f);
  }

  protected GradientDrawable bg(int color, int radius) {
    GradientDrawable g = new GradientDrawable();
    g.setColor(color);
    g.setCornerRadius(dp(radius));
    return g;
  }

  protected void page(String eyebrow, String title, String subtitle, boolean navigation) {
    root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setBackgroundColor(BG);
    root.setFitsSystemWindows(true);
    setContentView(root);
    root.setOnApplyWindowInsetsListener(
        (v, i) -> {
          if (android.os.Build.VERSION.SDK_INT >= 30) {
            android.graphics.Insets x =
                i.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.ime());
            v.setPadding(x.left, x.top, x.right, x.bottom);
          } else {
            v.setPadding(
                i.getSystemWindowInsetLeft(),
                i.getSystemWindowInsetTop(),
                i.getSystemWindowInsetRight(),
                i.getSystemWindowInsetBottom());
          }
          return i;
        });
    ScrollView scroll = new ScrollView(this);
    scroll.setFillViewport(true);
    root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
    body = new LinearLayout(this);
    body.setOrientation(LinearLayout.VERTICAL);
    body.setPadding(dp(24), dp(20), dp(24), dp(24));
    scroll.addView(body);
    if (!navigation && !(this instanceof Screens.Welcome))
      button(body, "‹  Назад", () -> finish(), false);
    text(body, eyebrow.toUpperCase(java.util.Locale.getDefault()), 11, GREEN, true);
    text(body, title, 32, INK, true);
    if (!subtitle.isEmpty()) text(body, subtitle, 15, MUTED, false);
    space(body, 16);
    if (navigation) {
      LinearLayout nav = new LinearLayout(this);
      nav.setPadding(dp(8), dp(6), dp(8), dp(6));
      root.addView(nav);
      String[] labels = {"Сегодня", "Привычки", "Прогресс", "Профиль"};
      Class<?>[] cls = {
        Screens.Today.class, Screens.Habits.class, Screens.Stats.class, Screens.Profile.class
      };
      for (int i = 0; i < 4; i++) {
        final Class<?> target = cls[i];
        TextView t =
            text(nav, labels[i], 12, getClass() == target ? GREEN : MUTED, getClass() == target);
        t.setGravity(Gravity.CENTER);
        t.setPadding(0, dp(14), 0, dp(14));
        t.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1));
        if (getClass() == target) t.setBackground(bg(LIGHT, 16));
        t.setOnClickListener(v -> go(target));
      }
    }
  }

  protected TextView text(LinearLayout parent, String value, int size, int color, boolean bold) {
    TextView t = new TextView(this);
    t.setText(value);
    t.setTextSize(size);
    t.setTextColor(color);
    t.setPadding(0, dp(5), 0, dp(5));
    if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    parent.addView(t);
    return t;
  }

  protected void space(LinearLayout p, int h) {
    View v = new View(this);
    p.addView(v, new LinearLayout.LayoutParams(1, dp(h)));
  }

  protected LinearLayout card(LinearLayout p) {
    LinearLayout c = new LinearLayout(this);
    c.setOrientation(LinearLayout.VERTICAL);
    c.setPadding(dp(18), dp(14), dp(18), dp(14));
    c.setBackground(bg(Color.WHITE, 22));
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
    lp.bottomMargin = dp(12);
    p.addView(c, lp);
    return c;
  }

  protected void button(LinearLayout p, String label, Runnable action, boolean primary) {
    Button b = new Button(this);
    b.setText(label);
    b.setTextSize(14);
    b.setAllCaps(false);
    b.setTextColor(primary ? Color.WHITE : GREEN);
    b.setBackground(bg(primary ? GREEN : LIGHT, 16));
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(50));
    lp.topMargin = dp(8);
    lp.bottomMargin = dp(6);
    p.addView(b, lp);
    b.setOnClickListener(v -> action.run());
  }

  protected EditText input(String label, String value, boolean multiline) {
    text(body, label, 13, INK, true);
    EditText e = new EditText(this);
    e.setId(fieldIndex++ == 0 ? R.id.form_first : R.id.form_second);
    e.setText(value);
    e.setTextSize(16);
    e.setTextColor(INK);
    e.setSingleLine(!multiline);
    e.setInputType(
        multiline
            ? android.text.InputType.TYPE_CLASS_TEXT
                | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
            : android.text.InputType.TYPE_CLASS_TEXT
                | android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    e.setPadding(dp(16), dp(12), dp(16), dp(12));
    e.setBackground(bg(Color.WHITE, 14));
    body.addView(e, new LinearLayout.LayoutParams(-1, multiline ? dp(100) : dp(54)));
    space(body, 10);
    return e;
  }

  protected void go(Class<?> c) {
    Intent i = new Intent(this, c);
    if (c == Screens.Today.class
        || c == Screens.Habits.class
        || c == Screens.Stats.class
        || c == Screens.Profile.class)
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    startActivity(i);
  }

  protected void detail(Habit h) {
    startActivity(new Intent(this, Screens.Detail.class).putExtra("id", h.id));
  }

  protected void edit(Habit h) {
    startActivity(new Intent(this, Screens.Editor.class).putExtra("id", h.id));
  }

  protected void toast(String s) {
    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
  }

  protected void empty(String title, String hint) {
    LinearLayout c = card(body);
    text(c, title, 20, INK, true);
    text(c, hint, 14, MUTED, false);
  }

  protected void habitCard(LinearLayout parent, Habit h, boolean toggle) {
    LinearLayout c = card(parent);
    text(c, h.category.toUpperCase(java.util.Locale.getDefault()), 10, GREEN, true);
    TextView title = text(c, h.title, 20, INK, true);
    title.setOnClickListener(v -> detail(h));
    text(c, "Серия: " + repo.streak(h) + " дн.  ·  Всего: " + h.days.size(), 12, MUTED, false);
    if (toggle)
      button(
          c,
          h.days.contains(LocalDate.now().toString())
              ? "✓  Выполнено · отменить"
              : "○  Отметить сегодня",
          () -> {
            repo.toggle(h);
            render();
          },
          !h.days.contains(LocalDate.now().toString()));
    button(c, "Подробнее  →", () -> detail(h), false);
  }
}
