package org.akrasnyansky.typetest;

public class QueuedRemove extends QueuedEditEvent {

    private final int offset;
    private final int length;

    public QueuedRemove (int p_offset, int p_length) {
        offset = p_offset;
        length = p_length;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    protected <V extends QueuedEditEvent> V innerMerge(V o_other) {
        QueuedRemove next = (QueuedRemove) o_other;
        // На этом работы были приостановлены
        // На этом были приостановлены | remove (8, 7) this
        // были приостановлены | remove (0, 8) next
        if (next.offset + next.length == this.offset) {
            return (V) new QueuedRemove(next.offset, next.length + this.length);
        }

        // На этом работы были приостановлены
        // На этом работы приостановлены | remove (15, 5) this
        // На этом работы остановлены | remove (15, 3) next
        if (this.offset == next.offset) {
            return (V) new QueuedRemove(this.offset, next.length + this.length);
        }

        return null;
    }
}
