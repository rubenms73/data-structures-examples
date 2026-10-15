package ds;

/** Algorithms whose work is discussed by the client. */
public final class CostAlgorithms
{
    private CostAlgorithms()
    {
    }

    public static void sumAndCount(int[] values)
    {
        int total = 0;
        int additions = 0;
        for (int value : values)
        {
            total += value;
            additions++;
        }
        System.out.println("n = " + values.length + ": sum = " + total
                + ", additions = " + additions);
    }

    /** Requires a nonempty array. */
    public static String middle(String[] values)
    {
        return values[values.length / 2];
    }
}
