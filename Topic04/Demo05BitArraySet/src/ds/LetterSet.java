package ds;

/** A set of lowercase English letters: index 0 is 'a' and index 25 is 'z'. */
public class LetterSet extends BitArraySet<Character>
{
    public LetterSet()
    {
        super(26);
    }

    @Override
    protected int index(Character letter)
    {
        return letter - 'a';
    }

    @Override
    protected Character element(int index)
    {
        return (char) ('a' + index);
    }
}
