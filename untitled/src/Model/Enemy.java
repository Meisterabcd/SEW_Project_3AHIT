package Model;

public class Enemy {
    private double x;
    private double y;

    private int currentTargetIndex = 1;

    private double speed; // px pro Sekunde

    public Enemy(double startX, double startY,int speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
    }

    public void update(double deltaTime, java.util.List<Double> xPoints, java.util.List<Double> yPoints) {
        if (currentTargetIndex >= xPoints.size()){

            return;
        }

        double targetX = xPoints.get(currentTargetIndex)-10;
        double targetY = yPoints.get(currentTargetIndex)-10;

        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1) {
            currentTargetIndex++;
            return;
        }

        double dirX = dx / distance;
        double dirY = dy / distance;

        double move = speed * deltaTime;

        x += dirX * move;
        y += dirY * move;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}