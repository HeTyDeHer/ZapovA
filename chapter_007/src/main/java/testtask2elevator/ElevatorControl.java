package testtask2elevator;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Тестовое задание лифт.
 * Запуск и управление лифтом.
 */
public class ElevatorControl {
    /** Все запланированые точки движения. */
    private final ConcurrentSkipListSet<Integer> allCommands = new ConcurrentSkipListSet<>();
    /** Точки движения, переданные из кабины лифта. Имеют больший приоритет. */
    private final ConcurrentSkipListSet<Integer> insideCommands = new ConcurrentSkipListSet<>();
    /** Ввод данных. */
    private final Input input = new ManualInput();
    /** МОнитор на ожидание команд лифтом. */
    private final Object monitorForCommandWaiting = new Object();

    /**
     * Запуск лифта.
     * @param elevatorControl управление.
     * @param speed скорость лифта, м/c.
     * @param floorHeight высота этажа, м.
     */
    private void startElevator(ElevatorControl elevatorControl, double speed, int floorHeight) {
        Thread elevatorThread = new Thread(new Elevator(elevatorControl, (int)(floorHeight / speed * 1000), 1000, monitorForCommandWaiting));
        elevatorThread.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean inside;
        while (elevatorThread.isAlive()) {
            int from;
            inside = (from = input.askFrom()) == 1;
            this.pressButton(inside);
        }
    }

    /**
     * Выбор этажа.
     * @param inside команда получена с внутренней панели или снаружи.
     */
    private void pressButton(boolean inside) {
        int destinationPoint = input.askWhere();
        allCommands.add(destinationPoint);
        if (inside) {
            insideCommands.add(destinationPoint);
        }
        synchronized (monitorForCommandWaiting) {
            monitorForCommandWaiting.notify();
        }
    }

    public static void main(String[] args) {
        ElevatorControl elevatorControl = new ElevatorControl();
        elevatorControl.startElevator(elevatorControl, 1.5, 3);
    }

    /**
     * Геттер.
     * @return Set с командами изнутри.
     */
    public ConcurrentSkipListSet<Integer> getInsideCommands() {
        return insideCommands;
    }

    /**
     * Геттер.
     * @return Set с командами снаружи.
     */
    public ConcurrentSkipListSet<Integer> getAllCommands() {
        return allCommands;
    }

}
