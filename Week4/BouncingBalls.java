import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Random;

public class BouncingBalls {
    int width;
    int height;
    int N;
    double dt;

    Ball[] balls;

    public static void main(String[] args) {
        BouncingBalls sim = new BouncingBalls(100, 100, 100, 1);
        sim.start();
    }

    public BouncingBalls(int width, int height, int N, double dt) {
        this.width = width;
        this.height = height;
        this.N = N;
        this.dt = dt;
        this.balls = new Ball[N];
        for (int i = 0; i < this.N; i++) {
            balls[i] = new Ball(StdRandom.uniform(10, width - 10), StdRandom.uniform(10, height - 10),
                    StdRandom.uniform(-1.0, 1.0), StdRandom.uniform(-1.0, 1.0), 1, 1);
        }
    }

    public void start() {
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();
        while (true) {
            StdDraw.clear();
            for (Ball ball : balls) {
                ball.update(dt, width, height);
                ball.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}

class Ball {
    double x;
    double y;
    double vx;
    double vy;
    double r;
    double m;
    double count;

    Ball(double x, double y, double vx, double vy, double r, double m) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
        this.m = m;
    }

    void update(double dt, int width, int height) {
        double futureX = x + vx * dt;
        double futureY = y + vy * dt;
        if (!(r < futureX && futureX < width - r)) { vx *= -1; }
        if (!(r < futureY && futureY < height - r)) { vy *= -1; }
        x += vx * dt;
        y += vy * dt;
    }

    void draw() {
        StdDraw.filledCircle(x, y, r);
    }

    public double timeToHit(Ball that)
    {
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx = that.x - this.x, dy = that.y - this.y;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if( dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.r + that.r;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

//    public double timeToHitHorizontalWall() { }
//    public double timeToHitVerticalWall() { }

    public void bounceOff(Ball that)
    {
        double dx = that.x - this.x, dy = that.y - this.y;
        double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        double dist = this.r + that.r;
        double J = 2 * this.m * that.m * dvdr / ((this.m + that.m) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;
        this.vx += Jx / this.m;
        this.vy += Jy / this.m;
        that.vx -= Jx / that.m;
        that.vy -= Jy / that.m;
        this.count++;
        that.count++;
    }
    public void bounceOffVerticalWall() { }
    public void bounceOffHorizontalWall() { }
}