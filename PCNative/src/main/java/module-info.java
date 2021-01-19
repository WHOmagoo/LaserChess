module net.ddns.whomagoo.laserchess.gui.pc {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;
    requires net.ddns.whomagoo.laserchess.game;
  requires java.xml;

  opens net.ddns.whomagoo.laserchess.gui.pc to javafx.fxml;
    exports net.ddns.whomagoo.laserchess.gui.pc;
}