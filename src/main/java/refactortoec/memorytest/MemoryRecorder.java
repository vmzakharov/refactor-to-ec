package refactortoec.memorytest;

import org.eclipse.collections.api.block.function.primitive.IntFunction0;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.primitive.IntProcedure;

public class MemoryRecorder
{
    private static final int MAX_SIZE = 1_000_000;
    protected static final int REPORT_EVERY = 10_000;

    public void recordMemoryUsageInt(Object anObject, IntFunction0 sizeCalculator, IntProcedure adder)
    {
        this.attemptGcAndGetUsedMemory();
        this.attemptGcAndGetUsedMemory();

        long payloadSize = 0;
        long initialUsedMemory = this.getInitialUsedMemory();

        recordUsage(anObject, sizeCalculator.value(), initialUsedMemory, payloadSize);

        for (int i = 0; i < MAX_SIZE; i++)
        {
            payloadSize += 0;
            adder.accept(i);

            recordUsage(anObject, sizeCalculator.value(), initialUsedMemory, payloadSize);
        }

        System.out.println("Done: " + sizeCalculator.value() + ", " + payloadSize);
    }

    public void recordMemoryUsageString(Object anObject, IntFunction0 sizeCalculator, Procedure<String> adder)
    {
        this.attemptGcAndGetUsedMemory();
        this.attemptGcAndGetUsedMemory();

        long payloadSize = 0;
        long initialUsedMemory = this.getInitialUsedMemory();

        recordUsage(anObject, sizeCalculator.value(), initialUsedMemory, payloadSize);
        for (int i = 0; i < MAX_SIZE; i++)
        {
            String payload = String.valueOf(i);
            payloadSize += this.sizeOfString(payload);
            adder.accept(payload);
            recordUsage(anObject, sizeCalculator.value(), initialUsedMemory, payloadSize);
        }

        System.out.println("Done: " + sizeCalculator.value() + ", " + payloadSize);
    }

    protected long getInitialUsedMemory()
    {
        return attemptGcAndGetUsedMemory();
    }

    protected long sizeOfString(String aString)
    {
        // String stores an array of chars, assuming 2 bytes each
        // Empty array - 16 bytes (8 object header + 4 length + 4 rounded to be 8 aligned)
        // String object - 16 bytes (8 object header + 4 hash code + 4 array reference)
        // 47 = Array (16) + Object (24) + Round up to multiple 8 (7)h
        //
        return 8 * (((aString.length() * 2) + 32 + 7) / 8);
    }

    protected void recordUsage(Object aCollection,
                             int collectionSize,
                             long initialUsedMemory,
                             long payloadSize)
    {
        if (collectionSize % REPORT_EVERY == 0)
        {
            System.out.printf("%s %,d %,d\n",
                              aCollection.getClass().getSimpleName(),
                              collectionSize,
                              (attemptGcAndGetUsedMemory() - initialUsedMemory - payloadSize) / 1024);
        }
    }

    private long attemptGcAndGetUsedMemory()
    {
        System.gc();
        System.gc();
        System.gc();
        Thread.yield();
        return Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    }
}
