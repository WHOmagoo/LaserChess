module net.ddns.whomagoo.laserchess.game {
    requires com.google.gson;
    exports net.ddns.whomagoo.laserchess.game;
    exports net.ddns.whomagoo.laserchess.game.move;
    exports net.ddns.whomagoo.laserchess.game.piece;

    opens net.ddns.whomagoo.laserchess.game;
    opens net.ddns.whomagoo.laserchess.game.move;
    opens net.ddns.whomagoo.laserchess.game.piece;
}