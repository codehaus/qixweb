package org.qixweb.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import org.qixweb.block.*;
import org.qixweb.util.CollectionUtil;

public abstract class WebNode implements Browsable
{
    public QixwebUrl[] connections()
    {
        ArrayList connections = new ArrayList();

        collectResultsFromMethodsReturning_Into(QixwebUrl.class, connections);
        collectResultsFromMethodsReturning_Into(QixwebUrl[].class, connections);
        collectWebUrlsOfMethodsReturningIteratorInto(connections);
        collectContainedObjectsOfType_FromMethodsReturningListInto(QixwebUrl.class, connections);
        collectWebUrlsOfMethodsReturningArrayOfIteratorsInto(connections);
        collectWebUrlsOfMethodsReturningFormInto(connections);

        return (QixwebUrl[]) CollectionUtil.toArray(connections, QixwebUrl.class);
    }

    private void collectWebUrlsOfMethodsReturningIteratorInto(ArrayList list)
    {
        Method[] publicMethods = getClass().getMethods();
        Method[] matchingMethods = (Method[]) LightInternalIterator.createOn(publicMethods).select(new Predicate()
        {
            public boolean is(Object each)
            {
                Method method = (Method) each;
                return method.getReturnType().equals(Iterator.class) && method.getParameterTypes().length == 0;
            }
        }, Method.class);
        Iterator[] matchingIterators = (Iterator[]) executeVoidParameterMethods(matchingMethods, Iterator.class);
        collectWebUrlsIteratingOver_into(matchingIterators, list);
    }

    protected void collectContainedObjectsOfType_FromMethodsReturningListInto(Class clazz, ArrayList list)
    {
        Method[] publicMethods = getClass().getMethods();
        Method[] matchingMethods = (Method[]) LightInternalIterator.createOn(publicMethods).select(new Predicate()
        {
            public boolean is(Object each)
            {
                Method method = (Method) each;
                return List.class.isAssignableFrom(method.getReturnType()) && method.getParameterTypes().length == 0;
            }
        }, Method.class);
        List[] resultsOfMatchingMethods = (List[]) executeVoidParameterMethods(matchingMethods, List.class);
        collectObjectsOfType_IteratingOver_into(clazz, resultsOfMatchingMethods, list);
    }

    private void collectObjectsOfType_IteratingOver_into(final Class clazz, List[] matchingList, ArrayList list)
    {
        Object[] elementsToAdd = CollectionUtil.flatWithoutNulls(LightInternalIterator.createOn(matchingList).collect(new Function()
        {
            public Object eval(Object each)
            {
                return selectOnlyObjectsOfType_From(clazz, (List) each);
            }
        }, Object[].class), Object.class);
        list.addAll(CollectionUtil.toArrayList(elementsToAdd));
    }

    private Object[] selectOnlyObjectsOfType_From(final Class clazz, List list)
    {
        return LightInternalIterator.createOn(list.iterator()).select(new Predicate()
        {
            public boolean is(Object each)
            {
                return clazz.isAssignableFrom(each.getClass());
            }
        }, clazz);
    }

    private void collectWebUrlsOfMethodsReturningFormInto(ArrayList list)
    {
        Method[] publicMethods = getClass().getMethods();
        Method[] matchingMethods = (Method[]) LightInternalIterator.createOn(publicMethods).select(new Predicate()
        {
            public boolean is(Object each)
            {
                Method method = (Method) each;
                return WebForm.class.isAssignableFrom(method.getReturnType()) && method.getParameterTypes().length == 0;
            }
        }, Method.class);
        WebForm[] matchingWebForms = (WebForm[]) executeVoidParameterMethods(matchingMethods, WebForm.class);
        List allMatchingWebForms = LightInternalIterator.createOn(matchingWebForms).collectAsList(new Function()
        {
            public Object eval(Object each)
            {
                WebForm form = (WebForm) each;
                return form.actionUrl();
            }
        }, QixwebUrl.class);

        list.addAll(allMatchingWebForms);
    }

    private void collectWebUrlsIteratingOver_into(Iterator[] matchingIterators, ArrayList list)
    {
        list.addAll(CollectionUtil.toArrayList(CollectionUtil.flatWithoutNulls(LightInternalIterator.createOn(matchingIterators).collect(new Function()
        {
            public Object eval(Object each)
            {
                return selectOnlyWebUrlsFrom((Iterator) each);
            }
        }, Object[].class), Object.class)));
    }

    private void collectWebUrlsOfMethodsReturningArrayOfIteratorsInto(ArrayList list)
    {
        Method[] publicMethods = getClass().getMethods();
        Method[] matchingMethods = (Method[]) LightInternalIterator.createOn(publicMethods).select(new Predicate()
        {
            public boolean is(Object each)
            {
                Method method = (Method) each;
                return method.getReturnType().equals(Iterator[].class);
            }
        }, Method.class);
        Iterator[] matchingIterators = (Iterator[]) CollectionUtil.flatWithoutNulls(executeVoidParameterMethods(matchingMethods, Iterator[].class), Iterator.class);
        collectWebUrlsIteratingOver_into(matchingIterators, list);
    }

    private Object[] selectOnlyWebUrlsFrom(Iterator iterator)
    {
        return LightInternalIterator.createOn(iterator).select(new Predicate()
        {
            public boolean is(Object each)
            {
                return each instanceof QixwebUrl;
            }
        }, QixwebUrl.class);
    }

    private void collectResultsFromMethodsReturning_Into(final Class aClazz, final ArrayList list)
    {
        Method[] publicMethods = getClass().getMethods();
        Method[] matchingMethods = (Method[]) LightInternalIterator.createOn(publicMethods).select(new Predicate()
        {
            public boolean is(Object each)
            {
                Method method = (Method) each;
                return aClazz.isAssignableFrom(method.getReturnType()) && method.getParameterTypes().length == 0 && !Modifier.isStatic(method.getModifiers()) && !method.getName().equals("connections");
            }
        }, Method.class);
        list.addAll(CollectionUtil.toArrayList(CollectionUtil.flatWithoutNulls(executeVoidParameterMethods(matchingMethods, aClazz), Object.class)));
    }

    private Object[] executeVoidParameterMethods(Method[] someVoidParameterMethods, Class aClazz)
    {
        return LightInternalIterator.createOn(someVoidParameterMethods).collect(new Function()
        {
            public Object eval(Object each)
            {
                Method method = (Method) each;
                try
                {
                    return method.invoke(WebNode.this, new Object[0]);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }, aClazz);
    }

    public void displayThrough(ResponseHandler aResponseHandler) throws IOException
    {
        aResponseHandler.display(this);
    }
}
