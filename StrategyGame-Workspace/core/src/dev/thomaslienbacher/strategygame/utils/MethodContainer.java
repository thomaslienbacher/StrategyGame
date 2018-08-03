package dev.thomaslienbacher.strategygame.utils;

/**
 * @author Thomas Lienbacher
 */
public abstract class MethodContainer {
    public Object[] refs;

    public MethodContainer(Object... refs){
        this.refs = refs;
    }

    public abstract void method();
}
