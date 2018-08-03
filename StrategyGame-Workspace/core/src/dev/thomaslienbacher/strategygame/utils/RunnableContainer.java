package dev.thomaslienbacher.strategygame.utils;

/**
 * @author Thomas Lienbacher
 */
public abstract class RunnableContainer implements Runnable {
    protected Object[] refs;

    public RunnableContainer(Object... refs) {
        super();
        this.refs = refs;
    }
}
