package net.ddns.whomagoo.laserchess.gui.pc;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;
import net.ddns.whomagoo.laserchess.game.Board;
import net.ddns.whomagoo.laserchess.game.LaserSegment;
import net.ddns.whomagoo.laserchess.game.move.Move;
import net.ddns.whomagoo.laserchess.game.piece.Piece;

import java.util.*;

public class GameView extends GridPane {
  private Board gameBoard;
  private int pieceSize = 80;
  private int padding = 15;
  private Node[][] display;
  private HashMap<Node, Pair<Integer, Integer>> overlayed;
  private HashMap<Pair<Integer, Integer>, MoveView> locationToOverlayed;
  private HashMap<Pair<Integer, Integer>, PieceView> piecesDisplayed;
  private HashMap<Node, Pair<Integer, Integer>> pieceLocation;
  private HashMap<Node, Pair<Integer, Integer>> buttons;
  private Collection<Node> lasersShowing;
  private Collection<LaserSegment> laserPath;

  //private HashMap<Pair<Integer, Integer>, ArrayList<Node>> itemsInLocation;

  private int totalSquaresX;
  private int totalSquaresY;

  private volatile Piece pieceSelected;

  private NumberBinding showingCellSize;

  EventHandler<MouseEvent> onClick;

  private volatile Mode mode;
  private enum Mode {
    ChoosePiece, ChooseMove, ConfirmMove, Waiting;
  }

  public GameView(Board gameBoard){
    initGameBoard(gameBoard);
    update();
  }

  public Pair<Integer, Integer> getLocation(Node n){
    if(pieceLocation.containsKey(n)){
      return pieceLocation.get(n);
    }

    if(buttons.containsKey(n)){
      return buttons.get(n);
    }

    if(overlayed.containsKey(n)){
      return overlayed.get(n);
    }

    return null;
  }

  public boolean isDisplaying(Node n){
    return
        pieceLocation.containsKey(n) ||
            buttons.containsKey(n) ||
            overlayed.containsKey(n);
  }

