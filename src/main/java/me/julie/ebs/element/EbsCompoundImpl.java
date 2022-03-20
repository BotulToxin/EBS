package me.julie.ebs.element;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.julie.ebs.type.EbsCompoundType;
import me.julie.ebs.type.EbsType;
import me.julie.ebs.visitor.EbsVisitor;
import me.julie.ebs.visitor.StringEbsVisitor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class EbsCompoundImpl implements EbsCompound {
    private final Map<String, EbsElement> entries;

    public EbsCompoundImpl(int size) {
        entries = new Object2ObjectOpenHashMap<>(size);
    }

    @Override
    public void accept(EbsVisitor visitor) {
        visitor.visitCompound(this);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public EbsElement get(String name) {
        return entries.get(Objects.requireNonNull(name, "get(String) called, name null"));
    }

    @Override
    public void put(String name, EbsElement element) {
        entries.put(
                Objects.requireNonNull(name, "put(String, EbsElement) called, name null"),
                Objects.requireNonNull(element, "Given element cannot be null")
        );
    }

    @Override
    public void putAll(EbsCompound compound) {
        entries.putAll(((EbsCompoundImpl) compound).entries);
    }

    @Override
    public void remove(String name) {
        entries.remove(Objects.requireNonNull(name, "remove(String) called, name null"));
    }

    @Override
    public Set<Map.Entry<String, EbsElement>> entrySet() {
        return entries.entrySet();
    }

    @Override
    public Set<String> keySet() {
        return entries.keySet();
    }

    @Override
    public Collection<EbsElement> values() {
        return entries.values();
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public EbsType<EbsCompound> getType() {
        return EbsCompoundType.getInstance();
    }

    @Override
    public EbsCompound clone() {
        EbsCompoundImpl result = new EbsCompoundImpl(entries.size());
        result.entries.putAll(entries);

        return result;
    }

    @Override
    public EbsCompound deepClone() {
        EbsCompoundImpl result = new EbsCompoundImpl(entries.size());

        for (Map.Entry<String, EbsElement> e: entrySet()) {
            result.put(e.getKey(), e.getValue().clone());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringEbsVisitor().visit(getClass().getSimpleName(), this);
    }
}
