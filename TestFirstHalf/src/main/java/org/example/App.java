package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static Point2D[] createPointArray(int numberOfPoints)
    {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
            Point2D[] p = new Point2D[numberOfPoints];
            for (int j = 0; j < numberOfPoints; j++)
            {
                double[] xy = new double[2];
                System.out.println((j + 1) + " point. Coordinate X:");
                xy[0] = Double.parseDouble(inputStream.readLine());
                System.out.println((j + 1) + " point. Coordinate Y:");
                xy[1] = Double.parseDouble(inputStream.readLine());
                p[j] = new Point2D(xy);
            }
            return p;
        }
        catch (IOException ioe){
            System.out.println("IOException occured");
            return null;
        }
    }
    public static IShape newShape(String type)
    {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
            IShape shape;
            if (type.equals("polyline"))
            {
                System.out.println("Number of points: ");
                int numberOfPoints = Integer.parseInt(inputStream.readLine());
                shape = new Polyline(createPointArray(numberOfPoints));
            }
            else if (type.equals("ngon"))
            {
                System.out.println("Number of points: ");
                int numberOfPoints = Integer.parseInt(inputStream.readLine());
                shape = new NGon(createPointArray(numberOfPoints));
            }
            else if (type.equals("qgon"))
            {
                shape = new QGon(createPointArray(4));
            }
            else if (type.equals("tgon"))
            {
                shape = new TGon(createPointArray(3));
            }
            else if (type.equals("trapeze"))
            {
                shape = new Trapeze(createPointArray(4));
            }
            else if (type.equals("rectangle"))
            {
                shape = new Rectangle(createPointArray(4));
            }
            else if (type.equals("segment"))
            {
                double[] start = new double[2];
                System.out.println("Start X coordinate: ");
                start[0] = Double.parseDouble(inputStream.readLine());
                System.out.println("Start Y coordinate: ");
                start[1] = Double.parseDouble(inputStream.readLine());
                double[] finish = new double[2];
                System.out.println("Finish X coordinate: ");
                finish[0] = Double.parseDouble(inputStream.readLine());
                System.out.println("Finish Y coordinate: ");
                finish[1] = Double.parseDouble(inputStream.readLine());
                shape = new Segment(new Point2D(start), new Point2D(finish));
            }
            else if (type.equals("circle"))
            {
                double[] center = new double[2];
                System.out.println("Center X coordinate: ");
                center[0] = Double.parseDouble(inputStream.readLine());
                System.out.println("Center Y coordinate: ");
                center[1] = Double.parseDouble(inputStream.readLine());
                System.out.println("Radius: ");
                double radius = Double.parseDouble(inputStream.readLine());
                shape = new Circle(new Point2D(center), radius);
            }
            else throw new NullPointerException("Inexistent shape type: " + type);
            return shape;
        }
        catch (IOException ioe){
            System.out.println("IOException occured");
            return null;
        }
    }
    public static void main(String[] args) {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));

            List<IShape> figures = new ArrayList<IShape>();

            System.out.println("Number of Shapes: ");
            String s = inputStream.readLine();
            int numberOfShapes = Integer.parseInt(s);

            for (int i = 0; i < numberOfShapes; i++) {
                System.out.println((i + 1) + " Shape type: ");
                String type = inputStream.readLine().toLowerCase();
                figures.add(newShape(type));
            }
            for (IShape f : figures) System.out.println(f.toString());

            double totalSquare = 0, totalLength = 0;
            for (IShape f : figures) {
                totalLength += f.length();
                totalSquare += f.square();
            }
            System.out.println("Total square of shapes: " + totalSquare);
            System.out.println("Total length of shapes: " + totalLength);
            System.out.println("Average square of a shape: " + totalSquare / numberOfShapes);


            System.out.println("Input ugly twins for preceding shapes ");
            for (int i = 0; i < numberOfShapes; i++) {
                IShape t = figures.get(i);
                System.out.println((i + 1) + " Shape type: " + t);
                IShape shape = null;
                if (t instanceof Segment) shape = newShape("segment");
                else if (t instanceof Polyline) shape = newShape("polyline");
                else if (t instanceof Circle) shape = newShape("circle");
                else if (t instanceof NGon) shape = newShape("ngon");
                else if (t instanceof QGon) shape = newShape("qgon");
                else if (t instanceof TGon) shape = newShape("tgon");
                else if (t instanceof Trapeze) shape = newShape("trapeze");
                else if (t instanceof Rectangle) shape = newShape("rectangle");
                System.out.println("Does the shape cross figure with the same index?");
                if (shape.cross(figures.get(i))) System.out.println("YES");
                else System.out.println("NO");
                System.out.println("Now its time to skrew these shapes. ");
                System.out.println("Movement type: ");
                String movementType = inputStream.readLine().toLowerCase();
                if (movementType.equals("shift")) {
                    double[] movementVector = new double[2];
                    System.out.println("Shift vector X coordinate: ");
                    movementVector[0] = Double.parseDouble(inputStream.readLine());
                    System.out.println("Shift vector Y coordinate: ");
                    movementVector[1] = Double.parseDouble(inputStream.readLine());
                    shape.shift(new Point2D(movementVector));
                } else if (movementType.equals("rot")) {
                    System.out.println("Rotation angle (counterclockwise): ");
                    double angle = Double.parseDouble(inputStream.readLine());
                    shape.rot(angle);
                } else if (movementType.equals("symaxis")) {
                    System.out.println("Axis index: ");
                    int axisOfSymmetryIndex = Integer.parseInt(inputStream.readLine());
                    shape.symAxis(axisOfSymmetryIndex);
                } else throw new NullPointerException("Such movement is not applicable");
                System.out.println("Movement parameters: ");
                System.out.println(shape.toString());
                System.out.println("Does the shifted shape cross figure with the same index?");
                if (shape.cross(figures.get(i))) System.out.println("YES");
                else System.out.println("NO");
            }
        }
        catch (IOException ioe){
            System.out.println("IOException occured");
        }
    }
}