  private void initGameBoard(Board gameBoard){
    locationToOverlayed = new HashMap<>();
    overlayed = new HashMap<>();
    piecesDisplayed = new HashMap<>();
    pieceLocation = new HashMap<>();
    //items = new HashMap<>();
    //itemsInLocation = new HashMap<>();
    buttons = new HashMap<>();

    this.gameBoard = gameBoard;

    totalSquaresX = gameBoard.sizeX * 2 + 1;
    totalSquaresY = gameBoard.getSizeY() * 2 + 1;

    display = new StackPane[totalSquaresX][totalSquaresY];

    //Initialize storage to keep track of children
    for(int x = 0; x < totalSquaresX; x++){
      for(int y = 0; y < totalSquaresY; y++){
        //itemsInLocation.put(new Pair<Integer, Integer>(x, y), new ArrayList<>());
      }
    }

    mode = Mode.ChoosePiece;

    //Initialize constraints
    setGridLinesVisible(true);
    RowConstraints showingRows = new RowConstraints();
    showingRows.setFillHeight(true);
    showingRows.setValignment(VPos.CENTER);
    showingRows.setPercentHeight(100.0 / gameBoard.getSizeY());
    showingRows.setVgrow(Priority.ALWAYS);

    RowConstraints buttonsGridRow = new RowConstraints();
    buttonsGridRow.setFillHeight(false);
    buttonsGridRow.setValignment(VPos.CENTER);
    buttonsGridRow.setPercentHeight(0);
    buttonsGridRow.setVgrow(Priority.NEVER);

    ColumnConstraints piecesGrid = new ColumnConstraints();
    piecesGrid.setHalignment(HPos.CENTER);
    piecesGrid.setFillWidth(true);
    piecesGrid.setPercentWidth(100.0 / gameBoard.sizeX);
    piecesGrid.setHgrow(Priority.ALWAYS);

    ColumnConstraints buttonsGridCol = new ColumnConstraints();
    buttonsGridCol.setHalignment(HPos.CENTER);
    buttonsGridCol.setFillWidth(false);
    buttonsGridCol.setPercentWidth(0);
    buttonsGridCol.setHgrow(Priority.NEVER);

    onClick = mouseEvent -> {
      Node target = mouseEvent.getPickResult().getIntersectedNode();

      int selectedX;
      int selectedY;

//      if(items.containsKey(target)){
      if(isDisplaying(target)){


        Pair<Integer, Integer> loc = getLocation(target);
        selectedX = loc.getKey();
        selectedY = loc.getValue();

        System.out.println("Clicked on " + selectedX + " " + selectedY);

        switch (getMode()){
          case ChoosePiece:
            pieceChosen(selectedX, selectedY);
            break;
          case ChooseMove:
            moveChosen(selectedX, selectedY);
            break;
          case ConfirmMove:
            moveConfirmed(selectedX, selectedY);
            break;
          case Waiting:
          default:
            clearOverlayed();
            return;
        }
      } else {
        System.out.println(target + " not found in items");
      }
    };

    //Add row and column constraints to maintain sizing correctly
    for(int i = 0; i < gameBoard.getSizeY(); i++){
      getRowConstraints().add(buttonsGridRow);
      getRowConstraints().add(showingRows);
    }
    getRowConstraints().add(buttonsGridRow);

    for(int i = 0; i < gameBoard.sizeX; i++){
      getColumnConstraints().add(buttonsGridCol);
      getColumnConstraints().add(piecesGrid);
    }
    getColumnConstraints().add(buttonsGridCol);

    int vCount = 0;

    for(int x = 1; x < totalSquaresX; x+=2){
      for(int y = 1; y < totalSquaresY; y+=2){
        Button b = new Button();
        b.prefWidthProperty().bind(getCellWidthProp(x,y));
        b.prefHeightProperty().bind(getCellHeightProp(x,y));

        Color c = Color.rgb(181, 136, 99);
        if((x / 2 + y / 2) % 2 == 0){
          c = Color.rgb(240, 217, 181);
        }

        b.setBackground(new Background(new BackgroundFill(c, null, null)));
        b.setOnMouseClicked(onClick);

        buttons.put(b, new Pair<>(x, y));
        super.add(b, x, y);
      }
    }

    for(int x = 0; x < gameBoard.sizeX; x++){
      for(int y = 0; y < gameBoard.getSizeY(); y++){
        Piece curPiece = gameBoard.getPiece(x, y);
        if(curPiece != null){
          PieceView pv = new PieceView(curPiece);
          addPiece(pv, x, y);
        }
      }
    }
    System.out.println(vCount);

    GameView current = this;

    setOnMouseClicked(onClick);

    for(int x = 0; x < gameBoard.sizeX; x++){
      Node t = makeTextBox(String.valueOf((char)('a' + x)));
      Node a = makeRowAnchorPane(t);
      add(a, x * 2 + 1,getRowCount() - 2);
    }

    for(int y = 0; y < gameBoard.getSizeY(); y++){
      Node t = makeTextBox(String.valueOf(y));
      Node a = makeColAnchorPane(t);
      add(a, getColumnCount() - 2,y * 2 + 1);
    }
//    setHgap(15);
//    setVgap(15);

    //display = new StackPane[gameBoard.sizeX * 2 - 1][gameBoard.getSizeY() * 2 - 1];

//    for(int x = 0; x < gameBoard.sizeX; x++){
////      for(int y = 0; y < gameBoard.getSizeY(); y++){
////        TextArea p = new TextArea();
////        p.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
////        p.setText(x + "," + y);
////
////        p.setPrefSize(140,140);
////
////        setElement(x, y, p);
////      }
////    }
  }

  private Node makeTextBox(String x) {
    Font f = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 14);
    Text t = new Text();
    t.setText(x);
    t.setFont(f);



