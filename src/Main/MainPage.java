package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage extends JFrame {

  private Graphics g;
  private String State = null;
  private int lx, ly, width, height;
  private Color currentFillColor = null;
  private int rotationAngle = 0;

  private int lineX1, lineY1, lineX2, lineY2;

  MainPage() {
    initComponents();
    addComponents();
    locateComponents();

    g = panel.getGraphics();
  }

  private void initComponents() {
    setVisible(true);
    setTitle("Projek Akhir Grafika Komputer (720x680)");
    setSize(750, 966);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    setLocationRelativeTo(null);

    // Shape
    btnRectangle.addActionListener(e -> btnRectangleHandler(e));
    btnOval.addActionListener(e -> btnOvalHandler(e));
    btnTriangle.addActionListener(e -> btnTriangleHandler(e));
    btnPentagon.addActionListener(e -> btnPentagonHandler(e));
    btnHexagon.addActionListener(e -> btnHexagonHandler(e));
    btnLine.addActionListener(e -> btnLineHandler(e));

    // Color
    btnRed.addActionListener(e -> btnColorHandler(e, Color.red));
    btnGreen.addActionListener(e -> btnColorHandler(e, Color.green));
    btnBlue.addActionListener(e -> btnColorHandler(e, Color.blue));

    // Manipulation
    btnTranslate.addActionListener(e -> btnTranslateHandler(e));
    btnScale.addActionListener(e -> btnScaleHandler(e));
    btnRotate.addActionListener(e -> btnRotateHandler(e));
    btnClear.addActionListener(e -> btnClearHandler(e));
  }

  private void addComponents() {
    add(panel);

    add(btnClear);
    add(btnTranslate);
    add(btnRotate);
    add(btnScale);

    add(btnRectangle);
    add(btnOval);
    add(btnTriangle);
    add(btnPentagon);
    add(btnHexagon);
    add(btnLine);

    add(btnRed);
    add(btnGreen);
    add(btnBlue);

    add(inputTranslateX);
    add(inputTranslateY);
    add(inputScaleX);
    add(inputScaleY);
    add(inputRotate);

    add(inputLineX1);
    add(inputLineY1);
    add(inputLineX2);
    add(inputLineY2);

    add(labelTitle);
    add(labelShape);
    add(labelFillColor);

    add(labelTranslate);
    add(labelInputTranslateX);
    add(labelInputTranslateY);

    add(labelScale);
    add(labelInputScaleX);
    add(labelInputScaleY);

    add(labelInputRotate);

    add(labelLine);
    add(labelInputLineX1);
    add(labelInputLineY1);
    add(labelInputLineX2);
    add(labelInputLineY2);
  }

  private void locateComponents() {
    labelShape.setBounds(20, 8, 240, 20);
    btnRectangle.setBounds(20, 32, 110, 28);
    btnOval.setBounds(136, 32, 110, 28);
    btnTriangle.setBounds(252, 32, 110, 28);
    btnPentagon.setBounds(368, 32, 110, 28);
    btnHexagon.setBounds(484, 32, 110, 28);
    btnLine.setBounds(600, 32, 110, 28);

    labelFillColor.setBounds(20, 70, 240, 20);
    btnRed.setBounds(20, 94, 96, 28);
    btnGreen.setBounds(122, 94, 96, 28);
    btnBlue.setBounds(224, 94, 96, 28);

    labelLine.setBounds(20, 132, 240, 20);
    labelInputLineX1.setBounds(40, 156, 30, 28);
    inputLineX1.setBounds(70, 156, 44, 28);
    labelInputLineY1.setBounds(124, 156, 30, 28);
    inputLineY1.setBounds(154, 156, 44, 28);
    labelInputLineX2.setBounds(208, 156, 30, 28);
    inputLineX2.setBounds(238, 156, 44, 28);
    labelInputLineY2.setBounds(292, 156, 30, 28);
    inputLineY2.setBounds(322, 156, 44, 28);

    // Adjusted positions for other manipulation tools
    labelTranslate.setBounds(20, 194, 240, 20);
    labelInputTranslateX.setBounds(40, 218, 24, 28);
    inputTranslateX.setBounds(68, 218, 44, 28);
    labelInputTranslateY.setBounds(124, 218, 24, 28);
    inputTranslateY.setBounds(152, 218, 44, 28);
    btnTranslate.setBounds(208, 218, 96, 28);

    labelScale.setBounds(340, 194, 240, 20);
    labelInputScaleX.setBounds(360, 218, 24, 28);
    inputScaleX.setBounds(388, 218, 44, 28);
    labelInputScaleY.setBounds(444, 218, 24, 28);
    inputScaleY.setBounds(472, 218, 44, 28);
    btnScale.setBounds(528, 218, 96, 28);

    labelInputRotate.setBounds(20, 256, 44, 28);
    inputRotate.setBounds(68, 256, 128, 28);
    btnRotate.setBounds(208, 256, 96, 28);

    btnClear.setBounds(340, 256, 284, 28);
    panel.setBackground(new java.awt.Color(255, 255, 255));
    panel.setBounds(8, 300, 720, 620); // Adjusted panel position
  }


  private void btnLineHandler(ActionEvent event) {
    try {
      String x1Str = inputLineX1.getText();
      String y1Str = inputLineY1.getText();
      String x2Str = inputLineX2.getText();
      String y2Str = inputLineY2.getText();

      if (x1Str.isEmpty() || y1Str.isEmpty() || x2Str.isEmpty() || y2Str.isEmpty()) {
        throw new Exception("Line coordinate inputs cannot be empty");
      }

      lineX1 = Integer.parseInt(x1Str);
      lineY1 = Integer.parseInt(y1Str);
      lineX2 = Integer.parseInt(x2Str);
      lineY2 = Integer.parseInt(y2Str);

      resetCanvas();
      resetAll();
      State = "Line";
      createShape();

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Please enter valid integer coordinates for the line.");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  void createBresenhamLine() {
    int x1 = lineX1;
    int y1 = lineY1;
    int x2 = lineX2;
    int y2 = lineY2;

    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);

    int sx = (x1 < x2) ? 1 : -1;
    int sy = (y1 < y2) ? 1 : -1;

    boolean isSteep = (dy > dx);

    if (isSteep) {
      int temp = dx;
      dx = dy;
      dy = temp;
    }

    int error = dx / 2;
    int x = x1;
    int y = y1;

    if (currentFillColor != null) {
      g.setColor(currentFillColor);
    } else {
      g.setColor(Color.BLACK);
    }

    for (int i = 0; i <= dx; i++) {
      g.fillRect(x, y, 1, 1);

      error -= dy;
      if (error < 0) {
        error += dx;
        if (isSteep) {
          x += sx;
        } else {
          y += sy;
        }
      }
      if (isSteep) {
        y += sy;
      } else {
        x += sx;
      }
    }
  }


  private void btnRectangleHandler(ActionEvent event) {
    resetCanvas();
    resetAll();
    State = "Rectangle";
    createRectangle();
  }

  private void btnOvalHandler(java.awt.event.ActionEvent event) {
    resetCanvas();
    resetAll();
    State = "Oval";
    createOval();
  }

  private void btnTriangleHandler(java.awt.event.ActionEvent event) {
    resetCanvas();
    resetAll();
    State = "Triangle";
    createTriangle();
  }

  private void btnPentagonHandler(ActionEvent event) {
    resetCanvas();
    resetAll();
    State = "Pentagon";
    createPentagon();
  }

  private void btnHexagonHandler(java.awt.event.ActionEvent event) {
    resetCanvas();
    resetAll();
    State = "Hexagon";
    createHexagon();
  }

  private void btnColorHandler(ActionEvent event, Color color) {
    if (State != null) {
      resetCanvas();
      currentFillColor = color;
      createShape();
    } else {
      JOptionPane.showMessageDialog(null, "Please select a shape first.");
    }
  }

  private void btnTranslateHandler(ActionEvent event) {
    try {
      if (State == null) {
        throw new Exception("Tidak ada bangun datar yang dipilih");
      }
      if ("Line".equals(State)) {
        JOptionPane.showMessageDialog(this, "Translation is not supported for Line shape.");
        return;
      }
      String inputX = inputTranslateX.getText();
      String inputY = inputTranslateY.getText();
      if ("".equals(inputX) || "".equals(inputY)) {
        throw new Exception("Input masih kosong");
      }

      int tx = Integer.parseInt(inputX);
      int ty = Integer.parseInt(inputY);
      lx += tx;
      ly += ty;
      resetCanvas();
      createShape();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }
  }

  private void btnRotateHandler(ActionEvent event) {
    try {
      if (State == null) {
        throw new Exception("Tidak ada bangun datar yang dipilih");
      }
      if ("Line".equals(State)) {
        JOptionPane.showMessageDialog(this, "Rotation is not supported for Line shape.");
        return;
      }
      String inputAngle = inputRotate.getText();
      if ("".equals(inputAngle)) {
        throw new Exception("Input masih kosong");
      }

      int angle = Integer.parseInt(inputAngle);
      rotationAngle += angle;
      resetCanvas();
      createShape();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }
  }

  private void btnScaleHandler(ActionEvent event) {
    try {
      if (State == null) {
        throw new Exception("Tidak ada bangun datar yang dipilih");
      }
      if ("Line".equals(State)) {
        JOptionPane.showMessageDialog(this, "Scaling is not supported for Line shape.");
        return;
      }
      String inputX = inputScaleX.getText();
      String inputY = inputScaleY.getText();
      if ("".equals(inputX) || "".equals(inputY)) {
        throw new Exception("Input masih kosong");
      }

      int scaleX = Integer.parseInt(inputX);
      int scaleY = Integer.parseInt(inputY);
      width += scaleX;
      height += scaleY;
      resetCanvas();
      createShape();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
    }
  }

  private void btnClearHandler(ActionEvent event) {
    panel.repaint();
    g.setColor(Color.BLACK);
    State = null;
    inputLineX1.setText("");
    inputLineY1.setText("");
    inputLineX2.setText("");
    inputLineY2.setText("");
  }

  void createShape() {
    switch (State) {
      case "Rectangle":
        createRectangle();
        break;
      case "Oval":
        createOval();
        break;
      case "Triangle":
        createTriangle();
        break;
      case "Pentagon":
        createPentagon();
        break;
      case "Hexagon":
        createHexagon();
        break;
      case "Line":
        createBresenhamLine();
        break;
      default:
        break;
    }
  }

  void createRectangle() {
    Graphics2D graphics2D = (Graphics2D) g.create();
    int centroidX = lx + (width / 2);
    int centroidY = ly + (height / 2);

    graphics2D.translate(centroidX, centroidY);
    graphics2D.rotate(Math.toRadians(rotationAngle));
    if (currentFillColor == null) {
      graphics2D.drawRect(-width / 2, -height / 2, width, height);
    } else {
      graphics2D.setColor(currentFillColor);
      graphics2D.fillRect(-width / 2, -height / 2, width, height);
    }
    graphics2D.dispose();
  }

  void createOval() {
    Graphics2D graphics2D = (Graphics2D) g.create();
    int centroidX = lx + (width / 2);
    int centroidY = ly + (height / 2);

    graphics2D.translate(centroidX, centroidY);
    graphics2D.rotate(Math.toRadians(rotationAngle));
    if (currentFillColor == null) {
      graphics2D.drawOval(-width / 2, -height / 2, width, height);
    } else {
      graphics2D.setColor(currentFillColor);
      graphics2D.fillOval(-width / 2, -height / 2, width, height);
    }
    graphics2D.dispose();
  }

  void createTriangle() {
    Graphics2D graphics2D = (Graphics2D) g.create();
    int x[] = {lx, lx + width / 2, lx + width};
    int y[] = {ly + height, ly, ly + height};
    int centroidX = (x[0] + x[1] + x[2]) / 3;
    int centroidY = (y[0] + y[1] + y[2]) / 3;

    graphics2D.rotate(Math.toRadians(rotationAngle), centroidX, centroidY);
    if (currentFillColor == null) {
      graphics2D.drawPolygon(x, y, x.length);
    } else {
      graphics2D.setColor(currentFillColor);
      graphics2D.fillPolygon(x, y, x.length);
    }
    graphics2D.dispose();
  }

  void createPentagon() {
    Graphics2D graphics2D = (Graphics2D) g.create();
    int yTengah = ly + (height / 2) - (height / 8);
    int x[] = {lx, lx + (width / 8), lx + width - (width / 8), lx + width, lx + (width / 2)};
    int y[] = {yTengah, ly + height, ly + height, yTengah, ly};
    int centroidX = (x[0] + x[1] + x[2] + x[3] + x[4]) / 5;
    int centroidY = (y[0] + y[1] + y[2] + y[3] + y[4]) / 5;

    graphics2D.rotate(Math.toRadians(rotationAngle), centroidX, centroidY);
    if (currentFillColor == null) {
      graphics2D.drawPolygon(x, y, x.length);
    } else {
      graphics2D.setColor(currentFillColor);
      graphics2D.fillPolygon(x, y, x.length);
    }
    graphics2D.dispose();
  }

  void createHexagon() {
    Graphics2D graphics2D = (Graphics2D) g.create();
    int yTengah = ly + height / 2;
    int xTengah1 = lx + (width / 4);
    int xTengah2 = lx + width - (width / 4);
    int x[] = {lx, xTengah1, xTengah2, lx + width, xTengah2, xTengah1};
    int y[] = {yTengah, ly + height, ly + height, yTengah, ly, ly};
    int centroidX = (x[0] + x[1] + x[2] + x[3] + x[4] + x[5]) / 6;
    int centroidY = (y[0] + y[1] + y[2] + y[3] + y[4] + x[5]) / 6;

    graphics2D.rotate(Math.toRadians(rotationAngle), centroidX, centroidY);
    if (currentFillColor == null) {
      graphics2D.drawPolygon(x, y, x.length);
    } else {
      graphics2D.setColor(currentFillColor);
      graphics2D.fillPolygon(x, y, x.length);
    }
    graphics2D.dispose();
  }

  void resetCanvas() {
    if (g != null) {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
      g.setColor(Color.BLACK);
    }
  }

  void resetAll() {
    resetColor();
    resetCoordinate();
    resetRotation();
    resetSize();
  }

  void resetColor() {
    currentFillColor = null;
  }

  void resetCoordinate() {
    lx = panel.getWidth() / 2 - 144 / 2;
    ly = panel.getHeight() / 2 - 144 / 2;
  }

  void resetRotation() {
    rotationAngle = 0;
  }

  void resetSize() {
    width = 144;
    height = 144;
  }

  // --- UI Component Declarations ---
  private final JPanel panel = new JPanel();
  private final JButton btnClear = new JButton("Clear");
  private final JButton btnTranslate = new JButton("Translate");
  private final JButton btnRotate = new JButton("Rotate");
  private final JButton btnScale = new JButton("Scale");
  private final JButton btnPentagon = new JButton("Pentagon");
  private final JButton btnRectangle = new JButton("Rectangle");
  private final JButton btnOval = new JButton("Oval");
  private final JButton btnTriangle = new JButton("Triangle");
  private final JButton btnHexagon = new JButton("Hexagon");
  private final JButton btnLine = new JButton("Line"); // New Button
  private final JButton btnRed = new JButton("Red");
  private final JButton btnGreen = new JButton("Green");
  private final JButton btnBlue = new JButton("Blue");

  private final JTextField inputTranslateX = new JTextField();
  private final JTextField inputTranslateY = new JTextField();
  private final JTextField inputScaleX = new JTextField();
  private final JTextField inputScaleY = new JTextField();
  private final JTextField inputRotate = new JTextField();
  private final JTextField inputLineX1 = new JTextField();
  private final JTextField inputLineY1 = new JTextField();
  private final JTextField inputLineX2 = new JTextField();
  private final JTextField inputLineY2 = new JTextField();

  private final JLabel labelTitle = new JLabel("Grafika Komputer");
  private final JLabel labelShape = new JLabel("Shape");
  private final JLabel labelFillColor = new JLabel("Fill Color");
  private final JLabel labelScale = new JLabel("Scale (X, Y)");
  private final JLabel labelInputScaleX = new JLabel("X = ");
  private final JLabel labelInputScaleY = new JLabel("Y = ");
  private final JLabel labelTranslate = new JLabel("Translate (X, Y)");
  private final JLabel labelInputTranslateX = new JLabel("X = ");
  private final JLabel labelInputTranslateY = new JLabel("Y = ");
  private final JLabel labelInputRotate = new JLabel("Rotate");


  private final JLabel labelLine = new JLabel("Bresenham's Line (x1, y1, x2, y2)");
  private final JLabel labelInputLineX1 = new JLabel("x1=");
  private final JLabel labelInputLineY1 = new JLabel("y1=");
  private final JLabel labelInputLineX2 = new JLabel("x2=");
  private final JLabel labelInputLineY2 = new JLabel("y2=");
}