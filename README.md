# Pomodoro óra

A Pomodoro timer in Java Swing: 25 minutes work, 5 minutes break,
and a long break after every 4th round. The times can be changed in the settings.

The code is split into three packages (MVC):

- `model`: the logic, no Swing in it
- `view`: the window and the settings dialog
- `controller`: the button listeners and the timer

The model notifies the window through the `PomodoroFigyelo` interface (Observer pattern).

## How to run

In NetBeans:

- **Build:** Shift+F11
- **Run:** F6
- **Tests:** Alt+F6

Needs JDK 17 or newer.

## How to use

- **Start**: starts or continues the timer
- **Szunet**: pauses it
- **Reset**: back to 25:00
- **Beallitasok**: change the work and break times

Colors: red = work, green = short break, blue = long break, gray = paused.


## Screenshots

Work:

<img width="600"  alt="image" src="https://github.com/user-attachments/assets/b2aff0d4-773d-4abe-ab4e-d0b0b128455d" />


Break:

<img width="600"  alt="image" src="https://github.com/user-attachments/assets/30c67f16-8e29-4115-b275-447a7191a074" />


Settings:

<img width="321" height="194" alt="image" src="https://github.com/user-attachments/assets/eb9f8482-b7de-4f2e-a03d-fba12cd472d3" />
