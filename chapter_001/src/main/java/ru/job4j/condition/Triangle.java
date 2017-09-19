package ru.job4j;
/**
 * Решение задачи 3.3 /001 [#9461].
 * Вычисление площади треугольника.
 * @author ZapovA
 * @since 14.9.17
*/
public class Triangle {
/**
* Точка А.
*/
	private Point a;
/**
* Точка В.
*/
    private Point b;
/**
* Точка С.
*/
    private Point c;
/**
 * Конструктор.
 * @param a первая точка.
 * @param b вторая точка.
 * @param c третья точка.
*/
   public Triangle(Point a, Point b, Point c) {
       this.a = a;
       this.b = b;
       this.c = c;
    }
/**
 * Расстояние между двумя точками.
 * @param left первая точка.
 * @param right вторая точка.
 * @return расстояние.
*/
	public double distance(Point left, Point right) {
    int ax = left.getX();
	int ay = left.getY();
	int bx = right.getX();
	int by = right.getY();
	return Math.sqrt(Math.pow((bx - ax), 2) + Math.pow((by - ay), 2));
	}
/**
 * Вычисление периметра треугольника.
 * @param ab расстояние ab.
 * @param ac расстояние ac.
 * @param bc расстояние bc.
 * @return периметр.
*/
	public double period(double ab, double ac, double bc) {
    return (ab + ac + bc);
	}
/**
 * Вычисление площади треугольника.
 * @return площадь, если треугольник существует. В противном случае -1.
*/
	public double area() {
    double rsl = -1;
    double ab = this.distance(this.a, this.b);
    double ac = this.distance(this.a, this.c);
    double bc = this.distance(this.b, this.c);
    double p = this.period(ab, ac, bc) / 2;
    if (this.exist(ab, ac, bc)) {
        rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
		}
	return rsl;
	}
/**
 * Существует ли треугольник (сумма любых 2 сторон > третьей).
 * @param ab расстояние ab.
 * @param ac расстояние ac.
 * @param bc расстояние bc.
 * @return да/нет.
*/
	private boolean exist(double ab, double ac, double bc) {
    return ((ab + ac) > bc && (ab + bc) > ac && (ac + bc) > ab);
	}
}