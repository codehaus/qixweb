package org.qixweb.core;

import java.util.Collections;
import java.util.Comparator;

import org.qixweb.block.*;

public class MultipleChoices extends AbstractChoice
{
    public MultipleChoices(String aName, boolean isEnabled)
    {
        super(aName, isEnabled);
    }

    public void add(Choice aChoice)
    {
        choices().add(aChoice);
    }

    public void sortByLabel()
    {
        Collections.sort(choices());
    }

    public void customSortByLabel(Comparator aComparator)
    {
        Collections.sort(choices(), aComparator);
    }

    public void sortByKey()
    {
        Collections.sort(choices(), onKey());
    }

    public Choice selectedChoice()
    {
        return (Choice) LightInternalIterator.createOn(choices()).detect(new Predicate()
        {
            public boolean is(Object each)
            {
                return ((Choice) each).isSelected().booleanValue();
            }
        });
    }

    public void selectExclusivelyBy(final Comparable anItem)
    {
        LightInternalIterator.createOn(choices()).forEach(new Procedure()
        {
            public void run(Object each)
            {
                Choice choice = (Choice) each;
                if (choice.item().compareTo(anItem) == 0)
                    choice.select();
                else
                    choice.deselect();
            }
        });
    }

    public void addOnTop(Choice aChoice)
    {
        choices().add(0, aChoice);
    }

    public static final Comparator caseInsensitiveOrderOnStringItem()
    {
        return new Comparator()
        {

            public int compare(Object o1, Object o2)
            {
                Choice choice1 = (Choice) o1;
                Choice choice2 = (Choice) o2;

                String string1 = (String) choice1.item();
                String string2 = (String) choice2.item();
                return string1.toLowerCase().compareTo(string2.toLowerCase());

            }
        };
    }

    private Comparator onKey()
    {
        return new Comparator()
        {

            public int compare(Object o1, Object o2)
            {
                Choice choice1 = (Choice) o1;
                Choice choice2 = (Choice) o2;

                String string1 = choice1.value();
                String string2 = choice2.value();
                return string1.compareTo(string2);

            }
        };
    }

}
