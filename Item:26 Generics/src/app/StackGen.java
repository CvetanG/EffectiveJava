package app;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;

// Initial attempt to generify Stack = won’t compile!
public class StackGen<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	// The elements array will contain only E instances from push(E).
	// This is sufficient to ensure type safety, but the runtime
	// type of the array won't be E[]; it will always be Object[]!
	@SuppressWarnings("unchecked")
	public StackGen() {
		// elements = new E[DEFAULT_INITIAL_CAPACITY]; // generic array creation
		elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY]; // unchecked cast
																// without
																// @SuppressWarnings
	}

	public void push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop() {
		if (size == 0)
			throw new EmptyStackException();
		E result = elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void ensureCapacity() {
		if (elements.length == size)
			elements = Arrays.copyOf(elements, 2 * size + 1);
	}

	// Generic method
	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}

	// Little program to exercise our generic Stack
	public static void main(String[] args) {
		StackGen<String> stack = new StackGen<String>();
		for (String arg : args)
			stack.push(arg);
		while (!stack.isEmpty())
			System.out.println(stack.pop().toUpperCase());

		// Simple program to exercise generic method
		Set<String> guys = new HashSet<String>(Arrays.asList("Tom", "Dick", "Harry"));
		Set<String> stooges = new HashSet<String>(Arrays.asList("Larry", "Moe", "Curly"));
		Set<String> aflCio = union(guys, stooges);
		System.out.println(aflCio);
	}
}
