package model.util;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;

public class SystemNotification {

    static {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

        if (os.startsWith("windows")) {
            SystemNotification.notifier = new WindowsNotifier();
        } else if (os.startsWith("mac") || os.startsWith("darwin")) {
            SystemNotification.notifier = new MacOSNotifier();
        } else if (os.startsWith("linux")) {
            SystemNotification.notifier = new LinuxNotifier();
        }
    }

    private static Notifier notifier;


    public static void sendNotification(String title, String message) {
        if (SystemNotification.notifier != null) {
            try {
                SystemNotification.notifier.sendNotification(title, message);
            } catch (Exception ignored) {
                System.err.println("Could not send notification to the system.");
            }
        }
    }

    private interface Notifier {
        void sendNotification(String title, String message);
    }

    private static class WindowsNotifier implements Notifier {
        private final TrayIcon trayIcon;

        private WindowsNotifier() {
            SystemTray tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            this.trayIcon = new TrayIcon(image, "event.Event notification");
            this.trayIcon.setImageAutoSize(true);
            this.trayIcon.setToolTip("event.Event notification system");
            try {
                tray.add(this.trayIcon);
            } catch (AWTException ignored) {}
        }

        @Override
        public void sendNotification(String title, String message) {
            new Thread(() -> this.trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO)).start();
//            this.trayIcon.addActionListener(actionEvent -> {
//                this.tray.remove(this.trayIcon);
//            });
        }
    }
    private static class MacOSNotifier implements Notifier {
        @Override
        public void sendNotification(String title, String message) {
            String[] cmd = {"osascript", "-e", String.format("display notification \"%s\" with title \"%s\"", message, title)};
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException ignored) {}
        }
    }
    private static class LinuxNotifier implements Notifier {
        @Override
        public void sendNotification(String title, String message) {
            String[] cmd = { "notify-send", "-t", "10000", title, message};
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException ignored) {}
        }
    }
}
