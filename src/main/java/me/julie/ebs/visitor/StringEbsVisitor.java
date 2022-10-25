package me.julie.ebs.visitor;

import me.julie.ebs.element.*;

import java.util.Iterator;
import java.util.Map;

public class StringEbsVisitor implements EbsVisitor {
    private static final char
            ARR_START = '[',
            ARR_END = ']',
            OBJ_START = '{',
            OBJ_END = '}',
            VAL_SEPARATOR = ',',
            KEY_VAL_SEPARATOR = '=';

    public static boolean TO_STRING_IS_FORMATTED = false;
    public static int INDENT_CHANGE = 2;

    private final StringBuilder builder;
    private final boolean allowFormatting;
    private boolean topLevelElement = true;

    private int currentIndent;

    public StringEbsVisitor() {
        this(new StringBuilder(), TO_STRING_IS_FORMATTED);
    }

    public StringEbsVisitor(StringBuilder builder, boolean allowFormatting) {
        this.builder = builder;
        this.allowFormatting = allowFormatting;
    }

    public String visit(String prefix, EbsElement element) {
        if (prefix != null && !prefix.isEmpty()) {
            write(prefix);
            write(KEY_VAL_SEPARATOR);
            write(' ');
        }

        element.accept(this);
        return builder.toString();
    }

    private void write(String s) {
        builder.append(s);
    }

    private void write(char c) {
        builder.append(c);
    }

    private void newLine() {
        if (!allowFormatting) {
            return;
        }
        write("\n");
    }

    private void addIndent() {
        if (!allowFormatting) {
            return;
        }
        currentIndent += INDENT_CHANGE;
    }

    private void removeIndent() {
        if (!allowFormatting) {
            return;
        }

        currentIndent -= INDENT_CHANGE;
    }

    private void writeIndent() {
        if (!allowFormatting) {
            return;
        }

        write(" ".repeat(currentIndent));
    }

    @Override
    public void visitUnknown(EbsElement element) {
        write(element.toString());
    }

    @Override
    public void visitArray(EbsArray array) {
        EbsArray<EbsElement> arr = array;

        write(ARR_START);

        if (!arr.isEmpty()) {
            addIndent();
            newLine();
            writeIndent();

            Iterator<EbsElement> iterator = arr.iterator();

            while (iterator.hasNext()) {
                EbsElement element = iterator.next();
                element.accept(this);

                if (iterator.hasNext()) {
                    write(VAL_SEPARATOR);
                } else {
                    removeIndent();
                }

                newLine();
                writeIndent();
            }
        }

        write(ARR_END);
    }

    @Override
    public void visitCompound(EbsCompound compound) {
        final boolean topLevel = topLevelElement && allowFormatting;
        topLevelElement = false;

        if (!topLevel) {
            write(OBJ_START);
        }

        if (!compound.isEmpty()) {
            newLine();

            if (!topLevel) {
                addIndent();
            }

            writeIndent();

            Iterator<Map.Entry<String, EbsElement>> iterator = compound.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, EbsElement> e = iterator.next();
                write(e.getKey());

                if (!allowFormatting || (!(e.getValue() instanceof EbsCompound)
                        && !(e.getValue() instanceof EbsArray<?>))
                ) {
                    if (allowFormatting) {
                        write(' ');
                    }

                    write(KEY_VAL_SEPARATOR);
                }

                if (allowFormatting) {
                    write(' ');
                }

                e.getValue().accept(this);

                if (iterator.hasNext()) {
                    if (!allowFormatting) {
                        write(VAL_SEPARATOR);
                        write(' ');
                    }
                } else if (!topLevel) {
                    removeIndent();
                }

                newLine();
                writeIndent();
            }
        }

        if (!topLevel) {
            write(OBJ_END);
        }
    }

    @Override
    public void visitNumber(EbsNumber number) {
        String prefix = switch (number.getType()) {
            case BYTE -> "b";
            case SHORT -> "s";
            case INTEGER -> "";
            case FLOAT -> "f";
            case DOUBLE -> "d";
            case LONG -> "l";
            case BIG_INTEGER -> "bi";
        };

        write(number.toString());
        write(prefix);
    }

    @Override
    public void visitUUID(EbsUUID uuid) {
        write(uuid.value().toString());
    }

    @Override
    public void visitString(EbsString string) {
        write('"');
        write(string.value());
        write('"');
    }

    @Override
    public void visitBool(EbsBoolean bool) {
        write(bool.toString());
    }
}