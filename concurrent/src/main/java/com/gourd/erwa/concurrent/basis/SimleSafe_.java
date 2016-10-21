package com.gourd.erwa.concurrent.basis;


/**
 * Created by IntelliJ IDEA.
 * User: wei.Li
 * Date: 14-8-6
 * Time: 16:32
 */
public class SimleSafe_ {

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            int i = 0;
            int k = 0;
            Count count = new Count(i++);

            @Override
            public void run() {
                System.out.println("Thread [" + k++ + "] run ...");
                count.demo();
            }
        };
        for (int j = 0; j < 100; j++) {
            new Thread(runnable).start();
        }
    }


}

class Count {
    private int num;
    private int objNum;

    Count(int objNum) {
        this.objNum = objNum;
    }

    void demo() {
        for (int i = 1; i <= 10000; i++) {
            num += i;
        }
        // Object objNum一值都是1，说明只有一个Count对象，保证多个线程共享一个Count对象。
        System.out.println(Thread.currentThread().getName() + " : " + num + " \t Object Number: " + objNum);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getObjNum() {
        return objNum;
    }

    public void setObjNum(int objNum) {
        this.objNum = objNum;
    }
}

/**
 * ===========
 * 可见性,有序性
 * ===========
 * 多个线程之间共享了Count类的一个对象，这个对象是被创建在主内存(堆内存)中，每个线程都有自己的工作内存(线程 栈)，
 * 工作内存存储了主内存Count对象的一个副本，当线程操作Count对象时，首先从主内存复制Count对象到工作内存中，
 * 然后执行代码 count.demo()，改变了num值，最后用工作内存Count刷新主内存Count。
 * 当一个对象在多个内存中都存在副本时，如果一个内存修改了 共享变量，其它线程也应该能够看到被修改后的值，此为可见性。
 * 由上述可知，一个运算赋值操作并不是一个原子性操作，多个线程执行时，CPU对线程的调度是随机的，我们不知道当前程序被执行到哪步就切换到了下一个线程
 * 经典的例子即银行存取钱。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 * <p>
 * ===========
 * 特别说明：
 * ===========
 * 1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 * 但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 * 2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 * 即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 * 3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 * 但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 */

/**
 *  ===========
 *  特别说明：
 *  ===========
 *  1. 10个线程，可能一开始都从主内存中读取到count对象的num的值都是1并放到各自的线程栈的工作内存中，
 *      但是当线程1执行完并刷新结果到主内存以后，线程2会在进行具体的操作之前，会去清楚自己的工作内存并重新从主内存中读取新的变量num的值。
 *  2. 有序性可以简单的理解为，无论是A线程还是B线程先执行，都要保证有序，
 *      即A线程要么先执行完，再执行B线程，或者B线程先执行完，再执行A线程。即要么先取款，或者要么先存款。
 *  3. 这一点大家一定要注意：特性1是可见性，这是多个线程共享同一个资源时，多个线程天然具有的特性，
 *      但是特性2 即有序性并不是天然具有的，而是我们要通过相关的API来解决的问题，我们往往要确保线程的执行是有序的，或者说是互斥的，即一个线程执行时，不允许另一个线程执行。
 */
