package me.julie.ebs.element;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.julie.ebs.type.EbsArrayType;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;
import me.julie.ebs.visitor.StringEbsVisitor;

import java.util.Objects;

class EbsArrayImpl<T extends EbsElement> extends ObjectArrayList<T> implements EbsArray<T> {
    private final EbsType arrayType;

    public EbsArrayImpl(int capacity, EbsType arrayType) {
        super(capacity);
        this.arrayType = arrayType;
    }

    @Override
    public T set(int index, T t) {
        return super.set(index, Objects.requireNonNull(t, "Cannot add null item to EBS list"));
    }

    @Override
    public boolean add(T t) {
        return super.add(Objects.requireNonNull(t, "Cannot add null item to EBS list"));
    }

    @Override
    public void add(int index, T t) {
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
    public void accept(EbsVisitor visitor) {
        visitor.visitArray(this);
    }

    @Override
    public boolean contains(Object o) {
        if(!isCorrectType(o)) return false;
        return super.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        if(!isCorrectType(o)) return -1;
        return super.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if(!isCorrectType(o)) return -1;
        return super.lastIndexOf(o);
    }

    private boolean isCorrectType(Object o) {
        if(!(o instanceof EbsElement e)) return false;
        return e.getType().equals(arrayType());
    }

    @Override
    public String toString() {
        return new StringEbsVisitor().visit(getClass().getSimpleName(), this);
    }
}
