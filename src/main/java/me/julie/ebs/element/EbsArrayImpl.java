package me.julie.ebs.element;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;
import me.julie.ebs.visitor.StringEbsVisitor;

import java.util.Objects;

class EbsArrayImpl<T extends EbsElement> extends ObjectArrayList<T> implements EbsArray<T> {
    private EbsType<T> arrayType;

    public EbsArrayImpl(int capacity, EbsType<T> arrayType) {
        super(capacity);
        this.arrayType = arrayType;
    }

    public EbsArrayImpl(int capacity) {
        super(capacity);
    }

    @Override
    public T set(int index, T t) {
        if (!isCorrectType(t, true)) {
            return null;
        }

        return super.set(index, Objects.requireNonNull(t, "Cannot add null item to EBS list"));
    }

    @Override
    public boolean add(T t) {
        if (!isCorrectType(t, true)) {
            return false;
        }

        return super.add(Objects.requireNonNull(t, "Cannot add null item to EBS list"));
    }

    @Override
    public void add(int index, T t) {
        if (!isCorrectType(t, true)) {
            return;
        }

        super.add(index, Objects.requireNonNull(t, "Cannot add null item to EBS list"));
    }

    @Override
    public EbsType<T> arrayType() {
        return arrayType;
    }

    @Override
    public EbsType<EbsArray> getType() {
        return EbsArrayType.getInstance();
    }

    @Override
    public EbsArrayImpl<T> clone() {
        EbsArrayImpl<T> result = new EbsArrayImpl<>(size, arrayType);

        for (T t: this) {
            result.add((T) t.clone());
        }

        return result;
    }

    @Override
    public EbsArray<T> deepClone() {
        EbsArray<T> arr = EbsElements.newArray(arrayType, size);

        for (var t: this) {
            arr.add((T) EbsElements.deepClone(t));
        }

        return arr;
    }

    @Override
    public EbsArray<T> merge(EbsArray<T> other) {
        addAll(other);
        return this;
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitArray(this);
    }

    @Override
    public boolean contains(Object o) {
        if (!isCorrectType(o, false)) {
            return false;
        }

        return super.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        if (!isCorrectType(o, false)) {
            return -1;
        }

        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (!isCorrectType(o, false)) {
            return -1;
        }

        return super.lastIndexOf(o);
    }

    private boolean isCorrectType(Object o, boolean modify) {
        if (!(o instanceof EbsElement e)) {
            return false;
        }

        return typeMatches(e.getType(), modify);
    }

    public boolean typeMatches(EbsType t, boolean modify) {
        if (arrayType() == null) {
            if (modify) {
                this.arrayType = t;
                return true;
            } else {
                return false;
            }
        }

        return arrayType().equals(t);
    }

    @Override
    public String toString() {
        return new StringEbsVisitor().visit(getClass().getSimpleName(), this);
    }
}