/*
 *	===============================================================================
 *	OvalShape.java : A shape that is an oval.
 *  YOUR UPI: sdes541
 *  Name: Sahil Desai
 *	=============================================================================== 
 */

import java.awt.*;
import java.util.*;


class NestedShape extends RectangleShape {
    private ArrayList<Shape> innerShapes = new ArrayList<Shape>();

    public NestedShape() {
        super();
        createInnerShape(0, 0, width / 2, height / 2, color, borderColor, PathType.BOUNCING, ShapeType.RECTANGLE);
    }

    public NestedShape(int x, int y, int width, int height, int panelWidth, int panelHeight, Color fillColor, Color borderColor, PathType pathType) {
        super(x, y, width, height, panelWidth, panelHeight, fillColor, borderColor, pathType);
        createInnerShape(0, 0, width / 2, height / 2, color, borderColor, PathType.BOUNCING, ShapeType.RECTANGLE);
    }

    public NestedShape(int width, int height) {
        super(0, 0, width, height, DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT, Color.black, Color.black, PathType.BOUNCING);
    }

    public Shape createInnerShape(int x, int y, int width, int height, Color fillColor, Color borderColor, PathType pathType, ShapeType shapeType) {

        Shape innerShape = null;

        switch (shapeType) {
            case RECTANGLE:
                innerShape = new RectangleShape(x, y, width, height, this.width, this.height, fillColor, borderColor, pathType);
                break;
            case OVAL:
                innerShape = new OvalShape(x, y, width, height, this.width, this.height, fillColor, borderColor, pathType);
                break;
            case NESTED:
                innerShape = new NestedShape(x, y, width, height, this.width, this.height, fillColor, borderColor, pathType);
                break;
        }

        
        innerShape.setParent(this);
        innerShapes.add(innerShape);

        return innerShape;
    }

    public Shape createInnerShape(PathType pathType, ShapeType shapeType) {
        int innerWidth = width / 2;
        int innerHeight = height / 2;

        Shape innerShape = createInnerShape(0, 0, innerWidth, innerHeight, this.color, this.borderColor, pathType, shapeType);

        return innerShape;
    }

    public Shape getInnerShapeAt(int index) {
        return innerShapes.get(index);
    }

    public int getSize() {
        return innerShapes.size();
    }
    
    public void draw(Graphics g) {
  
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        
        for (Shape innerShape : innerShapes) {
            g.translate(x, y);
            innerShape.draw(g);
            innerShape.drawHandles(g);
            innerShape.drawString(g);
            g.translate(-x, -y);
        }
    }
    
    public void move() {
        super.move(); 
        for (Shape innerShape : innerShapes) {
            innerShape.move(); 
        }
    }

    public int indexOf(Shape s) {
        return innerShapes.indexOf(s); 
    }
        
    public void addInnerShape(Shape s) {
        innerShapes.add(s); 
        s.setParent(this);
    }
    
    public void removeInnerShape(Shape s) {
        innerShapes.remove(s);
        s.setParent(null); 
    }
    
    public void removeInnerShapeAt(int index) {
        Shape selected = innerShapes.get(index); 
        innerShapes.remove(selected);
        selected.setParent(null);
    }
    
    public ArrayList<Shape> getAllInnerShapes() {
        return innerShapes; 
    }
    
}
