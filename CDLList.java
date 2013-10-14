/*
 * ssoni2@buffalo.edu
 * 50096364
 */
public class CDLList<T> {
	public Element head;

	// Create a CDLList with one value
	public CDLList(T v) {
		Element first = new Element(v); // creating new element
		first.next = first; // Making circular list my pointing itself
		first.previous = first;
		this.head = first; // assigning head
	}

	// If deleted an Element becomes invalid. Any access to an invalid element
	// throws an exception
	public class Element {
		public T data; // Element data value
		public Element next; // pointer for --> next element
		public Element previous; // pointer for <-- previous element
		public int lock;
		public RWLock rwLock;

		public Element(T data) { // Constructor to assign data to element
			this.data = data;
			next = null; // not required but for clear understanding
			previous = null; // not required but for clear understanding
			lock = 0;
			rwLock = new RWLock();
		}

		// return the element’s value
		public T value() {
			return data;
		}

		// extra method to display data
		public void displayData() {
			System.out.println("Element data : " + data);
		}
	}

	// Return the head of the list. Never null
	public Element head() {
		return this.head;
	}

	// Return a cursor at element from in the list
	public Cursor reader(Element from) {
		return new Cursor(from);
	}

	// A cursor on an invalid element throws an exception.
	public class Cursor {
		public Element current;

		public Cursor(Element from) {
			this.current = from;
		}

		// Return the current element.
		public Element current() {
			return current;
		}

		// Move to the previous element.
		public void previous() throws Exception {
			if (current.previous == null) {
				throw new Exception("Invalid Cursor");
			}
			current = current.previous;
		}

		// Move to the next element
		public void next() throws Exception {
			if (current.next == null) {
				throw new Exception("Invalid Cursor");

			}
			current = current.next;
		}

		// Returns a writer at the current element
		public Writer writer() {
			return new Writer(this.current);
		}
	}

	// A cursor on an invalid element throws an exception.
	public class Writer {
		public Element writer;

		public Writer(Element element) {
			this.writer = element;
		}

		// Delete the current element and becomes invalid.
		public boolean delete() throws Exception {

			if (this.writer.next == null || this.writer.previous == null) {
				// System.out
				// .println("Not deleted. Invalid Element. Doesnot exist in the list");
				throw new Exception(
						"Not deleted. Invalid Element. Doesnot exist in the list");
			} else if (this.writer.next == this.writer) {
				// System.out.println("Can't Delete. Only single element left.");
				throw new Exception("Can't Delete. Only single element left.");

			} else {
				this.writer.previous.next = this.writer.next;
				this.writer.next.previous = this.writer.previous;
				if (head == this.writer) {
					head = this.writer.next;
					System.out.println("head moved");
				}
				System.out.print("Deleted------");
				this.writer.displayData();
				this.writer.previous = null;
				this.writer.next = null;
				return true;
			}

		}

		// Add before the current element.
		public boolean insertBefore(T val) throws Exception {

			if (this.writer.next == null || this.writer.previous == null) {
				System.out.println("Invalid Writer !!!");
				throw new Exception("Invalid Writer !!!");
			}
			Element insert = new Element(val);
			insert.next = this.writer;
			insert.previous = this.writer.previous;
			this.writer.previous.next = insert;
			this.writer.previous = insert;
			return true;

		}

		// Add after the current element.
		public boolean insertAfter(T val) throws Exception {

			if (this.writer.next == null || this.writer.previous == null) {
				System.out.println("Invalid Writer !!!");
				throw new Exception("Invalid Writer !!!");
			}
			Element insert = new Element(val);
			insert.previous = this.writer;
			insert.next = this.writer.next;
			this.writer.next.previous = insert;
			this.writer.next = insert;

			return true;
		}

	}

	// Aditional method to display list element
	public void display() {
		System.out.println("Forward --------");
		Element display = head;
		display.displayData();
		display = display.next;
		int size = 1;
		while (display.next != head.next) {
			size++;
			display.displayData();
			display = display.next;
		}
		System.out.println("Backward -------");
		display = head;
		display.displayData();
		display = display.previous;
		while (display.previous != head.previous) {
			display.displayData();
			display = display.previous;
		}
		System.out.println(size);
	}
}
