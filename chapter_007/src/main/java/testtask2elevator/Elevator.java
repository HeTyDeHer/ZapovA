package testtask2elevator;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Тестовое задание лифт.
 * Класс, описывающий поведение лифта.
 */
public class Elevator implements Runnable {
    /** Управление лифтом. */
    private final ElevatorControl control;
    /** Время на преодоление одного этажа. */
    private final int timeForOneFloor;
    /** Время ожидания на этаже с открытыми дверями. */
    private final int waitingTime;
    /** Монитор на ожидание команд лифтом. */
    private final Object monitorForCommandWaiting;
    /** Текущий этаж лифта. */
    private int currentFloor = 0;

    /**
     * Конструктор.
     * @param control Управление лифтом.
     * @param timeForOneFloor Время на преодоление одного этажа.
     * @param waitingTime Время ожидания на этаже с открытыми дверями.
     * @param monitorForCommandWaiting Монитор на ожидание команд лифтом.
     */
    public Elevator(ElevatorControl control, int timeForOneFloor, int waitingTime, Object monitorForCommandWaiting) {
        this.control = control;
        this.timeForOneFloor = timeForOneFloor;
        this.waitingTime = waitingTime;
        this.monitorForCommandWaiting = monitorForCommandWaiting;
    }

    @Override
    public void run() {
        System.out.println("Лифт запущен.");
        int destinationPoint;
        while (!Thread.currentThread().isInterrupted()) {
            while (control.getAllCommands().isEmpty()) {
                System.out.printf("Лифт ждет на %d этаже %n", currentFloor);
                synchronized (monitorForCommandWaiting) {
                    try {
                        monitorForCommandWaiting.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            if (control.getInsideCommands().isEmpty()) {
                destinationPoint = this.chooseDestinationFloor(control.getAllCommands());
            } else {
                destinationPoint = this.chooseDestinationFloor(control.getInsideCommands());
            }

            if (destinationPoint > currentFloor) {
                this.moveUp(destinationPoint);
            } else {
                this.moveDown(destinationPoint);
            }
        }
        System.out.println("Лифт остановлен.");
    }

    /**
     * Выбор этажа для движения после простоя.
     * @param commands Все запланированые точки движения.
     * @return этаж, на который едем.
     */
    private int chooseDestinationFloor(ConcurrentSkipListSet<Integer> commands) {
        int destinationFloor;
        if (commands.last() >= currentFloor) {
            destinationFloor = commands.last();
        } else {
            destinationFloor = commands.first();
        }
        return destinationFloor;
    }

    /**
     * Движение лифта вверх.
     * При движении вверх лифт не реагирует на вызовы снаружи, не останавливается в промежуточных точках.
     * @param destinationFloor этаж назначения.
     */
    private void moveUp(int destinationFloor) {
        for (; currentFloor < destinationFloor; currentFloor++) {
            System.out.printf("Лифт на %d этаже.%n", currentFloor);
            if (control.getInsideCommands().contains(currentFloor)) {
                this.openDoors();
            } else {
                try {
                    Thread.sleep(timeForOneFloor);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.openDoors();
    }

    /**
     * Движение лифта вниз.
     * При движении вниз лифт останавливается в промежуточных точках.
     * @param destinationFloor этаж назначения.
     */
    private void moveDown(int destinationFloor) {
        for (; currentFloor > destinationFloor; currentFloor--) {
            if (control.getAllCommands().contains(currentFloor)) {
                this.openDoors();
            } else {
                System.out.printf("Лифт на %d этаже.%n", currentFloor);
                try {
                    Thread.sleep(timeForOneFloor);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.openDoors();
    }

    /**
     * Открываем двери, жде муказанное время, закрываем двери.
     */
    private void openDoors() {
        System.out.printf("Лифт на %d этаже.%n", currentFloor);
        System.out.println("Двери открываются");
        try {
            Thread.sleep(timeForOneFloor + waitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        control.getAllCommands().remove(currentFloor);
        control.getInsideCommands().remove(currentFloor);
        System.out.println(control.getAllCommands());
        System.out.println("Двери закрываются");
    }
}
