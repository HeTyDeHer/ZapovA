package ru.job4j;
/**
 * Решение задачи 3.2 /001 [#188].
 * Положение точки.
 * @author ZapovA
 * @since 14.9.17
*/
public class Point {
/**
* Координата x.
*/
   private int x;
/**
* На каждую переменную, что ли, JavaDoc comment нужен?.
*/
   private int y;
/**
* Set x,y.
* @param x - x.
* @param y - y.
*/
   public Point(int x, int y) {
      this.x = x;
      this.y = y;
  }
/**
 * Get x.
 * @return y.
*/
  public int getX() {
      return this.x;
  }
/**
 * Get y.
 *@return y.
*/
  public int getY() {
     return this.y;
  }

/**
 * Лежит ли точка на функции y=ax+b.
 * @param a - число a.
 * @param b - число b.
 * @return ответ.
*/
  public boolean isPointOnFunc(int a, int b) {
  return this.y == a * this.x + b;
  }
}