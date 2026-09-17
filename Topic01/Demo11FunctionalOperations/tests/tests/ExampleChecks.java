package tests;

import ds.FunctionalOperations;

public final class ExampleChecks
{
    private static int checks;

    public static void main(String[] args)
    {
        StringBuilder output = new StringBuilder();
        FunctionalOperations.process(new Integer[] {1, 2, 3, 4}, x -> x % 2 == 0,
                x -> "[" + x + "]", text -> output.append(text));
        equal("[2][4]", output.toString());

        output.setLength(0);
        FunctionalOperations.process(new Integer[] {1, 2, 3}, x -> x > 1,
                x -> "[" + x * x + "]", text -> output.append(text));
        equal("[4][9]", output.toString());

        output.setLength(0);
        FunctionalOperations.process(new Integer[0], x -> true,
                x -> x.toString(), text -> output.append(text));
        equal("", output.toString());

        FunctionalOperations.process(new Integer[] {1, 3}, x -> x % 2 == 0,
                ExampleChecks::unexpectedTransform, text -> output.append(text));
        equal("", output.toString());
        System.out.println("All " + checks + " checks passed.");
    }

    private static String unexpectedTransform(Integer value)
    {
        throw new AssertionError("Rejected values must not be transformed: " + value);
    }

    private static void equal(String expected, String actual)
    {
        checks++;
        if (!expected.equals(actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }
}
