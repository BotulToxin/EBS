package me.julie.ebs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import me.julie.ebs.element.*;
import me.julie.ebs.type.EbsNumberType;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class EbsOps implements DynamicOps<EbsElement> {
    private EbsOps() {}

    public static final EbsOps INSTANCE = new EbsOps();

    @Override
    public EbsElement empty() {
        return null;
    }

    @Override
    public <U> U convertTo(DynamicOps<U> outOps, EbsElement input) {
        if (input instanceof EbsString string) {
            return outOps.createString(string.value());
        }

        if (input instanceof EbsBoolean bool) {
            return outOps.createBoolean(bool.value());
        }

        if (input instanceof EbsNumber number) {
            Number val = number.value();

            if (val instanceof Byte b) {
                return outOps.createByte(b);
            }

            if (val instanceof Short s) {
                return outOps.createShort(s);
            }

            if (val instanceof Integer i) {
                return outOps.createInt(i);
            }

            if (val instanceof Float f) {
                return outOps.createFloat(f);
            }

            if (val instanceof Double d) {
                return outOps.createDouble(d);
            }

            if (val instanceof Long l) {
                return outOps.createLong(l);
            }

            if (val instanceof BigInteger integer) {
                return outOps.createByteList(
                        ByteBuffer.wrap(integer.toByteArray())
                );
            }

            return outOps.createNumeric(val);
        }

        if (input instanceof EbsUUID uuid) {
            return outOps.createString(uuid.toString());
        }

        if (input instanceof EbsArray<?> array) {
            return convertList(outOps, array);
        }

        if (input instanceof EbsCompound compound) {
            return convertMap(outOps, compound);
        }

        return null;
    }

    @Override
    public DataResult<Boolean> getBooleanValue(EbsElement input) {
        if (input instanceof EbsNumber num && num.getType() == EbsNumberType.BYTE) {
            return DataResult.success(num.byteValue() != 0);
        }

        if (input instanceof EbsBoolean bool) {
            return DataResult.success(bool.value());
        }

        return DataResult.error("Input is not a boolean: " + input);
    }

    @Override
    public EbsElement createBoolean(boolean value) {
        return EbsElements.of(value);
    }

    @Override
    public DataResult<Number> getNumberValue(EbsElement input) {
        if (input instanceof EbsNumber number) {
            return DataResult.success(number);
        }

        return DataResult.error("Input is not a number type");
    }

    @Override
    public EbsNumber createNumeric(Number i) {
        return EbsElements.of(i);
    }

    @Override
    public DataResult<String> getStringValue(EbsElement input) {
        if (input instanceof EbsString string) {
            return DataResult.success(string.value());
        }

        return DataResult.error("Input is not a string: " + input);
    }

    @Override
    public EbsString createString(String value) {
        return EbsElements.of(value);
    }

    @Override
    public DataResult<EbsElement> mergeToList(EbsElement list, EbsElement value) {
        if (list instanceof EbsArray) {
            EbsArray result = EbsElements.newArray();
            result.add(value);

            return DataResult.success(result);
        }

        return DataResult.error("Input is not a list: " + list);
    }

    @Override
    public DataResult<EbsElement> mergeToMap(EbsElement map, EbsElement key, EbsElement value) {
        if (!(map instanceof EbsCompound compound)) {
            return DataResult.error("Input was not compound: " + map);
        }

        if (!(key instanceof EbsString string)) {
            return DataResult.error("Key was not string: " + key);
        }

        EbsCompound result = compound.deepClone();
        result.put(string.value(), value);
        return DataResult.success(result);
    }

    @Override
    public DataResult<Stream<Pair<EbsElement, EbsElement>>> getMapValues(EbsElement input) {
        if (input instanceof EbsCompound compound) {
            return DataResult.success(
                    compound.entrySet()
                            .stream()
                            .map(entry -> Pair.of(createString(entry.getKey()), entry.getValue()))
            );
        }

        return DataResult.error("Input is not a compound map");
    }

    @Override
    public EbsCompound createMap(Stream<Pair<EbsElement, EbsElement>> map) {
        EbsCompound compound = EbsElements.newCompound();

        map.forEach(pair -> {
            String s = pair.getFirst().toString();
            compound.put(s, pair.getSecond());
        });

        return compound;
    }

    @Override
    public DataResult<Stream<EbsElement>> getStream(EbsElement input) {
        if (input instanceof EbsArray arr) {
            return DataResult.success(arr.stream());
        }

        return DataResult.error("Input is not an array");
    }

    @Override
    public EbsArray<EbsElement> createList(Stream<EbsElement> input) {
        return input.collect(EbsElements.toEbsArray());
    }

    @Override
    public EbsElement remove(EbsElement input, String key) {
        if (input instanceof EbsCompound compound) {
            EbsCompound result = EbsElements.newCompound(compound.size());

            compound.entrySet().stream()
                    .filter(entry -> entry.getKey().equals(key))
                    .forEach(entry -> result.put(entry.getKey(), entry.getValue()));

            return result;
        }

        return input;
    }

    @Override
    public EbsArray<EbsNumber> createIntList(IntStream input) {
        EbsArray<EbsNumber> numbers = EbsElements.newArray(EbsNumberType.INTEGER);
        input.forEach(value -> numbers.add(EbsElements.of(value)));

        return numbers;
    }

    @Override
    public EbsArray<EbsNumber> createByteList(ByteBuffer input) {
        EbsArray<EbsNumber> numbers = EbsElements.newArray(EbsNumberType.BYTE);

        while (input.hasRemaining()) {
            numbers.add(EbsElements.of(input.get()));
        }

        return numbers;
    }

    @Override
    public EbsArray<EbsNumber> createLongList(LongStream input) {
        EbsArray<EbsNumber> numbers = EbsElements.newArray(EbsNumberType.LONG);
        input.forEach(value -> numbers.add(EbsElements.of(value)));

        return numbers;
    }

    @Override
    public EbsElement createMap(Map<EbsElement, EbsElement> map) {
        EbsCompound compound = EbsElements.newCompound(map.size());
        map.forEach((element, element2) -> compound.put(element.toString(), element2));
        return compound;
    }
}