    return t;
  }

  private static final double anchorPadding = 2.0;

  private Node makeColAnchorPane(Node child){
    AnchorPane ap = new AnchorPane();
    AnchorPane.setTopAnchor(child, anchorPadding);
    AnchorPane.setRightAnchor(child, anchorPadding);
    ap.getChildren().add(child);

    return ap;
  }

  private Node makeRowAnchorPane(Node child){
    AnchorPane ap = new AnchorPane();
    AnchorPane.setBottomAnchor(child, anchorPadding);
    AnchorPane.setLeftAnchor(child, anchorPadding);
    ap.getChildren().add(child);

    return ap;
  }

  private Mode getMode(){
    return mode;
  }

  private void moveConfirmed(int selectedX, int selectedY) {
    //TODO
  }

  private void moveChosen(int selectedX, int selectedY) {
    Pair<Integer, Integer> selectedLoc = viewLocationToGameLocation(selectedX, selectedY);


    Pair<Integer, Integer> loc = new Pair<Integer, Integer>(selectedX, selectedY);
    MoveView mv = locationToOverlayed.get(loc);
    if(mv != null){
      mv.getMove().doMove(gameBoard);
      clearOverlayed();
      update();
      mode = Mode.ChoosePiece;
      return;
    }

    if(pieceSelected != null && selectedLoc != null && pieceSelected.xPos() == selectedLoc.getKey() && pieceSelected.yPos() == selectedLoc.getValue()){
      //Same piece was selected, cancel the move selection process
      clearOverlayed();
      mode = Mode.ChoosePiece;
      return;
    }

    mode = Mode.ChoosePiece;
    clearOverlayed();
    pieceChosen(selectedX, selectedY);

  }

  private void pieceChosen(int selectedX, int selectedY) {

    Pair<Integer, Integer> gameLoc = viewLocationToGameLocation(selectedX, selectedY);

    if(gameLoc == null){
      //An invalid piece must have been selected;
      this.pieceSelected = null;
      mode = Mode.ChoosePiece;
      return;
    }

    int pieceX = gameLoc.getKey();
    int pieceY = gameLoc.getValue();

    pieceSelected = gameBoard.getPiece(pieceX, pieceY);
    List<Move> moves = gameBoard.getMoves(pieceSelected);

    if(moves.size() == 0){
      pieceSelected = null;
      mode = Mode.ChoosePiece;
      return;
    }


    boolean containsFire = false;

    ArrayList<Move> selfTargetMoves = new ArrayList<>(4);
    HashMap<Pair<Integer, Integer>, Move> other = new HashMap<>(4);


    for(Move move : moves){

      Pair<Integer, Integer> displayLoc = gameLocationToViewLocation(move.getTargetX(), move.getTargetY());
      if(move.getTargetX() == pieceX && move.getTargetY() == pieceY){
        selfTargetMoves.add(move);
      } else {
        if(move.getName().equals(Move.FIRE_LASER) && !gameBoard.inBounds(move.getTargetX(), move.getTargetY())){
          displayLoc = gameLocationToViewLocation(move.getSource().xPos(), move.getSource().yPos());
        }
        other.put(new Pair<>(move.getTargetX(), move.getTargetY()), move);
        addOverlay(makeArrow(move), displayLoc.getKey(), displayLoc.getValue());
      }
    }

    for (Move move : selfTargetMoves){
      Pair<Integer, Integer> displayLoc = gameLocationToViewLocation(move.getTargetX(), move.getTargetY());
      int x = displayLoc.getKey(), y = displayLoc.getValue();
      if(move.getName().equals(Move.ROTATE_COUNTERCLOCKWISE)){
        x -= 2;
        y -= 2;
      } else if(move.getName().equals(Move.ROTATE_CLOCKWISE)){
        x += 2;
        y -= 2;
      }

      if(!inBounds(x, y)){
        x += x < displayLoc.getKey() ? 4 : -4;
        y += 4;
      }

      if(!inBounds(x, y)){
        x = displayLoc.getKey();
        y = displayLoc.getValue();
      }

      Node arrow = makeArrow(move);

      if(x > displayLoc.getKey()){
        arrow.setRotate(180.0);
      } else if(x == displayLoc.getKey()){
        arrow.setRotate(90);
      }

      addOverlay(arrow, x, y);
    }

    mode = Mode.ChooseMove;
  }

  private void clearOverlayed(){
    removeAll(new HashSet<>(overlayed.keySet()));
  }

  private void removeFromLocations(Node node){
    pieceLocation.remove(node);
    overlayed.remove(node);
  }

  private void removeAll(Collection<Node> nodes){
    for(Node node : nodes){
      Pair<Integer, Integer> loc = remove(node);
      if(loc != null) {
        //ArrayList<Node> items = itemsInLocation.get(loc);
        //items.remove(node);

        overlayed.remove(node);
        locationToOverlayed.remove(loc);
      }
    }

    getChildren().removeAll(nodes);
  }

  public void addPiece(PieceView pv, int gameX, int gameY){
    if(inBounds(gameX, gameY)) {
      Pair<Integer, Integer> loc = gameLocationToViewLocation(gameX, gameY);
      //piecesDisplayed.put(loc, pv);
      pieceLocation.put(pv, loc);
      piecesDisplayed.put(loc, pv);
      add(pv, loc.getKey(), loc.getValue());
    }
  }

  @Override
  public void add(Node node, int x, int y) {
    if(inBounds(x, y)) {
      //node.setOnMouseClicked(onClick);

      //System.out.println(x + " " + y + " " + sp.getBoundsInParent());
      //node.setOnMouseClicked((e) -> System.out.println("Clicked on " + x + " " + y));



      super.add(node, x, y);

      Pair<Integer, Integer> loc = new Pair<>(x, y);
      //items.put(node, loc);
      //itemsInLocation.get(loc).add(node);

      if(node  instanceof ImageView){
        ImageView iv = (ImageView) node;
        iv.maxHeight(Double.MAX_VALUE);
        iv.minHeight(0);
        iv.prefHeight(200);
        iv.maxWidth(Double.MAX_VALUE);
        iv.minWidth(0);
        iv.prefWidth(200);

        iv.fitWidthProperty().bind(getCellSizeAsSquare(x, y).multiply(.9));
        iv.fitHeightProperty().bind(getCellSizeAsSquare(x, y).multiply(.9));
      }

      if(node instanceof MoveView){
        MoveView mv = (MoveView) node;
        //mv.setOnMouseClicked(onClick);
        overlayed.put(node, loc);
        locationToOverlayed.put(loc, (MoveView) node);
      }
    }
  }

  public Pair<Integer, Integer> remove(Node n){
    Pair<Integer, Integer> result = overlayed.remove(n);
    Pair<Integer, Integer> result2 = pieceLocation.remove(n);

    if(result == null && result2 == null){
      return null;
    }

    piecesDisplayed.remove(result2);
    locationToOverlayed.remove(result);
    getChildren().remove(n);
    return result != null ? result : result2;
  }

  private boolean inBounds(int x, int y) {
    return 0 <= x && x < totalSquaresX && 0 <= y && y < totalSquaresY;
  }

  private void addOverlay(Node newNode, int viewX, int viewY){
    //overlayed.put(newNode, new Pair<>(viewX, viewY));

    add(newNode, viewX, viewY);
  }

  private void setElement(int gameX, int gameY, Node newNode){
    Pair<Integer, Integer> point = gameLocationToViewLocation(gameX, gameY);
    if(newNode == null){
      //removeAll(new ArrayList<>(itemsInLocation.get(point)));
      return;
    }

    int viewX = point.getKey();
    int viewY = point.getValue();

    Node old = display[viewX][viewY];

    if(old != null){
      getChildren().remove(old);
    }

    add(newNode, viewX, viewY);
    newNode.autosize();

    setMargin(newNode, new Insets(padding,padding,padding,padding));
  }

  private void renderLaserPath(){
    Collection<LaserSegment> newPath = gameBoard.getLaserPath();

    if(lasersShowing != null) {
      removeAll(lasersShowing);
    }

    if (laserPath != newPath) {
      laserPath = newPath;
      lasersShowing = new ArrayList<>(newPath.size());

      for (LaserSegment ls : newPath) {
        for (ImageView iv : LaserView.getViews(ls)) {
          Pair<Integer, Integer> displayLoc = gameLocationToViewLocation(ls.getLocX(), ls.getLocY());
          add(iv, displayLoc.getKey(), displayLoc.getValue());
          lasersShowing.add(iv);
        }
      }
    }

  }

  public void update(){
    HashMap<Pair<Integer, Integer>, PieceView> newPiecesDisplayed = new HashMap<>(piecesDisplayed.size());

    int capacity = buttons.size() + pieceLocation.size() + overlayed.size();
    HashMap<Node, Pair<Integer, Integer>> newPieceLocation = new HashMap<>(capacity);
    newPieceLocation.putAll(buttons);

    ArrayList<PieceView> piecesToRelocate = new ArrayList<>();
    ArrayList<PieceView> piecesToRemove = new ArrayList<>();

    for(Map.Entry<Pair<Integer, Integer>, PieceView> pieceSet : piecesDisplayed.entrySet()){
      PieceView piece = pieceSet.getValue();
      if(piece != null){
        piece.updateRotation();
        if(!Objects.equals(pieceSet.getKey(), gameLocationToViewLocation(piece.getGameX(), piece.getGameY()))){
          if(!(piece.getGameX() == -1 && piece.getGameY() == -1)){
            piecesToRelocate.add(piece);
          }

          piecesToRemove.add(piece);
        }
      }
    }

    for(Node p : piecesToRemove){
      remove(p);
    }

    for(PieceView n : piecesToRelocate){
      addPiece(n, n.getGameX(), n.getGameY());
    }

    renderLaserPath();
//    for(int gameX = 0; gameX < gameBoard.sizeX; gameX++){
//      for(int gameY = 0; gameY < gameBoard.getSizeY(); gameY++){
//        Pair<Integer, Integer> viewLocation = gameLocationToViewLocation(gameX, gameY);
//        Piece curPiece = gameBoard.getPiece(gameX, gameY);
//
//        PieceView cur = piecesDisplayed.get(viewLocation);
//
//        if(cur != null){
//          cur.updateRotation();
//          Pair<Integer, Integer> newLoc = gameLocationToViewLocation(cur.getGameX(), cur.getGameY());
//          piecesDisplayed.remove(newLoc);
//          newPiecesDisplayed.put(newLoc, cur);
//          if(gameX != cur.getGameX() || gameY != cur.getGameY()){
//            piecesToRelocate.add(cur);
//            remove(cur);
//            //itemsInLocation.get(viewLocation).remove(cur);
//            //items.put(cur, newLoc);
//          } else {
//            newPieceLocation.put(cur, newLoc);
//            //ArrayList<Node> items = newPiecesDisplayed.get(newLoc);
//          }
//          remove(cur);
//        }
//      }
//    }
  }

  private Node makeArrow(Move move){
    Node result = new MoveView(move);
    //result.setOnMouseClicked(this.onClick);
    return result;
  }

  private Node makeGamePiece(Piece gp){
    return new PieceView(gp);
  }

  private NumberBinding getCellHeightProp(int x, int y){
    return heightProperty().multiply(getRowConstraints().get(y).percentHeightProperty()).divide(100);
  }

  private NumberBinding getCellWidthProp(int x, int y){
    return widthProperty().multiply(getColumnConstraints().get(x).percentWidthProperty()).divide(100);
  }

  private NumberBinding getCellSizeAsSquare(int x, int y){
    return Bindings.max(getCellHeightProp(x, y), getCellWidthProp(x, y));
  }

  private Pair<Integer, Integer> gameLocationToViewLocation(int gameX, int gameY){
    return new Pair<>(gameX * 2 + 1, (gameBoard.getSizeY() - gameY - 1) * 2 + 1);
  }

  private boolean isValidPieceLocation(int viewX, int viewY){
    return viewX % 2 == 1 && viewY % 2 == 1;
  }

  private Pair<Integer, Integer> viewLocationToGameLocation(int viewX, int viewY){
    return isValidPieceLocation(viewX, viewY) ? new Pair<>(viewX / 2, gameBoard.getSizeY() - viewY / 2 - 1) : null;
  }
}
