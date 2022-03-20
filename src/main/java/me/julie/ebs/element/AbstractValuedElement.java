package me.julie.ebs.element;

public abstract class AbstractValuedElement<T> implements EbsValuedElement<T> {
    protected final T value;

    public AbstractValuedElement(T value) {
        this.value = value;
    }

    @Override
    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public abstract AbstractValuedElement<T> clone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractValuedElement)) return false;

        AbstractValuedElement<?> element = (AbstractValuedElement<?>) o;

        return value.equals(element.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
